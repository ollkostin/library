package ru.practice.kostin.library.service.dto;


public class ErrorDto {

    private String message;

    private int code;

    public ErrorDto(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDto() {
    }
}
