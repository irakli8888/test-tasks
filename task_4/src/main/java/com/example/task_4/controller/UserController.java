package com.example.task_4.controller;

import com.example.task_4.model.User;
import com.example.task_4.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

   //добавление пользователя(могут добавить не авторизованные пользователи)
    @PostMapping("/user/add")
    public ResponseEntity addUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/user")
    public List<User> getAll(){
        return userService.findAll();
    }
}
