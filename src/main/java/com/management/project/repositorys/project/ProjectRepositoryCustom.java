package com.management.project.repositorys.project;

import com.management.project.requests.FindProjectRequest;
import com.management.project.responses.ProjectResponse;

import java.util.List;

public interface ProjectRepositoryCustom {
    List<ProjectResponse> searchProject(Long userId, FindProjectRequest request);
    Integer countProject(Long userId, FindProjectRequest request);
}
