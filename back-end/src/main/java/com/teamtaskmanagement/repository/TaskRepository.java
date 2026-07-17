package com.teamtaskmanagement.repository;

import com.teamtaskmanagement.entity.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @EntityGraph(attributePaths = {"project", "project.owner", "assignee", "supervisor"})
    Optional<Task> findWithProjectByTaskId(Integer taskId);

    @EntityGraph(attributePaths = {"assignee"})
    List<Task> findByProjectProjectId(Integer projectId);

    boolean existsByTaskIdAndAssigneeUserId(Integer taskId, Integer userId);

    long countByProjectProjectId(Integer projectId);
    long countByProjectProjectIdAndStatus(Integer projectId, String status);

    @Query("select count(t) from Task t where t.project.projectId = :projectId and t.plannedEndDate < :today and t.status <> 'Done'")
    long countOverdue(@Param("projectId") Integer projectId, @Param("today") LocalDate today);
}
