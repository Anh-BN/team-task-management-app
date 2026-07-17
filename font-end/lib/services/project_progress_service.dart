import '../core/network/api_client.dart';
import '../models/project_progress_model.dart';

class ProjectProgressService {
  final _dio = ApiClient.instance.dio;

  Future<ProjectProgressModel> getProgressSummary(int projectId) async {
    final response = await _dio.get('/projects/$projectId/progress-summary');
    return ProjectProgressModel.fromJson(response.data['data']);
  }
}
