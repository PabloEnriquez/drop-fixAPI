package com.itesm.demo.dao;

import com.itesm.demo.domain.Reporte;
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
public class ReporteDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ReporteDAO.class);

    public Optional<Reporte> getByUuid(String uuid) {
        String sql = "SELECT * FROM reporte WHERE uuid=?";
        try {
            BeanPropertyRowMapper<Reporte> rowMapper = new BeanPropertyRowMapper<>(Reporte.class);
            Reporte reporte = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting reporte with uuid: " + uuid);
            return Optional.of(reporte);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No reporte with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<Reporte> insert(Reporte reporte) {
        String newUuid = UUID.randomUUID().toString();
        try {
            jdbcTemplate.update(
                    "INSERT INTO reporte "
                            + " (uuid, status, fecha_creacion, fecha_modificacion,"
                            + " descripcion, id_equipo_computo, id_usuario )"
                            + " VALUES (?,?,?,?,?,?,?)",
                    newUuid, reporte.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                    reporte.getDescripcion(), reporte.getId_equipo_computo(), reporte.getId_usuario() );
            logger.debug("Inserting reporte");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert reporte");
            return Optional.empty();
        }
    }

    public Optional<Reporte> update(Reporte reporte){
        try {
            jdbcTemplate.update("UPDATE reporte SET " +
                            "status=?, fecha_modificacion=?, descripcion=? WHERE uuid=?",
                    reporte.getStatus(), Timestamp.from(Instant.now()),
                    reporte.getDescripcion(), reporte.getUuid() );
            logger.debug("Updating reporte: " + reporte.getUuid());
            return getByUuid(reporte.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update reporte: " + reporte.getUuid());
            return Optional.empty();
        }
    }

    public Optional<List<Reporte>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM reporte WHERE status != -1 LIMIT ?, ?";
        try {
            List<Reporte> reportes = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Reporte.class), (page * size), size);
            logger.debug("Getting reportes list ");
            return Optional.of(reportes);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get reportes list ");
        }
        return Optional.empty();
    }

}
