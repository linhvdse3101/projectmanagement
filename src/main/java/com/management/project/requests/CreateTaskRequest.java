package com.management.project.requests;

import jakarta.validation.Valid;
import lombok.*;


@Data
@NoArgsConstructor
@Valid
@AllArgsConstructor
public class CreateTaskRequest {
    private String taskName;
    @NonNull
    private Long projectId;
    private String description;
}
