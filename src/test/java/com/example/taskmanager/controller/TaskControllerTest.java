package com.example.taskmanager.controller;

import com.example.taskmanager.exception.CommonException;
import com.example.taskmanager.model.CreateTaskDto;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void createTask_ShouldReturnSuccessMessage() throws Exception {
        // Arrange
        CreateTaskDto createTaskDto = new CreateTaskDto("Test Task", "Test Description");
        when(taskService.addTask("Test Task", "Test Description")).thenReturn("Test Task task successfully created");

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createTaskDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Test Task task successfully created"));
    }


    @Test
    void getTaskById_ShouldReturnTask_WhenExists() throws Exception {
        // Arrange
        Long id = 1L;
        Task task = new Task();
        task.setId(id);
        when(taskService.getTaskById(id)).thenReturn(Optional.of(task));

        // Act & Assert
        mockMvc.perform(get("/api/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getAllTasks_ShouldReturnTasks() throws Exception {
        // Arrange
        Task task = new Task();
        task.setId(1L);
        when(taskService.getAllTasks()).thenReturn(Collections.singletonList(task));

        // Act & Assert
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() throws Exception {
        // Arrange
        Long id = 1L;
        Task taskDetails = new Task();
        taskDetails.setTitle("Updated Task");
        taskDetails.setDescription("Updated Description");

        when(taskService.updateTask(eq(id), any(Task.class))).thenReturn(taskDetails);

        // Act & Assert
        mockMvc.perform(put("/api/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }


    @Test
    void deleteTask_ShouldReturnSuccessMessage() throws Exception {
        // Arrange
        Long id = 1L;
        when(taskService.deleteTask(id)).thenReturn("Task deleted successfully");

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted successfully"));
    }

}
