package com.example.demo.Repositories;

import com.example.demo.Entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> getAllByUserId(Integer userId);
    Optional<Bill> findByBillIdAndUserId(Integer billId, Integer userId);
}
