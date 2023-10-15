package com.juliolmuller.todo.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.juliolmuller.todo.model.Task;

public interface ITaskRepository extends JpaRepository<Task, UUID> {}
