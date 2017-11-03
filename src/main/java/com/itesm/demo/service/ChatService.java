package com.itesm.demo.service;

import com.itesm.demo.dao.ChatDAO;
import com.itesm.demo.domain.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatDAO chatDAO;

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
        chat.setStatus("1");
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

}
