package com.juliolmuller.todo.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juliolmuller.todo.model.User;
import com.juliolmuller.todo.repository.IUserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserRepository userRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody User user) {
        var existingUser = this.userRepository.findByUsername(user.getUsername());

        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already in use.");
        }

        var charArrayPassword = user.getPassword().toCharArray();
        var encryptedPassword = BCrypt.withDefaults().hashToString(12, charArrayPassword);

        user.setPassword(encryptedPassword);

        var userCreated = this.userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
