package com.copernic.projecte2_openroad;

//import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
import org.springframework.context.ApplicationContext;

import com.copernic.projecte2_openroad.model.enums.Reputacio;
import com.copernic.projecte2_openroad.model.mongodb.HistoricComentari;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.repository.mongodb.ComentarisRepositoryMongo;
import com.copernic.projecte2_openroad.repository.mysql.ClientRepositorySQL;
*/

@SpringBootApplication
public class Projecte2OpenRoadApplication {

    public static void main(String[] args) {
        SpringApplication.run(Projecte2OpenRoadApplication.class, args);

        /*
        ApplicationContext context = SpringApplication.run(Projecte2OpenRoadApplication.class, args);

        var comentarisRepo = context.getBean(ComentarisRepositoryMongo.class);

        var clientRepo = context.getBean(ClientRepositorySQL.class);

        var client = Client.builder()
                .dni("12345678A")
                .nom("Paco")
                .cognom1("Lopez")
                .cognom2("Garriga")
                .numContacte1(630288174)
                .email("paco@gmail.com")
                .contrasenya("Hola1234")
                .nomUsuari("paco1234")
                .reputacio(Reputacio.NORMAL)
                .build();

        LocalDate dataCom = LocalDate.parse("2022-03-22");
        Double val = 9.30;
        Boolean rec = true;
        String miss = "Muy buen coche!";

        var comentari = new HistoricComentari(null, client, dataCom, val, rec, miss, 8);

        clientRepo.save(client);

        comentarisRepo.save(comentari);
        */
    }

}
