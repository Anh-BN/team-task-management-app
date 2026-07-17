package com.teamtaskmanagement.repository;

import com.teamtaskmanagement.entity.ProjectMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Integer> {
    boolean existsByProjectProjectIdAndUserUserId(Integer projectId, Integer userId);
    @EntityGraph(attributePaths = {"user", "user.role"})
    List<ProjectMember> findByProjectProjectId(Integer projectId);
}
