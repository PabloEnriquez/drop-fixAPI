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

@Service
public class ServicioService {

    @Autowired
    private ServicioDAO servicioDAO;

    @Autowired
    private CompraDAO compraDAO;

    public Optional<Servicio> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<Servicio> servicio = servicioDAO.getByUuid(uuid);
//        equipo_computo.set;
        return servicio;
    }

    public Optional<Servicio> insert(Servicio servicio){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        servicio.setStatus(1);
//        user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        return servicioDAO.insert(servicio);
    }

    public Optional<Servicio> update(Servicio servicio){
        // validar los datos y cualquier lógica de negocio
        Optional<Servicio> servicioDB = servicioDAO.getByUuid(servicio.getUuid());
        if(servicioDB.isPresent()) {
            if(servicio.getStatus() == null){
                servicio.setStatus(servicioDB.get().getStatus());
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
        return servicioDAO.list(page, size);
    }

    public Optional<List<Compra>> listComprasServicio(Long id_servicio, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return compraDAO.listComprasServicio(id_servicio, page, size);
    }

    public Optional<List<Servicio>> getNombre(String nombre, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return servicioDAO.getByNombre(nombre, page, size);
    }

    public Optional<List<Servicio>> getCosto(Double costo, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return servicioDAO.getByCosto(costo, page, size);
    }

}
