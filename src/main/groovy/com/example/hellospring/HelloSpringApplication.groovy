package com.example.hellospring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class HelloSpringApplication {

    static void main(String[] args) {
        SpringApplication.run(HelloSpringApplication, args)
    }

    @GetMapping("/")
    String home() {
        return "<html>" +
                    "<head>" +
                        "<title>ju1ianvn</title>" +
                    "</head>" +
                    "<body>" +
                        "<h1>ju1ianvn</h1><br><h2>Devops</h2>" +
                    "</body>" +
                "</html>";
    }

    @GetMapping("/hello")
    String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name)
    }

    @RequestMapping("/add")
    @ResponseBody Float add(@RequestParam(name = "a") Float a, @RequestParam(name = "b") Float b) {
        return a + b
    }

}
