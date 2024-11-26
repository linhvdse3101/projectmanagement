package com.management.project.controllers;

import com.management.project.requests.CreateProjectRequest;
import com.management.project.responses.CommonResponse;
import com.management.project.responses.ProjectResponse;
import com.management.project.services.projects.ProjectServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "Project", description = "Project management APIs require authentication")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
@Log4j2
public class ProjectController {
    private final ProjectServices projectServices;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<ProjectResponse>> registerProject(@RequestBody @Valid CreateProjectRequest request) {
        log.debug("Request received: {}", request);
        var response = projectServices.createProject(request);
        return ResponseEntity.ok(new CommonResponse<>(response));
    }
}