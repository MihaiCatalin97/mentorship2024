package org.java.mentorship.notification.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java.mentorship.notification.domain.NotificationEntity;
import org.java.mentorship.notification.domain.enums.NotificationType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Map;

@Component
public class NotificationRowMapper implements RowMapper<NotificationEntity> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NotificationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        NotificationEntity notification = new NotificationEntity();

        notification.setId(rs.getInt("id"));
        notification.setUserId(rs.getInt("user_id"));
        notification.setEmail(rs.getString("email"));
        notification.setMarkedAsRead(rs.getBoolean("marked_as_read"));
        notification.setType(NotificationType.valueOf(rs.getString("type")));
        notification.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class));
        String payloadJson = rs.getString("payload");

        try {
            Map<String, Object> payload = objectMapper.readValue(payloadJson, new TypeReference<>() {
            });
            notification.setPayload(payload);
        } catch (JsonProcessingException e) {
            throw new SQLException("Failed to map to JSON fields " + payloadJson, e);
        }

        return notification;
    }

}
