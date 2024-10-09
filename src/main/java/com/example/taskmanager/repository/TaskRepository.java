package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tasks (title, description) VALUES (?1, ?2)",nativeQuery = true)
    void saveTask(String title, String description);

    @Query(value = "select * from tasks where title = ?1 or description = ?2",nativeQuery = true)
    List<Object[]> findByDescriptionOrTitle(String title, String description);

    @Query(value="select * from tasks where id = ?1",nativeQuery = true)
    Task findByIdUsingQuery(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete from tasks where id = ?1",nativeQuery = true)
    void deleteByIdUsingQuery(Long id);

    @Modifying
    @Transactional
    @Query(value= "update tasks set description = ?3 ,title = ?2 where id = ?1",nativeQuery = true)
    void updateTask(Long id, String title, String description);
}

