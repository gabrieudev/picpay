package br.com.gabrieudev.picpay.application.usecases;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.picpay.application.exceptions.InvalidOperationException;
import br.com.gabrieudev.picpay.application.gateways.AuthorizationGateway;
import br.com.gabrieudev.picpay.application.gateways.NotificationGateway;
import br.com.gabrieudev.picpay.application.gateways.WalletGateway;
import br.com.gabrieudev.picpay.domain.entities.Wallet;
import br.com.gabrieudev.picpay.domain.entities.WalletType;

public class WalletInteractor {
    private final WalletGateway walletGateway;
    private final AuthorizationGateway authorizationGateway;
    private final NotificationGateway notificationGateway;
    
    public WalletInteractor(WalletGateway walletGateway, AuthorizationGateway authorizationGateway, NotificationGateway notificationGateway) {
        this.walletGateway = walletGateway;
        this.authorizationGateway = authorizationGateway;
        this.notificationGateway = notificationGateway;
    }

    @Transactional
    public void tranfer(BigDecimal value, UUID payerId, UUID payeeId) {
        Wallet payer = walletGateway.findById(payerId);
        Wallet payee = walletGateway.findById(payeeId);
        WalletType payerType = payer.getType();
        
        if (payerType.getName().equals("Shopkeeper")) {
            throw new InvalidOperationException("Comerciantes não podem realizar transferências");
        }
        
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOperationException("Valor da transferência inválido");
        }

        if (payerId.equals(payeeId)) {
            throw new InvalidOperationException("Não é possível transferir para a mesma conta");
        }

        if (payer.getBalance().compareTo(value) > 0) {
            throw new InvalidOperationException("Saldo insuficiente");
        }

        payee.withdraw(value);
        payer.deposit(value);

        if (!authorizationGateway.isAuthorized()) {
            throw new InvalidOperationException("Operação não autorizada");
        }

        walletGateway.save(payer);
        walletGateway.save(payee);

        notificationGateway.sendNotification();
    }

    public void deposit(BigDecimal value, UUID walletId) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOperationException("Valor do deposito inválido");
        }
        walletGateway.deposit(value, walletId);
    }

    public Wallet findById(UUID id) {
        return walletGateway.findById(id);
    }

    public Wallet save(Wallet wallet) {
        if (existsByCpfCnpj(wallet.getCpfCnpj())) {
            throw new InvalidOperationException("Já existe uma carteira cadastrada com esse CPF/CNPJ");
        }
        
        if (existsByEmail(wallet.getEmail())) {
            throw new InvalidOperationException("Já existe uma carteira cadastrada com esse e-mail");
        }
        
        wallet.setBalance(BigDecimal.ZERO);
        return walletGateway.save(wallet);
    }

    public Wallet update(Wallet wallet) {
        Wallet walletToUpdate = walletGateway.findById(wallet.getId());

        if (!walletToUpdate.getEmail().equals(wallet.getEmail()) && existsByEmail(wallet.getEmail())) {
            throw new InvalidOperationException("Já existe uma carteira cadastrada com esse e-mail");
        }

        if (!walletToUpdate.getCpfCnpj().equals(wallet.getCpfCnpj()) && existsByCpfCnpj(wallet.getCpfCnpj())) {
            throw new InvalidOperationException("Já existe uma carteira cadastrada com esse CPF/CNPJ");
        }

        return walletGateway.update(wallet);
    }

    public void delete(UUID id) {
        walletGateway.delete(id);
    }

    public List<Wallet> findAll(Integer page, Integer size) {
        return walletGateway.findAll(page, size);
    }

    public boolean existsByCpfCnpj(String cpfCnpj) {
        return walletGateway.existsByCpfCnpj(cpfCnpj);
    }

    public boolean existsByEmail(String email) {
        return walletGateway.existsByEmail(email);
    }
}
