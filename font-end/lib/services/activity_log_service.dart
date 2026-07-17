import '../core/network/api_client.dart';
import '../models/activity_log_model.dart';

class ActivityLogService {
  final _dio = ApiClient.instance.dio;

  Future<List<ActivityLogModel>> getProjectLogs({
    required int projectId,
    String? action,
    int? userId,
    int page = 0,
    int size = 20,
  }) async {
    final response = await _dio.get('/projects/$projectId/activity-logs', queryParameters: {
      if (action != null && action != 'All') 'action': action,
      if (userId != null) 'userId': userId,
      'page': page,
      'size': size,
    });
    final content = response.data['data']['content'] as List<dynamic>;
    return content.map((e) => ActivityLogModel.fromJson(e)).toList();
  }
}
