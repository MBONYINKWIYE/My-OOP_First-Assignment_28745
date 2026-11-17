package com.auca.feepayment;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FeePayment implements Identifiable, Registrable {
    private String paymentId;
    private Student student;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String recieptNumber;

    public FeePayment(String paymentId, Student student, BigDecimal amount, LocalDate paymentDate, String paymentMethod, String recieptNumber) {
        this.paymentId = paymentId;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.recieptNumber = recieptNumber;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRecieptNumber() {
        return recieptNumber;
    }

    public void setRecieptNumber(String recieptNumber) {
        this.recieptNumber = recieptNumber;
    }

    @Override
    public String getId() {
        return paymentId;
    }

    @Override
    public String getDisplayName() {
        return "Fee Payment:" + recieptNumber;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() {

        if (paymentId == null || paymentId.trim().isEmpty()){
            System.out.println("Payment ID is required .");
        }
        if (student == null){
            System.out.println("Student is required . ");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            System.out.println("Payment amount must be greater than zero. ");
        }
        if (paymentDate == null || paymentDate.isAfter(LocalDate.now())){
            System.out.println("Payment Date must not be in future ");
        }
        if (paymentMethod == null || paymentMethod.trim().isEmpty()){
            System.out.println("Payment Method is required .");
        }
        if (recieptNumber == null || recieptNumber.trim().isEmpty()){
            System.out.println("The Reciept Number is required ");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return "Fee Payment ==> Receipt: "+ recieptNumber
                + "\n ,Amount: "+amount
                +" ,Student: "
                +(student != null ? student.getDisplayName(): "Not Assigned");
    }

    @Override
    public String toString() {
        return "FeePayment{" +
                "paymentId='" + paymentId + '\'' +
                ", student=" + (student != null? student.getDisplayName():"Not Assigned") +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", recieptNumber='" + recieptNumber + '\'' +
                '}';
    }

}
