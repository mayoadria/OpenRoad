package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.EstatLocalitat;
import com.copernic.projecte2_openroad.model.enums.Pais;
import com.copernic.projecte2_openroad.model.mysql.Agent;
import com.copernic.projecte2_openroad.model.mysql.Localitat;
import com.copernic.projecte2_openroad.security.TipusPermis;
import com.copernic.projecte2_openroad.service.mysql.LocalitatServiceSQL;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AgenteController {

    @Autowired
    UsuariServiceSQL usuariServiceSQL;

    @Autowired
    LocalitatServiceSQL localitatService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/newAgent")
    public String mostrarFormAgent(Model model) {
        Agent agent = new Agent();
        agent.setLocalitat(new Localitat());

        model.addAttribute("paisos", Pais.values());
        model.addAttribute("agent", agent);
        model.addAttribute("localitats", localitatService.llistarLocalitats());
        return "crearAgent";
    }

    @PostMapping("/new")
    public String crearAgente(@Valid @ModelAttribute("agent") Agent agent, BindingResult result,
                              @ModelAttribute("localitatOption") String localitatOption, Model model) {

        // Verificar si el email ya existe
        if (usuariServiceSQL.existeEmail(agent.getEmail())) {
            result.rejectValue("email", "error.agent", "El correo electrónico ya está registrado");
        }

        // Manejar errores de validación
        if (result.hasErrors()) {
            model.addAttribute("paisos", Pais.values());
            model.addAttribute("localitats", localitatService.llistarLocalitats());
            return "crearAgent";
        }

        
        
        // Procesar la localidad
        if (agent.getLocalitat() != null) {
            Localitat localitat = agent.getLocalitat();
            localitat.setEstatLocalitat(EstatLocalitat.LLIURE);
            localitatService.guardarLocalitat(localitat);
        }
        if ("new".equals(localitatOption)) {
            Localitat novaLocalitat = agent.getLocalitat();
            if (novaLocalitat == null) {
                novaLocalitat = new Localitat();
                agent.setLocalitat(novaLocalitat);
            }
            novaLocalitat.setEstatLocalitat(EstatLocalitat.ASSIGNADA);
            localitatService.guardarLocalitat(novaLocalitat);
            agent.setAdreca(novaLocalitat.getDireccio());
            agent.setCodiPostal(novaLocalitat.getCodiPostalLoc());
        } else if (localitatOption.isEmpty()) {
            if (agent.getLocalitat() != null) {
                Localitat localitat = agent.getLocalitat();
                localitat.setEstatLocalitat(EstatLocalitat.LLIURE);
            }
            agent.setLocalitat(null);
            
        } else {
            Localitat localitatExist = localitatService.findByNomUsuari(localitatOption);
            localitatExist.setEstatLocalitat(EstatLocalitat.ASSIGNADA);
            agent.setLocalitat(localitatExist);
            agent.setAdreca(localitatExist.getDireccio());
            agent.setCodiPostal(localitatExist.getCodiPostalLoc());
        }

        // Configurar datos adicionales del agente
        agent.setContrasenya(passwordEncoder.encode(agent.getContrasenya()));
        String[] partes = agent.getEmail().split("@");
        agent.setNomUsuari(partes[0]);
        agent.setPermisos(TipusPermis.AGENT.toString());
        agent.setEnabled(true);

        // Guardar agente
        usuariServiceSQL.guardarAgent(agent);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/editAgent/{dniAgent}")
    public String editarAgente(@PathVariable String dniAgent, @Valid @ModelAttribute("agent") Agent agent, BindingResult result,
                              @ModelAttribute("localitatOption") String localitatOption, Model model) {

        // Manejar errores de validación
        if (result.hasErrors()) {
            model.addAttribute("paisos", Pais.values());
            model.addAttribute("localitats", localitatService.llistarLocalitats());
            return "editarAgent";
        }

        // Procesar la localidad
        if (agent.getLocalitat() != null) {
            Localitat localitat = agent.getLocalitat();
            localitat.setEstatLocalitat(EstatLocalitat.LLIURE);
            localitatService.guardarLocalitat(localitat);
        }
        if ("new".equals(localitatOption)) {
            Localitat novaLocalitat = agent.getLocalitat();
            if (novaLocalitat == null) {
                novaLocalitat = new Localitat();
                agent.setLocalitat(novaLocalitat);
            }
            novaLocalitat.setEstatLocalitat(EstatLocalitat.ASSIGNADA);
            localitatService.guardarLocalitat(novaLocalitat);
            agent.setAdreca(novaLocalitat.getDireccio());
            agent.setCodiPostal(novaLocalitat.getCodiPostalLoc());
        } else if (localitatOption.isEmpty()) {
            agent.setLocalitat(null);
        } else {
            Localitat localitatExist = localitatService.findByNomUsuari(localitatOption);
            localitatExist.setEstatLocalitat(EstatLocalitat.ASSIGNADA);
            agent.setLocalitat(localitatExist);
            agent.setAdreca(localitatExist.getDireccio());
            agent.setCodiPostal(localitatExist.getCodiPostalLoc());
        }

        // Configurar datos adicionales del agente
        agent.setContrasenya(passwordEncoder.encode(agent.getContrasenya()));
        String[] partes = agent.getEmail().split("@");
        agent.setNomUsuari(partes[0]);
        agent.setPermisos(TipusPermis.AGENT.toString());
        agent.setEnabled(true);

        // Guardar agente
        usuariServiceSQL.modificarAgent(agent, dniAgent);

        return "redirect:/admin/dashboard";
    }

}
