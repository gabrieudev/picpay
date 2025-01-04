package br.com.gabrieudev.picpay.infrastructure.web.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.picpay.application.exceptions.StandardException;
import br.com.gabrieudev.picpay.application.usecases.WalletInteractor;
import br.com.gabrieudev.picpay.domain.entities.Wallet;
import br.com.gabrieudev.picpay.infrastructure.web.dtos.CreateWalletDTO;
import br.com.gabrieudev.picpay.infrastructure.web.dtos.DepositRequestDTO;
import br.com.gabrieudev.picpay.infrastructure.web.dtos.TransferRequestDTO;
import br.com.gabrieudev.picpay.infrastructure.web.dtos.UpdateWalletDTO;
import br.com.gabrieudev.picpay.infrastructure.web.dtos.WalletDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/wallets")
public class WalletController {
    private final WalletInteractor walletInteractor;

    public WalletController(WalletInteractor walletInteractor) {
        this.walletInteractor = walletInteractor;
    }

    @Operation(
        summary = "Criar carteira",
        description = "Cria uma nova carteira",
        tags = { "Wallet" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Carteira criada com sucesso"
            ),
            @ApiResponse(
                responseCode = "406",
                description = "Parâmetros de entrada inválidos",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno ao criar carteira",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @PostMapping
    public ResponseEntity<WalletDTO> save(
        @Valid
        @RequestBody
        CreateWalletDTO createWalletDTO
    ) {
        Wallet wallet = walletInteractor.save(createWalletDTO.toDomainObj());
        return ResponseEntity.status(HttpStatus.CREATED).body(WalletDTO.fromDomainObj(wallet));
    }

    @Operation(
        summary = "Atualizar carteira",
        description = "Atualiza uma carteira",
        tags = { "Wallet" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Carteira atualizada com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Carteira não encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "406",
                description = "Parâmetros de entrada inválidos",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno ao criar carteira",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @PutMapping
    public ResponseEntity<WalletDTO> update(
        @Valid
        @RequestBody
        UpdateWalletDTO updateWalletDTO
    ) {
        Wallet wallet = walletInteractor.update(updateWalletDTO.toDomainObj());
        return ResponseEntity.status(HttpStatus.OK).body(WalletDTO.fromDomainObj(wallet));
    }

    @Operation(
        summary = "Depositar na carteira",
        description = "Deposita um valor na carteira",
        tags = { "Wallet" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Depósito realizado com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Carteira não encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "406",
                description = "Parâmetros de entrada inválidos",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Operação inválida",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno ao criar carteira",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(
        @Valid
        @RequestBody
        DepositRequestDTO depositRequestDTO
    ) {
        walletInteractor.deposit(depositRequestDTO.getValue(), depositRequestDTO.getWalletId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
        summary = "Transferir valor",
        description = "Transfere um valor de uma carteira para outra",
        tags = { "Wallet" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Transferência realizada com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Carteira não encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "406",
                description = "Parâmetros de entrada inválidos",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Operação inválida",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno ao criar carteira",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(
        @Valid
        @RequestBody
        TransferRequestDTO transferRequestDTO
    ) {
        walletInteractor.tranfer(transferRequestDTO.getValue(), transferRequestDTO.getPayerid(), transferRequestDTO.getPayeeid());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
        summary = "Obter todas as carteiras",
        description = "Obtém todas as carteiras",
        tags = { "Wallet" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Carteiras obtidas com sucesso"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno ao criar carteira",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @GetMapping
    public ResponseEntity<Page<WalletDTO>> findAll(
        Pageable pageable
    ) {
        List<WalletDTO> wallets = walletInteractor.findAll(pageable.getPageNumber(), pageable.getPageSize()).stream()
            .map(WalletDTO::fromDomainObj)
            .toList();
        
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(wallets, pageable, wallets.size()));
    }

    @Operation(
        summary = "Obter uma carteira",
        description = "Obtém uma carteira por id",
        tags = { "Wallet" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Carteira obtida com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Carteira não encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno ao criar carteira",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @GetMapping("/{UUID}")
    public ResponseEntity<WalletDTO> findById(
        @Parameter(
            description = "Identificador da carteira",
            example = "5c4c5c4c-5c4c-5c4c-5c4c-5c4c5c4c5c4c",
            required = true
        )
        @PathVariable
        UUID UUID
    ) {
        Wallet wallet = walletInteractor.findById(UUID);
        return ResponseEntity.status(HttpStatus.OK).body(WalletDTO.fromDomainObj(wallet));
    }

    @Operation(
        summary = "Deletar uma carteira",
        description = "Deleta uma carteira por id",
        tags = { "Wallet" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "204",
                description = "Carteira deletada com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Carteira não encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno ao criar carteira",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
        }
    )
    @DeleteMapping("/{UUID}")
    public ResponseEntity<Void> delete(
        @Parameter(
            description = "Identificador da carteira",
            example = "5c4c5c4c-5c4c-5c4c-5c4c-5c4c5c4c5c4c",
            required = true
        )
        @PathVariable
        UUID UUID
    ) {
        walletInteractor.delete(UUID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
