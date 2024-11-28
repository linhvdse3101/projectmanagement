package com.management.project.repositorys.Task;

import com.management.project.domains.project.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom {
    Optional<Task> findByTaskNameAndProject_ProjectIdAndAssignedTo_UserIdAndDeleteFlagFalse(String taskName, Long projectId, Long userId);
    Optional<Task> findByTaskIdAndProject_ProjectIdAndDeleteFlagFalse(Long taskId, Long projectId);
    Optional<Task> findByTaskIdAndAssignedTo_UserIdAndDeleteFlagFalse(Long taskId, Long userId);

    List<Task> findByAssignedTo_UserIdAndDeleteFlagFalse(Long userId);
    @Query(value = "", nativeQuery = true)
    List<Task> findByProject_ProjectIdAndDeleteFlagFalse(Long projectId);
}
