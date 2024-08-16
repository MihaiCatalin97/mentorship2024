package org.java.mentorship.notification.mapper;


import org.java.mentorship.notification.domain.enums.NotificationChannel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class NotificationChannelRowMapper implements RowMapper<NotificationChannel> {

    @Override
    public NotificationChannel mapRow(ResultSet rs, int rowNum) throws SQLException {
        String channel = rs.getString("channel");
        return NotificationChannel.valueOf(channel.toUpperCase());
    }

}