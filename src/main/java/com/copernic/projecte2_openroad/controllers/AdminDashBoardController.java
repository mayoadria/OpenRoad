package com.copernic.projecte2_openroad.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashBoardController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // dashboard.html en templates.
    }
}
