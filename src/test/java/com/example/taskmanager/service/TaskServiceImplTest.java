package com.example.taskmanager.service;

import com.example.taskmanager.exception.CommonException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addTask_ShouldCreateTask_WhenNotExists() throws CommonException {
        // Arrange
        String title = "Test Task";
        String description = "Test Description";
        when(taskRepository.findByDescriptionOrTitle(title, description)).thenReturn(Collections.emptyList());

        // Act
        String result = taskService.addTask(title, description);

        // Assert
        assertEquals("Test Task task successfully created", result);
        verify(taskRepository).saveTask(title, description);
    }

    @Test
    void addTask_ShouldThrowException_WhenTaskExists() {
        // Arrange
        String title = "Test Task";
        String description = "Test Description";
        when(taskRepository.findByDescriptionOrTitle(title, description)).thenReturn(Collections.singletonList(new Object[]{}));

        // Act & Assert
        CommonException exception = assertThrows(CommonException.class, () -> taskService.addTask(title, description));
        assertEquals("Task with title or description has already created", exception.getMessage());
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenExists() throws CommonException {
        // Arrange
        Long id = 1L;
        Task task = new Task();
        task.setId(id);
        when(taskRepository.findByIdUsingQuery(id)).thenReturn(task);

        // Act
        Optional<Task> result = taskService.getTaskById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void getTaskById_ShouldThrowException_WhenNotExists() {
        // Arrange
        Long id = 1L;
        when(taskRepository.findByIdUsingQuery(id)).thenReturn(null);

        // Act & Assert
        CommonException exception = assertThrows(CommonException.class, () -> taskService.getTaskById(id));
        assertEquals("Error while fetching task with id 1", exception.getMessage());
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        // Arrange
        List<Task> tasks = Collections.singletonList(new Task());
        when(taskRepository.findAll()).thenReturn(tasks);

        // Act
        List<Task> result = taskService.getAllTasks();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void updateTask_ShouldUpdateTask_WhenExists() throws CommonException {
        // Arrange
        Long id = 1L;
        Task taskDetails = new Task();
        taskDetails.setTitle("Updated Title");
        taskDetails.setDescription("Updated Description");

        when(taskRepository.findById(id)).thenReturn(Optional.of(new Task()));

        // Act
        Task result = taskService.updateTask(id, taskDetails);

        // Assert
        assertEquals(taskDetails.getTitle(), result.getTitle());
        assertEquals(taskDetails.getDescription(), result.getDescription());
        verify(taskRepository).updateTask(id, taskDetails.getTitle(), taskDetails.getDescription());
    }

    @Test
    void updateTask_ShouldThrowException_WhenNotExists() {
        // Arrange
        Long id = 1L;
        Task taskDetails = new Task();
        taskDetails.setTitle("Updated Title");
        taskDetails.setDescription("Updated Description");

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        CommonException exception = assertThrows(CommonException.class, () -> taskService.updateTask(id, taskDetails));
        assertEquals("Task not found with id 1", exception.getMessage());
    }

    @Test
    void deleteTask_ShouldDeleteTask_WhenExists() throws CommonException {
        // Arrange
        Long id = 1L;
        Task task = new Task();
        task.setTitle("Task to be deleted");
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        // Act
        String result = taskService.deleteTask(id);

        // Assert
        assertEquals("Task to be deleted deleted successfully", result);
        verify(taskRepository).deleteByIdUsingQuery(id);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenNotExists() {
        // Arrange
        Long id = 1L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        CommonException exception = assertThrows(CommonException.class, () -> taskService.deleteTask(id));
        assertEquals("Task not found with id 1", exception.getMessage());
    }
}
