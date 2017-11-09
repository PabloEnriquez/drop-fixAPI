package com.itesm.demo.service;

import com.itesm.demo.dao.UserDAO;
import com.itesm.demo.domain.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public Optional<User> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<User> user = userDAO.getByUuid(uuid);
        user.get().setPassword(null);
        return user;
    }

    public Optional<User> insert(User user){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        user.setStatus(1);
        user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        return userDAO.insert(user);
    }

    public Optional<User> update(User user){
        // validar los datos y cualquier lógica de negocio
        Optional<User> userDB = userDAO.getByUuid(user.getUuid());
        if(userDB.isPresent()) {
            if(user.getStatus() == null){
                user.setStatus(userDB.get().getStatus());
            }
            if(user.getName() == null){
                user.setName(userDB.get().getName());
            }
            if(user.getLastname() == null){
                user.setLastname(userDB.get().getLastname());
            }
            return userDAO.update(user);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<User>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return userDAO.list(page, size);
    }

}

