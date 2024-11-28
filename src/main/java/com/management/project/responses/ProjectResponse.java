package com.management.project.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse implements Serializable {
    private Long projectId;
    private String name;
    private String userName;
    private String description;
}
