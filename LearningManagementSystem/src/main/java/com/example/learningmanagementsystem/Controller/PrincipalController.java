package com.example.learningmanagementsystem.Controller;

import com.example.learningmanagementsystem.ApiResponse.ApiResponse;
import com.example.learningmanagementsystem.Model.Principal;
import com.example.learningmanagementsystem.Service.PrincipalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/principal")
public class PrincipalController {

    private final PrincipalService principalService;

    @GetMapping("/get")
    public ResponseEntity<?>getPrincipals(){
        return ResponseEntity.status(200).body(principalService.getPrincipals());
    }

    @PostMapping("/add")
    public ResponseEntity<?>addPrincipal(@RequestBody @Valid Principal principal, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        principalService.addPrincipal(principal);
        return ResponseEntity.status(200).body(new ApiResponse("Principal added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>updatePrincipal(@PathVariable String id, @RequestBody @Valid Principal principal, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }

        boolean found = principalService.updatePrincipal(id,principal);

        if(!found){
            return ResponseEntity.status(400).body(new ApiResponse("Principal not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Principal updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deletePrincipal(@PathVariable String id){
        boolean found = principalService.deletePrincipal(id);

        if(!found){
            return ResponseEntity.status(400).body(new ApiResponse("Principal not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Principal deleted"));
    }

    @PutMapping("/approve-budget/{id}/{amount}")
    public ResponseEntity<?>approveBudget(@PathVariable String id, @PathVariable Double amount){
        String output = principalService.approveBudget(id,amount);
        if(!output.contains("approved")){
            return ResponseEntity.status(400).body(output);
        }
        return ResponseEntity.status(200).body(output);
    }

    @PutMapping("/add-budget/{id}/{amount}")
    public ResponseEntity<?>addBudget(@PathVariable String id, @PathVariable Double amount){
        String output = principalService.addBudget(id,amount);
        if(!output.contains("successfully")){
            return ResponseEntity.status(400).body(output);
        }
        return ResponseEntity.status(200).body(output);
    }

    @PutMapping("/approve-activity/{id}/{activity}")
    public ResponseEntity<?>approveActivity(@PathVariable String id, @PathVariable String activity){
        String output = principalService.approveActivity(id,activity);
        if(!output.contains("approved")){
            return ResponseEntity.status(400).body(output);
        }
        return ResponseEntity.status(200).body(output);
    }

    @PutMapping("/approve-promotion/{id}/{staff}")
    public ResponseEntity<?>approvePromotion(@PathVariable String id, @PathVariable String staff){
        String output = principalService.approvePromotion(id,staff);
        if(!output.contains("approved")){
            return ResponseEntity.status(400).body(output);
        }
        return ResponseEntity.status(200).body(output);
    }

    @PutMapping("/approve-leave/{id}")
    public ResponseEntity<?>approveLeave(@PathVariable String id){
        String output = principalService.approveLeave(id);
        if(!output.contains("approved")){
            return ResponseEntity.status(400).body(output);
        }
        return ResponseEntity.status(200).body(output);
    }

    @GetMapping("/get-high-budget/{amount}")
    public ResponseEntity<?>getBudgetHigherThan(@PathVariable Double amount){
        ArrayList<Principal>highBudget =principalService.getBudgetHigherThan(amount);
        if(highBudget.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("no principals as requested "));
        }
        return ResponseEntity.status(200).body(highBudget);
    }

    @GetMapping("/get-by-status/{status}")
    public ResponseEntity<?>getPrincipalsByStatus(@PathVariable String status){
        ArrayList<Principal>sameStatus =principalService.getPrincipalsByStatus(status);
        if(sameStatus.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("no principals as requested "));
        }
        return ResponseEntity.status(200).body(sameStatus);
    }
    @GetMapping("/get-approved-promotion/{id}")
    public ResponseEntity<?>getApprovedPromotions(@PathVariable String id){
        Integer promotions = principalService.getApprovedPromotions(id);
        if(promotions==-1){
            return ResponseEntity.status(400).body(new ApiResponse("principal not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("principal promoted: "+promotions+" staffs"));
    }

    @GetMapping("/get-approved-leaves/{id}")
    public ResponseEntity<?>getApprovedLeaves(@PathVariable String id){
        Integer leaves = principalService.getApprovedLeaves(id);
        if(leaves==-1){
            return ResponseEntity.status(400).body(new ApiResponse("principal not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("principal approved leaves : "+leaves+" times"));
    }

}
