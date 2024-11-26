package com.copernic.projecte2_openroad.model.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogsServer {

    @Id
    private String idLog;

    private String missatgeLog;
}
