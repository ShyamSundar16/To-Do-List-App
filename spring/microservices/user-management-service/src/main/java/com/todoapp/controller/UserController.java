package com.todoapp.controller;

import com.todoapp.repositories.UserRepository;
import com.todoapp.service.UserService;
import com.todoapp.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @CrossOrigin
    @GetMapping("")
    public List<User> getAllUsers() {
        logger.info("Finding users from db..");
        return userService.getAllUsers();
    }

    @CrossOrigin
    @PostMapping("/register")
    public String addUser(@RequestBody User user) throws Exception {
        logger.info("Registering a new user");
        userService.save(user);
        return "redirect:../";
    }


    @CrossOrigin
    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        logger.info("Find user with Id : " + id);
        return userService.getUserById(id);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public User modifyUser(@PathVariable String id, @RequestBody User user) {
        return userService.modifyUser(id, user);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public boolean removeUser(@PathVariable String id) {
        logger.info("User to delete: " + id);
        return userService.removeUser(id);
    }

//    @PostMapping("/validate")
//    public boolean authorize(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
//        return userRepo.validateUser(email, password);
//    }

}
