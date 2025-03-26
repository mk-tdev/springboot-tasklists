package com.mktdev.tasklist.services.impl;

import com.mktdev.tasklist.domain.entities.Task;
import com.mktdev.tasklist.domain.entities.TaskList;
import com.mktdev.tasklist.domain.entities.TaskPriority;
import com.mktdev.tasklist.domain.entities.TaskStatus;
import com.mktdev.tasklist.repositories.TaskListRepository;
import com.mktdev.tasklist.repositories.TaskRepository;
import com.mktdev.tasklist.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (task.getId() != null) {
            throw new IllegalArgumentException("Task already has an ID!");
        }
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title is null or empty!");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new IllegalArgumentException("Task list does not exist!"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToCreate = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskStatus,
                taskPriority,
                taskList,
                now,
                now
        );

        return taskRepository.save(taskToCreate);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Task does not have an ID!");
        }
        if (!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException("Task ID does not match!");
        }
        if (task.getPriority() == null) {
            throw new IllegalArgumentException("Task priority is null!");
        }
        if (task.getStatus() == null) {
            throw new IllegalArgumentException("Task status is null!");
        }
        if (task.getDueDate() == null) {
            throw new IllegalArgumentException("Task due date is null!");
        }

        Task taskToUpdate = taskRepository.findByTaskListIdAndId(taskListId, taskId).orElseThrow(() -> new IllegalArgumentException("Task does not exist!"));
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setDueDate(task.getDueDate());
        taskToUpdate.setStatus(task.getStatus());
        taskToUpdate.setPriority(task.getPriority());
        taskToUpdate.setUpdated(LocalDateTime.now());

        return taskRepository.save(taskToUpdate);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
