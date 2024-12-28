package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.Pais;
import com.copernic.projecte2_openroad.model.mysql.Agent;
import com.copernic.projecte2_openroad.model.mysql.Localitat;
import com.copernic.projecte2_openroad.security.TipusPermis;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

        @GetMapping("/newAgent")
        public String mostrarFormAgent(Model model) {
            Agent agent = new Agent();
            agent.setLocalitat(new Localitat());
            
            model.addAttribute("paisos", Pais.values());
            model.addAttribute("agent", agent);
        return "crearAgent";
    }

        @PostMapping("/new")
        public String crearAgente(@ModelAttribute Agent agent) {
        agent.setContrasenya(passwordEncoder.encode(agent.getContrasenya()));
        String[] part = agent.getEmail().split("@");
        String username = part[0];
        agent.setNomUsuari(username);
        agent.setAdreca(agent.getLocalitat().getDireccio());
        agent.setCodiPostal(agent.getLocalitat().getCodiPostalLoc());
        agent.setPermisos(TipusPermis.MODIFICAR_PERFIL.toString());
        agent.setEnabled(true);
        usuariServiceSQL.guardarAgent(agent);

        return "redirect:/admin/dashboard";
    }
    }
