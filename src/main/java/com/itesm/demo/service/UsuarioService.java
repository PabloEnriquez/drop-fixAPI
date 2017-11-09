package com.itesm.demo.service;

import com.itesm.demo.dao.UsuarioDAO;
import com.itesm.demo.domain.Usuario;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    public Optional<Usuario> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<Usuario> usuario = usuarioDAO.getByUuid(uuid);
        usuario.get().setContrasena(null);
        return usuario;
    }

    public Optional<Usuario> insert(Usuario usuario){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        usuario.setStatus(1);
        usuario.setContrasena(DigestUtils.sha1Hex(usuario.getContrasena()));
        return usuarioDAO.insert(usuario);
    }

    public Optional<Usuario> update(Usuario usuario){
        // validar los datos y cualquier lógica de negocio
        Optional<Usuario> usuarioDB = usuarioDAO.getByUuid(usuario.getUuid());
        if(usuarioDB.isPresent()) {
            if(usuario.getStatus() == null){
                usuario.setStatus(usuarioDB.get().getStatus());
            }
            if(usuario.getEmail() == null){
                usuario.setEmail(usuarioDB.get().getEmail());
            }
            if(usuario.getNombre() == null){
                usuario.setNombre(usuarioDB.get().getNombre());
            }
            if(usuario.getContrasena() == null){
                usuario.setContrasena(usuarioDB.get().getContrasena());
            }
            if(usuario.getTelefono() == null){
                usuario.setTelefono(usuarioDB.get().getTelefono());
            }
            if(usuario.getUsuario() == null){
                usuario.setUsuario(usuarioDB.get().getUsuario());
            }
            if(usuario.getTipo_usuario() == null){
                usuario.setTipo_usuario(usuarioDB.get().getTipo_usuario());
            }
            return usuarioDAO.update(usuario);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<Usuario>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return usuarioDAO.list(page, size);
    }

}
