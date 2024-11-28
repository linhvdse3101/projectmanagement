package com.management.project.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@NoArgsConstructor
@Valid
@AllArgsConstructor
public class CreateTaskRequest {
    @NotBlank(message = "taskName can't empty")
    private String taskName;
    @NotNull(message = "projectId can't null")
    private Long projectId;
    private String description;
    @NotBlank(message = "status can't null")
    private String status;
    @NotBlank(message = "assign to can't null")
    private String userName;
}
