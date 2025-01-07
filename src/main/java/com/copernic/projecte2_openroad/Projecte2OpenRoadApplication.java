/**
 * Classe principal de l'aplicació Spring Boot per al projecte "Projecte2OpenRoad".
 * Aquesta classe inicialitza i arrenca l'aplicació.
 */
package com.copernic.projecte2_openroad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe d'entrada per a l'aplicació Spring Boot.
 */
@SpringBootApplication
public class Projecte2OpenRoadApplication {

    /**
     * Mètode principal per arrencar l'aplicació.
     *
     * @param args arguments passats a l'execució de l'aplicació.
     */
    public static void main(String[] args) {
        SpringApplication.run(Projecte2OpenRoadApplication.class, args);
    }
}
