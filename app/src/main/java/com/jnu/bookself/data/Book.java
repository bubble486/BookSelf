package com.jnu.bookself.data;

public class Book {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPubYear() {
        return pubYear;
    }

    public void setPubYear(int pubYear) {
        this.pubYear = pubYear;
    }

    public int getPubMonth() {
        return pubMonth;
    }

    public void setPubMonth(int pubMonth) {
        this.pubMonth = pubMonth;
    }

    private String title;
    private int resourceId;
    private String author;
    private String publisher;
    private int pubYear;
    private int pubMonth;

    public Book(String title, int resourceId, String author, String publisher, int pubYear, int pubMonth) {
        this.title = title;
        this.resourceId = resourceId;
        this.author = author;
        this.publisher = publisher;
        this.pubYear = pubYear;
        this.pubMonth = pubMonth;
    }

}
