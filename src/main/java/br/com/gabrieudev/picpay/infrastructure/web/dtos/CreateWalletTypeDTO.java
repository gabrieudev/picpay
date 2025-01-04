package br.com.gabrieudev.picpay.infrastructure.web.dtos;

import org.modelmapper.ModelMapper;

import br.com.gabrieudev.picpay.domain.entities.WalletType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWalletTypeDTO {
    @Schema(
        description = "Nome do tipo de carteira",
        example = "Lojista",
        required = true
    )
    @NotBlank(message = "Nome obrigatório")
    private String name;

    private String description;

    public WalletType toDomainObj() {
        return new ModelMapper().map(this, WalletType.class);
    }
}
