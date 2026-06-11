package com.techlab.spring.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techlab.spring.entity.Imagen;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.exception.ProductoNotFoundException;
import com.techlab.spring.repository.ImagenRepository;
import com.techlab.spring.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImagenService implements IImagenService {
    private final Cloudinary cloudinary;
    private final ImagenRepository repo;
    private final ProductoRepository productoRepository;

    public ImagenService(Cloudinary cloudinary, ImagenRepository repo, ProductoRepository productoRepository) {
        this.cloudinary = cloudinary;
        this.repo = repo;
        this.productoRepository = productoRepository;
    }

    @Override
    public Imagen subirImagenACloudinary(MultipartFile archivo, Integer productoId) throws IOException {
        Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new ProductoNotFoundException("El procucto no existe"));

        Map cloudinaryResponse = cloudinary.uploader().upload(archivo.getBytes(), ObjectUtils.emptyMap());

        String urlImagen = cloudinaryResponse.get("url").toString();
        String publicId = cloudinaryResponse.get("public_id").toString();

        Imagen nuevaImagen = new Imagen();
        nuevaImagen.setUrl(urlImagen);
        nuevaImagen.setPublicId(publicId);

        nuevaImagen.setProducto(producto);
        return repo.save(nuevaImagen);
    }
}
