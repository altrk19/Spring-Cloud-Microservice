package com.microservice.client;

import com.microservice.client.dto.AccountResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("account-service")
public interface AccountServiceClient {

    @GetMapping("/account/{id}")
    ResponseEntity<AccountResource> getSingleAccount(@PathVariable("id") String id);

}
