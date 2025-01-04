package br.com.gabrieudev.picpay.infrastructure.gateways;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.picpay.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.picpay.application.gateways.WalletGateway;
import br.com.gabrieudev.picpay.domain.entities.Wallet;
import br.com.gabrieudev.picpay.infrastructure.persistence.models.WalletModel;
import br.com.gabrieudev.picpay.infrastructure.persistence.repositories.WalletRepository;

@Service
public class WalletServiceGateway implements WalletGateway {
    private final WalletRepository walletRepository;

    public WalletServiceGateway(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!walletRepository.existsById(id)) {
            throw new EntityNotFoundException("Conta não encontrada");
        }
        walletRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deposit(BigDecimal value, UUID walletId) {
        WalletModel wallet = walletRepository.findById(walletId)
            .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        wallet.deposit(value);

        walletRepository.save(wallet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Wallet> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return walletRepository.findAll(pageable).stream()
            .map(WalletModel::toDomainObj)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Wallet findById(UUID id) {
        return walletRepository.findById(id)
            .map(WalletModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));
    }

    @Override
    @Transactional
    public Wallet save(Wallet wallet) {
        return walletRepository.save(WalletModel.fromDomainObj(wallet)).toDomainObj();
    }

    @Override
    @Transactional
    public Wallet update(Wallet wallet) {
        if (!walletRepository.existsById(wallet.getId())) {
            throw new EntityNotFoundException("Conta não encontrada");
        }
        return walletRepository.save(WalletModel.fromDomainObj(wallet)).toDomainObj();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCpfCnpj(String cpfCnpj) {
        return walletRepository.existsByCpfCnpj(cpfCnpj);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return walletRepository.existsByEmail(email);
    }    
}
