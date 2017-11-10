package com.itesm.demo.dao;

import com.itesm.demo.domain.Noticia;
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
public class NoticiaDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(NoticiaDAO.class);

    public Optional<Noticia> getByUuid(String uuid) {
        String sql = "SELECT * FROM noticia WHERE uuid=?";
        try {
            BeanPropertyRowMapper<Noticia> rowMapper = new BeanPropertyRowMapper<>(Noticia.class);
            Noticia noticia = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting noticia with uuid: " + uuid);
            return Optional.of(noticia);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No noticia with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<Noticia> insert(Noticia noticia) {
        String newUuid = UUID.randomUUID().toString();
        try {
            jdbcTemplate.update(
                    "INSERT INTO noticia "
                            + " (uuid, status, fecha_creacion, fecha_modificacion,"
                            + " titulo, descripcion, url)"
                            + " VALUES (?,?,?,?,?,?,?)",
                    newUuid, noticia.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                    noticia.getTitulo(),noticia.getDescripcion(), noticia.getUrl() );
            logger.debug("Inserting noticia");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert noticia");
            return Optional.empty();
        }
    }

    public Optional<Noticia> update(Noticia noticia){
        try {
            jdbcTemplate.update("UPDATE noticia SET " +
                    "status=?, fecha_modificacion=?, titulo=?, descripcion=?, url=?  WHERE uuid=?",
                    noticia.getStatus(), Timestamp.from(Instant.now()), noticia.getTitulo(),
                    noticia.getDescripcion(), noticia.getUrl(), noticia.getUuid() );
            logger.debug("Updating noticia: " + noticia.getUuid());
            return getByUuid(noticia.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update noticia: " + noticia.getUuid());
            return Optional.empty();
        }
    }


    public Optional<List<Noticia>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM noticia WHERE status != -1 LIMIT ?, ?";
        try {
            List<Noticia> noticias = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Noticia.class), (page * size), size);
            logger.debug("Getting noticia list ");
            return Optional.of(noticias);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get noticia list ");
        }
        return Optional.empty();
    }

    public Optional<List<Noticia>> getByTitulo(String titulo, Integer page, Integer size) {
        String sql = "SELECT * FROM noticia WHERE titulo=? LIMIT ?, ?";
        try {
            List<Noticia> noticias = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Noticia.class), titulo, (page * size), size);
            logger.debug("Getting noticia list por titulo ");
            return Optional.of(noticias);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get noticia list por titulo ");
        }
        return Optional.empty();
    }



}
