package org.java.mentorship.notification.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static org.java.mentorship.notification.sql.SQLfindAll.getSQL;

@Repository
public class NotificationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<NotificationEntity> notificationRowMapper;


    public List<NotificationEntity> getNotifications(Map<String, Object> params) {
        String sql = "SELECT * FROM notifications " + getSQL(params);
        return jdbcTemplate.query(sql, params.values().toArray(), notificationRowMapper);
    }

    public NotificationEntity getNotificationById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT \n" +
                "    n.*, \n" +
                "    (SELECT \n" +
                "         json_agg(nc.channel) \n" +
                "     FROM \n" +
                "         notifications_channels nc \n" +
                "     WHERE \n" +
                "         nc.notification_id = n.id\n" +
                "    ) AS channels\n" +
                "FROM \n" +
                "    notifications n\n" +
                "WHERE\n" +
                "  n.id = ?;", notificationRowMapper, id);
    }

    public NotificationEntity create(NotificationEntity notification) {
        String paylodJson;
        try{
            paylodJson = new ObjectMapper().writeValueAsString(notification.getPayload());
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        jdbcTemplate.update("INSERT INTO notifications (user_id, email, marked_as_read, created_at, payload, type) " +
                        "VALUES (?,?,?,?,?,?)",
                notification.getUserId(),
                notification.getEmail(),
                notification.getMarkedAsRead(),
                notification.getCreatedAt(),
                paylodJson,
                String.valueOf(notification.getType()));
        return notification;
    }

    public boolean deleteNotification(Integer id) {
        jdbcTemplate.update("DELETE FROM notifications WHERE id = ?", id);
        return true;
    }

    public NotificationEntity update(Integer id, NotificationEntity notification) {
        jdbcTemplate.update("UPDATE notifications SET user_id = ? ,email = ? , marked_as_read = ? , created_at= ? , payload = ?, type = ? WHERE id = ?",
                notification.getUserId(), notification.getEmail(), notification.getMarkedAsRead(), notification.getCreatedAt(), String.valueOf(notification.getPayload()), String.valueOf(notification.getType()), id);
        return notification;
    }

//    public List<NotificationEntity> getWebNotificationsByUser(Integer userId) {
//        String sql = "SELECT * FROM notifications WHERE user_id = ? AND type @> '[\"WEB\"]'";
//        return jdbcTemplate.query(sql, new Object[]{userId}, new NotificationRowMapper());
//    }
}
