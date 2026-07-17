import '../core/network/api_client.dart';
import '../models/login_response_model.dart';
import '../models/user_model.dart';

class AuthService {
  final _dio = ApiClient.instance.dio;

  Future<LoginResponseModel> login(String email, String password) async {
    final response = await _dio.post('/auth/login', data: {'email': email, 'password': password});
    return LoginResponseModel.fromJson(response.data['data']);
  }

  Future<UserModel> me() async {
    final response = await _dio.get('/auth/me');
    return UserModel.fromJson(response.data['data']);
  }

  Future<void> logout(String refreshToken) async {
    await _dio.post('/auth/logout', data: {'refreshToken': refreshToken});
  }
}
