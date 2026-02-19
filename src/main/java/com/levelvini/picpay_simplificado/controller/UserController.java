package com.levelvini.picpay_simplificado.controller;

import com.levelvini.picpay_simplificado.dtos.UserDTO;
import com.levelvini.picpay_simplificado.model.User;
import com.levelvini.picpay_simplificado.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {

    UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody UserDTO user){
        User newUser = service.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> AllUsers(){
        List<User> allUsers = service.findAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
