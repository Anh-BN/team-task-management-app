import 'package:flutter/foundation.dart';
import 'package:dio/dio.dart';

import '../core/storage/token_storage.dart';
import '../models/user_model.dart';
import '../services/auth_service.dart';

class AuthController extends ChangeNotifier {
  final AuthService _authService = AuthService();
  final TokenStorage _tokenStorage = TokenStorage();

  UserModel? currentUser;
  bool loading = false;
  String? errorMessage;

  Future<bool> login(String email, String password) async {
    loading = true;
    errorMessage = null;
    notifyListeners();
    try {
      final result = await _authService.login(email.trim(), password);
      await _tokenStorage.saveTokens(result.accessToken, result.refreshToken);
      currentUser = result.user;
      return true;
    } on DioException catch (e) {
      final message = e.response?.data is Map ? e.response?.data['message'] : null;
      errorMessage = message?.toString() ?? 'Cannot connect to server';
      return false;
    } catch (e) {
      errorMessage = 'Invalid email or password';
      return false;
    } finally {
      loading = false;
      notifyListeners();
    }
  }

  Future<void> logout() async {
    final refreshToken = await _tokenStorage.getRefreshToken();
    try {
      if (refreshToken != null) await _authService.logout(refreshToken);
    } finally {
      await _tokenStorage.clear();
      currentUser = null;
      notifyListeners();
    }
  }

  Future<void> loadCurrentUser() async {
    final token = await _tokenStorage.getAccessToken();
    if (token == null) return;
    try {
      currentUser = await _authService.me();
      notifyListeners();
    } catch (_) {
      await _tokenStorage.clear();
    }
  }

  Future<bool> checkAuthentication() async {
    await loadCurrentUser();
    return currentUser != null;
  }

  bool hasRole(String role) => currentUser?.role == role;
}
