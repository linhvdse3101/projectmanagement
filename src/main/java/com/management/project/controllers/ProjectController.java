package com.management.project.controllers;

import com.management.project.requests.CreateProjectRequest;
import com.management.project.requests.FindProjectRequest;
import com.management.project.responses.commons.CommonResponse;
import com.management.project.responses.ProjectResponse;
import com.management.project.responses.commons.PageResponse;
import com.management.project.services.projects.ProjectServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "Project", description = "Project management APIs require authentication")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
@Log4j2
public class ProjectController {
    private final ProjectServices projectServices;

    @PostMapping("/register")
    public CommonResponse<ProjectResponse> registerProject(@RequestBody @Valid CreateProjectRequest request) {
        log.debug("Request received: {}", request);
        var response = projectServices.createProject(request);
        return new CommonResponse<>(response);
    }

    @GetMapping("/search")
    public CommonResponse<PageResponse<ProjectResponse>> searchProjects(@ModelAttribute FindProjectRequest request) {
        log.debug("Request received: {}", request);
        var response = projectServices.searchProjects(request);
        return new CommonResponse<>(response);
    }

    @GetMapping("/detail/{projectId}")
    public CommonResponse<ProjectResponse> getProjectDetail(@PathVariable("projectId") Long projectId) {
        log.debug("Request received: {}", projectId);
        var response = projectServices.getProjectDetail(projectId);
        return new CommonResponse<>(response);
    }

}