package com.itesm.demo.service;

import com.itesm.demo.dao.ReporteDAO;
import com.itesm.demo.dao.UsuarioDAO;
import com.itesm.demo.dao.EquipoComputoDAO;
import com.itesm.demo.domain.EquipoComputo;
import com.itesm.demo.domain.Reporte;
import com.itesm.demo.domain.Usuario;
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

    public Optional<Usuario> getMail(String email){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<Usuario> usuario = usuarioDAO.getByMail(email);
        return usuario;
    }

    public Optional<Usuario> getNombre(String nombre){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<Usuario> usuario = usuarioDAO.getByNombre(nombre);
        return usuario;
    }

    public Optional<Usuario> getUsuarioNombre(String usuario_nombre){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<Usuario> usuario = usuarioDAO.getByUsuario(usuario_nombre);
        return usuario;
    }

    public Optional<Usuario> getFechaCreacion(Date fecha_creacion){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<Usuario> usuario = usuarioDAO.getByFechaCreacion(fecha_creacion);
        return usuario;
    }

    public Optional<Usuario> getTipoUsuario(Integer tipo_usuario){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<Usuario> usuario = usuarioDAO.getByTipoUsuario(tipo_usuario);
        return usuario;
    }

    public Optional<List<EquipoComputo>> listEquipos(Integer page, Integer size, Long id_usuario){
        // validar los datos y cualquier lógica de negocio
        return equipoComputoDAO.listEquiposUsuario(page, size, id_usuario);
    }

    public Optional<List<Reporte>> listReportes(Long id_usuario, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return reporteDAO.listReportesUsuario(id_usuario, page, size);
    }

}
