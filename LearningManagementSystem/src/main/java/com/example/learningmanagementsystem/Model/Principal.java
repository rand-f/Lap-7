package com.example.learningmanagementsystem.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Principal {
    @NotEmpty(message = "ID can not be empty")
    @Pattern(regexp = "^\\d{10}$", message = "ID must be 10 digits")
    private String id;

    @NotEmpty(message = "Name can not be empty")
    private String name;

    @PositiveOrZero(message = "budget must be 0 or positive")
    private Double budget;

    @Min(value = 0, message = "Leave approvals can not be negative")
    private Integer leavesApproved = 0;

    @Min(value = 0, message = "Promotions can not be negative")
    private Integer promotionsApproved = 0;

    @NotNull(message = "Status can not be null")
    @Pattern(regexp = "^(active|retired|on leave)$")
    private String status;
}
