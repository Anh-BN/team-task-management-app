package com.teamtaskmanagement.repository;

import com.teamtaskmanagement.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @EntityGraph(attributePaths = "role")
    Optional<User> findByEmail(String email);
}
