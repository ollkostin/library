package ru.practice.kostin.library.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDto {
    private String isn;
    private String name;
    private String author;
    private String username;

    public BookDto(String isn, String name, String author, String username) {
        this.isn = isn;
        this.name = name;
        this.author = author;
        this.username = username;
    }

    public BookDto(String isn, String name, String author) {
        this.isn = isn;
        this.name = name;
        this.author = author;
    }

    public BookDto() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
