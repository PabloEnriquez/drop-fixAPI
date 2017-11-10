package com.itesm.demo.dao;

import com.itesm.demo.domain.Chat;
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
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ChatDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ChatDAO.class);

    public Optional<Chat> getByUuid(String uuid) {
        String sql = "SELECT * FROM chat WHERE uuid=?";
        try {
            BeanPropertyRowMapper<Chat> rowMapper = new BeanPropertyRowMapper<>(Chat.class);
            Chat chat = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting chat with uuid: " + uuid);
            return Optional.of(chat);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No chat with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<Chat> insert(Chat chat) {
        String newUuid = UUID.randomUUID().toString();
        Long newId = Long.valueOf(new AtomicInteger(0).incrementAndGet());
        try {
            jdbcTemplate.update(
                    "INSERT INTO chat "
                            + " ( id, uuid, status, fecha_creacion, fecha_modificacion,"
                            + " id_usuario, id_tecnico )"
                            + " VALUES (?,?,?,?,?,?,?)",
                    newId, newUuid, chat.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()),
                    chat.getId_usuario(), chat.getId_tecnico() );
            logger.debug("Inserting chat");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert chat");
            return Optional.empty();
        }
    }

    public Optional<Chat> update(Chat chat){
        try {
            jdbcTemplate.update("UPDATE chat SET " +
                            "status=?, fecha_modificacion=? WHERE uuid=?",
                    chat.getStatus(), Timestamp.from(Instant.now()), chat.getUuid() );
            logger.debug("Updating chat: " + chat.getUuid());
            return getByUuid(chat.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update chat: " + chat.getUuid());
            return Optional.empty();
        }
    }

    public Optional<List<Chat>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM chat WHERE status != -1 LIMIT ?, ?";
        try {
            List<Chat> chats = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Chat.class), (page * size), size);
            logger.debug("Getting chats list ");
            return Optional.of(chats);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get chats list ");
        }
        return Optional.empty();
    }

    public Optional<List<Chat>> getByFechaCreacion(Date fecha_creacion, Integer page, Integer size ) {
        String sql = "SELECT * FROM chat WHERE fecha_creacion LIKE %?% LIMIT ?, ?";
        try {
            List<Chat> chats = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Chat.class), fecha_creacion, (page * size), size);
            logger.debug("Getting chats list por fecha de creacion ");
            return Optional.of(chats);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get chats list por fecha de creacion ");
        }
        return Optional.empty();
    }

    public Optional<List<Chat>> listChatsUsuario(Long id_usuario, Integer page, Integer size) {
        String sql = "SELECT * FROM chat WHERE id_usuario=? LIMIT ?, ?";
        try {
            List<Chat> chats = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Chat.class), id_usuario, (page * size), size);
            logger.debug("Getting chats por usuario list ");
            return Optional.of(chats);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get chats por usuario list ");
        }
        return Optional.empty();
    }

    public Optional<List<Chat>> listChatsTecnico(Long id_tecnico, Integer page, Integer size) {
        String sql = "SELECT * FROM chat WHERE id_tecnico=? LIMIT ?, ?";
        try {
            List<Chat> chats = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(Chat.class), id_tecnico, (page * size), size);
            logger.debug("Getting chats por tecnico list ");
            return Optional.of(chats);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get chats por tecnico list ");
        }
        return Optional.empty();
    }

}
