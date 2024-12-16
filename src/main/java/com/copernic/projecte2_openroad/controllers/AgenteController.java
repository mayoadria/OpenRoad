package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.mysql.Agent;
import com.copernic.projecte2_openroad.security.TipusPermis;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AgenteController
    {

        @Autowired
        UsuariServiceSQL usuariServiceSQL;

        @Autowired
        PasswordEncoder passwordEncoder;

        @GetMapping("")
        public String crearAdmin() {
        return "CrearAgente";
    }

        @PostMapping("/new")
        public String crearAgente(@ModelAttribute("agent") Agent agent) {
        agent.setContrasenya(passwordEncoder.encode(agent.getContrasenya()));
        String[] part = agent.getEmail().split("@");
        String username = part[0];
        agent.setNomUsuari(username);
        agent.setPermisos(TipusPermis.MOSTRAR_PEPE.toString());

        usuariServiceSQL.guardarAgent(agent);

        return "redirect:/admin/dashboard";
    }
    }

