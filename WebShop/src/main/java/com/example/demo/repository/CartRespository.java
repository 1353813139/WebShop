package com.example.demo.repository;

import com.example.demo.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRespository extends JpaRepository<Cart,Integer>{
    List<Cart> findCartsByUsernameAndName(String username,String name);
    Cart findCartByUsernameAndName(String username,String name);
    List<Cart> findCartsByUsername(String username);
    void deleteByUsername(String username);
}
