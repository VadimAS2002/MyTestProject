package com.example.demo.models;

import java.time.LocalDateTime;

public class Notification {

    private Long id;
    private String message;
    private LocalDateTime timestamp;
    private boolean read;
    private Task task;

    public Notification() {
    }

    public Notification(Long id, String message, LocalDateTime timestamp, boolean read, Task task) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.read = read;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
