package com.example.hellospring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class HelloSpringApplication {

    static void main(String[] args) {
        SpringApplication.run(HelloSpringApplication, args)
    }

    @GetMapping("/hello")
    String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name)
    }

}
