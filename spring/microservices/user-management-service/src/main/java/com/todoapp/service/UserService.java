package com.todoapp.service;

import com.todoapp.entities.User;
import com.todoapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Cacheable("users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Cacheable("user")
    public User getUserById(String id) {
        Optional<User> optional = userRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            System.out.println("User not found with id: " + id);
            return null;
        }

    }

    @Caching(evict = {
            @CacheEvict(value = "user", allEntries = true),
            @CacheEvict(value = "users", allEntries = true)})
    public User save(User user) throws Exception {

        return userRepository.save(user);
    }

    @Caching(evict = {
            @CacheEvict(value = "user", allEntries = true),
            @CacheEvict(value = "users", allEntries = true)})
    public User modifyUser(String id, User user) {
        if (userRepository.existsById(id)) {
            user.setEmail(id);
            return userRepository.save(user);
        } else {
            System.out.println("User not found with id: " + id);
            return null;
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "user", allEntries = true),
            @CacheEvict(value = "users", allEntries = true)})
    public boolean removeUser(String id) {
        userRepository.deleteById(id);
        return true;
    }
}
