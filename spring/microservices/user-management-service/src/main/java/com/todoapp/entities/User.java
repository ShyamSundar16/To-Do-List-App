package com.todoapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String email;
    //private String id;
    private String name;
    private Long phone;
    private String password;
    private String role;

    @Override
    public String toString() {
        return "User{" +
                //"id='" + id + '\'' +
                " name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
