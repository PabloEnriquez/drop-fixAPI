package com.itesm.demo.dao;

import com.itesm.demo.domain.Mensaje;
import com.sun.org.apache.regexp.internal.RE;
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
public class MensajeDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MensajeDAO.class);

    public Optional<Mensaje> getByUuid(String uuid) {
        String sql = "SELECT * FROM mensaje WHERE uuid=?";
        try {
            BeanPropertyRowMapper<Mensaje> rowMapper = new BeanPropertyRowMapper<>(Mensaje.class);
            Mensaje mensaje = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting mensaje with uuid: " + uuid);
            return Optional.of(mensaje);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No mensaje with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<Mensaje> insert(Mensaje mensaje) {
        String newUuid = UUID.randomUUID().toString();
        try {
            jdbcTemplate.update(
                    "INSERT INTO mensaje "
                            + " (uuid, id_dueno, fecha_creacion,"
                            + " contenido, id_chat )"
                            + " VALUES (?,?,?,?,?)",
                    newUuid, mensaje.getId_dueno(), Timestamp.from(Instant.now()),
                    mensaje.getContenido(), mensaje.getId_chat() );
            logger.debug("Inserting mensaje");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert mensaje");
            return Optional.empty();
        }
    }

    public Optional<Mensaje> update(Mensaje mensaje){
        try {
            jdbcTemplate.update("UPDATE mensaje SET " +
                            "contenido=? WHERE uuid=?",
                    mensaje.getContenido(), mensaje.getUuid() );
            logger.debug("Updating mensaje: " + mensaje.getUuid());
            return getByUuid(mensaje.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update mensaje: " + mensaje.getUuid());
            return Optional.empty();
        }
    }

    public Optional<List<Mensaje>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM mensaje WHERE status != -1 LIMIT ?, ?";
        try {
            List<Mensaje> mensajes = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Mensaje.class), (page * size), size);
            logger.debug("Getting mensajes list ");
            return Optional.of(mensajes);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get mensajes list ");
        }
        return Optional.empty();
    }

}
