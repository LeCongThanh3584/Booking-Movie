package com.example.demo.Repositories;

import com.example.demo.Entities.Role;
import com.example.demo.Enum.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(ERole eRole);
}
