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
import java.util.Date;
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
                            + " (uuid, status, status_atendido, fecha_creacion, fecha_modificacion,"
                            + " descripcion, id_equipo_computo, id_usuario, id_chat )"
                            + " VALUES (?,?,?,?,?,?,?,?,?)",
                    newUuid, reporte.getStatus(), reporte.getStatus_atendido(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                    reporte.getDescripcion(), reporte.getId_equipo_computo(), reporte.getId_usuario(), reporte.getId_chat() );
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
                            "status=?, status_atendido=?, fecha_modificacion=?, descripcion=? WHERE uuid=?",
                    reporte.getStatus(), reporte.getStatus_atendido(), Timestamp.from(Instant.now()),
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

    public Optional<List<Reporte>> getByStatusAtendido(Long status_atendido, Integer page, Integer size ) {
        String sql = "SELECT * FROM reporte WHERE status_atendido LIKE %?% LIMIT ?, ?";
        try {
            List<Reporte> reportes = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Reporte.class), status_atendido, (page * size), size);
            logger.debug("Getting reportes list por status atendido ");
            return Optional.of(reportes);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get reportes list por status atendido ");
        }
        return Optional.empty();
    }

    public Optional<List<Reporte>> getByFechaCreacion(Date fecha_creacion, Integer page, Integer size) {
        String sql = "SELECT * FROM reporte WHERE fecha_creacion LIKE %?% LIMIT ?, ?";
        try {
            List<Reporte> reportes = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Reporte.class), fecha_creacion, (page * size), size);
            logger.debug("Getting reportes list por fecha de creacion ");
            return Optional.of(reportes);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get reportes list por fecha de cracion ");
        }
        return Optional.empty();
    }

    public Optional<List<Reporte>> listReportesEquipo(Long id_equipo_computo, Integer page, Integer size) {
        String sql = "SELECT * FROM reporte WHERE id_equipo_computo=? LIMIT ?, ?";
        try {
            List<Reporte> reportes = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Reporte.class), id_equipo_computo, (page * size), size);
            logger.debug("Getting reportes por equipos list ");
            return Optional.of(reportes);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get reportes por equipos list ");
        }
        return Optional.empty();
    }

    public Optional<List<Reporte>> listReportesUsuario(Long id_usuario, Integer page, Integer size) {
        String sql = "SELECT * FROM reporte WHERE id_usuario=? LIMIT ?, ?";
        try {
            List<Reporte> reportes = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Reporte.class), id_usuario, (page * size), size);
            logger.debug("Getting reportes por usuario list ");
            return Optional.of(reportes);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get reportes por usuario list ");
        }
        return Optional.empty();
    }

    public Optional<List<Reporte>> listReportesChat(Long id_chat, Integer page, Integer size) {
        String sql = "SELECT * FROM reporte WHERE id_chat=? LIMIT ?, ?";
        try {
            List<Reporte> reportes = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Reporte.class), id_chat, (page * size), size);
            logger.debug("Getting reportes por chat list ");
            return Optional.of(reportes);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get reportes por chat list ");
        }
        return Optional.empty();
    }

}
