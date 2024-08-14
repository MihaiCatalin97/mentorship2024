package org.java.mentorship.contracts.user.client;

import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "sessionClient",
        url = "${service.user.url}")
public interface SessionFeignClient {
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/sessions"
    )
    Session createSession(@RequestBody LoginRequest loginRequest);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/sessions/{key}"
    )
    Session getSession(@PathVariable("key") String key);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/sessions"
    )
    List<Session> find(@RequestParam(name = "userId") Integer userId, @RequestParam(name = "isActive") Boolean isActive);
}
