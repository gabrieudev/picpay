package br.com.gabrieudev.picpay.infrastructure.config.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.picpay.application.gateways.WalletTypeGateway;
import br.com.gabrieudev.picpay.application.usecases.WalletTypeInteractor;
import br.com.gabrieudev.picpay.infrastructure.gateways.WalletTypeServiceGateway;
import br.com.gabrieudev.picpay.infrastructure.persistence.repositories.WalletTypeRepository;

@Configuration
public class WalletTypeConfig {
    @Bean
    WalletTypeInteractor walletTypeInteractor(WalletTypeGateway walletTypeGateway) {
        return new WalletTypeInteractor(walletTypeGateway);
    }

    @Bean
    WalletTypeGateway walletTypeGateway(WalletTypeRepository walletTypeRepository) {
        return new WalletTypeServiceGateway(walletTypeRepository);
    }
}
