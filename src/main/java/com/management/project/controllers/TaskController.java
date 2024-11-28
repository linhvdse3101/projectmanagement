package com.management.project.controllers;

import com.management.project.domains.project.Task;
import com.management.project.requests.*;
import com.management.project.responses.ProjectResponse;
import com.management.project.responses.TaskResponse;
import com.management.project.responses.commons.CommonResponse;
import com.management.project.responses.commons.PageResponse;
import com.management.project.services.projects.ProjectServices;
import com.management.project.services.tasks.TaskServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@Tag(name = "Task", description = "Task management APIs require authentication")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
@Log4j2
public class TaskController {
    private final TaskServices taskServices;

    @PostMapping("/register")
    public CommonResponse<Task> registerTasks(@RequestBody @Valid CreateTaskRequest request) {
        log.debug("Request received: {}", request);
        var response = taskServices.createTask(request);
        return new CommonResponse<>(response);
    }

    @GetMapping("/search")
    public CommonResponse<PageResponse<TaskResponse>> searchTasks(@ModelAttribute FindTaskRequest request) {
        log.debug("Request received: {}", request);
        var response = taskServices.getTaskByProject(request);
        return new CommonResponse<>(response);
    }

    @PutMapping("/update/{taskId}")
    public CommonResponse<TaskResponse> updateTasks(@PathVariable("taskId") Long taskId ,@RequestBody @Valid UpdateTaskRequest request) {
        log.debug("Request received: {}", taskId);
        var response = taskServices.updateTask(taskId, request);
        return new CommonResponse<>(response);
    }

    @DeleteMapping("/remove/{taskId}")
    public CommonResponse<String> deleteTasks(@PathVariable("taskId") Long taskId) {
        log.debug("Request received: {}", taskId);
        var response = taskServices.removeTask(taskId);
        return new CommonResponse<>(response);
    }

    @GetMapping("/users")
    public CommonResponse<List<String>> getAllUserName() {
        var response = taskServices.getAlluser();
        return new CommonResponse<>(response);
    }

}