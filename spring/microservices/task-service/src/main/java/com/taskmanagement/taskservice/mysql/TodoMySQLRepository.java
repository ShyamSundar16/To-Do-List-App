package com.taskmanagement.taskservice.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoMySQLRepository extends JpaRepository<Todo, String> {
}