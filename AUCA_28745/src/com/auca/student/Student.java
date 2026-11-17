package com.auca.student;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;

import java.time.LocalDate;

public class Student extends AcademicMember {
    private String studentNumber;
    private String programName;
    private java.time.LocalDate admissionDate;
    private int studyYear;
    private String guardianPhone;
    private boolean scholarshipStatus;

    public Student(String personId, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth, String memberNumber, Department department, LocalDate joinDate, String academicStatus, String institutionalEmail, String advisorName, String studentNumber, String programName, LocalDate admissionDate, int studyYear, String guardianPhone, boolean scholarshipStatus) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth, memberNumber, department, joinDate, academicStatus, institutionalEmail, advisorName);
        this.studentNumber = studentNumber;
        this.programName = programName;
        this.admissionDate = admissionDate;
        this.studyYear = studyYear;
        this.guardianPhone = guardianPhone;
        this.scholarshipStatus = scholarshipStatus;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public void setGuardianPhone(String guardianPhone) {
        this.guardianPhone = guardianPhone;
    }

    public boolean isScholarshipStatus() {
        return scholarshipStatus;
    }

    public void setScholarshipStatus(boolean scholarshipStatus) {
        this.scholarshipStatus = scholarshipStatus;
    }

    //Implementation methoeds

    @Override
    public String getAcademicCategory() {
        return "Student";
    }

    @Override
    public String getRoleDescription() {

        return "Student";
    }

    @Override
    public void validate() {
        super.validate();
        if (studentNumber == null || studentNumber.trim().isEmpty()) {
            System.out.println("Student number is required.");
        }

        if (programName == null || programName.trim().isEmpty()) {
            System.out.println("Program name is required.");
        }

        if (admissionDate == null || admissionDate.isAfter(LocalDate.now())) {
            System.out.println("Admission date cannot be in the future.");
        }

        if (studyYear <= 0 || studyYear > 8) {
            System.out.println("Study year must be between 1 and 8.");
        }

        if (guardianPhone == null || guardianPhone.trim().length() < 4 || guardianPhone.trim().length() > 15) {
            System.out.println("Invalid guardian phone number.");
        }
    }

    @Override
    public String toString() {
        return super.toString()
                +"studentNumber='" + studentNumber + '\'' +
                ", programName='" + programName + '\'' +
                ", admissionDate=" + admissionDate +
                ", studyYear=" + studyYear +
                ", guardianPhone='" + guardianPhone + '\'' +
                ", scholarshipStatus=" + scholarshipStatus;
    }
}
