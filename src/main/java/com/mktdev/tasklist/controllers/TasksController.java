package com.mktdev.tasklist.controllers;

import com.mktdev.tasklist.domain.dto.TaskDto;
import com.mktdev.tasklist.domain.dto.TaskListDto;
import com.mktdev.tasklist.domain.entities.Task;
import com.mktdev.tasklist.mappers.TaskListMapper;
import com.mktdev.tasklist.mappers.TaskMapper;
import com.mktdev.tasklist.services.TaskListService;
import com.mktdev.tasklist.services.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks-lists/{task_list_id}/tasks")
public class TasksController {
    private final TaskService taskService;
    private final TaskListMapper taskListMapper;
    private final TaskMapper taskMapper;

    public TasksController(TaskService taskService, TaskListMapper taskListMapper, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskListMapper = taskListMapper;
        this.taskMapper = taskMapper;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id") UUID taskListId) {
        return taskService.listTasks(taskListId).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskDto taskDto) {
        Task taskCreated = taskService.createTask(taskListId, taskMapper.fromDto(taskDto));

        return taskMapper.toDto(taskCreated);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/{task_id}")
    public Optional<TaskDto> getTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId) {
        return taskService.getTask(taskListId, taskId).map(taskMapper::toDto);
    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updateTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId,
            @RequestBody TaskDto taskDto
    ) {
        Task updatedTask = taskService.updateTask(taskListId, taskId, taskMapper.fromDto(taskDto));

        return taskMapper.toDto(updatedTask);
    }

    @DeleteMapping(path = "/{task_id}")
    public void deleteTask(@PathVariable("task_list_id") UUID taskListId,
                           @PathVariable("task_id") UUID taskId) {
        taskService.deleteTask(taskListId, taskId);
    }
}
