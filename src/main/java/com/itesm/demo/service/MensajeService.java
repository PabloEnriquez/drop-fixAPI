package com.itesm.demo.service;

import com.itesm.demo.dao.MensajeDAO;
import com.itesm.demo.domain.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MensajeService {

    @Autowired
    private MensajeDAO mensajeDAO;

    public Optional<Mensaje> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(uuid);
        boolean b = m.find();
        if ( (!uuid.isEmpty()) && (!b) ){
            Optional<Mensaje> mensaje = mensajeDAO.getByUuid(uuid);
            return mensaje;
        }else {
            return Optional.empty();
        }
    }

    public Optional<Mensaje> insert(Mensaje mensaje){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
//        mensaje.setStatus("1");
        if ( (mensaje.getId_dueno() != null) && (mensaje.getContenido() != null) &&
                (mensaje.getId_chat() != null) ){
            return mensajeDAO.insert(mensaje);
        }else{
            return Optional.empty();
        }
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
        if ( (page != null && page > 0) && (size != null && size > 0) ){
            return mensajeDAO.list(page, size);
        }else {
            return Optional.empty();
        }
    }

}
