package com.itesm.demo.service;

import com.itesm.demo.dao.NoticiaDAO;
import com.itesm.demo.domain.Noticia;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaDAO noticiaDAO;

    public Optional<Noticia> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        //Preguntar al pablovich
        Optional<Noticia> noticia = noticiaDAO.getByUuid(uuid);
        return noticia;
    }

    public Optional<Noticia> insert(Noticia noticia){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        noticia.setStatus(1);//marca error de incopatibilidad con int to java.lang.string
        return noticiaDAO.insert(noticia);
    }

    public Optional<Noticia> update(Noticia noticia){
        // validar los datos y cualquier lógica de negocio
        Optional<Noticia> noticiaDB = noticiaDAO.getByUuid(noticia.getUuid());
        if(noticiaDB.isPresent()) {
            if(noticia.getStatus() == null){
                noticia.setStatus(noticiaDB.get().getStatus());
            }
            if(noticia.getTitulo() == null){
                noticia.setTitulo(noticiaDB.get().getTitulo());
            }
            if(noticia.getDescripcion() == null){
                noticia.setDescripcion(noticiaDB.get().getDescripcion());
            }
            return noticiaDAO.update(noticia);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<Noticia>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return noticiaDAO.list(page, size);
    }

}
