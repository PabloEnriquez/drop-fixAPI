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

    public Optional<Usuario> getByMail(String email){
        String sql = "SELECT * FROM usuario WHERE email=?";
        try {
            BeanPropertyRowMapper<Usuario> rowMapper = new BeanPropertyRowMapper<>(Usuario.class);
            Usuario usuario = jdbcTemplate.queryForObject(sql, rowMapper, email);
            logger.debug("Getting usuario with email: " + email);
            return Optional.of(usuario);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No usuario with email: " + email);
        }
        return Optional.empty();
    }

    public Optional<Usuario> getByNombre(String nombre){
        String sql = "SELECT * FROM usuario WHERE nombre=?";
        try {
            BeanPropertyRowMapper<Usuario> rowMapper = new BeanPropertyRowMapper<>(Usuario.class);
            Usuario usuario = jdbcTemplate.queryForObject(sql, rowMapper, nombre);
            logger.debug("Getting usuario with nombre: " + nombre);
            return Optional.of(usuario);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No usuario with nombre: " + nombre);
        }
        return Optional.empty();
    }

    public Optional<Usuario> getByUsuario(String usuario_nombre){
        String sql = "SELECT * FROM usuario WHERE usuario=?";
        try {
            BeanPropertyRowMapper<Usuario> rowMapper = new BeanPropertyRowMapper<>(Usuario.class);
            Usuario usuario = jdbcTemplate.queryForObject(sql, rowMapper, usuario_nombre);
            logger.debug("Getting usuario with nombre usuario: " + usuario_nombre);
            return Optional.of(usuario);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No usuario with nombre usuario: " + usuario_nombre);
        }
        return Optional.empty();
    }

    public Optional<Usuario> getByFechaCreacion(Date fecha_creacion){
        String sql = "SELECT * FROM usuario WHERE fecha_creacion=?";
        try {
            BeanPropertyRowMapper<Usuario> rowMapper = new BeanPropertyRowMapper<>(Usuario.class);
            Usuario usuario = jdbcTemplate.queryForObject(sql, rowMapper, fecha_creacion);
            logger.debug("Getting usuario with fecha de creacion: " + fecha_creacion);
            return Optional.of(usuario);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No usuario with fecha de creacion: " + fecha_creacion);
        }
        return Optional.empty();
    }

    public Optional<Usuario> getByTipoUsuario(Integer tipo_usuario){
        String sql = "SELECT * FROM usuario WHERE tipo_usuario=?";
        try {
            BeanPropertyRowMapper<Usuario> rowMapper = new BeanPropertyRowMapper<>(Usuario.class);
            Usuario usuario = jdbcTemplate.queryForObject(sql, rowMapper, tipo_usuario);
            logger.debug("Getting usuario with tipo usuario: " + tipo_usuario);
            return Optional.of(usuario);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No usuario with tipo usuario: " + tipo_usuario);
        }
        return Optional.empty();
    }

}
