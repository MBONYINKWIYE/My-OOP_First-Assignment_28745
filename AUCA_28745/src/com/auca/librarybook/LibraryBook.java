package com.auca.librarybook;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

public class LibraryBook implements Identifiable, Registrable {
    private String bookId;
    private String isbn;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public LibraryBook(String bookId, String isbn, String title, String author, int totalCopies, int availableCopies) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public String getId() {
        return bookId;
    }

    @Override
    public String getDisplayName() {
        return title;
    }

    @Override
    public boolean isActive() {
        return availableCopies > 0;
    }

    @Override
    public void validate() {

        if (bookId == null || bookId.trim().isEmpty()) {
            System.out.println("Book ID is required");
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            System.out.println("ISBN for book is required");
        }
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Book title is required");
        }
        if (author == null || author.trim().isEmpty()) {
            System.out.println("Book Author is required");
        }
        if (totalCopies <= 0) {
            System.out.println("Please Enter Positive Number of Copies");
        }
        if (availableCopies < totalCopies) {
            System.out.println("The available Copies must be Less than Total Copies");
        }

    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String getRegistrationSummary() {
        return "Book ==> BookID: "+bookId
                +"\n ISBN: "+isbn
                +"\n Title: " + title+ " ,Author "+author;
    }

    @Override
    public String toString() {
        return "LibraryBook{" +
                "bookId='" + bookId + '\'' +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                '}';
    }
}
