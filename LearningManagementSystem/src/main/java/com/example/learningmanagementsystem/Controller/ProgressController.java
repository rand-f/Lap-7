package com.example.learningmanagementsystem.Controller;

import com.example.learningmanagementsystem.ApiResponse.ApiResponse;
import com.example.learningmanagementsystem.Model.Progress;
import com.example.learningmanagementsystem.Service.ProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @GetMapping("/get")
    public ResponseEntity<?> getProgresses(){
        return ResponseEntity.status(200).body(progressService.getProgresses());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProgress(@Valid @RequestBody Progress progress, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        progressService.addProgress(progress);
        return ResponseEntity.status(200).body(new ApiResponse("Progress added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProgress(@PathVariable String id, @Valid @RequestBody Progress progress, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        boolean found = progressService.updateProgress(id, progress);

        if(found){
            return ResponseEntity.status(200).body(new ApiResponse("Progress updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("progress not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProgress(@PathVariable String id){
        boolean found = progressService.deleteProgress(id);

        if(found){
            return ResponseEntity.status(200).body(new ApiResponse("Progress deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("progress not found"));
    }

    @GetMapping("/absent-rate/{id}")
    public ResponseEntity<?> calculateAbsentRate(@PathVariable String id){
        String result = progressService.calculateAbsentRate(id);
        if(result.equals("not found")){
            return ResponseEntity.status(400).body(new ApiResponse("Progress not found"));
        }
        return ResponseEntity.status(200).body(result);
    }

    @PutMapping("/grade-loss/{id}/{loss}")
    public ResponseEntity<?> gradeLoss(@PathVariable String id, @PathVariable Double loss){
        String result = progressService.gradeLoss(id,loss);
        if(result.equals("not found")){
            return ResponseEntity.status(400).body(new ApiResponse("Progress not found"));
        }
        return ResponseEntity.status(200).body(result);
    }

    @PutMapping("/mark-absent/{id}")
    public ResponseEntity<?> markAbsent(@PathVariable String id){
        String result = progressService.markAbsent(id);
        if(result.equals("not found")){
            return ResponseEntity.status(400).body(new ApiResponse("Progress not found"));
        }
        return ResponseEntity.status(200).body(result);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<?> completeCourse(@PathVariable String id){
        String result = progressService.complete(id);
        if(result.equals("not found")){
            return ResponseEntity.status(400).body(new ApiResponse("Progress not found"));
        }
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/same-status/{status}")
    public ResponseEntity<?> getSameStatus(@PathVariable String status){
        ArrayList<Progress> sameProgresses = progressService.getSameStatus(status);
        if(sameProgresses.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Progress not found"));
        }
        return ResponseEntity.status(200).body(sameProgresses);
    }

    @GetMapping("/start-together/{start}")
    public ResponseEntity<?> getStartTogether(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start){
        ArrayList<Progress> sameProgresses = progressService.getStartTogether(start);
        if(sameProgresses.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Progress not found"));
        }
        return ResponseEntity.status(200).body(sameProgresses);
    }

    @GetMapping("/end-together/{end}")
    public ResponseEntity<?> getEndTogether(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end){
        ArrayList<Progress> sameProgresses = progressService.getEndTogether(end);
        if(sameProgresses.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("Progress not found"));
        }
        return ResponseEntity.status(200).body(sameProgresses);
    }
}

