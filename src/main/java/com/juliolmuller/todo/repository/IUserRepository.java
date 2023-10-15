package com.juliolmuller.todo.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.juliolmuller.todo.model.User;

public interface IUserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
}
