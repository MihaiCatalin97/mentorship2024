package org.java.mentorship.contracts.notification.client;

import jakarta.validation.Valid;
import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.notification.dto.NotificationChannel;
import org.java.mentorship.contracts.notification.dto.NotificationType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@FeignClient(value = "notificationClient",
        url = "${service.notification.url}")
public interface NotificationFeignClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/notifications"
    )
    List<Notification> getNotifications(@RequestParam(required = false, name = "id") Integer id,
                                        @RequestParam(required = false, name = "userId") Integer user_id,
                                        @RequestParam(required = false, name = "email") String email,
                                        @RequestParam(required = false, name = "payload") Map<String, String> payload,
                                        @RequestParam(required = false, name = "channels") List<NotificationChannel> channels,
                                        @RequestParam(required = false, name = "type") List<NotificationType> types,
                                        @RequestParam(required = false, name = "marked") Boolean marked,
                                        @RequestParam(required = false, name = "createAt") OffsetDateTime createAt);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/notifications{id}"
    )
    Notification getNOtificationById(@PathVariable("id") Integer id);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/notifications"
    )
    Notification postNotification(@RequestBody Notification notification);

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/notifications{id}"
    )
    Notification putNotification(@PathVariable("id") Integer id, @RequestBody Notification notification);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/notifications{id}/read"
    )
    Notification postNotificationMarkAsRead(@PathVariable("id") Integer id);

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/notifications{id}"
    )
    Notification deleteNotification(@PathVariable("id") Integer id);
}
