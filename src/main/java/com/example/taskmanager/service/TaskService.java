package com.example.taskmanager.service;

import com.example.taskmanager.exception.CommonException;
import com.example.taskmanager.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    public String addTask(String title, String description) throws CommonException;

    public Optional<Task> getTaskById(Long id) throws CommonException;

    public List<Task> getAllTasks();

    public Task updateTask(Long id, Task taskDetails) throws CommonException;

    public String deleteTask(Long id) throws CommonException;

}
