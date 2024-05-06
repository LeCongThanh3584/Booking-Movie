package com.example.demo.Repositories;

import com.example.demo.Entities.ConfirmCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmCodeRepository extends JpaRepository<ConfirmCode, Integer> {
    Optional<ConfirmCode> findByCode(String code);
}
