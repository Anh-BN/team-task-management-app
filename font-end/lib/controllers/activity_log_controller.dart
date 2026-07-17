import 'package:flutter/foundation.dart';

import '../models/activity_log_model.dart';
import '../services/activity_log_service.dart';

class ActivityLogController extends ChangeNotifier {
  final ActivityLogService _service = ActivityLogService();

  final List<ActivityLogModel> logs = [];
  bool loading = false;
  bool hasMore = true;
  String? errorMessage;
  int _page = 0;
  String action = 'All';

  Future<void> refresh(int projectId, {int? userId}) async {
    logs.clear();
    _page = 0;
    hasMore = true;
    await loadMore(projectId, userId: userId);
  }

  Future<void> loadMore(int projectId, {int? userId}) async {
    if (loading || !hasMore) return;
    loading = true;
    errorMessage = null;
    notifyListeners();
    try {
      final items = await _service.getProjectLogs(projectId: projectId, action: action, userId: userId, page: _page);
      logs.addAll(items);
      hasMore = items.length == 20;
      _page++;
    } catch (e) {
      errorMessage = 'Cannot load activity logs';
    } finally {
      loading = false;
      notifyListeners();
    }
  }

  Future<void> setAction(int projectId, String value) async {
    action = value;
    await refresh(projectId);
  }
}
