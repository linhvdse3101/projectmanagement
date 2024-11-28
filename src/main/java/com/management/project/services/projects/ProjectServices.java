package com.management.project.services.projects;

import com.management.project.requests.CreateProjectRequest;
import com.management.project.requests.FindProjectRequest;
import com.management.project.responses.ProjectResponse;
import com.management.project.responses.ProjectWithTaskResponse;
import com.management.project.responses.commons.PageResponse;

import java.util.List;

public interface ProjectServices {

    ProjectResponse createProject(CreateProjectRequest createProjectRequest);
    PageResponse<ProjectResponse> searchProjects(FindProjectRequest request);
    ProjectResponse getProjectDetail(Long projectId);
}
