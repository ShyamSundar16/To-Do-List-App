package com.taskmanagement.taskservice.controller;

import com.taskmanagement.taskservice.model.Status;
import com.taskmanagement.taskservice.model.Todo;
import com.taskmanagement.taskservice.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;
    String jsonContent;
    Todo todoObj = new Todo();

    @BeforeEach
    public void setup() throws IOException {
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
        Path path = Paths.get(ResourceUtils.getFile("classpath:Todo.json").toURI());
        jsonContent = new String(Files.readAllBytes(path));
        todoObj.setTitle("Complete project documentation");
        todoObj.setDescription("Write and review the project documentation for the upcoming release.");
        todoObj.setStartDate(Date.from(Instant.now()));
        todoObj.setEndDate(Date.from(Instant.now()));
        todoObj.setStatus(Status.PENDING);
        todoObj.setEffortRequired(10);
        todoObj.setUserId("user123");
        todoObj.setCategory("Work");
        todoObj.setReminderDate(Date.from(Instant.now()));
    }

    @Test
    public void whenPostAddTodo_thenCreateTodo() throws Exception {
        doReturn(todoObj).when(todoService).addTodo(any(Todo.class));

        mockMvc.perform(post("/api/v1/user/add-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Complete project documentation")))
                .andExpect(jsonPath("$.description", is("Write and review the project documentation for the upcoming release.")));
    }

    @Test
    public void whenPutUpdateTodo_thenUpdateTodo() throws Exception {
        // Read JSON content from file
        Path path = Paths.get(ResourceUtils.getFile("classpath:Todo.json").toURI());
        String jsonContent = new String(Files.readAllBytes(path));

        String id = "1";
        Todo updatedTodo = new Todo();
        updatedTodo.setId(id);
        updatedTodo.setTitle("Complete project documentation");
        updatedTodo.setDescription("Write and review the project documentation for the upcoming release.");
        updatedTodo.setStartDate(Date.from(Instant.now()));
        updatedTodo.setEndDate(Date.from(Instant.now()));
        updatedTodo.setStatus(Status.PENDING);
        updatedTodo.setEffortRequired(10);
        updatedTodo.setUserId("user123");
        updatedTodo.setCategory("Work");
        updatedTodo.setReminderDate(Date.from(Instant.now()));

        doReturn(Optional.of(updatedTodo)).when(todoService).updateTodo(eq(id), any(Todo.class));

        mockMvc.perform(put("/api/v1/user/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Complete project documentation")))
                .andExpect(jsonPath("$.description", is("Write and review the project documentation for the upcoming release.")));
    }

    @Test
    public void whenGetAllTodos_thenReturnTodoList() throws Exception {
        List<Todo> todos = List.of(todoObj);
        doReturn(todos).when(todoService).getAllTodos();

        mockMvc.perform(get("/api/v1/user/list/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(todos.size())));
    }

    @Test
    public void whenGetTodoById_thenReturnTodo() throws Exception {
        String id = "1";
        todoObj.setId(id);
        doReturn(Optional.of(todoObj)).when(todoService).getTodoById(id);

        mockMvc.perform(get("/api/v1/user/list/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.title", is("Complete project documentation")))
                .andExpect(jsonPath("$.description", is("Write and review the project documentation for the upcoming release.")));
    }

    @Test
    public void whenDeleteTodoById_thenReturnNoContent() throws Exception {
        String id = "1";
        doNothing().when(todoService).deleteTodoById(id);

        mockMvc.perform(delete("/api/v1/user/list/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenGetTodosByUserId_thenReturnTodoList() throws Exception {
        String userId = "user123";
        List<Todo> todos = List.of(todoObj);
        doReturn(todos).when(todoService).findByUserIdAndFilters(eq(userId), any(), any());

        mockMvc.perform(get("/api/v1/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(todos.size())));
    }
}
