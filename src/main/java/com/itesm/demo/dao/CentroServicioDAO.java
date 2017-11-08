package com.itesm.demo.dao;

import com.itesm.demo.domain.CentroServicio;
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
public class CentroServicioDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CentroServicioDAO.class);

    public Optional<CentroServicio> getByUuid(String uuid) {
        String sql = "SELECT * FROM centroServicio WHERE uuid=?";
        try {
            BeanPropertyRowMapper<CentroServicio> rowMapper = new BeanPropertyRowMapper<>(CentroServicio.class);
            CentroServicio centroServicio = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting centroServicio with uuid: " + uuid);
            return Optional.of(centroServicio);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No centroServicio with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<CentroServicio> insert(CentroServicio centroServicio) {
        String newUuid = UUID.randomUUID().toString();
        try {
            jdbcTemplate.update(
                    "INSERT INTO centroServicio "
                            + " (uuid, longitud, latitud,descripcion,direccion,titulo,url,"
                            + " status, date_created, date_modified)"
                            + " VALUES (?,?,?,?, ?,?,?)",
                    newUuid, centroServicio.getLongitud(), centroServicio.getLatitud(),centroServicio.getDescripcion(),centroServicio.getDireccion(),centroServicio.getTitulo(),
                    centroServicio.getUrl(),centroServicio.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            logger.debug("Inserting centroServicio");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert centroServicio");
            return Optional.empty();
        }
    }

    public Optional<CentroServicio> update(CentroServicio centroServicio){
        try {
            jdbcTemplate.update("UPDATE centroServicio SET " +
                    "longitud=?,latitud=?,descripcion=?,direccion=?, titulo=?,url=?, status=?, date_modified=? WHERE uuid=?",
                    centroServicio.getLongitud(), centroServicio.getLatitud(),centroServicio.getDescripcion(),centroServicio.getDireccion(),centroServicio.getTitulo(),  centroServicio.getUrl(),centroServicio.getStatus(),
                    Timestamp.from(Instant.now()), centroServicio.getUuid());
            logger.debug("Updating centroServicio: " + centroServicio.getUuid());
            return getByUuid(centroServicio.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update centroServicio: " + centroServicio.getUuid());
            return Optional.empty();
        }
    }


    public Optional<List<CentroServicio>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM centroServicio WHERE status != -1 LIMIT ?, ?";
        try {
            List<CentroServicio> centroServicios = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(CentroServicio.class), (page * size), size);
            logger.debug("Getting centroServicio list ");
            return Optional.of(centroServicios);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get centroServicio list ");
        }
        return Optional.empty();
    }



}
