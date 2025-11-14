package com.example.learningmanagementsystem.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Progress {

    @NotEmpty(message = "ID can not be empty")
    @Pattern(regexp = "^\\d{10}$",message = "ID must be 10 digits")
    private String id;

//    the following IDs are to connect with other classes (Models)
//    as we are not allowed to have them they are ignored
//    private String courseID;
//    private String studentID;

    @NotNull(message = "total course period can not be null")
    private Integer allDays;

    // 0 absents by default
    private Integer absentDays=0;

    //private boolean courseActive=true;

    @NotEmpty(message = "status can not be empty")
    @Pattern(regexp = "^(on going|pulled|completed)$", message = "status must be on going, pulled, or completed")
    private String status;

    @Max(value = 100,message = "grade can not be more than 100")
    @Min(value = 0,message = "grade can not be less than 0")
    private Double totalGrade = 100.0;

    private LocalDate startDate;
    private LocalDate endDate;

}
