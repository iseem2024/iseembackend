package com.iseem.backend.Controllers;

import com.iseem.backend.Entities.Task;
import com.iseem.backend.Entities.UserTask;
import com.iseem.backend.Services.TaskService;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        Task task = taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.findAll();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
        List<Task> tasks = taskService.findTaskByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestParam List<Integer> assignedUserIds) throws MessagingException {
        Task createdTask = taskService.createTask(task, assignedUserIds);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{taskId}/in-progress/{userId}")
    public ResponseEntity<Void> makeTaskInProgress(@PathVariable int userId, @PathVariable int taskId) {
        taskService.makeTaskInProgress(userId, taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}/completed/{userId}")
    public ResponseEntity<Void> makeTaskCompleted(@PathVariable int userId, @PathVariable int taskId) {
        taskService.makeTaskCompleted(userId, taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserTask>> getTasksForUser(@PathVariable int userId) {
        List<UserTask> userTasks = taskService.getTasksForUser(userId);
        return ResponseEntity.ok(userTasks);
    }

    @PutMapping("/user/{userId}/mark-as-viewed")
    public ResponseEntity<Void> markTasksAsViewed(@PathVariable int userId) {
        taskService.markTaskAsViewed(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{taskId}/user-tasks")
    public ResponseEntity<List<UserTask>> getUserTasksByTask(@PathVariable int taskId) {
        List<UserTask> userTasks = taskService.findUserTasksByTask(taskId);
        return ResponseEntity.ok(userTasks);
    }

    @GetMapping("/user/{userId}/notViewed")
    public ResponseEntity<List<UserTask>> getNotViewdTask(@PathVariable int userId) {
        List<UserTask> userTasks = taskService.getUserTaskNotViewedByUser(userId);
        return ResponseEntity.ok(userTasks);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable int taskId) {
        taskService.delete(taskId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usertask/{userId}/{taskId}")
    public ResponseEntity<Void> deleteUserTask(@PathVariable int userId, @PathVariable int taskId) {
        taskService.deleteUserTask(userId, taskId);
        return ResponseEntity.noContent().build();
    }

}
