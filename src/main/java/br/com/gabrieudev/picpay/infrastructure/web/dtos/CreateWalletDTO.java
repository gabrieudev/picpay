package br.com.gabrieudev.picpay.infrastructure.web.dtos;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.picpay.domain.entities.Wallet;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWalletDTO {
    @Schema(
        description = "Nome do usuário",
        example = "João",
        required = true
    )
    @NotBlank(message = "Nome obrigatório")
    private String fullName;

    @Schema(
        description = "CPF/CNPJ do usuário",
        example = "123.456.789-00",
        required = true
    )
    @NotBlank(message = "CPF/CNPJ obrigatório")
    private String cpfCnpj;

    @Schema(
        description = "Email do usuário",
        example = "joao@gmail.com",
        required = true
    )
    @NotBlank(message = "Email obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @Schema(
        description = "Senha do usuário",
        example = "123456",
        required = true
    )
    @NotBlank(message = "Senha obrigatória")
    private String password;

    @Schema(
        description = "Tipo de carteira",
        required = true
    )
    @NotNull(message = "Tipo obrigatório")
    private WalletTypeDTO type;

    public Wallet toDomainObj() {
        return new ModelMapper().map(this, Wallet.class);
    }
}
