import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'controllers/auth_controller.dart';
import 'screens/login_screen.dart';

void main() {
  runApp(const TeamTaskApp());
}

class TeamTaskApp extends StatelessWidget {
  const TeamTaskApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (_) => AuthController()..loadCurrentUser(),
      child: MaterialApp(
        debugShowCheckedModeBanner: false,
        title: 'Team Task Management',
        theme: ThemeData(colorSchemeSeed: Colors.teal, useMaterial3: true),
        home: const LoginScreen(),
      ),
    );
  }
}
