package com.itesm.demo.dao;

import com.itesm.demo.domain.CasoAtendido;
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
public class CasoAtendidoDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CasoAtendidoDAO.class);

    public Optional<CasoAtendido> getByUuid(String uuid) {
        String sql = "SELECT * FROM caso_atendido WHERE uuid=?";
        try {
            BeanPropertyRowMapper<CasoAtendido> rowMapper = new BeanPropertyRowMapper<>(CasoAtendido.class);
            CasoAtendido caso_atendido = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting caso atendido with uuid: " + uuid);
            return Optional.of(caso_atendido);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No caso atendido with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<CasoAtendido> insert(CasoAtendido caso_atendido) {
        String newUuid = UUID.randomUUID().toString();
        try {
            jdbcTemplate.update(
                    "INSERT INTO caso_atendido "
                            + " (uuid, status, fecha_creacion, fecha_modificacion,"
                            + " descripcion, id_reporte, id_usuario )"
                            + " VALUES (?,?,?,?,?,?,?)",
                    newUuid, caso_atendido.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                    caso_atendido.getDescripcion(), caso_atendido.getId_reporte(), caso_atendido.getId_usuario() );
            logger.debug("Inserting caso atendido");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert caso atendido");
            return Optional.empty();
        }
    }

    public Optional<CasoAtendido> update(CasoAtendido caso_atendido){
        try {
            jdbcTemplate.update("UPDATE caso_atendido SET " +
                            "status=?, fecha_modificacion=?, descripcion=? WHERE uuid=?",
                    caso_atendido.getStatus(), Timestamp.from(Instant.now()),
                    caso_atendido.getDescripcion(), caso_atendido.getUuid() );
            logger.debug("Updating caso catendido: " + caso_atendido.getUuid());
            return getByUuid(caso_atendido.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update caso atendido: " + caso_atendido.getUuid());
            return Optional.empty();
        }
    }

    public Optional<List<CasoAtendido>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM caso_atendido WHERE status != -1 LIMIT ?, ?";
        try {
            List<CasoAtendido> casos = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(CasoAtendido.class), (page * size), size);
            logger.debug("Getting casos list ");
            return Optional.of(casos);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get casos list ");
        }
        return Optional.empty();
    }

}
