package com.management.project.services.projects.impl;

import com.management.project.auth.SecurityUtils;
import com.management.project.domains.project.Project;
import com.management.project.domains.user.UserAccount;
import com.management.project.handelexceptions.NotFoundException;
import com.management.project.repositorys.project.ProjectRepository;
import com.management.project.repositorys.user.UserRepository;
import com.management.project.requests.CreateProjectRequest;
import com.management.project.requests.FindProjectRequest;
import com.management.project.responses.ProjectResponse;
import com.management.project.responses.commons.PageResponse;
import com.management.project.responses.commons.UserAccountDto;
import com.management.project.services.projects.ProjectServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServicesImpl implements ProjectServices {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public ProjectResponse createProject(CreateProjectRequest createProjectRequest) {
        final UserAccountDto currentUser = SecurityUtils.getLoggedInUser();
        Project projectExited = projectRepository.findProjectByNameAndUserId(createProjectRequest.getName(), currentUser.getUserId());
        UserAccount userAccount = userRepository.findByUserName(currentUser.getUsername()).orElse(null);
        if (Objects.nonNull(projectExited)) {
            log.error("Project is exited ");
            throw new DuplicateKeyException("Project is exited ");
        }
        if (Objects.isNull(userAccount)) {
            log.error("user is not exited ");
            throw new UsernameNotFoundException("user is not exited ");
        }

        projectExited = Project.builder()
                .name(createProjectRequest.getName())
                .owner(userAccount)
                .projectDescription(createProjectRequest.getDescription())
                .build();

        projectRepository.save(projectExited);
        return ProjectResponse.builder().projectId(projectExited.getProjectId()).name(projectExited.getName()).description(projectExited.getProjectDescription()).build();
    }

    @Override
    public PageResponse<ProjectResponse> searchProjects(FindProjectRequest request) {
        log.info("searchProjects is runing in " + ProjectServices.class.getName());
        UserAccountDto accountDto = SecurityUtils.getLoggedInUser();
        if (Objects.isNull(accountDto)){
            log.error("fail authenticate ");
            throw new RuntimeException("fail authenticate");
        }
        List<ProjectResponse> projectList = projectRepository.searchProject(accountDto.getUserId(), request);
        Integer totalProject = projectRepository.countProject(accountDto.getUserId(), request);
        // Build and return the response
        return PageResponse.<ProjectResponse>builder()
                .content(projectList)
                .page(request.getPage()+1)
                .pageSize(request.getPageSize())
                .totalElements(totalProject)
                .build();
    }

    @Override
    public ProjectResponse getProjectDetail(Long projectId){
        final UserAccountDto currentUser = SecurityUtils.getLoggedInUser();
        if (Objects.isNull(currentUser)){
            log.error("fail authenticate ");
            throw new RuntimeException("fail authenticate");
        }
        Project projectExited = projectRepository.findByProjectIdAndOwner_UserId(projectId, currentUser.getUserId());
        if (Objects.isNull(projectExited)){
            log.error("Project Exited does not exit " + projectId);
            throw new NotFoundException("Project Exited does not exit" + projectId);
        }

        return  ProjectResponse.builder()
                .projectId(projectExited.getProjectId())
                .userName(currentUser.getUsername())
                .description(projectExited.getProjectDescription())
                .name(projectExited.getName())
                .build();
    }


}