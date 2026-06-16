package com.techlab.spring.service;

import com.techlab.spring.entity.Imagen;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImagenService {
    Imagen subirImagenACloudinary(MultipartFile archivo, Integer productoId,String descripcion) throws IOException;

    void borrarImagen(Integer id);
}
