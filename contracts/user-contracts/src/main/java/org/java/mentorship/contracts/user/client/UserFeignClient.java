package org.java.mentorship.contracts.user.client;

import org.java.mentorship.contracts.user.dto.Session;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.request.LoginRequest;
import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "userClient",
        url = "${service.user.url}")
public interface UserFeignClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/users"
    )
    List<User> getUsers();

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/users/{id}"
    )
    User getUser(@PathVariable("id") Integer id);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/register"
    )
    User registerUser(@RequestBody RegistrationRequest registrationRequest);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/verify/{id}/{token}"
    )
    Boolean verifyUser(@PathVariable("id") Integer id, @PathVariable("token") String token);


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
            value = "/sessions/user/{id}"
    )
    List<Session> getSessions(@PathVariable("id") Integer id);

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/sessions/user/{id}/active"
    )
    List<Session> getActiveSessions(@PathVariable("id") Integer id);

}
