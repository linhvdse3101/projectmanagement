package com.management.project.repositorys.project;

import com.management.project.domains.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.name = :name AND p.owner.userName = :userName")
    Project findProjectByNameAndUserId(@Param("name") String name, @Param("userName") String userName);
}
