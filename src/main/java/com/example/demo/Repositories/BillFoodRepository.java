package com.example.demo.Repositories;

import com.example.demo.Entities.BillFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillFoodRepository extends JpaRepository<BillFood, Integer> {
}
