package br.com.gabrieudev.picpay.infrastructure.web.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrieudev.picpay.application.exceptions.StandardException;
import br.com.gabrieudev.picpay.application.usecases.WalletTypeInteractor;
import br.com.gabrieudev.picpay.domain.entities.WalletType;
import br.com.gabrieudev.picpay.infrastructure.web.dtos.CreateWalletTypeDTO;
import br.com.gabrieudev.picpay.infrastructure.web.dtos.UpdateWalletTypeDTO;
import br.com.gabrieudev.picpay.infrastructure.web.dtos.WalletTypeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/wallet-types")
public class WalletTypeController {
    private final WalletTypeInteractor walletTypeInteractor;

    public WalletTypeController(WalletTypeInteractor walletTypeInteractor) {
        this.walletTypeInteractor = walletTypeInteractor;
    }

    @Operation(
        summary = "Criar tipo de cartão",
        description = "Cria um novo tipo de cartão",
        tags = { "WalletType" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201",
                description = "Tipo de cartão criado com sucesso"
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
                description = "Erro interno ao criar tipo de cartão",
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
    public ResponseEntity<WalletTypeDTO> save(
        @Valid
        @RequestBody
        CreateWalletTypeDTO createWalletTypeDTO
    ) {
        WalletType savedWalletType = walletTypeInteractor.save(createWalletTypeDTO.toDomainObj());
        return ResponseEntity.status(HttpStatus.CREATED).body(WalletTypeDTO.fromDomainObj(savedWalletType));
    }

    @Operation(
        summary = "Atualizar tipo de carteira",
        description = "Atualiza um tipo de carteira",
        tags = { "WalletType" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Tipo de carteira atualizado com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tipo de carteira não encontrado",
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
                description = "Erro interno ao atualizar tipo de carteira",
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
    public ResponseEntity<WalletTypeDTO> update(
        @Valid
        @RequestBody
        UpdateWalletTypeDTO updateWalletTypeDTO
    ) {
        WalletType updatedWalletType = walletTypeInteractor.update(updateWalletTypeDTO.toDomainObj());
        return ResponseEntity.status(HttpStatus.OK).body(WalletTypeDTO.fromDomainObj(updatedWalletType));
    }

    @Operation(
        summary = "Obter todos os tipos de carteira",
        description = "Obtém todos os tipos de carteira",
        tags = { "WalletType" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Tipos de carteira obtidos com sucesso"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno ao obter tipos de carteira",
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
    public ResponseEntity<Page<WalletTypeDTO>> findAll(
        Pageable pageable
    ) {
        List<WalletTypeDTO> walletTypes = walletTypeInteractor.findAll(pageable.getPageNumber(), pageable.getPageSize()).stream()
            .map(WalletTypeDTO::fromDomainObj)
            .toList();
        Page<WalletTypeDTO> walletTypesPage = new PageImpl<>(walletTypes, pageable, walletTypes.size());
        return ResponseEntity.status(HttpStatus.OK).body(walletTypesPage);
    }

    @Operation(
        summary = "Obter um tipo de cartão",
        description = "Obtém um tipo de carteira por ID",
        tags = { "WalletType" }
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Tipo de carteira obtido com sucesso"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Tipo de carteira não encontrado",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = StandardException.class
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Erro interno ao obter tipo de carteira",
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
    public ResponseEntity<WalletTypeDTO> findById(
        @Parameter(
            description = "Identificador do tipo de carteira", 
            example = "5c4c5c4c-5c4c-5c4c-5c4c-5c4c5c4c5c4c",
            required = true
        )
        @PathVariable
        UUID UUID
    ) {
        WalletType walletType = walletTypeInteractor.findById(UUID);
        return ResponseEntity.status(HttpStatus.OK).body(WalletTypeDTO.fromDomainObj(walletType));
    }
}
