package org.java.mentorship.contracts.user.client;

import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.request.RegistrationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
            value = "/users/{userId}"
    )
    User getUser(@PathVariable("userId") Integer userId);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/register"
    )
    User registerUser(@RequestBody RegistrationRequest registrationRequest);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/verify/{userId}/{token}"
    )
    Boolean verifyUser(@PathVariable("userId") Integer userId, @PathVariable("token") String token);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/verify/{userId}"
    )
    Boolean resendVerificationToken(@PathVariable("userId") Integer userId);
}
