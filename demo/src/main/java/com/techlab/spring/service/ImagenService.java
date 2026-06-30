package com.techlab.spring.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techlab.spring.entity.Imagen;
import com.techlab.spring.entity.Producto;
import com.techlab.spring.exception.ProductoNotFoundException;
import com.techlab.spring.exception.RecursoNotFoundException;
import com.techlab.spring.repository.ImagenRepository;
import com.techlab.spring.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
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

    //funciona
    @Override
    public Imagen subirImagenACloudinary(MultipartFile archivo, Integer productoId, String descripcion) throws IOException {
        Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new ProductoNotFoundException("El procucto no existe"));

        Map<String, String> metadata = new HashMap<>();

        if(descripcion != null && !descripcion.isEmpty()){
            metadata.put("alt", descripcion);
        }

        metadata.put("caption", producto.getNombre());

        Map<String, Object> opcionesSubida = ObjectUtils.asMap("context", metadata);

        Map cloudinaryResponse = cloudinary.uploader().upload(archivo.getBytes(), opcionesSubida);

        String urlImagen = cloudinaryResponse.get("url").toString();
        String publicId = cloudinaryResponse.get("public_id").toString();

        Imagen nuevaImagen = new Imagen();
        nuevaImagen.setUrl(urlImagen);
        nuevaImagen.setPublicId(publicId);
        nuevaImagen.setDescripcion(descripcion);
        nuevaImagen.setProducto(producto);
        return repo.save(nuevaImagen);
    }

    @Override
    public void borrarImagen(Integer id){
        Imagen imagen = repo.findById(id).orElseThrow(() -> new RecursoNotFoundException("La imagen con id: "+ id + " no existe"));

        String publicId = imagen.getPublicId();

        try {
            Map options = ObjectUtils.asMap("invalidate", true);
            Map resultado = cloudinary.uploader().destroy(publicId,options);

            if(!"ok".equals(resultado.get("result"))){
                throw new RuntimeException("Cloudinary no pudo eliminar el archivo");
            }
        }catch (IOException e){
            throw new RuntimeException("Error de conexion al destruir la imagen en Cloudinary: " + e.getMessage());
        }
        repo.delete(imagen);
    }
}
