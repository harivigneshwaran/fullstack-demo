package com.example.fullstack_demo.controller;

import com.example.fullstack_demo.exception.UserNotFoundException;
import com.example.fullstack_demo.model.User;
import com.example.fullstack_demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
public class UserController {

    @Autowired
    UserRepo userRepObj;

    @PostMapping("addUser")
    public User newUser(@RequestBody User userObj){
        return userRepObj.save(userObj);
    }

    @GetMapping("/userList")
    List<User> getAllUsers() {
        return userRepObj.findAll();
    }




    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepObj.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        System.out.println("1--->"+newUser.getEmail());
        return userRepObj.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return userRepObj.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepObj.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepObj.deleteById(id);
        return  "User with id "+id+" has been deleted success.";
    }

}
