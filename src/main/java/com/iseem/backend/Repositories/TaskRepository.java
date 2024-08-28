package com.iseem.backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iseem.backend.Entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
    List<Task> findAllByOrderByCreationDateDesc();
    List<Task> findByStatusOrderByCreationDateAsc(String status);
}
