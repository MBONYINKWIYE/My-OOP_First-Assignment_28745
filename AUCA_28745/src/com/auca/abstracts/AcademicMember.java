package com.auca.abstracts;

import com.auca.department.Department;

import java.time.LocalDate;

public abstract class AcademicMember extends Person{
    private String memberNumber;
    private Department department;
    private java.time.LocalDate joinDate;
    private String academicStatus;
    private String institutionalEmail;
    private String advisorName;

    public AcademicMember(String personId, String firstName, String lastName, String email,
                          String phoneNumber, LocalDate dateOfBirth, String memberNumber, Department department,
                          LocalDate joinDate, String academicStatus, String institutionalEmail, String advisorName) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
        this.memberNumber = memberNumber;
        this.department = department;
        this.joinDate = joinDate;
        this.academicStatus = academicStatus;
        this.institutionalEmail = institutionalEmail;
        this.advisorName = advisorName;
    }

    //Getters and Setters

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    public String getInstitutionalEmail() {
        return institutionalEmail;
    }

    public void setInstitutionalEmail(String institutionalEmail) {
        this.institutionalEmail = institutionalEmail;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    //Implementation methods
    @Override
    public boolean isActive() {
        return "active".equalsIgnoreCase(academicStatus);
    }

    //Abstract Method
    public abstract String getAcademicCategory();

    @Override
    public void validate() {
        super.validate();
        if (memberNumber == null || memberNumber.trim().isEmpty()){
            System.out.println("Member name is required ");
        }
        if (department == null){
            System.out.println("Department is required");
        }
        if (joinDate == null || joinDate.isAfter((LocalDate.now()))){
            System.out.println("Join Date can not be in future");
        }
        if (academicStatus == null || !(academicStatus.equalsIgnoreCase("active") || academicStatus.equalsIgnoreCase("inactive"))){
            System.out.println("Academic status must be active or inactive");
        }
        if (institutionalEmail == null || !institutionalEmail.contains("@")){
            System.out.println("invalid institutional Email");
        }
        if (advisorName == null || advisorName.trim().length() < 2){
            System.out.println("Advisor name must contain at least 2 characters");
        }
    }

    @Override
    public String getRegistrationSummary() {
        return getAcademicCategory() + ": "
                + getDisplayName() + " ,Member Number: "+memberNumber
                + " ,Department: "+(department !=null ? department.getDepartmentName(): "Not Assigned")
                +" ,Status: "+academicStatus;
    }

    @Override
    public String toString() {
        return "AcademicMember{" +
                "memberNumber='" + memberNumber + '\'' +
                ", department=" + department +
                ", joinDate=" + joinDate +
                ", academicStatus='" + academicStatus + '\'' +
                ", institutionalEmail='" + institutionalEmail + '\'' +
                ", advisorName='" + advisorName + '\'' +
                '}';
    }
}
