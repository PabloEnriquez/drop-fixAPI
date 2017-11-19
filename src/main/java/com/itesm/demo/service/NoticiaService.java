package com.itesm.demo.service;

import com.itesm.demo.dao.NoticiaDAO;
import com.itesm.demo.domain.Noticia;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaDAO noticiaDAO;

    public Optional<Noticia> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Pattern p = Pattern.compile("(^[a-zA-Z0-9][ A-Za-z0-9_-]*$)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(uuid);
        boolean b = m.find();
        if ( (!uuid.isEmpty()) /*&& (!b)*/ ){
            Optional<Noticia> noticia = noticiaDAO.getByUuid(uuid);
            return noticia;
        }else {
            return Optional.empty();
        }
    }

    public Optional<Noticia> insert(Noticia noticia){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        if ( (noticia.getTitulo() != null) && (noticia.getDescripcion() != null) && (noticia.getUrl() != null) ){
            noticia.setStatus(1);
            return noticiaDAO.insert(noticia);
        }else{
            return Optional.empty();
        }
    }

    public Optional<Noticia> update(Noticia noticia){
        // validar los datos y cualquier lógica de negocio
        Optional<Noticia> noticiaDB = noticiaDAO.getByUuid(noticia.getUuid());
        if(noticiaDB.isPresent()) {
            if(noticia.getStatus() == null){
                noticia.setStatus(noticiaDB.get().getStatus());
            }
            if(noticia.getFecha_modificacion() == null){
                noticia.setFecha_modificacion(noticiaDB.get().getFecha_modificacion());
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
        if ( (page != null && page >= 0) && (size != null && size > 0) ){
            return noticiaDAO.list(page, size);
        }else {
            return Optional.empty();
        }
    }

    public Optional<List<Noticia>> getTitulo(String titulo, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( (!titulo.isEmpty()) && (page != null && page >= 0) && (size != null && size > 0) ){
            return noticiaDAO.getByTitulo(titulo, page, size);
        }else{
            return Optional.empty();
        }
    }

}
