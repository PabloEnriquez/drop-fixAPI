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
        Optional<Chat> chat = chatDAO.getByUuid(uuid);
//        equipo_computo.set;
        return chat;
    }

    public Optional<Chat> insert(Chat chat){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        chat.setStatus(1);
//        user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        return chatDAO.insert(chat);
    }

    public Optional<Chat> update(Chat chat){
        // validar los datos y cualquier lógica de negocio
        Optional<Chat> chatDB = chatDAO.getByUuid(chat.getUuid());
        if(chatDB.isPresent()) {
            if(chat.getStatus() == null){
                chat.setStatus(chatDB.get().getStatus());
            }
            return chatDAO.update(chat);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<Chat>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return chatDAO.list(page, size);
    }

    public Optional<List<Reporte>> listReportes(Long id_chat, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return reporteDAO.listReportesChat(id_chat, page, size);
    }

    public Optional<List<Mensaje>> listMensajes(Long id_chat, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return mensajeDAO.listMensajesChat(id_chat, page, size);
    }

    public Optional<List<Chat>> getFechaCreacion(Date fecha_creacion, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        return chatDAO.getByFechaCreacion(fecha_creacion, page, size);
    }

}
