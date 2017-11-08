package com.itesm.demo.service;

import com.itesm.demo.dao.CentroServicioDAO;
import com.itesm.demo.domain.CentroServicio;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class CentroServicioService {

    @Autowired
    private CentroServicioDAO CentroServicioDAO;

    public Optional<CentroServicio> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<CentroServicio> CentroServicio = CentroServicioDAO.getByUuid(uuid);
        return CentroServicio;
    }

    public Optional<CentroServicio> insert(CentroServicio CentroServicio){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        CentroServicio.setStatus(1);
        return CentroServicioDAO.insert(CentroServicio);
    }

    public Optional<CentroServicio> update(CentroServicio CentroServicio){
        // validar los datos y cualquier lógica de negocio
        Optional<CentroServicio> CentroServicioDB = CentroServicioDAO.getByUuid(CentroServicio.getUuid());
        if(CentroServicioDB.isPresent()) {
            if(CentroServicio.getStatus() == null){
                CentroServicio.setStatus(CentroServicioDB.get().getStatus());
            }
            if(CentroServicio.getTitulo() == null){
                CentroServicio.setTitulo(CentroServicioDB.get().getTitulo());
            }
            if(CentroServicio.getDescripcion() == null){
                CentroServicio.setDescripcion(CentroServicioDB.get().getDescripcion());
            }
            return CentroServicioDAO.update(CentroServicio);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<CentroServicio>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return CentroServicioDAO.list(page, size);
    }

}
