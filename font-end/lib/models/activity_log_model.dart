class ActivityLogModel {
  final int logId;
  final int taskId;
  final String taskName;
  final int userId;
  final String userFullName;
  final String? userAvatarUrl;
  final String action;
  final String? fieldName;
  final String? oldValue;
  final String? newValue;
  final DateTime createdAt;
  final String displayMessage;

  ActivityLogModel.fromJson(Map<String, dynamic> json)
      : logId = json['logId'],
        taskId = json['taskId'],
        taskName = json['taskName'] ?? '',
        userId = json['userId'],
        userFullName = json['userFullName'] ?? '',
        userAvatarUrl = json['userAvatarUrl'],
        action = json['action'] ?? '',
        fieldName = json['fieldName'],
        oldValue = json['oldValue'],
        newValue = json['newValue'],
        createdAt = DateTime.parse(json['createdAt']),
        displayMessage = json['displayMessage'] ?? '';
}
