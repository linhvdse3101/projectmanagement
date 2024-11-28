package com.management.project.responses;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {
    private Long taskId;
    private String taskName;
    private String taskStatus;
    private String assignedTo;
    private String taskDescription;
    private Long projectId;
}