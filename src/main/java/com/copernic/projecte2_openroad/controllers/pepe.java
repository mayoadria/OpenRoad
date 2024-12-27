package com.copernic.projecte2_openroad.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class pepe {

    @GetMapping("/pepe")
    public String pepeC() {
        return "pepe";
    }
}
