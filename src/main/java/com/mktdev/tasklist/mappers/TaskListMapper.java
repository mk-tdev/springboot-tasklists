package com.mktdev.tasklist.mappers;

import com.mktdev.tasklist.domain.dto.TaskListDto;
import com.mktdev.tasklist.domain.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
