package com.example.project.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.auth.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByRole(String role);
}