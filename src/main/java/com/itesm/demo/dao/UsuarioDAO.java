package com.itesm.demo.dao;

import com.itesm.demo.domain.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UsuarioDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);

    public Optional<Usuario> getByUuid(String uuid) {
        String sql = "SELECT * FROM usuario WHERE uuid=?";
        try {
            BeanPropertyRowMapper<Usuario> rowMapper = new BeanPropertyRowMapper<>(Usuario.class);
            Usuario usuario = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting usuario with uuid: " + uuid);
            return Optional.of(usuario);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No usuario with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<Usuario> insert(Usuario usuario) {
        String newUuid = UUID.randomUUID().toString();
        try {
            jdbcTemplate.update(
                    "INSERT INTO usuario "
                            + " ( uuid, status, fecha_creacion, fecha_modificacion,"
                            + " email, nombre, contrasena, telefono, usuario, tipo_usuario )"
                            + " VALUES (?,?,?,?,?,?,?,?,?,?)",
                    newUuid, usuario.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                    usuario.getEmail(), usuario.getNombre(), usuario.getContrasena(),
                    usuario.getTelefono(), usuario.getUsuario(), usuario.getTipo_usuario() );
            logger.debug("Inserting usuario");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert usuario");
            return Optional.empty();
        }
    }

    public Optional<Usuario> update(Usuario usuario){
        try {
            jdbcTemplate.update("UPDATE usuario SET " +
                            "fecha_modificacion=?, email=?, nombre=?, contrasena=?, " +
                            "telefono=?, usuario=?, tipo_usuario=? WHERE uuid=?",
                    Timestamp.from(Instant.now()), usuario.getEmail(), usuario.getNombre(),
                    usuario.getContrasena(), usuario.getTelefono(), usuario.getUsuario(),
                    usuario.getTipo_usuario(), usuario.getUuid() );
            logger.debug("Updating usuario: " + usuario.getUuid());
            return getByUuid(usuario.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update usuario: " + usuario.getUuid());
            return Optional.empty();
        }
    }

    public Optional<List<Usuario>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM usuario WHERE status != -1 LIMIT ?, ?";
        try {
            List<Usuario> usuarios = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Usuario.class), (page * size), size);
            logger.debug("Getting usuarios list ");
            return Optional.of(usuarios);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get usuarios list ");
        }
        return Optional.empty();
    }

}
