package br.com.gabrieudev.picpay.domain.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class Wallet {
    private UUID id;
    private String fullName;
    private String cpfCnpj;
    private String email;
    private String password;
    private BigDecimal balance;
    private WalletType type;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getCpfCnpj() {
        return cpfCnpj;
    }
    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public WalletType getType() {
        return type;
    }
    public void setType(WalletType type) {
        this.type = type;
    }

    public Wallet(UUID id, String fullName, String cpfCnpj, String email, String password, BigDecimal balance, WalletType type) {
        this.id = id;
        this.fullName = fullName;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.type = type;
    }

    public Wallet() {
    }

    public void deposit(BigDecimal value) {
        this.balance = this.balance.add(value);
    }

    public void withdraw(BigDecimal value) {
        this.balance = this.balance.subtract(value);
    }
}
