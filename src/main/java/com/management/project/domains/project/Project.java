package com.management.project.domains.project;

import com.management.project.domains.AbstractEntity;
import com.management.project.domains.user.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "st_project", indexes = {@Index(name = "idx_project_owner", columnList = "user_id"), @Index(name = "idx_project_status", columnList = "status")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(
        name = "Project.tasks",
        attributeNodes = @NamedAttributeNode("tasks")
)
public class Project extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String name;
    @Column(length = 500)
    private String projectDescription;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount owner; // Chủ sở hữu dự án
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();
}
