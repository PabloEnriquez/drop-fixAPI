package com.itesm.demo.dao;

import com.itesm.demo.domain.Servicio;
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
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ServicioDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ServicioDAO.class);

    public Optional<Servicio> getByUuid(String uuid) {
        String sql = "SELECT * FROM servicio WHERE uuid=?";
        try {
            BeanPropertyRowMapper<Servicio> rowMapper = new BeanPropertyRowMapper<>(Servicio.class);
            Servicio servicio = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting servicio with uuid: " + uuid);
            return Optional.of(servicio);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No servicio with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<Servicio> insert(Servicio servicio) {
        String newUuid = UUID.randomUUID().toString();
        Long newId = Long.valueOf(new AtomicInteger(0).incrementAndGet());
        try {
            jdbcTemplate.update(
                    "INSERT INTO servicio "
                            + " ( id, uuid, status, fecha_creacion, fecha_modificacion,"
                            + " costo, nombre, descripcion, imagen ) "
                            + " VALUES (?,?,?,?,?,?,?,?,?)",
                    newId, newUuid, servicio.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                    servicio.getCosto(), servicio.getNombre(), servicio.getDescripcion(), servicio.getImagen() );
            logger.debug("Inserting servicio");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert servicio");
            return Optional.empty();
        }
    }

    public Optional<Servicio> update(Servicio servicio){
        try {
            jdbcTemplate.update("UPDATE servicio SET " +
                            "status=?, fecha_modificacion=?, costo=?, nombre=?," +
                            "descripcion=?, imagen=? WHERE uuid=?",
                    servicio.getStatus(), Timestamp.from(Instant.now()),
                    servicio.getCosto(), servicio.getNombre(),
                    servicio.getDescripcion(), servicio.getImagen(), servicio.getUuid() );
            logger.debug("Updating servicio: " + servicio.getUuid());
            return getByUuid(servicio.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update servicio: " + servicio.getUuid());
            return Optional.empty();
        }
    }

    public Optional<List<Servicio>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM servicio WHERE status != -1 LIMIT ?, ?";
        try {
            List<Servicio> servicios = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Servicio.class), (page * size), size);
            logger.debug("Getting servicios list ");
            return Optional.of(servicios);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get servicios list ");
        }
        return Optional.empty();
    }

    public Optional<List<Servicio>> getByNombre(String nombre, Integer page, Integer size) {
        String sql = "SELECT * FROM servicio WHERE nombre LIKE %?% LIMIT ?, ?";
        try {
            List<Servicio> servicios = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Servicio.class), nombre, (page * size), size);
            logger.debug("Getting servicios list ");
            return Optional.of(servicios);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get servicios list ");
        }
        return Optional.empty();
    }

    public Optional<List<Servicio>> getByCosto(Double costo, Integer page, Integer size) {
        String sql = "SELECT * FROM servicio WHERE costo LIKE %?% LIMIT ?, ?";
        try {
            List<Servicio> servicios = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Servicio.class), costo, (page * size), size);
            logger.debug("Getting servicios list ");
            return Optional.of(servicios);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get servicios list ");
        }
        return Optional.empty();
    }

}
