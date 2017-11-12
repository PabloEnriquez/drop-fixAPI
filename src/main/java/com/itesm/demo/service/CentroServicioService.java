package com.itesm.demo.service;

import com.itesm.demo.dao.CentroServicioDAO;
import com.itesm.demo.domain.CentroServicio;
import com.itesm.demo.domain.Usuario;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CentroServicioService {

    @Autowired
    private CentroServicioDAO centroServicioDAO;

    public Optional<CentroServicio> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(uuid);
        boolean b = m.find();
        if ( (!uuid.isEmpty()) && (!b) ){
            Optional<CentroServicio> centroServicio = centroServicioDAO.getByUuid(uuid);
            return centroServicio;
        }else {
            return Optional.empty();
        }
    }

    public Optional<CentroServicio> insert(CentroServicio centroServicio){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        if ( (centroServicio.getLongitud() != null) && (centroServicio.getLatitud() != null) && (centroServicio.getDescripcion() != null)
                && (centroServicio.getDireccion() != null) && (centroServicio.getTitulo() != null) && (centroServicio.getUrl() != null) ){
            centroServicio.setStatus(1);
            return centroServicioDAO.insert(centroServicio);
        }else{
            return Optional.empty();
        }
    }

    public Optional<CentroServicio> update(CentroServicio centroServicio){
        // validar los datos y cualquier lógica de negocio
        Optional<CentroServicio> centroServicioDB = centroServicioDAO.getByUuid(centroServicio.getUuid());
        if(centroServicioDB.isPresent()) {
            if(centroServicio.getStatus() == null){
                centroServicio.setStatus(centroServicioDB.get().getStatus());
            }
            if(centroServicio.getFecha_modificacion() == null){
                centroServicio.setFecha_modificacion(centroServicioDB.get().getFecha_modificacion());
            }
            if(centroServicio.getLongitud() == null){
                centroServicio.setLongitud(centroServicioDB.get().getLongitud());
            }
            if(centroServicio.getLatitud() == null){
                centroServicio.setLatitud(centroServicioDB.get().getLatitud());
            }
            if(centroServicio.getDescripcion() == null){
                centroServicio.setDescripcion(centroServicioDB.get().getDescripcion());
            }
            if(centroServicio.getDireccion() == null){
                centroServicio.setDireccion(centroServicioDB.get().getDireccion());
            }
            if(centroServicio.getTitulo() == null){
                centroServicio.setTitulo(centroServicioDB.get().getTitulo());
            }
            if(centroServicio.getUrl() == null){
                centroServicio.setUrl(centroServicioDB.get().getUrl());
            }
            return centroServicioDAO.update(centroServicio);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<CentroServicio>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( (page != null && page > 0) && (size != null && size > 0) ){
            return centroServicioDAO.list(page, size);
        }else {
            return Optional.empty();
        }
    }

    public Optional<List<CentroServicio>> getTitulo(String titulo, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(titulo);
        boolean b = m.find();
        if ( (!titulo.isEmpty() && (!b)) && (page != null && page > 0) && (size != null && size > 0) ){
            return centroServicioDAO.getByTitulo(titulo, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<CentroServicio>> getDireccion(String direccion, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(direccion);
        boolean b = m.find();
        if ( (!direccion.isEmpty() && (!b)) && (page != null && page > 0) && (size != null && size > 0) ){
            return centroServicioDAO.getByDireccion(direccion, page, size);
        }else{
            return Optional.empty();
        }
    }

}
