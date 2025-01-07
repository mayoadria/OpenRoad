package com.copernic.projecte2_openroad.controllers;

import com.copernic.projecte2_openroad.model.enums.Pais;
import com.copernic.projecte2_openroad.model.mongodb.DocumentClient;
import com.copernic.projecte2_openroad.model.mysql.Client;
import com.copernic.projecte2_openroad.security.TipusPermis;
import com.copernic.projecte2_openroad.service.mysql.UsuariServiceSQL;
import com.copernic.projecte2_openroad.service.mongodb.DocumentServiceMongo;
import jakarta.validation.Valid;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para gestionar el registro de nuevos clientes en la aplicación.
 * Permite a los usuarios registrarse proporcionando su información personal
 * y cargando documentos como el DNI y el carnet de conducir.
 */
@Controller
@RequestMapping("/registre")
public class RegistroClienteController {

    @Autowired
    private UsuariServiceSQL clientServiceSQL;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DocumentServiceMongo documentServiceMongo; // Servicio para interactuar con MongoDB

    /**
     * Muestra la página de registro para que el usuario ingrese sus datos.
     *
     * @param model el modelo utilizado para pasar los datos a la vista.
     * @return el nombre de la vista "Registre" para que el usuario complete el registro.
     */
    @GetMapping("")
    public String Registre(Model model) {
        model.addAttribute("paisos", Pais.values());
        model.addAttribute("client", new Client());
        return "Registre"; // Vista "Registre"
    }

    /**
     * Procesa los datos enviados desde el formulario de registro y guarda
     * al nuevo cliente tanto en la base de datos MySQL como en MongoDB.
     *
     * Este método realiza las siguientes tareas:
     * <ul>
     *   <li>Valida si el correo electrónico ya está registrado.</li>
     *   <li>Codifica la contraseña antes de guardarla.</li>
     *   <li>Crea un nombre de usuario a partir del correo electrónico.</li>
     *   <li>Guarda los datos del cliente en MySQL.</li>
     *   <li>Guarda los documentos de identificación (DNI y carnet de conducir) en MongoDB.</li>
     * </ul>
     *
     * @param cli el cliente con los datos proporcionados por el usuario.
     * @param result los resultados de la validación del formulario.
     * @param dniFile el archivo que contiene la imagen del DNI del cliente.
     * @param carnetFile el archivo que contiene la imagen del carnet de conducir del cliente.
     * @param model el modelo utilizado para pasar los datos a la vista.
     * @return la vista correspondiente dependiendo del resultado del registro:
     *         redirige al login si todo está correcto, o muestra un mensaje de error si hubo un problema.
     */
    @PostMapping("/new")
    public String save(
            @Valid @ModelAttribute("client") Client cli, BindingResult result,
            @RequestParam("dniFile") MultipartFile dniFile,
            @RequestParam("carnetFile") MultipartFile carnetFile,
            Model model
    ) {
        try {
            // Verificar si el email ya está registrado
            if (clientServiceSQL.existeEmail(cli.getEmail())) {
                result.rejectValue("email", "error.cli", "El correu electronic ya está registrat");
            }
            if(clientServiceSQL.existeDni(cli.getDni())) {
                result.rejectValue("dni", "error.cli", "El dni ja está registrat");
            }

            // Procesar y guardar datos en MySQL
            cli.setContrasenya(passwordEncoder.encode(cli.getContrasenya())); // Codificar contraseña
            String[] part = cli.getEmail().split("@");
            String username = part[0];
            cli.setNomUsuari(username); // Crear nombre de usuario
            cli.setPermisos(TipusPermis.CLIENT.toString());
            cli.setEnabled(false); // Usuario no habilitado hasta validación

            // Si hay errores, regresar a la vista de registro con los mensajes de error
            if (result.hasErrors()) {
                model.addAttribute("paisos", Pais.values());
                return "Registre";
            } else {
                // Guardar cliente en la base de datos MySQL
                clientServiceSQL.guardarClient(cli);

                // Procesar y guardar imágenes en MongoDB
                DocumentClient document = new DocumentClient();
                document.setIdClient(cli.getDni());

                // Crear un mapa con nombres descriptivos para las imágenes
                Map<String, Binary> docMap = new HashMap<>();
                if (!dniFile.isEmpty()) {
                    docMap.put("dni", new Binary(dniFile.getBytes()));
                }
                if (!carnetFile.isEmpty()) {
                    docMap.put("carnetConduir", new Binary(carnetFile.getBytes()));
                }

                document.setClientDoc(docMap);

                // Guardar los documentos en MongoDB
                documentServiceMongo.guardarDocument(document);

                return "redirect:/login"; // Redirigir al login tras registro exitoso
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "error"; // Página de error si ocurre una excepción
        }
    }
}
