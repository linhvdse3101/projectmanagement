package com.management.project.controllers;

import com.management.project.requests.CreateProjectRequest;
import com.management.project.requests.RegisterRequest;
import com.management.project.responses.AuthResponse;
import com.management.project.responses.CommonResponse;
import com.management.project.responses.ProjectResponse;
import com.management.project.services.projects.ProjectServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "Auth", description = "Auth management APIs")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectServices projectServices;
    @PostMapping("/register")
    public ResponseEntity<CommonResponse<ProjectResponse>> registerProject(@RequestBody @Valid CreateProjectRequest request) {
        var response = projectServices.createProject(request);
        return ResponseEntity.ok(new CommonResponse<>(response));
    }
}
