package com.juliolmuller.todo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @GetMapping
    public ResponseEntity index() {
        var tasks = this.taskRepository.findAll();

        return ResponseEntity.status(HttpStatus.CREATED).body(tasks);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Task task) {
        var taskCreated = this.taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }
}
