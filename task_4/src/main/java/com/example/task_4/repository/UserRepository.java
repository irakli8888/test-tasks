package com.example.task_4.repository;

import com.example.task_4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User,Long>{
    User findByUsername(String username);
}
