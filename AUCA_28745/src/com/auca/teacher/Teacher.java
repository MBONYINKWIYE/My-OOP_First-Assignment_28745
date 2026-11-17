package com.auca.teacher;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Teacher extends AcademicMember {
    private String employeeNumber;
    private String specialization;
    private LocalDate hireDate;
    private BigDecimal salary;
    private String officeNumber;
    private String employmentType;

    public Teacher(String personId, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String memberNumber, Department department, LocalDate joinDate, String academicStatus, String institutionalEmail, String advisorName, String employeeNumber, String specialization, LocalDate hireDate, BigDecimal salary, String officeNumber, String employmentType) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth, memberNumber, department, joinDate, academicStatus, institutionalEmail, advisorName);
        this.employeeNumber = employeeNumber;
        this.specialization = specialization;
        this.hireDate = hireDate;
        this.salary = salary;
        this.officeNumber = officeNumber;
        this.employmentType = employmentType;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    @Override
    public String getAcademicCategory() {
        return "Teacher";
    }

    @Override
    public String getRoleDescription() {
        return "Teacher";
    }

    @Override
    public void validate() {
        super.validate();

        if (employeeNumber == null || employeeNumber.trim().isEmpty()) {
            System.out.println("Employee Number is required");
        }
        if (specialization == null || specialization.trim().isEmpty()) {
            System.out.println("Specialization is required");
        }
        if (hireDate == null || hireDate.isAfter(LocalDate.now())) {
            System.out.println("Hire date is required and can not be in future");
        }
        if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0){
            System.out.println("Salary must be Greater than 0");
        }
        if (officeNumber == null || officeNumber.trim().isEmpty()) {
            System.out.println("Office Number is required");
        }
        if (employmentType == null || employmentType.trim().isEmpty()) {
            System.out.println("Employment Type is required");
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                "employeeNumber='" + employeeNumber + '\'' +
                ", specialization='" + specialization + '\'' +
                ", hireDate=" + hireDate +
                ", salary=" + salary +
                ", officeNumber='" + officeNumber + '\'' +
                ", employmentType='" + employmentType;
    }
}
