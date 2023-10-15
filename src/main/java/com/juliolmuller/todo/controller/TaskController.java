package com.juliolmuller.todo.controller;

import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juliolmuller.todo.model.Task;
import com.juliolmuller.todo.repository.ITaskRepository;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @GetMapping
    public ResponseEntity index(HttpServletRequest request) {
        var userId = (UUID) request.getAttribute("userId");
        var tasks = this.taskRepository.findAllByUserId(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(tasks);
    }

    @PostMapping
    public ResponseEntity create(HttpServletRequest request, @RequestBody Task task) {
        var userId = (UUID) request.getAttribute("userId");

        task.setUserId(userId);

        var taskCreated = this.taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }
}
