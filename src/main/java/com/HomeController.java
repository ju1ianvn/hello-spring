package com.example.hellospring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class HomeControllerTest {

    @RequestMapping("/hola")
    public @ResponseBody String homeController() {
        return "Hola";
    }
}