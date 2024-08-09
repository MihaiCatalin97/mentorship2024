package org.java.mentorship.contracts.notification.client;

import org.java.mentorship.contracts.notification.dto.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "notificationClient",
        url = "${service.notification.url}")
public interface NotificationFeignClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/notifications"
    )
    List<Notification> getNotifications();

}
