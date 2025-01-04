package br.com.gabrieudev.picpay.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
    name = "notification",
    url = "https://67782c5d80a79bf919041cf2.mockapi.io/api/"
)
public interface NotificationClient {
    
    @PostMapping("/notify")
    ResponseEntity<Void> sendNotification();

}
