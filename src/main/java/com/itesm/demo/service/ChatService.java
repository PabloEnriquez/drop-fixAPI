package com.itesm.demo.service;

import com.itesm.demo.dao.ChatDAO;
import com.itesm.demo.dao.MensajeDAO;
import com.itesm.demo.dao.ReporteDAO;
import com.itesm.demo.domain.Chat;
import com.itesm.demo.domain.Mensaje;
import com.itesm.demo.domain.Reporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChatService {

    @Autowired
    private ChatDAO chatDAO;

    @Autowired
    private ReporteDAO reporteDAO;

    @Autowired
    private MensajeDAO mensajeDAO;

    public Optional<Chat> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Pattern p = Pattern.compile("(^[a-zA-Z0-9][ A-Za-z0-9_-]*$)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(uuid);
        boolean b = m.find();
        if ( (!uuid.isEmpty()) && (!b) ){
            Optional<Chat> chat = chatDAO.getByUuid(uuid);
            return chat;
        }else {
            return Optional.empty();
        }
    }

    public Optional<Chat> insert(Chat chat){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        if ( (chat.getId_tecnico() != null && chat.getId_tecnico() > 0) && (chat.getId_usuario() != null && chat.getId_usuario() > 0) ){
            chat.setStatus(1);
            return chatDAO.insert(chat);
        }else{
            return Optional.empty();
        }
    }

    public Optional<Chat> update(Chat chat){
        // validar los datos y cualquier lógica de negocio
        Optional<Chat> chatDB = chatDAO.getByUuid(chat.getUuid());
        if(chatDB.isPresent()) {
            if(chat.getStatus() == null){
                chat.setStatus(chatDB.get().getStatus());
            }
            if(chat.getFecha_modificacion() == null){
                chat.setFecha_modificacion(chatDB.get().getFecha_modificacion());
            }
            return chatDAO.update(chat);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<Chat>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( (page != null && page >= 0) && (size != null && size > 0) ){
            return chatDAO.list(page, size);
        }else {
            return Optional.empty();
        }
    }

    public Optional<List<Reporte>> listReportes(Long id_chat, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( ((id_chat != null) && (id_chat > 0)) && (page != null && page >= 0) && (size != null && size > 0) ){
            return reporteDAO.listReportesChat(id_chat, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<Mensaje>> listMensajes(Long id_chat, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( ((id_chat != null) && (id_chat > 0)) && (page != null && page >= 0) && (size != null && size > 0) ){
            return mensajeDAO.listMensajesChat(id_chat, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<Chat>> getFechaCreacion(Date fecha_creacion, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        if ( (fecha_creacion != null) && (page != null && page >= 0) && (size != null && size > 0) ){
            return chatDAO.getByFechaCreacion(fecha_creacion, page, size);
        }else{
            return Optional.empty();
        }
    }

}
