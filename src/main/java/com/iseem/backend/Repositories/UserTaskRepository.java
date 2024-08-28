package com.iseem.backend.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iseem.backend.Entities.Task;
import com.iseem.backend.Entities.User;
import com.iseem.backend.Entities.UserTask;
import com.iseem.backend.Entities.UserTaskId;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskId> {
    List<UserTask> findByUserOrderByTaskCreationDateDesc(User user);
    List<UserTask> findByTask(Task task);
    List<UserTask> findByUserAndIsViewedFalse(User user);
}
