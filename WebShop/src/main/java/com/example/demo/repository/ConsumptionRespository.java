package com.example.demo.repository;

import com.example.demo.domain.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumptionRespository extends JpaRepository<Consumption,Integer>{
    List<Consumption> findConsumptionsByName(String name);
    List<Consumption> findConsumptionsByUsername(String username);
}
