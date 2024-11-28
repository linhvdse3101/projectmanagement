package com.management.project.repositorys.Task;

import com.management.project.requests.FindTaskRequest;
import com.management.project.responses.TaskResponse;

import java.util.List;

public interface TaskRepositoryCustom {
    List<TaskResponse> searchTasks(FindTaskRequest request);
    Integer countTasks(FindTaskRequest request);
}
