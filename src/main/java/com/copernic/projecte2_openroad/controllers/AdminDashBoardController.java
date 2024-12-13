package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Agent;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDashBoardController {

    @Autowired
    UsuariServiceSQL usuariServiceSQL;


    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Agent> agents = usuariServiceSQL.llistarAgents();
        List<Client> clients = usuariServiceSQL.llistarClient();
        model.addAttribute("agents", agents);
        model.addAttribute("clients", clients);
        return "dashboard"; // dashboard.html en templates.
    }

    @GetMapping("/crearAgente")
    public String crearAgent(){
        return "CrearAgente";
    }
}
