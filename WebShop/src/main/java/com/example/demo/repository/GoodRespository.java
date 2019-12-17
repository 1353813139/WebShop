package com.example.demo.repository;

import com.example.demo.domain.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodRespository extends JpaRepository<Good,Integer>{
    List<Good> findGoodsByTagEquals(String tag);
    Good findGoodByName(String name);
    List<Good> findGoodsByNameLike(String name);
}
