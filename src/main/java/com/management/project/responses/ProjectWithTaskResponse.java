package com.management.project.responses;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectWithTaskResponse extends ProjectResponse {
    private List<TaskResponse> taskResponses = new ArrayList<>();
}
