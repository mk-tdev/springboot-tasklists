package com.mktdev.tasklist.controllers;

import com.mktdev.tasklist.domain.dto.TaskListDto;
import com.mktdev.tasklist.domain.entities.TaskList;
import com.mktdev.tasklist.mappers.TaskListMapper;
import com.mktdev.tasklist.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/task-lists")
public class TaskListController {
    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<TaskListDto> listTaskLists() {
        return taskListService.listTaskLists().stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
        TaskList createdTaskList = taskListService.createTaskList(
                taskListMapper.fromDto(taskListDto));
        return taskListMapper.toDto(createdTaskList);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/{task_list_id}")
    public Optional<TaskListDto> getTaskList(@PathVariable("task_list_id") UUID taskListId) {
        return taskListService.getTaskList(taskListId).map(taskListMapper::toDto);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path = "/{task_list_id}")
    public TaskListDto updateTaskList(@PathVariable("task_list_id") UUID taskListId, @RequestBody TaskListDto taskListDto) {
        TaskList updateTaskList = taskListService.updateTaskList(taskListId, taskListMapper.fromDto(taskListDto));

        return taskListMapper.toDto(updateTaskList);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(path = "/{task_list_id}")
    public void deleteTaskList(@PathVariable("task_list_id") UUID taskListId) {
        taskListService.deleteTaskList(taskListId);
    }
}
