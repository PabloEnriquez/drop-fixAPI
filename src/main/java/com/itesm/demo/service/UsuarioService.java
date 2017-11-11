package com.itesm.demo.service;

import com.itesm.demo.dao.*;
import com.itesm.demo.domain.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private EquipoComputoDAO equipoComputoDAO;

    @Autowired
    private ReporteDAO reporteDAO;

    @Autowired
    private ChatDAO chatDAO;

    @Autowired
    private CompraDAO compraDAO;

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
            if(usuario.getFecha_modificacion() == null){
                usuario.setFecha_modificacion(usuarioDB.get().getFecha_modificacion());
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
        if ( (page != null && page != 0) && (size != null && size != 0) ){
            return usuarioDAO.list(page, size);
        }else {
            return Optional.empty();
        }

    }

    public Optional<List<Usuario>> getMail(String email, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        return usuarioDAO.getByMail(email, page, size);
    }

    public Optional<List<Usuario>> getNombre(String nombre, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        return usuarioDAO.getByNombre(nombre, page, size);
    }

    public Optional<List<Usuario>> getUsuarioNombre(String usuario_nombre, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        return usuarioDAO.getByUsuario(usuario_nombre, page, size);
    }

    public Optional<List<Usuario>> getFechaCreacion(Date fecha_creacion, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        return usuarioDAO.getByFechaCreacion(fecha_creacion, page, size);
    }

    public Optional<List<Usuario>> getTipoUsuario(Integer tipo_usuario, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        return usuarioDAO.getByTipoUsuario(tipo_usuario, page, size);
    }

    public Optional<List<EquipoComputo>> listEquipos(Long id_usuario, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return equipoComputoDAO.listEquiposUsuario(id_usuario, page, size);
    }

    public Optional<List<Reporte>> listReportes(Long id_usuario, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return reporteDAO.listReportesUsuario(id_usuario, page, size);
    }

    public Optional<List<Chat>> listChatsUsuario(Long id_usuario, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return chatDAO.listChatsUsuario(id_usuario, page, size);
    }

    public Optional<List<Chat>> listChatsTecnico(Long id_tecnico, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return chatDAO.listChatsTecnico(id_tecnico, page, size);
    }

    public Optional<List<Compra>> listComprasUsuario(Long id_usuario, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return compraDAO.listComprasUsuario(id_usuario, page, size);
    }

}
