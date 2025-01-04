package br.com.gabrieudev.picpay.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    name = "authorization",
    url = "https://67782c5d80a79bf919041cf2.mockapi.io/api/"
)
public interface AuthorizationClient {
    
    @GetMapping("/authorize")
    ResponseEntity<Void> authorize();

}
