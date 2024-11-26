package com.management.project.services.projects;

import com.management.project.requests.CreateProjectRequest;
import com.management.project.responses.ProjectResponse;

public interface ProjectServices {

    ProjectResponse createProject(CreateProjectRequest createProjectRequest);
}
