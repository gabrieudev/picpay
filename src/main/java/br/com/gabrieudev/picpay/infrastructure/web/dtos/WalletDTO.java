package br.com.gabrieudev.picpay.infrastructure.web.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.picpay.domain.entities.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {
    private UUID id;
    private String fullName;
    private String cpfCnpj;
    private String email;
    private String password;
    private BigDecimal balance;
    private WalletTypeDTO type;

    public static WalletDTO fromDomainObj(Wallet wallet) {
        return new ModelMapper().map(wallet, WalletDTO.class);
    }

    public Wallet toDomainObj() {
        return new ModelMapper().map(this, Wallet.class);
    }
}
