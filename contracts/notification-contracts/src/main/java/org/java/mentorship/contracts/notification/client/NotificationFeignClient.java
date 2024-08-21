package org.java.mentorship.contracts.notification.client;

import org.java.mentorship.contracts.notification.dto.Notification;
import org.java.mentorship.contracts.notification.dto.NotificationChannel;
import org.java.mentorship.contracts.notification.dto.NotificationType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "notificationClient",
        url = "${service.notification.url}")
public interface NotificationFeignClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/notifications"
    )
    List<Notification> getNotifications(@RequestParam(required = false, name = "userId") Integer userId,
                                        @RequestParam(required = false, name = "email") String email,
                                        @RequestParam(required = false, name = "channel") NotificationChannel channel,
                                        @RequestParam(required = false, name = "type") NotificationType type,
                                        @RequestParam(required = false, name = "marked") Boolean marked);

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
    Notification markNotificationMarkAsRead(@PathVariable("id") Integer id);

}
