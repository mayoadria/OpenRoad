package com.copernic.projecte2_openroad.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class PanellAdminController {

        @GetMapping("/")
        public String index(){
            return "PanellAdmin";
        }

        @GetMapping("/menuVehicles")
        public String menuVehicles(){
            return "menuVehicles";
        }

}
