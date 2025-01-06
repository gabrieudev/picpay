package br.com.gabrieudev.picpay.infrastructure.persistence.models;

import java.io.Serializable;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.picpay.domain.entities.WalletType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(of = "id")
@Table(name = "WalletTypes")
public class WalletTypeModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    public static WalletTypeModel fromDomainObj(WalletType walletType) {
        return new ModelMapper().map(walletType, WalletTypeModel.class);
    }

    public WalletType toDomainObj() {
        return new ModelMapper().map(this, WalletType.class);
    }
}
