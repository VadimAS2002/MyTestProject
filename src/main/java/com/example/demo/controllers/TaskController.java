package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.models.User;
import com.example.demo.services.NotificationService;
import com.example.demo.services.TaskService;
import com.example.demo.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final NotificationService notificationService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, NotificationService notificationService) {
        this.taskService = taskService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Task>> getPendingTasks() {
        List<Task> pendingTasks = taskService.getPendingTasks();
        return ResponseEntity.ok(pendingTasks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable Long userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);

        for (int i = 0; i < tasks.size(); i++)
            notificationService.setReadNotificationTrue(tasks.get(i).getId());

        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty() || task.getUser() == null || task.getUser().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            User user = userService.getUserById(task.getUser().getId());
            if(user == null){
                return ResponseEntity.badRequest().build();
            }
            task.setUser(user);
            Task createdTask = taskService.createTask(task);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}