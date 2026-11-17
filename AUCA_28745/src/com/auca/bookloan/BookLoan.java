package com.auca.bookloan;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;

import java.time.LocalDate;

public class BookLoan implements Identifiable, Registrable {
    private String loanId;
    private Student student;
    private LibraryBook book;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public BookLoan(String loanId, Student student, LibraryBook book, LocalDate borrowDate, LocalDate dueDate, LocalDate returnedDate) {
        this.loanId = loanId;
        this.student = student;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LibraryBook getBook() {
        return book;
    }

    public void setBook(LibraryBook book) {
        this.book = book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    @Override
    public String getId() {
        return loanId;
    }

    @Override
    public String getDisplayName() {
        return "Book Loan: "+(book != null ? book.getTitle() : "Unknown Book");
    }

    @Override
    public boolean isActive() {
        return returnedDate == null;
    }

    @Override
    public void validate() {

        if (loanId == null || loanId.trim().isEmpty()) {
            System.out.println("Please Loan ID is required. ");
        }
        if (student == null) {
            System.out.println("Student is required");
        }
        if (book == null) {
            System.out.println("Book is required");
        }
        if (borrowDate == null || borrowDate.isAfter(LocalDate.now())) {
            System.out.println("Borrowed date is required and cannot be in future");
        }
        if (dueDate == null) {
            System.out.println("Due Date is required ");
        } else if (borrowDate != null && dueDate != null && dueDate.isBefore(borrowDate)) {
            System.out.println("Due date must be after borrowed date");
        }

        if (returnedDate == null) {
            System.out.println("Returned Date required and can not be in Past");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return "Book Loan: \n Student: "
                +(student != null ? student.getDisplayName(): "Not assigned")+
                "\n Book: " + (book != null ? book.getTitle():"Not assigned")+
                "Status: " + (isActive() ? "BORROWED" : "RETURNED");
    }

    @Override
    public String toString() {
        return "BookLoan{" +
                "loanId='" + loanId + '\'' +
                ", student=" + student +
                ", book=" + book +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnedDate=" + returnedDate +
                '}';
    }
}
