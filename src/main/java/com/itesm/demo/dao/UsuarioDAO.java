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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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
        Long newId = Long.valueOf(new AtomicInteger(0).incrementAndGet());
        try {
            jdbcTemplate.update(
                    "INSERT INTO usuario "
                            + " ( id, uuid, status, fecha_creacion, fecha_modificacion,"
                            + " email, nombre, contrasena, telefono, usuario, tipo_usuario )"
                            + " VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                    newId, newUuid, usuario.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
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
                            "status=?, fecha_modificacion=?, email=?, nombre=?, contrasena=?, " +
                            "telefono=?, usuario=?, tipo_usuario=? WHERE uuid=?",
                    usuario.getStatus(), Timestamp.from(Instant.now()), usuario.getEmail(), usuario.getNombre(),
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

    public Optional<List<Usuario>> getByMail(String email, Integer page, Integer size){
        String sql = "SELECT * FROM usuario WHERE email LIKE %?% LIMIT ?, ?";
        try {
            List<Usuario> usuarios = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Usuario.class), email, (page * size), size);
            logger.debug("Getting usuarios list por email ");
            return Optional.of(usuarios);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get usuarios list por email ");
        }
        return Optional.empty();
    }

    public Optional<List<Usuario>> getByNombre(String nombre, Integer page, Integer size){
        String sql = "SELECT * FROM usuario WHERE nombre LIKE %?% LIMIT ?, ?";
        try {
            List<Usuario> usuarios = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Usuario.class), nombre, (page * size), size);
            logger.debug("Getting usuarios list por nombre");
            return Optional.of(usuarios);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get usuarios list por nombre ");
        }
        return Optional.empty();
    }

    public Optional<List<Usuario>> getByUsuario(String usuario_nombre, Integer page, Integer size){
        String sql = "SELECT * FROM usuario WHERE usuario LIKE %?% LIMIT ?, ?";
        try {
            List<Usuario> usuarios = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Usuario.class), usuario_nombre, (page * size), size);
            logger.debug("Getting usuarios list por nombre de usuario");
            return Optional.of(usuarios);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get usuarios list por nombre de usuario ");
        }
        return Optional.empty();
    }

    public Optional<List<Usuario>> getByFechaCreacion(Date fecha_creacion, Integer page, Integer size){
        String sql = "SELECT * FROM usuario WHERE fecha_creacion LIKE %?% LIMIT ?, ?";
        try {
            List<Usuario> usuarios = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Usuario.class), fecha_creacion, (page * size), size);
            logger.debug("Getting usuarios list por fecha de creacion");
            return Optional.of(usuarios);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get usuarios list por fecha de creacion ");
        }
        return Optional.empty();
    }

    public Optional<List<Usuario>> getByTipoUsuario(Integer tipo_usuario, Integer page, Integer size){
        String sql = "SELECT * FROM usuario WHERE tipo_usuario=? LIMIT ?, ?";
        try {
            List<Usuario> usuarios = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Usuario.class), tipo_usuario, (page * size), size);
            logger.debug("Getting usuarios list por nombre de usuario");
            return Optional.of(usuarios);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get usuarios list por nombre de usuario ");
        }
        return Optional.empty();
    }

}
