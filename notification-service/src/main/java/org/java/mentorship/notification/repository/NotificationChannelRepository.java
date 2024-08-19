package org.java.mentorship.notification.repository;

import org.java.mentorship.notification.domain.enums.NotificationChannel;
import org.java.mentorship.notification.mapper.NotificationChannelRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static org.java.mentorship.notification.sql.SQLfindAll.getSQL;

@Repository
public class NotificationChannelRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NotificationChannelRowMapper notificationChannelRowMapper;

    public List<NotificationChannel> getNotificationsChannels(Map<String, Object> params) {
        String sql = "SELECT * FROM notifications_channels " + getSQL(params);
        return jdbcTemplate.query(sql, params.values().toArray(), notificationChannelRowMapper);
    }

    public List<NotificationChannel> getNotificationsById(int notificationId) {
        String sql = "SELECT channel FROM notifications_channels WHERE notification_id = ?";
        return jdbcTemplate.query(sql, new NotificationChannelRowMapper(), notificationId);
    }

    public int updateNotificationChannel(int id, NotificationChannel newChannel) {
        String sql = "UPDATE notifications_channels SET channel = ? WHERE id = ?";
        return jdbcTemplate.update(sql, newChannel.name(), id);
    }

    public int deleteNotificationChannel(int id) {
        String sql = "DELETE FROM notifications_channels WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int createNotificationChannel(int notificationId, NotificationChannel channel) {
        String sql = "INSERT INTO notifications_channels (notification_id, channel) VALUES (?, ?)";
        return jdbcTemplate.update(sql, notificationId, channel.name());
    }

}
