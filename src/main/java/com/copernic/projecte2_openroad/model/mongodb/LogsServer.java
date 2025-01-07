/**
 * Classe que representa un registre de log generat pel servidor.
 * Aquesta classe està emmagatzemada en una col·lecció MongoDB anomenada "logs_server".
 * Conté informació sobre el tipus de log i el missatge associat.
 */
package com.copernic.projecte2_openroad.model.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.copernic.projecte2_openroad.model.enums.LogType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un registre de log del servidor emmagatzemat a MongoDB.
 */
@Document(collection = "logs_server")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogsServer {

    /**
     * Identificador únic del log.
     */
    @Id
    private String idLog;

    /**
     * Tipus de log (p. ex., CRITICAL, ERROR, WARNING, INFO).
     */
    private LogType type;

    /**
     * Missatge descriptiu del log.
     */
    private String missatgeLog;
}
