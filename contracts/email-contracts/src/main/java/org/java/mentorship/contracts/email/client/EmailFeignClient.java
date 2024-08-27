package org.java.mentorship.contracts.email.client;

import org.java.mentorship.contracts.email.dto.EmailRequest;
import org.java.mentorship.contracts.email.dto.EmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "emailClient",
        url = "${service.email.url}")
public interface EmailFeignClient {

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/test"
    )
    EmailResponse sendTest(@RequestBody EmailRequest emailRequest);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/verify"
    )
    EmailResponse sendVerification(@RequestBody EmailRequest emailRequest);

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/overspend"
    )
    EmailResponse sendOverSpend(@RequestBody EmailRequest emailRequest);
}
