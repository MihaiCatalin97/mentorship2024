package org.java.mentorship.contracts.user.client;

import feign.Param;
import feign.RequestLine;
import org.java.mentorship.contracts.user.dto.User;
import org.java.mentorship.contracts.user.dto.UserRegistrationRequest;
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
            value = "/users/{id}"
    )
    User getUser(@PathVariable("id") Integer id);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/register"
    )
    User registerUser(@RequestBody UserRegistrationRequest registrationRequest);
}
