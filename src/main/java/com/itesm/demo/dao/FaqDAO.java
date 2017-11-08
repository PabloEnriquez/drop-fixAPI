package com.itesm.demo.dao;

import com.itesm.demo.domain.Faq;
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
public class FaqDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(FaqDAO.class);

    public Optional<Faq> getByUuid(String uuid) {
        String sql = "SELECT * FROM faq WHERE uuid=?";
        try {
            BeanPropertyRowMapper<Faq> rowMapper = new BeanPropertyRowMapper<>(Faq.class);
            Faq faq = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting faq with uuid: " + uuid);
            return Optional.of(faq);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No faq with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<Faq> insert(Faq faq) {
        String newUuid = UUID.randomUUID().toString();
        try {
            jdbcTemplate.update(
                    "INSERT INTO faq "
                            + " (uuid, titulo, descripcion,"
                            + " status, date_created, date_modified)"
                            + " VALUES (?,?,?,?, ?,?,?)",
                    newUuid, faq.getTitulo(), faq.getDescripcion(),
                    faq.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            logger.debug("Inserting faq");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert faq");
            return Optional.empty();
        }
    }

    public Optional<Faq> update(Faq faq){
        try {
            jdbcTemplate.update("UPDATE faq SET " +
                    "titulo=?, descripcion=?, status=?, date_modified=? WHERE uuid=?",
                    faq.getTitulo(), faq.getDescripcion(), faq.getStatus(),
                    Timestamp.from(Instant.now()), faq.getUuid());
            logger.debug("Updating faq: " + faq.getUuid());
            return getByUuid(faq.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update faq: " + faq.getUuid());
            return Optional.empty();
        }
    }


    public Optional<List<Faq>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM faq WHERE status != -1 LIMIT ?, ?";
        try {
            List<Faq> faqs = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Faq.class), (page * size), size);
            logger.debug("Getting faq list ");
            return Optional.of(faqs);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get faq list ");
        }
        return Optional.empty();
    }



}
