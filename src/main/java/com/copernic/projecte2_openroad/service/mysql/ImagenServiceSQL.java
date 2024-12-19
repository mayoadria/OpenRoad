package com.copernic.projecte2_openroad.service.mysql;

import com.copernic.projecte2_openroad.model.mysql.Imagen;
import com.copernic.projecte2_openroad.repository.mysql.ImagenesRepositorySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImagenServiceSQL {

    @Autowired
    private ImagenesRepositorySQL imageRepository;

    // Guardar imagen en la base de datos
    public String saveImage(MultipartFile file) throws IOException {
        Imagen image = new Imagen();
        image.setNombre(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setImageData(file.getBytes());

        imageRepository.save(image);
        return "Imagen guardada exitosamente: " + file.getOriginalFilename();
    }

    // Obtener imagen por ID
    public byte[] getImage(Long id) {
        Optional<Imagen> dbImage = imageRepository.findById(id);
        return dbImage.map(Imagen::getImageData).orElse(null);
    }
}
