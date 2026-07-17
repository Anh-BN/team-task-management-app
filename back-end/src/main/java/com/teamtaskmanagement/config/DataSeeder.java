package com.teamtaskmanagement.config;

import com.teamtaskmanagement.entity.Role;
import com.teamtaskmanagement.entity.User;
import com.teamtaskmanagement.repository.RoleRepository;
import com.teamtaskmanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Role admin = role("Admin");
        Role manager = role("Manager");
        Role member = role("TeamMember");

        user("admin@gmail.com", "Admin User", admin);
        user("manager@gmail.com", "Manager User", manager);
        user("member@gmail.com", "Team Member", member);
    }

    private Role role(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setRoleName(roleName);
            return roleRepository.save(role);
        });
    }

    private void user(String email, String fullName, Role role) {
        if (userRepository.findByEmail(email).isPresent()) return;
        User user = new User();
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPasswordHash(passwordEncoder.encode("123456"));
        user.setActive(true);
        user.setRole(role);
        userRepository.save(user);
    }
}
