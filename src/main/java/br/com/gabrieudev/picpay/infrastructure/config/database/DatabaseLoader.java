package br.com.gabrieudev.picpay.infrastructure.config.database;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.gabrieudev.picpay.infrastructure.persistence.models.WalletTypeModel;
import br.com.gabrieudev.picpay.infrastructure.persistence.repositories.WalletTypeRepository;

@Configuration
public class DatabaseLoader implements CommandLineRunner {
    private final WalletTypeRepository walletTypeRepository;

    public DatabaseLoader(WalletTypeRepository walletTypeRepository) {
        this.walletTypeRepository = walletTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!walletTypeRepository.existsByName("Merchant") && !walletTypeRepository.existsByName("Common")) {
            WalletTypeModel shopkeeperWalletType = WalletTypeModel.builder()
                .name("Shopkeeper")
                .description("Shopkeeper wallet type")
                .build();
            WalletTypeModel commomWalletType = WalletTypeModel.builder()
                .name("Common")
                .description("Common wallet type")
                .build();

            walletTypeRepository.saveAll(List.of(shopkeeperWalletType, commomWalletType));
        }
    }
}
