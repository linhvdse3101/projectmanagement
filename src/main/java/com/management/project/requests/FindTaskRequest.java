package com.management.project.requests;

import com.management.project.commons.PagingRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Valid
@AllArgsConstructor
public class FindTaskRequest extends PagingRequestDto {
    private String taskName;
    @NotNull(message = "projectId can't null")
    private Long projectId;
}
