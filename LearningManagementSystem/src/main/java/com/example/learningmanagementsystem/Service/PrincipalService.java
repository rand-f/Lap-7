package com.example.learningmanagementsystem.Service;

import com.example.learningmanagementsystem.Model.Principal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PrincipalService {

    ArrayList<Principal>principals=new ArrayList<>();

    public ArrayList<Principal>getPrincipals(){
        return principals;
    }

    public void addPrincipal(Principal principal) {
        principals.add(principal);
    }

    public boolean updatePrincipal(String id, Principal principal) {
        for (Principal p : principals) {
            if (p.getId().equals(id)) {
                principals.set(principals.indexOf(p), principal);
                return true;
            }
        }
        return false;
    }

    public boolean deletePrincipal(String id) {
        for (Principal p : principals) {
            if (p.getId().equals(id)) {
                principals.remove(p);
                return true;
            }
        }
        return false;
    }

    public String approveBudget(String id, Double amount) {

        for (Principal principal : principals) {
            if (principal.getId().equals(id)) {

                if (!principal.getStatus().equals("active")) {
                    return "principal is not active";
                }

                if (principal.getBudget() < amount) {
                    return "not enough budget current budget: " + principal.getBudget();
                }

                principal.setBudget(principal.getBudget() - amount);
                return "budget approved current budget: " + principal.getBudget();
            }
        }
        return "principal not found";
    }

    public String addBudget(String id, Double amount) {
        for (Principal principal : principals) {
            if (principal.getId().equals(id)) {

                if (amount <= 0) {
                    return "amount must be positive";
                }

                principal.setBudget(principal.getBudget() + amount);
                return "budget increased successfully new budget: " + principal.getBudget();
            }
        }
        return "principal not found";
    }

    public String approveActivity(String id, String activityName) {
        for (Principal principal : principals) {
            if (principal.getId().equals(id)) {

                if (!principal.getStatus().equals("active")) {
                    return "principal is not active";
                }

                return "activity " + activityName + " has been approved ";
            }
        }
        return "principal not found";
    }

    public String approvePromotion(String id, String staffId) {
        for (Principal principal : principals) {
            if (principal.getId().equals(id)) {

                if (!principal.getStatus().equals("active")) {
                    return "principal is not active";
                }

                principal.setPromotionsApproved(principal.getPromotionsApproved() + 1);

                return "promotion approved for staff ID: " + staffId;
            }
        }
        return "principal not found.";
    }

    public String approveLeave(String id) {
        for (Principal principal : principals) {
            if (principal.getId().equals(id)) {

                if (!principal.getStatus().equals("active")) {
                    return "principal is not active";
                }

                principal.setLeavesApproved(principal.getLeavesApproved() + 1);

                return "Leave request approved";
            }
        }
        return "principal not found.";
    }

    public ArrayList<Principal> getBudgetHigherThan(Double amount) {
        ArrayList<Principal> highBudget = new ArrayList<>();

        for (Principal principal : principals) {
            if (principal.getBudget() > amount) {
                highBudget.add(principal);
            }
        }
        return highBudget;
    }

    public ArrayList<Principal> getPrincipalsByStatus(String status) {
        ArrayList<Principal> sameStatus = new ArrayList<>();

        for (Principal principal : principals) {
            if (principal.getStatus().equalsIgnoreCase(status)) {
                sameStatus.add(principal);
            }
        }
        return sameStatus;
    }

    public Integer getApprovedPromotions(String id){
        for(Principal principal:principals){
            if(id.equals(principal.getId())){
                return principal.getPromotionsApproved();
            }
        }
     return -1;
    }

    public Integer getApprovedLeaves(String id){
        for(Principal principal:principals){
            if(id.equals(principal.getId())){
                return principal.getLeavesApproved();
            }
        }
        return -1;
    }

    public ArrayList<Principal> getApprovedPromotions() {
        ArrayList<Principal> approved= new ArrayList<>();

        for (Principal principal : principals) {
            if (principal.getPromotionsApproved() > 0) {
                approved.add(principal);
            }
        }
        return approved;
    }
    public ArrayList<Principal> getApprovedLeaves() {
        ArrayList<Principal> approved = new ArrayList<>();

        for (Principal principal : principals) {
            if (principal.getLeavesApproved() > 0) {
                approved.add(principal);
            }
        }
        return approved;
    }

}


