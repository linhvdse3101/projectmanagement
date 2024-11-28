package com.management.project.repositorys.project;

import com.management.project.domains.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {
    @Query("SELECT p FROM Project p WHERE p.name = :name AND p.owner.id = :userId")
    Project findProjectByNameAndUserId(@Param("name") String name, @Param("userId") Long userId);

    Project findByProjectIdAndOwner_UserId(Long projectId, Long userId);
}
