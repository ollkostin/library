package ru.practice.kostin.library.model;

public class Book {
    private String isn;
    private String name;
    private String author;
    private User user;

    public Book(String isn, String name, String author) {
        this.isn = isn;
        this.name = name;
        this.author = author;
    }

    public Book(String isn, String name, String author, User user) {
        this.isn = isn;
        this.name = name;
        this.author = author;
        this.user = user;
    }

    public Book(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (!getIsn().equals(book.getIsn())) return false;
        if (!getName().equals(book.getName())) return false;
        return getAuthor().equals(book.getAuthor());
    }

    @Override
    public int hashCode() {
        int result = getIsn().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getAuthor().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isn=" + isn +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public String getIsn() {
        return isn;
    }
    public void setIsn(String isn) {
        this.isn = isn;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
