package com.mktdev.tasklist.mappers;

import com.mktdev.tasklist.domain.dto.TaskDto;
import com.mktdev.tasklist.domain.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
