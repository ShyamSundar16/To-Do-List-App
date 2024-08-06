package com.todoapp.repositories;

import com.todoapp.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
//    public boolean validateUser(String id,String Password);
}
