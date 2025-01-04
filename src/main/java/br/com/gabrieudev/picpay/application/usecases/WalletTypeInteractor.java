package br.com.gabrieudev.picpay.application.usecases;

import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.picpay.application.exceptions.InvalidOperationException;
import br.com.gabrieudev.picpay.application.gateways.WalletTypeGateway;
import br.com.gabrieudev.picpay.domain.entities.WalletType;

public class WalletTypeInteractor {
    private final WalletTypeGateway walletTypeGateway;

    public WalletTypeInteractor(WalletTypeGateway walletTypeGateway) {
        this.walletTypeGateway = walletTypeGateway;
    }

    public WalletType save(WalletType walletType) {
        if (walletTypeGateway.existsByName(walletType.getName())) {
            throw new InvalidOperationException("Já existe um tipo de conta com esse nome");
        }
        return walletTypeGateway.save(walletType);
    }

    public WalletType update(WalletType walletType) {
        WalletType walletTypeToUpdate = walletTypeGateway.findById(walletType.getId());

        if (!walletTypeToUpdate.getName().equals(walletType.getName()) && walletTypeGateway.existsByName(walletType.getName())) {
            throw new InvalidOperationException("Já existe um tipo de conta com esse nome");
        }

        return walletTypeGateway.update(walletType);
    }

    public WalletType findById(UUID id) {
        return walletTypeGateway.findById(id);
    }

    public List<WalletType> findAll(Integer page, Integer size) {
        return walletTypeGateway.findAll(page, size);
    }

    public boolean existsByName(String name) {
        return walletTypeGateway.existsByName(name);
    }
}
