package com.example.demo.Repositories;

import com.example.demo.Entities.UserStatus;
import com.example.demo.Enum.EUserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {
    Optional<UserStatus> findByUserStatusName(EUserStatus userStatusName);
}
