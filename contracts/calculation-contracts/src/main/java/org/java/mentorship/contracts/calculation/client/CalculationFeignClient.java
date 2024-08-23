package org.java.mentorship.contracts.calculation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "calculationClient", url = "${service.calculation.url}")
public interface CalculationFeignClient {
    @GetMapping("/")
    String hello();
}
