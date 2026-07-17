package com.teamtaskmanagement.repository;

import com.teamtaskmanagement.entity.ActivityLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Integer>, JpaSpecificationExecutor<ActivityLog> {
    @EntityGraph(attributePaths = {"task", "task.project", "user"})
    Page<ActivityLog> findByTaskTaskId(Integer taskId, Pageable pageable);
}
