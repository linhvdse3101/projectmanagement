package com.management.project.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Valid
@AllArgsConstructor
public class CreateProjectRequest {
    @NotBlank(message = "userName can't empty")
    @Min(value = 6)
    @Max(value = 255)
   private String name;
   private String description;
}
