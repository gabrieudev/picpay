package br.com.gabrieudev.picpay.infrastructure.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gabrieudev.picpay.infrastructure.persistence.models.WalletModel;

@Repository
public interface WalletRepository extends JpaRepository<WalletModel, UUID> {
    boolean existsByCpfCnpj(String cpfCnpj);
    boolean existsByEmail(String email);
}
