package com.example.fullstack_demo.repo;

import com.example.fullstack_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User,Long> {
}
