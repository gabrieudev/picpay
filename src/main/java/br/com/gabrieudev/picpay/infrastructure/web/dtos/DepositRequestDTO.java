package br.com.gabrieudev.picpay.infrastructure.web.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequestDTO {
    @Schema(
        description = "Id da carteira",
        example = "5c4c5c4c-5c4c-5c4c-5c4c-5c4c5c4c5c4c",
        required = true
    )
    @NotNull(message = "Id da carteira obrigatório")
    private UUID walletId;

    @Schema(
        description = "Valor",
        example = "100.00",
        required = true
    )
    @NotNull(message = "Valor obrigatório")
    private BigDecimal value;
}
