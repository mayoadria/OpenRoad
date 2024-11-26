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
                .dni("45884552N")
                .nom("Marc")
                .cognom1("Botana")
                .cognom2("Martínez")
                .numContacte1(620016600)
                .email("marc.botana@gmail.com")
                .contrasenya("Hola1234")
                .nomUsuari("marc1234")
                .reputacio(Reputacio.NORMAL)
                .build();

        LocalDate dataCom = LocalDate.parse("2002-03-24");
        Double val = 9.30;
        Boolean rec = true;
        String miss = "Muy buen coche!";

        var comentari = new HistoricComentari(null, client, dataCom, val, rec, miss, 8);

        clientRepo.save(client);

        comentarisRepo.save(comentari);
        */
    }

}
