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
public class TransferRequestDTO {
    @Schema(
        description = "Valor da transferência",
        example = "100.00",
        required = true
    )
    @NotNull(message = "Valor obrigatório")
    private BigDecimal value;

    @Schema(
        description = "Id da carteira de origem",
        example = "5c4c5c4c-5c4c-5c4c-5c4c-5c4c5c4c5c4c",
        required = true
    )
    @NotNull(message = "Id da carteira de origem obrigatório")
    private UUID payerid;

    @Schema(
        description = "Id da carteira de destino",
        example = "5c4c5c4c-5c4c-5c4c-5c4c-5c4c5c4c5c4c",
        required = true
    )
    @NotNull(message = "Id da carteira de destino obrigatório")
    private UUID payeeid;
}
