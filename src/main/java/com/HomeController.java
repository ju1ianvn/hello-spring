package com.example.hellospring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class HomeControllerTest {

    @GetMapping("/hola")
    public @ResponseBody String homeController() {
        return "Hola";
    }
}