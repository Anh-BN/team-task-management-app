import 'package:flutter/material.dart';

import '../models/project_progress_model.dart';
import '../services/project_progress_service.dart';
import 'activity_log_screen.dart';

class ProjectProgressScreen extends StatelessWidget {
  const ProjectProgressScreen({super.key, required this.projectId});

  final int projectId;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Project Progress')),
      body: FutureBuilder<ProjectProgressModel>(
        future: ProjectProgressService().getProgressSummary(projectId),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) return const Center(child: CircularProgressIndicator());
          if (snapshot.hasError) return Center(child: OutlinedButton.icon(onPressed: () {}, icon: const Icon(Icons.refresh), label: const Text('Retry')));
          final progress = snapshot.data;
          if (progress == null) return const Center(child: Text('No progress data'));
          return ListView(
            padding: const EdgeInsets.all(16),
            children: [
              Text(progress.projectName, style: Theme.of(context).textTheme.headlineSmall),
              const SizedBox(height: 16),
              LinearProgressIndicator(value: progress.completionPercentage / 100),
              const SizedBox(height: 8),
              Text('${progress.completionPercentage.toStringAsFixed(1)}% complete'),
              const SizedBox(height: 16),
              Wrap(
                spacing: 8,
                runSpacing: 8,
                children: [
                  _Metric(label: 'Total', value: progress.totalTasks),
                  _Metric(label: 'Pending', value: progress.pendingTasks),
                  _Metric(label: 'In Progress', value: progress.inProgressTasks),
                  _Metric(label: 'Done', value: progress.doneTasks),
                  _Metric(label: 'Incomplete', value: progress.incompleteTasks),
                  _Metric(label: 'Overdue', value: progress.overdueTasks),
                ],
              ),
              const SizedBox(height: 20),
              Text('Members', style: Theme.of(context).textTheme.titleLarge),
              const SizedBox(height: 8),
              ...progress.memberProgress.map((member) => Card(
                    child: ListTile(
                      leading: CircleAvatar(backgroundImage: member.avatarUrl == null ? null : NetworkImage(member.avatarUrl!), child: member.avatarUrl == null ? Text(member.fullName.substring(0, 1)) : null),
                      title: Text(member.fullName),
                      subtitle: Text('Done ${member.doneTasks}/${member.assignedTasks} • Pending ${member.pendingTasks} • In Progress ${member.inProgressTasks} • Overdue ${member.overdueTasks}'),
                      trailing: Text('${member.completionPercentage.toStringAsFixed(0)}%'),
                      onTap: () => Navigator.of(context).push(MaterialPageRoute(builder: (_) => ActivityLogScreen(projectId: projectId, userId: member.userId))),
                    ),
                  )),
            ],
          );
        },
      ),
    );
  }
}

class _Metric extends StatelessWidget {
  const _Metric({required this.label, required this.value});

  final String label;
  final int value;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 150,
      child: Card(
        child: Padding(
          padding: const EdgeInsets.all(12),
          child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            Text(label, style: Theme.of(context).textTheme.labelLarge),
            const SizedBox(height: 6),
            Text('$value', style: Theme.of(context).textTheme.headlineSmall),
          ]),
        ),
      ),
    );
  }
}
