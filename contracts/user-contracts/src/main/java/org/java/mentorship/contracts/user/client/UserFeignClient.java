package org.java.mentorship.contracts.user.client;

import org.java.mentorship.contracts.user.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
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
}
