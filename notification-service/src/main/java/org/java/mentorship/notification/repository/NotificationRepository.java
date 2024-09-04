package org.java.mentorship.notification.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.java.mentorship.notification.sql.SQLfindAll.getSQL;

@Repository
public class NotificationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<NotificationEntity> notificationRowMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public List<NotificationEntity> getNotifications(Map<String, Object> params) {
        String sql = "SELECT * FROM notifications " + getSQL(params);
        return jdbcTemplate.query(sql, params.values().toArray(), notificationRowMapper);
    }

    public NotificationEntity getNotificationById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM notifications WHERE id = ?", notificationRowMapper, id);
    }

    public NotificationEntity create(NotificationEntity notification) {
        String paylodJson;
        try {
            paylodJson = objectMapper.writeValueAsString(notification.getPayload());
            System.out.println(notification.getPayload());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String sql = "INSERT INTO notifications (user_id, email, marked_as_read, created_at, payload, type) " +
                "VALUES (?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, notification.getUserId());
            ps.setString(2, notification.getEmail());
            ps.setBoolean(3, notification.getMarkedAsRead());
            ps.setObject(4, Timestamp.from(notification.getCreatedAt().toInstant()));
            ps.setString(5, paylodJson);
            ps.setString(6, String.valueOf(notification.getType()));
            return ps;
        };

        jdbcTemplate.update(psc, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            notification.setId(key.intValue());
        }
        return notification;
    }


    public boolean deleteNotification(Integer id) {
        jdbcTemplate.update("DELETE FROM notifications WHERE id = ?", id);
        return true;
    }

    public NotificationEntity update(Integer id, NotificationEntity notification) {
        try {
            jdbcTemplate.update("UPDATE notifications SET user_id = ? ,email = ? , marked_as_read = ? , created_at= ? , payload = ?, type = ? WHERE id = ?",
                    notification.getUserId(), notification.getEmail(), notification.getMarkedAsRead(), notification.getCreatedAt(), objectMapper.writeValueAsString(notification.getPayload()), String.valueOf(notification.getType()), id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return notification;
    }

}
