package com.copernic.projecte2_openroad.model.mysql;

// Jakarta
import jakarta.persistence.*;

// Lombok
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

// Enums
import com.copernic.projecte2_openroad.model.enums.Reputacio;

@Entity
@Table(name = "admin")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Admin {

    @Id
    @Column(nullable = false, name = "nom_usuari")
    private String nomUsuari;
    @Column(nullable = false)
    private String contrasenya;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Roles role;
}
