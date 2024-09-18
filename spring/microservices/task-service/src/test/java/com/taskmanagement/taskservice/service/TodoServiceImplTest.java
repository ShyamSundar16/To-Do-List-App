package com.taskmanagement.taskservice.service;

import com.taskmanagement.taskservice.model.Todo;
import com.taskmanagement.taskservice.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;


    @InjectMocks
    private TodoServiceImpl todoService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenAddTodo_thenTodoIsAdded() {
        Todo todo = new Todo();
        todo.setId("1");
        todo.setTitle("New Todo");
        todo.setStartDate(Date.from(Instant.now()));
        todo.setEndDate(Date.from(Instant.now()));
        doReturn(todo).when(todoRepository).save(any(Todo.class));

        Todo result = todoService.addTodo(todo);

        assertThat(result.getId(), is("1"));
        assertThat(result.getTitle(), is("New Todo"));
    }


    @Test
    public void whenUpdateTodo_thenTodoIsUpdated() {
        String id = "1";
        Todo existingTodo = new Todo();
        existingTodo.setId(id);
        doReturn(Optional.of(existingTodo)).when(todoRepository).findById(id);

        Todo updatedDetails = new Todo();
        updatedDetails.setTitle("Updated Title");
        updatedDetails.setStartDate(Date.from(Instant.now()));
        updatedDetails.setEndDate(Date.from(Instant.now()));
        doReturn(updatedDetails).when(todoRepository).save(any(Todo.class));

        Optional<Todo> result = todoService.updateTodo(id, updatedDetails);

        assertTrue(result.isPresent());
        assertThat(result.get().getTitle(), is("Updated Title"));
    }

    @Test
    public void whenGetAllTodos_thenReturnTodoList() {
        List<Todo> todos = List.of(new Todo(), new Todo());
        doReturn(todos).when(todoRepository).findAll();

        List<Todo> result = todoService.getAllTodos();

        assertThat(result.size(), is(2));
    }

    @Test
    public void whenGetTodoById_thenReturnTodo() {
        String id = "1";
        Todo todo = new Todo();
        todo.setId(id);
        doReturn(Optional.of(todo)).when(todoRepository).findById(id);

        Optional<Todo> result = todoService.getTodoById(id);

        assertTrue(result.isPresent());
        assertThat(result.get().getId(), is(id));
    }

    @Test
    public void whenDeleteTodoById_thenTodoIsDeleted() {
        String id = "1";
        doReturn(true).when(todoRepository).existsById(id);
        doNothing().when(todoRepository).deleteById(id);

        todoService.deleteTodoById(id);

        verify(todoRepository, times(1)).deleteById(id);
    }

    @Test
    public void whenFindByUserIdAndFilters_thenReturnFilteredTodos() {
        String userId = "user123";
        List<Todo> todos = List.of(new Todo(), new Todo());
        doReturn(todos).when(todoRepository).findByUserId(userId);

        List<Todo> result = todoService.findByUserIdAndFilters(userId, null, null);

        assertThat(result.size(), is(2));
    }
}