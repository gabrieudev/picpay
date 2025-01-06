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
import br.com.gabrieudev.picpay.infrastructure.persistence.redis.WalletRedisRepository;
import br.com.gabrieudev.picpay.infrastructure.persistence.repositories.WalletRepository;

@Service
public class WalletServiceGateway implements WalletGateway {
    private final WalletRepository walletRepository;
    private final WalletRedisRepository walletRedisRepository;

    public WalletServiceGateway(WalletRepository walletRepository, WalletRedisRepository walletRedisRepository) {
        this.walletRepository = walletRepository;
        this.walletRedisRepository = walletRedisRepository;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!walletRepository.existsById(id)) {
            throw new EntityNotFoundException("Conta não encontrada");
        }

        walletRedisRepository.delete(id);
        walletRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deposit(BigDecimal value, UUID walletId) {
        WalletModel wallet = walletRepository.findById(walletId)
            .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        wallet.deposit(value);

        walletRedisRepository.save(wallet);
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
        WalletModel wallet = (WalletModel) walletRedisRepository.findById(id);
        
        if (wallet != null) {
            return wallet.toDomainObj();
        }

        WalletModel walletModel = walletRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        walletRedisRepository.save(walletModel);

        return walletModel.toDomainObj();
    }

    @Override
    @Transactional
    public Wallet save(Wallet wallet) {
        WalletModel savedWallet = walletRepository.save(WalletModel.fromDomainObj(wallet));

        walletRedisRepository.save(savedWallet);
        
        return savedWallet.toDomainObj();
    }

    @Override
    @Transactional
    public Wallet update(Wallet wallet) {
        if (!walletRepository.existsById(wallet.getId())) {
            throw new EntityNotFoundException("Conta não encontrada");
        }

        WalletModel savedWallet = walletRepository.save(WalletModel.fromDomainObj(wallet));

        walletRedisRepository.save(savedWallet);

        return savedWallet.toDomainObj();
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
