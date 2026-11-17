package com.auca.enrollment;

import com.auca.course.Course;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;

import java.time.LocalDate;

public class Enrollment implements Identifiable, Registrable {
    private String enrollmentId;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private String semester;
    private String enrollmentStatus;

    public Enrollment(String enrollmentId, Student student, Course course, LocalDate enrollmentDate, String semester, String enrollmentStatus) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.semester = semester;
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    @Override
    public String getId() {
        return enrollmentId;
    }

    @Override
    public String getDisplayName() {
        return student.getDisplayName()+
                " - " + course.getCourseTitle();
    }

    @Override
    public boolean isActive() {
        return "active".equalsIgnoreCase(enrollmentStatus);
    }

    @Override
    public void validate() {
        if (enrollmentId == null || enrollmentId.trim().isEmpty()) {
            System.out.println("Enrollment ID is required");
        }
        if (student == null){
            System.out.println("Student is required. ");
        }
        if (course == null){
            System.out.println("Course is required");
        }
        if (enrollmentDate== null || enrollmentDate.isAfter(LocalDate.now())){
            System.out.println("Enrollment Date can not be in future ");
        }
        if (semester == null || semester.trim().isEmpty()){
            System.out.println("Semester is required ");
        }
        if (enrollmentStatus == null || enrollmentStatus.equalsIgnoreCase("active")
        || enrollmentStatus.equalsIgnoreCase("completed")
        || enrollmentStatus.equalsIgnoreCase("canceled")){
            System.out.println("Invalid enrollment status");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return "Enrollment ID: " + enrollmentId
                + " ,Student: " +
                (student != null ? student.getDisplayName(): "Not Assigned") + " ,Course: "+
                (course != null ? course.getCourseTitle(): "Not Assigned") + " ,Status: "+ enrollmentStatus;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId='" + enrollmentId + '\'' +
                ", student=" + (student != null ? student.getDisplayName(): "Not Assigned") +
                ", course=" + (course != null ? course.getCourseTitle() : "Not Assigned") +
                ", enrollmentDate=" + enrollmentDate +
                ", semester='" + semester + '\'' +
                ", enrollmentStatus='" + enrollmentStatus + '\'' +
                '}';
    }

}
