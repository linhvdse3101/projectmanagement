package com.management.project.domains.project;

import com.management.project.domains.AbstractEntity;
import com.management.project.domains.user.UserAccount;
import com.management.project.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "st_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String taskName;
    private String taskDescription;
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount assignedTo;
}
