package com.teamtaskmanagement.repository;

import com.teamtaskmanagement.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    boolean existsByProjectIdAndOwnerUserId(Integer projectId, Integer ownerId);
}
