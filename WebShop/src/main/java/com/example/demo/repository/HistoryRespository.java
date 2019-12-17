package com.example.demo.repository;

import com.example.demo.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRespository extends JpaRepository<History,Integer>{
    List<History> findHistoriesByUsername(String username);
}
