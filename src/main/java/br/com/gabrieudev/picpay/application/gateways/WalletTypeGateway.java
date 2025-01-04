package br.com.gabrieudev.picpay.application.gateways;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.picpay.domain.entities.WalletType;

public interface WalletTypeGateway {
    WalletType save(WalletType walletType);
    WalletType update(WalletType walletType);
    WalletType findById(UUID id);
    WalletType findByName(String name);
    List<WalletType> findAll(Integer page, Integer size);
    boolean existsByName(String name);
}
