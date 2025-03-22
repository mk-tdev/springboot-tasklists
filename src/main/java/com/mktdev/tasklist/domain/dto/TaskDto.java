package com.mktdev.tasklist.domain.dto;

import com.mktdev.tasklist.domain.entities.TaskPriority;
import com.mktdev.tasklist.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
