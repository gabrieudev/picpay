package br.com.gabrieudev.picpay.infrastructure.persistence.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.picpay.domain.entities.Wallet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Wallets")
public class WalletModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "fullName", nullable = false)
    private String fullName;
    
    @Column(name = "cpfCnpj", nullable = false, unique = true)
    private String cpfCnpj;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
    
    @ManyToOne
    @JoinColumn(name = "walletTypeId")
    private WalletTypeModel type;

    public static WalletModel fromDomainObj(Wallet wallet) {
        return new ModelMapper().map(wallet, WalletModel.class);
    }

    public Wallet toDomainObj() {
        return new ModelMapper().map(this, Wallet.class);
    }

    public void deposit(BigDecimal value) {
        this.balance = this.balance.add(value);
    }

    public void withdraw(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }
}
