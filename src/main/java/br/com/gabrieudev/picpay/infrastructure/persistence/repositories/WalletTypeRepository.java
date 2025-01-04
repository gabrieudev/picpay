package br.com.gabrieudev.picpay.infrastructure.persistence.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.picpay.infrastructure.persistence.models.WalletTypeModel;

@Repository
public interface WalletTypeRepository extends JpaRepository<WalletTypeModel, UUID>{
    Optional<WalletTypeModel> findByName(String name);
    boolean existsByName(String name);
}
