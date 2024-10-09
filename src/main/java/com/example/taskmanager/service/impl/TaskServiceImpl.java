package com.example.taskmanager.service.impl;

import com.example.taskmanager.exception.CommonException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.taskmanager.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    //create a task with native query
    public String addTask(String title, String description) throws CommonException {
        try{
            List<Object[]> isPresent = taskRepository.findByDescriptionOrTitle(title, description);
            if(isPresent.isEmpty()){
                taskRepository.saveTask(title,description);
                return title + " task successfully created";
            }else{
                throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR,"Task with title or description has already created");
            }
        }catch (Exception e){
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    //read
    public Optional<Task> getTaskById(Long id) throws CommonException{
        try{
            Task task = taskRepository.findByIdUsingQuery(id);
            if(ObjectUtils.isNotEmpty(task)){
                return Optional.of(task);
            }
            throw new CommonException(HttpStatus.BAD_REQUEST, "Error while fetching task with id " + id);
        }catch (Exception e) {
            throw new CommonException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    //update
    public Task updateTask(Long id, Task taskDetails) throws CommonException {
        try{
            Optional<Task> byId = taskRepository.findById(id);
            if(byId.isPresent()){
                taskRepository.updateTask(id,taskDetails.getTitle(),taskDetails.getDescription());
                return taskDetails;
            }
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR,"Task not found with id "+id);
        }catch(Exception e){
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    //delete
    public String deleteTask(Long id) throws CommonException {
        try{
            Optional<Task> byId = taskRepository.findById(id);
            if(byId.isPresent()){
                taskRepository.deleteByIdUsingQuery(id);
                return byId.get().getTitle()+" deleted successfully";
            }
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR,"Task not found with id "+id);
        }catch(Exception e){
            throw new CommonException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }
}

