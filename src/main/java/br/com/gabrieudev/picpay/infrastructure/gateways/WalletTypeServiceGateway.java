package br.com.gabrieudev.picpay.infrastructure.gateways;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gabrieudev.picpay.application.exceptions.EntityNotFoundException;
import br.com.gabrieudev.picpay.application.gateways.WalletTypeGateway;
import br.com.gabrieudev.picpay.domain.entities.WalletType;
import br.com.gabrieudev.picpay.infrastructure.persistence.models.WalletTypeModel;
import br.com.gabrieudev.picpay.infrastructure.persistence.repositories.WalletTypeRepository;

@Service
public class WalletTypeServiceGateway implements WalletTypeGateway {
    private final WalletTypeRepository walletTypeRepository;

    public WalletTypeServiceGateway(WalletTypeRepository walletTypeRepository) {
        this.walletTypeRepository = walletTypeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalletType> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return walletTypeRepository.findAll(pageable).stream()
            .map(WalletTypeModel::toDomainObj)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public WalletType findById(UUID id) {
        return walletTypeRepository.findById(id)
            .map(WalletTypeModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Tipo de conta não encontrado"));
    }

    @Override
    @Transactional
    public WalletType save(WalletType walletType) {
        return walletTypeRepository.save(WalletTypeModel.fromDomainObj(walletType)).toDomainObj();
    }

    @Override
    @Transactional
    public WalletType update(WalletType walletType) {
        if (!walletTypeRepository.existsById(walletType.getId())) {
            throw new EntityNotFoundException("Tipo de conta não encontrado");
        }
        return walletTypeRepository.save(WalletTypeModel.fromDomainObj(walletType)).toDomainObj();
    }

    @Override
    @Transactional(readOnly = true)
    public WalletType findByName(String name) {
        return walletTypeRepository.findByName(name)
            .map(WalletTypeModel::toDomainObj)
            .orElseThrow(() -> new EntityNotFoundException("Tipo de conta não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return walletTypeRepository.existsByName(name);
    }
}
