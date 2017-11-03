package com.itesm.demo.dao;

import com.itesm.demo.domain.Compra;
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
public class CompraDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CompraDAO.class);

    public Optional<Compra> getByUuid(String uuid) {
        String sql = "SELECT * FROM compra WHERE uuid=?";
        try {
            BeanPropertyRowMapper<Compra> rowMapper = new BeanPropertyRowMapper<>(Compra.class);
            Compra compra = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting compra with uuid: " + uuid);
            return Optional.of(compra);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No compra with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<Compra> insert(Compra compra) {
        String newUuid = UUID.randomUUID().toString();
        try {
            jdbcTemplate.update(
                    "INSERT INTO compra "
                            + " (uuid, status, fecha_creacion, fecha_modificacion,"
                            + " monto_total, id_reporte, id_usuario, id_servicio )"
                            + " VALUES (?,?,?,?,?,?,?,?)",
                    newUuid, compra.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                    compra.getMonto_total(), compra.getId_reporte(), compra.getId_usuario(), compra.getId_servicio() );
            logger.debug("Inserting compra");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert compra");
            return Optional.empty();
        }
    }

    public Optional<Compra> update(Compra compra){
        try {
            jdbcTemplate.update("UPDATE compra SET " +
                            "status=?, fecha_modificacion=?, monto_total=? WHERE uuid=?",
                    compra.getStatus(), Timestamp.from(Instant.now()),
                    compra.getMonto_total(), compra.getUuid() );
            logger.debug("Updating compra: " + compra.getUuid());
            return getByUuid(compra.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update compra: " + compra.getUuid());
            return Optional.empty();
        }
    }

    public Optional<List<Compra>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM compra WHERE status != -1 LIMIT ?, ?";
        try {
            List<Compra> compras = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Compra.class), (page * size), size);
            logger.debug("Getting compras list ");
            return Optional.of(compras);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get compras list ");
        }
        return Optional.empty();
    }

}
