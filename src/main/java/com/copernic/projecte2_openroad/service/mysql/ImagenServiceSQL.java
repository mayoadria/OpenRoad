package com.copernic.projecte2_openroad.service.mysql;

import com.copernic.projecte2_openroad.model.mysql.Imagen;
import com.copernic.projecte2_openroad.repository.mysql.ImagenesRepositorySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Servicio encargado de gestionar las imágenes dentro de la base de datos.
 * Proporciona métodos para guardar y recuperar imágenes asociadas a un identificador único (ID).
 *
 * Este servicio utiliza el repositorio {@link ImagenesRepositorySQL} para interactuar con la base de datos
 * y realizar las operaciones CRUD sobre las imágenes almacenadas.
 *
 * @see ImagenesRepositorySQL
 * @see Imagen
 */
@Service
public class ImagenServiceSQL {

    /**
     * Repositorio para interactuar con la base de datos y manejar las operaciones sobre las imágenes.
     */
    @Autowired
    private ImagenesRepositorySQL imageRepository;

    /**
     * Guarda una imagen en la base de datos.
     *
     * Este método recibe un archivo MultipartFile, crea una instancia de {@link Imagen} y almacena
     * el archivo en la base de datos. La imagen se guarda con su nombre original, tipo y los datos
     * binarios del archivo.
     *
     * @param file El archivo de imagen que se va a guardar.
     * @return Un mensaje indicando si la imagen se guardó exitosamente o no.
     * @throws IOException Si ocurre un error al leer los bytes del archivo.
     */
    public String saveImage(MultipartFile file) throws IOException {
        Imagen image = new Imagen();
        image.setNombre(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImageData(file.getBytes());

        imageRepository.save(image);
        return "Imagen guardada exitosamente: " + file.getOriginalFilename();
    }

    /**
     * Recupera los datos binarios de una imagen desde la base de datos utilizando su ID.
     *
     * Este método consulta el repositorio por el ID de la imagen y devuelve los datos binarios
     * de la imagen si se encuentra en la base de datos. Si la imagen no existe, devuelve {@code null}.
     *
     * @param id El ID de la imagen que se desea recuperar.
     * @return Los datos binarios de la imagen o {@code null} si la imagen no existe.
     */
    public byte[] getImage(Long id) {
        Optional<Imagen> dbImage = imageRepository.findById(id);
        return dbImage.map(Imagen::getImageData).orElse(null);
    }
}
