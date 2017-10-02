package ru.practice.kostin.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/books")
    public String books(){
        return "books";
    }

    @RequestMapping("/users")
    public String users(){
        return "users";
    }
}
