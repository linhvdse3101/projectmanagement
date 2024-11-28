package com.management.project.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Valid
@AllArgsConstructor
public class UpdateTaskRequest {
    @NotBlank(message = "taskName can't empty")
    private String taskName;
    @NotNull(message = "projectId can't null")
    private Long projectId;
    @NotNull(message = "taskId can't null")
    private Long taskId;
    private String description;
    private String status;
    private String userAssign;
}
