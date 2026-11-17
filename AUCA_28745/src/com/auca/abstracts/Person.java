package com.auca.abstracts;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import java.time.LocalDate;

public abstract class Person implements Identifiable, Registrable {
    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private java.time.LocalDate dateOfBirth;

    public Person(String personId, String firstName,
                  String lastName, String email,
                  String phoneNumber, LocalDate dateOfBirth) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    //Implementations methods of identifiable and Regitrable
    @Override
    public String getId() {
        return personId;
    }

    @Override
    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    @Override
    public abstract boolean isActive();
    public abstract String getRoleDescription();
    @Override
    public void validate() {
        if (personId == null || personId.trim().isEmpty()){
            System.out.println("Person ID is required");
        }
        if (firstName == null || firstName.trim().length() < 2){
            System.out.println("First name is required and must be at least 2 characters long");
        }
        if (lastName == null || lastName.trim().length() < 2){
            System.out.println("Last name is required and must be at least 2 characters long");
        }
        if (email == null || !email.contains("@")){
            System.out.println("Valid email is required");
        }
        if (phoneNumber == null || phoneNumber.trim().length() < 4 || phoneNumber.trim().length() > 15){
            System.out.println("Phone number must contain 4-15 characters");
        }
        if (dateOfBirth == null || !dateOfBirth.isBefore(LocalDate.now())){
            System.out.println("The date of Birth must be in past");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return getRoleDescription()+": "+ getDisplayName() + "(ID: "+personId +")";
    }

    //toString

    @Override
    public String toString() {
        return "Person{" +
                "personId='" + personId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

}
