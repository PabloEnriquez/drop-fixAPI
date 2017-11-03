package com.itesm.demo.service;

import com.itesm.demo.dao.CasoAtendidoDAO;
import com.itesm.demo.domain.CasoAtendido;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CasoAtendidoService {

    @Autowired
    private CasoAtendidoDAO casoAtendidoDAO;

    public Optional<CasoAtendido> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<CasoAtendido> caso_atendido = casoAtendidoDAO.getByUuid(uuid);
//        equipo_computo.set;
        return caso_atendido;
    }

    public Optional<CasoAtendido> insert(CasoAtendido caso_atendido){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        caso_atendido.setStatus("1");
//        user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        return casoAtendidoDAO.insert(caso_atendido);
    }

    public Optional<CasoAtendido> update(CasoAtendido caso_atendido){
        // validar los datos y cualquier lógica de negocio
        Optional<CasoAtendido> caso_atendidoDB = casoAtendidoDAO.getByUuid(caso_atendido.getUuid());
        if(caso_atendidoDB.isPresent()) {
            if(caso_atendido.getStatus() == null){
                caso_atendido.setStatus(caso_atendidoDB.get().getStatus());
            }
            if(caso_atendido.getDescripcion() == null){
                caso_atendido.setDescripcion(caso_atendidoDB.get().getDescripcion());
            }
            return casoAtendidoDAO.update(caso_atendido);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<CasoAtendido>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return casoAtendidoDAO.list(page, size);
    }

}
