package com.example.learningmanagementsystem.Service;

import com.example.learningmanagementsystem.Model.Progress;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ProgressService {

    ArrayList<Progress>progresses=new ArrayList<>();

    public ArrayList<Progress>getProgresses(){
        return progresses;
    }

    public void addProgress(Progress progress){
        progresses.add(progress);
    }

    public boolean updateProgress(String id, Progress progress){
        for (Progress p:progresses){
            if (p.getId().equals(id)){
                progresses.set(progresses.indexOf(p),progress);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProgress(String id){
        for(Progress progress:progresses){
            if(progress.getId().equals(id)){
                progresses.remove(progress);
                return true;
            }
        }
        return false;
    }

    public String calculateAbsentRate(String id){
        for (Progress progress:progresses){
            if(progress.getId().equals(id)){
                double rate =  (progress.getAbsentDays() * 100.0) /progress.getAllDays();
                if(rate>25){
                    progress.setStatus("pulled");
                    return " you (pulled out) can not continue because your absent rate="+rate+"% is more than 25%";
                } else if (rate>10) {
                    return " you need to be careful, your absent rate="+rate+"% ";
                }else if(rate>0){
                    return " you are ok, your absent rate="+rate+"% ";
                }else{
                    return " you are great, your absent rate="+rate+"% ";
                }
            }
        }
        return "not found";
    }

    public String gradeLoss(String id, Double lostGrades){
        // my plan for this endpoint is
        // grades start at 100 and with every loss it decreases
        // by the end of the course the grade is the on after loss
        for(Progress progress:progresses){
            if(progress.getId().equals(id)) {
                if(progress.getStatus().equals("completed")){
                    return "this course is already completed and the total grade was "+progress.getTotalGrade();
                }
                if(progress.getStatus().equals("pulled")){
                    return " you are pulled out of the course";
                }

                progress.setTotalGrade(progress.getTotalGrade() - lostGrades);

                if (progress.getTotalGrade() < 60) {
                    progress.setStatus("pulled");
                    return "you lost more than 40 marks so you are pulled out of the course";
                }

                return "yor total grades now are "+progress.getTotalGrade();
            }
        }
        return "not found";
    }

    public String markAbsent(String id) {
        for (Progress progress : progresses) {
            if (progress.getId().equals(id)) {

                if ((progress.getStatus().equals("completed") || progress.getStatus().equals("pulled"))) {
                    return "course already " + progress.getStatus();
                }

                progress.setAbsentDays(progress.getAbsentDays() + 1);

                return "absent days now are "+progress.getAbsentDays() +
                        " | "+ calculateAbsentRate(id);

            }
        }
        return "not found";
    }

    public String complete(String id){
        for(Progress progress:progresses){
            if(progress.getId().equals(id)){
                if ((progress.getStatus().equals("completed") || progress.getStatus().equals("pulled"))) {
                    return "course already " + progress.getStatus();
                }

                progress.setEndDate(LocalDate.now());
                progress.setStatus("completed");

                return "this course is now completed: "+progress;
            }
        }
        return "not found";
    }
    public ArrayList<Progress>getSameStatus(String status){
        ArrayList<Progress>sameProgress=new ArrayList<>();
        for(Progress progress:progresses){
            if(progress.getStatus().equals(status)){
                sameProgress.add(progress);
            }
        }
        return sameProgress;
    }

    public ArrayList<Progress>getStartTogether(LocalDate start){
        ArrayList<Progress>sameProgress=new ArrayList<>();
        for(Progress progress:progresses){
            if (progress.getStartDate() != null && progress.getStartDate().equals(start)) {
                sameProgress.add(progress);
            }
        }
        return sameProgress;
    }

    public ArrayList<Progress>getEndTogether(LocalDate end){
        ArrayList<Progress>sameProgress=new ArrayList<>();
        for(Progress progress:progresses){
            if(progress.getEndDate() != null && progress.getEndDate().equals(end)){
                sameProgress.add(progress);
            }
        }
        return sameProgress;
    }
}
