import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

import '../controllers/activity_log_controller.dart';

class ActivityLogScreen extends StatelessWidget {
  const ActivityLogScreen({super.key, required this.projectId, this.userId});

  final int projectId;
  final int? userId;

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (_) => ActivityLogController()..refresh(projectId, userId: userId),
      child: Scaffold(
        appBar: AppBar(title: const Text('Activity Logs')),
        body: Consumer<ActivityLogController>(
          builder: (context, controller, _) => Column(
            children: [
              Padding(
                padding: const EdgeInsets.all(12),
                child: DropdownButtonFormField<String>(
                  initialValue: controller.action,
                  decoration: const InputDecoration(labelText: 'Action', prefixIcon: Icon(Icons.filter_list)),
                  items: const ['All', 'Created', 'StatusChanged', 'Assigned', 'Updated', 'AttachmentAdded']
                      .map((e) => DropdownMenuItem(value: e, child: Text(e)))
                      .toList(),
                  onChanged: (value) {
                    if (value != null) controller.setAction(projectId, value);
                  },
                ),
              ),
              Expanded(
                child: RefreshIndicator(
                  onRefresh: () => controller.refresh(projectId, userId: userId),
                  child: _LogList(projectId: projectId, userId: userId),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class _LogList extends StatelessWidget {
  const _LogList({required this.projectId, this.userId});

  final int projectId;
  final int? userId;

  @override
  Widget build(BuildContext context) {
    final controller = context.watch<ActivityLogController>();
    if (controller.errorMessage != null && controller.logs.isEmpty) {
      return Center(child: OutlinedButton.icon(onPressed: () => controller.refresh(projectId, userId: userId), icon: const Icon(Icons.refresh), label: const Text('Retry')));
    }
    if (controller.loading && controller.logs.isEmpty) return const Center(child: CircularProgressIndicator());
    if (controller.logs.isEmpty) return const Center(child: Text('No activity logs'));

    return ListView.separated(
      physics: const AlwaysScrollableScrollPhysics(),
      itemCount: controller.logs.length + (controller.hasMore ? 1 : 0),
      separatorBuilder: (_, __) => const Divider(height: 1),
      itemBuilder: (context, index) {
        if (index == controller.logs.length) {
          controller.loadMore(projectId, userId: userId);
          return const Padding(padding: EdgeInsets.all(16), child: Center(child: CircularProgressIndicator()));
        }
        final log = controller.logs[index];
        return ListTile(
          leading: CircleAvatar(backgroundImage: log.userAvatarUrl == null ? null : NetworkImage(log.userAvatarUrl!), child: log.userAvatarUrl == null ? Text(log.userFullName.substring(0, 1)) : null),
          title: Text(log.displayMessage),
          subtitle: Text('${log.taskName}\n${log.oldValue ?? ''} -> ${log.newValue ?? ''}\n${DateFormat('dd/MM/yyyy • HH:mm').format(log.createdAt)}'),
          isThreeLine: true,
        );
      },
    );
  }
}
