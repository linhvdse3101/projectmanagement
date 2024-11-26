package com.management.project.services.projects.impl;

import com.management.project.auth.SecurityUtils;
import com.management.project.domains.project.Project;
import com.management.project.domains.user.UserAccount;
import com.management.project.repositorys.project.ProjectRepository;
import com.management.project.requests.CreateProjectRequest;
import com.management.project.responses.ProjectResponse;
import com.management.project.responses.UserAccountDto;
import com.management.project.services.projects.ProjectServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServicesImpl implements ProjectServices {

    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponse createProject(CreateProjectRequest createProjectRequest) {
        final String currentUser  = SecurityUtils.getLoggedInUsername();
        Project projectExited = projectRepository.findProjectByNameAndUserId(createProjectRequest.getName(), currentUser);
        if (Objects.isNull(projectExited)){
            projectExited = Project.builder()
                    .name(createProjectRequest.getName())
                    .projectDescription(createProjectRequest.getDescription())
                    .build();
        }
        return null;
    }
}