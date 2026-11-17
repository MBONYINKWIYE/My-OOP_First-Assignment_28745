package com.auca.department;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.teacher.Teacher;

public class Department implements Identifiable, Registrable {
    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String officeLocation;
    private String contactEmail;
    private Teacher headTeacher;

    public Department(String departmentId, String departmentName, String departmentCode, String officeLocation, String contactEmail, Teacher headTeacher) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.officeLocation = officeLocation;
        this.contactEmail = contactEmail;
        this.headTeacher = headTeacher;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getCantactEmail() {
        return contactEmail;
    }

    public void setCantactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Teacher getHeadTeacher() {
        return headTeacher;
    }

    public void setHeadTeacher(Teacher headTeacher) {
        this.headTeacher = headTeacher;
    }

    @Override
    public String getId() {
        return departmentId;
    }

    @Override
    public String getDisplayName() {
        return departmentName;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() {
        if (departmentId == null || departmentId.trim().isEmpty()){
            System.out.println("Department ID is required");
        }
        if (departmentName == null || departmentName.trim().length() < 2){
            System.out.println("Department NAme must be at least 2 characters ");
        }
        if (departmentCode == null || departmentCode.trim().isEmpty()){
            System.out.println("Department Code is required ");
        }
        if (officeLocation == null || officeLocation.trim().isEmpty()){
            System.out.println("Office Location is required");
        }
        if (contactEmail == null || !contactEmail.contains("@")){
            System.out.println("Invalid department Email");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return "Department: " + departmentName + " ,Code: " + departmentCode +
                " ,ID: "+departmentId +
                "Head Teacher: " + (headTeacher != null ? headTeacher.getDisplayName(): "NotAssigned");
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", departmentCode='" + departmentCode + '\'' +
                ", officeLocation='" + officeLocation + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", headTeacher=" + (headTeacher != null ? headTeacher.getDisplayName(): "NotAssigned") +
                '}';
    }
}
