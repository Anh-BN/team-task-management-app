class MemberProgressModel {
  final int userId;
  final String fullName;
  final String? avatarUrl;
  final int assignedTasks;
  final int pendingTasks;
  final int inProgressTasks;
  final int doneTasks;
  final int overdueTasks;
  final double completionPercentage;

  MemberProgressModel.fromJson(Map<String, dynamic> json)
      : userId = json['userId'],
        fullName = json['fullName'],
        avatarUrl = json['avatarUrl'],
        assignedTasks = json['assignedTasks'],
        pendingTasks = json['pendingTasks'],
        inProgressTasks = json['inProgressTasks'],
        doneTasks = json['doneTasks'],
        overdueTasks = json['overdueTasks'],
        completionPercentage = (json['completionPercentage'] as num).toDouble();
}

class ProjectProgressModel {
  final int projectId;
  final String projectName;
  final int totalTasks;
  final int pendingTasks;
  final int inProgressTasks;
  final int doneTasks;
  final int incompleteTasks;
  final int overdueTasks;
  final double completionPercentage;
  final List<MemberProgressModel> memberProgress;

  ProjectProgressModel.fromJson(Map<String, dynamic> json)
      : projectId = json['projectId'],
        projectName = json['projectName'],
        totalTasks = json['totalTasks'],
        pendingTasks = json['pendingTasks'],
        inProgressTasks = json['inProgressTasks'],
        doneTasks = json['doneTasks'],
        incompleteTasks = json['incompleteTasks'],
        overdueTasks = json['overdueTasks'],
        completionPercentage = (json['completionPercentage'] as num).toDouble(),
        memberProgress = (json['memberProgress'] as List<dynamic>)
            .map((e) => MemberProgressModel.fromJson(e))
            .toList();
}
