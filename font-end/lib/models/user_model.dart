class UserModel {
  final int userId;
  final String email;
  final String fullName;
  final String? phone;
  final String? avatarUrl;
  final String role;
  final bool isActive;

  UserModel({
    required this.userId,
    required this.email,
    required this.fullName,
    this.phone,
    this.avatarUrl,
    required this.role,
    required this.isActive,
  });

  factory UserModel.fromJson(Map<String, dynamic> json) => UserModel(
        userId: json['userId'],
        email: json['email'],
        fullName: json['fullName'],
        phone: json['phone'],
        avatarUrl: json['avatarUrl'],
        role: json['role'],
        isActive: json['isActive'] == true,
      );
}
