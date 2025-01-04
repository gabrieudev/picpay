package br.com.gabrieudev.picpay.infrastructure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.picpay.application.gateways.AuthorizationGateway;
import br.com.gabrieudev.picpay.application.gateways.NotificationGateway;
import br.com.gabrieudev.picpay.application.gateways.WalletGateway;
import br.com.gabrieudev.picpay.application.usecases.WalletInteractor;
import br.com.gabrieudev.picpay.infrastructure.gateways.WalletServiceGateway;
import br.com.gabrieudev.picpay.infrastructure.persistence.repositories.WalletRepository;

@Configuration
public class WalletConfig {
    @Bean
    WalletInteractor walletInteractor(WalletGateway walletGateway, AuthorizationGateway authorizationGateway, NotificationGateway notificationGateway) {
        return new WalletInteractor(walletGateway, authorizationGateway, notificationGateway);
    }

    @Bean
    WalletGateway walletGateway(WalletRepository walletRepository) {
        return new WalletServiceGateway(walletRepository);
    }
}
