package ru.practice.kostin.library.service.dto;

public class ErrorDto {
    private String message;
    private String code;

    public ErrorDto(String code,String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
