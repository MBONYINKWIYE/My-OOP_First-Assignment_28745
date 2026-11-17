package com.auca.assessment;

import com.auca.enrollment.Enrollment;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

import java.time.LocalDate;

public class Assessment implements Identifiable, Registrable {
    private String assessmentId;
    private Enrollment enrollment;
    private String assessmentType;
    private double maximumScore;
    private double obtainedScore;
    private LocalDate assessmentDate;

    public Assessment(String assessmentId, Enrollment enrollment, String assessmentType, double maximumScore, double obtainedScore, LocalDate assessmentDate) {
        this.assessmentId = assessmentId;
        this.enrollment = enrollment;
        this.assessmentType = assessmentType;
        this.maximumScore = maximumScore;
        this.obtainedScore = obtainedScore;
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public double getMaximumScore() {
        return maximumScore;
    }

    public void setMaximumScore(double maximumScore) {
        this.maximumScore = maximumScore;
    }

    public double getObtainedScore() {
        return obtainedScore;
    }

    public void setObtainedScore(double obtainedScore) {
        this.obtainedScore = obtainedScore;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    @Override
    public String getId() {
        return assessmentId;
    }

    @Override
    public String getDisplayName() {
        return assessmentType + " Assessment";
    }

    @Override
    public boolean isActive() {
        return enrollment != null && enrollment.isActive();
    }

    @Override
    public void validate() {
        if (assessmentId == null || assessmentId.trim().isEmpty()){
            System.out.println("Assessment ID is required. ");
        }
        if (enrollment == null){
            System.out.println("Enrollment is required");
        } else if (!enrollment.isActive()) {
            System.out.println("Assessment require the active enrollment");
        }

        if (assessmentType == null || assessmentType.trim().isEmpty()){
            System.out.println("Assessment Type is required .");
        }
        if (maximumScore <= 0){
            System.out.println("Maximum Score must be greater than zero");
        }
        if (obtainedScore <= 0 || obtainedScore > maximumScore){
            System.out.println("Obtained Score must be between 0 and maximum Score");
        }
        if (assessmentDate == null || assessmentDate.isAfter(LocalDate.now())){
            System.out.println("Assessment date can not be in the future .");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return "Assessment: "+ assessmentType + " |ID: "+assessmentId
                + "\n ,Score: "+ obtainedScore+"/"+ maximumScore;
    }

    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentId='" + assessmentId + '\'' +
                ", enrollment=" + (enrollment != null ?
                enrollment.getEnrollmentId(): "Not Assigned") +
                ", assessmentType='" + assessmentType + '\'' +
                ", maximumScore=" + maximumScore +
                ", obtainedScore=" + obtainedScore +
                ", assessmentDate=" + assessmentDate +
                '}';
    }
}
