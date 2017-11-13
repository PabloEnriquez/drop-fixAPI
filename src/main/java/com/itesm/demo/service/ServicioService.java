package com.itesm.demo.service;

import com.itesm.demo.dao.CompraDAO;
import com.itesm.demo.dao.ServicioDAO;
import com.itesm.demo.domain.Compra;
import com.itesm.demo.domain.Servicio;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ServicioService {

    @Autowired
    private ServicioDAO servicioDAO;

    @Autowired
    private CompraDAO compraDAO;

    public Optional<Servicio> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(uuid);
        boolean b = m.find();
        if ( (!uuid.isEmpty()) && (!b) ){
            Optional<Servicio> servicio = servicioDAO.getByUuid(uuid);
            return servicio;
        }else {
            return Optional.empty();
        }
    }

    public Optional<Servicio> insert(Servicio servicio){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        if ( (servicio.getCosto() != null && servicio.getCosto() > 0) && (servicio.getDescripcion() != null)
                && (servicio.getNombre() != null) && (servicio.getImagen() != null) ){
            servicio.setStatus(1);
            return servicioDAO.insert(servicio);
        }else{
            return Optional.empty();
        }
    }

    public Optional<Servicio> update(Servicio servicio){
        // validar los datos y cualquier lógica de negocio
        Optional<Servicio> servicioDB = servicioDAO.getByUuid(servicio.getUuid());
        if(servicioDB.isPresent()) {
            if(servicio.getStatus() == null){
                servicio.setStatus(servicioDB.get().getStatus());
            }
            if(servicio.getFecha_modificacion() == null){
                servicio.setFecha_modificacion(servicioDB.get().getFecha_modificacion());
            }
            if(servicio.getCosto() == null){
                servicio.setCosto(servicioDB.get().getCosto());
            }
            if(servicio.getNombre() == null){
                servicio.setNombre(servicioDB.get().getNombre());
            }
            if(servicio.getDescripcion() == null){
                servicio.setDescripcion(servicioDB.get().getDescripcion());
            }
            if(servicio.getImagen() == null){
                servicio.setImagen(servicioDB.get().getImagen());
            }
            return servicioDAO.update(servicio);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<Servicio>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( (page != null && page > 0) && (size != null && size > 0) ){
            return servicioDAO.list(page, size);
        }else {
            return Optional.empty();
        }
    }

    public Optional<List<Compra>> listComprasServicio(Long id_servicio, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( ((id_servicio != null) && (id_servicio > 0)) && (page != null && page > 0) && (size != null && size > 0) ){
            return compraDAO.listComprasServicio(id_servicio, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<Servicio>> getNombre(String nombre, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( (!nombre.isEmpty()) && (page != null && page > 0) && (size != null && size > 0) ){
            return servicioDAO.getByNombre(nombre, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<Servicio>> getCosto(Double costo, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( ((costo != null) && (costo > 0)) && (page != null && page > 0) && (size != null && size > 0) ){
            return servicioDAO.getByCosto(costo, page, size);
        }else{
            return Optional.empty();
        }
    }

}
