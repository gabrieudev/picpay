package br.com.gabrieudev.picpay.infrastructure.web.dtos;

import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.picpay.domain.entities.WalletType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTypeDTO {
    private UUID id;
    private String name;
    private String description;

    public static WalletTypeDTO fromDomainObj(WalletType walletType) {
        return new ModelMapper().map(walletType, WalletTypeDTO.class);
    }

    public WalletType toDomainObj() {
        return new ModelMapper().map(this, WalletType.class);
    }
}
