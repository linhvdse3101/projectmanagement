package com.management.project.services.tasks.impl;

import com.management.project.auth.SecurityUtils;
import com.management.project.domains.project.Project;
import com.management.project.domains.project.Task;
import com.management.project.domains.user.UserAccount;
import com.management.project.enums.TaskStatus;
import com.management.project.handelexceptions.ValidateException;
import com.management.project.repositorys.Task.TaskRepository;
import com.management.project.repositorys.project.ProjectRepository;
import com.management.project.repositorys.user.UserRepository;
import com.management.project.requests.CreateTaskRequest;
import com.management.project.requests.FindTaskRequest;
import com.management.project.requests.UpdateTaskRequest;
import com.management.project.responses.ProjectResponse;
import com.management.project.responses.TaskResponse;
import com.management.project.responses.commons.PageResponse;
import com.management.project.responses.commons.UserAccountDto;
import com.management.project.services.projects.ProjectServices;
import com.management.project.services.tasks.TaskServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServicesImpl implements TaskServices {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public Task createTask(CreateTaskRequest createProjectRequest) {
        log.info("createTask is ruing " + TaskServices.class.getName());

        UserAccountDto accountDto = SecurityUtils.getLoggedInUser();
        Task task = taskRepository.findByTaskNameAndProject_ProjectIdAndAssignedTo_UserIdAndDeleteFlagFalse(createProjectRequest.getTaskName(),
                createProjectRequest.getProjectId(), accountDto.getUserId()).orElse(null);
        if (Objects.nonNull(task)) {
            log.error("Task name is exited in project");
            throw new DuplicateKeyException("Task name is exited in project ");
        }
        Project project = projectRepository.findByProjectIdAndOwner_UserId(createProjectRequest.getProjectId(), accountDto.getUserId());
        UserAccount user = userRepository.findByUserName(accountDto.getUsername()).orElse(null);

        if (Objects.isNull(project)) {
            log.error("can't create Task out of project");
            throw new ValidateException("can't create Task out of project");
        }
        task = Task.builder()
                .project(project)
                .taskName(createProjectRequest.getTaskName())
                .taskDescription(createProjectRequest.getDescription())
                .taskStatus(TaskStatus.NOT_STARTED)
                .assignedTo(user)
                .build();

        taskRepository.save(task);
        log.info("create task success: " + task.getTaskId());
        return task;
    }

    @Override
    public TaskResponse updateTask(Long taskId, UpdateTaskRequest request) {
        Task task = taskRepository.findByTaskIdAndProject_ProjectIdAndDeleteFlagFalse(taskId, request.getProjectId()).orElse(null);
        if (Objects.isNull(task)) {
            log.error("Task name dose not exited in project");
            throw new DuplicateKeyException("Task name dose not exited in project ");
        }
        UserAccount owner = userRepository.findByUserName(request.getUserAssign()).orElse(null);
        if (Objects.isNull(owner)) {
            log.error("user assigned does not exited");
            throw new DuplicateKeyException("user assigned does not exited" + request.getUserAssign());
        }

        task.setAssignedTo(owner);
        task.setTaskName(request.getTaskName());
        task.setTaskStatus(TaskStatus.getNameByKey(request.getStatus()));
        task.setTaskDescription(request.getDescription());

        taskRepository.save(task);
        log.info("updateTask success");

        return TaskResponse.builder()
                .taskId(task.getTaskId())
                .taskName(task.getTaskName())
                .taskStatus(task.getTaskStatus().getValue())
                .taskDescription(task.getTaskDescription())
                .assignedTo(owner.getUserName())
                .build();
    }

    @Override
    public String removeTask(Long taskId) {
        UserAccountDto accountDto = SecurityUtils.getLoggedInUser();
        Task task = taskRepository.findByTaskIdAndAssignedTo_UserIdAndDeleteFlagFalse(taskId, accountDto.getUserId()).orElse(null);
        if (Objects.isNull(task)) {
            log.error("Task name dose not exited in project");
            throw new DuplicateKeyException("Task name dose not exited in project ");
        }
        task.setDeleteFlag(true);
        taskRepository.save(task);
        return task.getTaskName();
    }

    @Override
    public PageResponse<TaskResponse> getTaskByProject(FindTaskRequest request) {
        log.info("search Task is runing in " + ProjectServices.class.getName());
        UserAccountDto accountDto = SecurityUtils.getLoggedInUser();
        if (Objects.isNull(accountDto)) {
            log.error("Fail authenticate ");
            throw new RuntimeException("fail authenticate");
        }
        List<TaskResponse> taskResponseList = taskRepository.searchTasks(request);
        Integer countTasks = taskRepository.countTasks(request);
        // Build and return the response
        return PageResponse.<TaskResponse>builder()
                .content(taskResponseList)
                .page(request.getPage() + 1)
                .pageSize(request.getPageSize())
                .totalElements(countTasks)
                .build();
    }

    @Override
    public List<String> getAlluser() {
        List<String> userNames = userRepository.findAllUsernames();
        return userNames == null ? new ArrayList<>() : userNames;
    }
}
