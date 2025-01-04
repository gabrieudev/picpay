package br.com.gabrieudev.picpay.application.gateways;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import br.com.gabrieudev.picpay.domain.entities.Wallet;

public interface WalletGateway {
    Wallet save(Wallet wallet);
    Wallet findById(UUID id);
    List<Wallet> findAll(Integer page, Integer size);
    Wallet update(Wallet wallet);
    void delete(UUID id);
    void deposit(BigDecimal value, UUID walletId);
    boolean existsByCpfCnpj(String cpfCnpj);
    boolean existsByEmail(String email);
}
