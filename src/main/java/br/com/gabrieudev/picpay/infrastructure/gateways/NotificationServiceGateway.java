package br.com.gabrieudev.picpay.infrastructure.gateways;

import org.springframework.stereotype.Service;

import br.com.gabrieudev.picpay.application.gateways.NotificationGateway;
import br.com.gabrieudev.picpay.infrastructure.client.NotificationClient;

@Service
public class NotificationServiceGateway implements NotificationGateway {
    private final NotificationClient notificationClient;

    public NotificationServiceGateway(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    @Override
    public boolean sendNotification() {
        return notificationClient.sendNotification().getStatusCode().is2xxSuccessful();
    }
    
}
