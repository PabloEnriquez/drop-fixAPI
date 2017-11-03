package com.itesm.demo.dao;

import com.itesm.demo.domain.User;
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
public class UserDAO {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public Optional<User> getByUuid(String uuid) {
        String sql = "SELECT * FROM user WHERE uuid=?";
        try {
            BeanPropertyRowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
            User user = jdbcTemplate.queryForObject(sql, rowMapper, uuid);
            logger.debug("Getting user with uuid: " + uuid);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("No user with uuid: " + uuid);
        }
        return Optional.empty();
    }

    public Optional<User> insert(User user) {
        String newUuid = UUID.randomUUID().toString();
        try {
            jdbcTemplate.update(
                    "INSERT INTO user "
                            + " (uuid, name, lastname, password,"
                            + " status, date_created, date_modified)"
                            + " VALUES (?,?,?,?, ?,?,?)",
                    newUuid, user.getName(), user.getLastname(), user.getPassword(),
                    user.getStatus(), Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
            logger.debug("Inserting user");
            return getByUuid(newUuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not insert user");
            return Optional.empty();
        }
    }

    public Optional<User> update(User user){
        try {
            jdbcTemplate.update("UPDATE user SET " +
                    "name=?, lastname=?, status=?, date_modified=? WHERE uuid=?",
                    user.getName(), user.getLastname(), user.getStatus(),
                    Timestamp.from(Instant.now()), user.getUuid());
            logger.debug("Updating user: " + user.getUuid());
            return getByUuid(user.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Could not update user: " + user.getUuid());
            return Optional.empty();
        }
    }


    public Optional<List<User>> list(Integer page, Integer size) {
        String sql = "SELECT * FROM user WHERE status != -1 LIMIT ?, ?";
        try {
            List<User> users = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(User.class), (page * size), size);
            logger.debug("Getting user list ");
            return Optional.of(users);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.debug("Could not get user list ");
        }
        return Optional.empty();
    }



}
