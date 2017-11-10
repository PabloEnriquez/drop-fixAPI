package com.itesm.demo.service;

import com.itesm.demo.dao.MensajeDAO;
import com.itesm.demo.domain.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensajeService {

    @Autowired
    private MensajeDAO mensajeDAO;

    public Optional<Mensaje> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<Mensaje> mensaje = mensajeDAO.getByUuid(uuid);
//        equipo_computo.set;
        return mensaje;
    }

    public Optional<Mensaje> insert(Mensaje mensaje){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
//        mensaje.setStatus("1");
//        user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        return mensajeDAO.insert(mensaje);
    }

//    public Optional<Mensaje> update(Mensaje mensaje){
//        // validar los datos y cualquier lógica de negocio
//        Optional<Mensaje> mensajeDB = mensajeDAO.getByUuid(mensaje.getUuid());
//        if(mensajeDB.isPresent()) {
//            if(mensaje.getContenido() == null){
//                mensaje.setContenido(mensajeDB.get().getContenido());
//            }
//            return mensajeDAO.update(mensaje);
//        } else {
//            return Optional.empty();
//        }
//    }

    public Optional<List<Mensaje>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return mensajeDAO.list(page, size);
    }

}
