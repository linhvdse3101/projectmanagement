package com.management.project.services.tasks;

import com.management.project.domains.project.Task;
import com.management.project.requests.CreateTaskRequest;
import com.management.project.requests.FindTaskRequest;
import com.management.project.requests.UpdateTaskRequest;
import com.management.project.responses.TaskResponse;
import com.management.project.responses.commons.PageResponse;

import java.util.List;


public interface TaskServices {
    Task createTask(CreateTaskRequest createTaskRequest);
    TaskResponse updateTask(Long taskId, UpdateTaskRequest taskRequest);
    String removeTask(Long taskId);
    PageResponse<TaskResponse> getTaskByProject(FindTaskRequest findTaskRequest);
    List<String> getAlluser();
}
