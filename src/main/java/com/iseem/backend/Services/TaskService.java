package com.iseem.backend.Services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iseem.backend.Entities.Task;
import com.iseem.backend.Entities.User;
import com.iseem.backend.Entities.UserTask;
import com.iseem.backend.Entities.UserTaskId;
import com.iseem.backend.Exceptions.NotFoundException;
import com.iseem.backend.Repositories.TaskRepository;
import com.iseem.backend.Repositories.UserTaskRepository;
import com.iseem.backend.Utils.SendEmail;
import com.iseem.backend.dao.IDao;

import jakarta.mail.MessagingException;

@Service
public class TaskService implements IDao<Task> {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private SendEmail emailService;

    @Override
    public Task findById(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found with id : " + id));
        return task;
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = taskRepository.findAllByOrderByCreationDateDesc();
        return tasks;
    }

    public List<Task> findTaskByStatus(String status) {
        return taskRepository.findByStatusOrderByCreationDateAsc(status);
    }

    public UserTask findUserTaskById(int userId, int taskId) {
        UserTask userTask = userTaskRepository.findById(new UserTaskId(userId, taskId))
                .orElseThrow(() -> new NotFoundException(
                        "UserTask not found with id : " + userId + " and taskId : " + taskId));
        return userTask;
    }

    public Task createTask(Task task, List<Integer> assignedUserIds) throws MessagingException {
        task.setStatus("À faire");
        task.setCreationDate(LocalDateTime.now());
        task = taskRepository.save(task);
        List<UserTask> list = new ArrayList<>();
        for (int userId : assignedUserIds) {
            User user = userService.findById(userId);
            UserTask userTask = new UserTask(new UserTaskId(user.getId(), task.getId()), "À faire", user, task, false,
                    null);
            UserTask ut = userTaskRepository.save(userTask);
            list.add(ut);
            emailService.sendTaskEmail(userTask);
            // emailService.sendEmail(userTask.getUser().getEmail(), "Test",
            // "hello"+userTask.getUser().getNom());
        }
        task.getAssignedUsers().clear();
        task.getAssignedUsers().addAll(list);
        taskRepository.save(task);
        return task;
    }

    public void makeTaskInProgress(int userId, int taskId) {
        Task task = findById(taskId);
        UserTask userTask = findUserTaskById(userId, taskId);
        userTask.setStatus("En cours");
        userTaskRepository.save(userTask);
        task.setStatus("En cours");
        taskRepository.save(task);
    }

    public void makeTaskCompleted(int userId, int taskId) {
        Task task = findById(taskId);
        UserTask userTask = findUserTaskById(userId, taskId);
        userTask.setStatus("Terminé");
        userTask.setCompleteDate(LocalDateTime.now());
        userTaskRepository.save(userTask);
        boolean allCompleted = userTaskRepository.findByTask(task)
                .stream()
                .allMatch(ut -> ut.getStatus().equals("Terminé"));
        if (allCompleted) {
            task.setStatus("Terminé");
            taskRepository.save(task);
        }
    }

    public List<UserTask> getTasksForUser(int userId) {
        User user = userService.findById(userId);
        return userTaskRepository.findByUserOrderByTaskCreationDateDesc(user);
    }

    public void markTaskAsViewed(int userId) {
        List<UserTask> userTasks = getTasksForUser(userId);
        for (UserTask userTask : userTasks) {
            userTask.setIsViewed(true);
            userTaskRepository.save(userTask);
        }
    }

    public List<UserTask> findUserTasksByTask(int taskId) {
        Task task = findById(taskId);
        List<UserTask> userTasks = userTaskRepository.findByTask(task);
        return userTasks;
    }

    public void deleteUserTask(int userId, int taskId) {
        Task task = findById(taskId);
        UserTask userTask = findUserTaskById(userId, taskId);
        task.getAssignedUsers().remove(userTask);
        taskRepository.save(task);
        userTaskRepository.delete(userTask);
    }

    public List<UserTask> getUserTaskNotViewedByUser(int userId){
        User user = userService.findById(userId);
        return userTaskRepository.findByUserAndIsViewedFalse(user);
    }

    @Override
    public Task create(Task o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Task update(Task o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(int id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }
}
