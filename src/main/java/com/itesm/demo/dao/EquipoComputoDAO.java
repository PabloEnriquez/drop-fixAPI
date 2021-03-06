package com.itesm.demo.dao;

import com.itesm.demo.domain.EquipoComputo;
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
public class EquipoComputoDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(EquipoComputoDAO.class);

    public Optional<EquipoComputo> getByUuid(String uuid) {
        String sql = "SELECT * FROM equipo_computo WHERE uuid=?";
        try {
            BeanPropertyRowMapper<EquipoComputo> rowMapper = new BeanPropertyRowMapper<>(EquipoComputo.class);
            EquipoComputo equipo_computo = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting equipo de computo with uuid: " + uuid);
            return Optional.of(equipo_computo);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No equipo de computo with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<EquipoComputo> insert(EquipoComputo equipo_computo) {
        String newUuid = UUID.randomUUID().toString();
        Long newId = Long.valueOf(new AtomicInteger(0).incrementAndGet());
        try {
            jdbcTemplate.update(
                    "INSERT INTO equipo_computo "
                            + " ( uuid, status, fecha_creacion, fecha_modificacion,"
                            + " nombre, num_serie, modelo, marca, sistema_operativo,"
                            + " id_usuario) "
                            + " VALUES (?,?,?,?,?,?,?,?,?,?)",
                    newUuid, equipo_computo.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                    equipo_computo.getNombre(), equipo_computo.getNum_serie(), equipo_computo.getModelo(),
                    equipo_computo.getMarca(), equipo_computo.getSistema_operativo(), equipo_computo.getId_usuario() );
            logger.debug("Inserting equipo computo");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert equipo computo");
            return Optional.empty();
        }
    }

    public Optional<EquipoComputo> update(EquipoComputo equipo_computo){
        try {
            jdbcTemplate.update("UPDATE equipo_computo SET " +
                            "status=?, fecha_modificacion=?, nombre=?, num_serie=?," +
                            "modelo=?, marca=?, sistema_operativo=? WHERE uuid=?",
                    equipo_computo.getStatus(), Timestamp.from(Instant.now()),
                    equipo_computo.getNombre(), equipo_computo.getNum_serie(),
                    equipo_computo.getModelo(), equipo_computo.getMarca(),
                    equipo_computo.getSistema_operativo(), equipo_computo.getUuid() );
            logger.debug("Updating equipo computo: " + equipo_computo.getUuid());
            return getByUuid(equipo_computo.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update equipo computo: " + equipo_computo.getUuid());
            return Optional.empty();
        }
    }

    public Optional<List<EquipoComputo>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM equipo_computo WHERE status != -1 LIMIT ?, ?";
        try {
            List<EquipoComputo> equipos = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(EquipoComputo.class), (page * size), size);
            logger.debug("Getting equipo computo list ");
            return Optional.of(equipos);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get equipos computo list ");
        }
        return Optional.empty();
    }

    public Optional<List<EquipoComputo>> getByFechaCreacion(Date fecha_creacion, Integer page, Integer size) {
        String sql = "SELECT * FROM equipo_computo WHERE fecha_creacion LIKE %?% LIMIT ?, ?";
        try {
            List<EquipoComputo> equipos = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(EquipoComputo.class), fecha_creacion, (page * size), size);
            logger.debug("Getting equipo computo list por fecha de creacion ");
            return Optional.of(equipos);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get equipos computo list por fecha de creacion ");
        }
        return Optional.empty();
    }

    public Optional<List<EquipoComputo>> getByNombre(String nombre, Integer page, Integer size) {
        String sql = "SELECT * FROM equipo_computo WHERE nombre LIKE %?% LIMIT ?, ?";
        try {
            List<EquipoComputo> equipos = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(EquipoComputo.class), nombre, (page * size), size);
            logger.debug("Getting equipo computo list por nombre ");
            return Optional.of(equipos);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get equipos computo list por nombre ");
        }
        return Optional.empty();
    }

    public Optional<List<EquipoComputo>> getByNumSerie(String num_serie, Integer page, Integer size) {
        String sql = "SELECT * FROM equipo_computo WHERE num_serie LIKE %?% LIMIT ?, ?";
        try {
            List<EquipoComputo> equipos = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(EquipoComputo.class), num_serie, (page * size), size);
            logger.debug("Getting equipo computo list por num_serie ");
            return Optional.of(equipos);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get equipos computo list por num_serie ");
        }
        return Optional.empty();
    }

    public Optional<List<EquipoComputo>> getByModelo(String modelo, Integer page, Integer size) {
        String sql = "SELECT * FROM equipo_computo WHERE modelo LIKE %?% LIMIT ?, ?";
        try {
            List<EquipoComputo> equipos = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(EquipoComputo.class), modelo, (page * size), size);
            logger.debug("Getting equipo computo list por modelo ");
            return Optional.of(equipos);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get equipos computo list por modelo ");
        }
        return Optional.empty();
    }

    public Optional<List<EquipoComputo>> getByMarca(String marca, Integer page, Integer size) {
        String sql = "SELECT * FROM equipo_computo WHERE marca LIKE %?% LIMIT ?, ?";
        try {
            List<EquipoComputo> equipos = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(EquipoComputo.class), marca, (page * size), size);
            logger.debug("Getting equipo computo list por marca ");
            return Optional.of(equipos);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get equipos computo list por marca ");
        }
        return Optional.empty();
    }

    public Optional<List<EquipoComputo>> getBySistemaOp(String sistema_operativo, Integer page, Integer size) {
        String sql = "SELECT * FROM equipo_computo WHERE sistema_operativo LIKE %?% LIMIT ?, ?";
        try {
            List<EquipoComputo> equipos = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(EquipoComputo.class), sistema_operativo, (page * size), size);
            logger.debug("Getting equipo computo list por sistema operativo ");
            return Optional.of(equipos);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get equipos computo list por sistema operativo ");
        }
        return Optional.empty();
    }

    public Optional<List<EquipoComputo>> listEquiposUsuario(Long id_usuario, Integer page, Integer size) {
        String sql = "SELECT * FROM equipo_computo WHERE id_usuario=? LIMIT ?, ?";
        try {
            List<EquipoComputo> equipos = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(EquipoComputo.class), id_usuario, (page * size), size);
            logger.debug("Getting equipo computo por usuario list ");
            return Optional.of(equipos);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get equipos computo por usuario list ");
        }
        return Optional.empty();
    }

}
