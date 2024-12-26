package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Agent;
import com.copernic.projecte2_openroad.model.mysql.Reserva;
import com.copernic.projecte2_openroad.service.mysql.ReservaServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DashboardClient {

    @Autowired
    private ReservaServiceSQL reservaServiceSQL;

    @GetMapping("/client/reserves")
    public String reserves(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Reserva> reserva = reservaServiceSQL.findReservasByClient_nom(username);
        model.addAttribute("reserva" ,reserva);
        return "DashboardClient";
    }


}
