package com.likelion.springpractice;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Component

public class HelloController {

    @GetMapping("hello")
    public String helloWorld() {
        return "Hello World!";
    }
}
