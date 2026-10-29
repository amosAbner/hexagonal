package com.study.hexagonal.adapters.in.controller;

import com.study.hexagonal.adapters.in.controller.request.CustomerRequest;
import com.study.hexagonal.adapters.in.controller.response.CustomerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@Tag(name = "Clientes", description = "Operações para cadastro e consulta de clientes")
public interface CustomerSwagger {

    @Operation(summary = "Cadastrar cliente", description = "Cadastra um cliente e solicita a validação do CPF.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado",
                    headers = @Header(name = "Location", description = "URL do cliente criado"),
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Requisição inválida: nome, CPF e CEP são obrigatórios",
                    content = @Content)
    })
    ResponseEntity<Void> insertCustomer(
            @Valid
            @RequestBody(required = true, content = @Content(
                    schema = @Schema(implementation = CustomerRequestSchema.class)))
            CustomerRequest customerRequest);

    @Operation(summary = "Buscar cliente", description = "Busca os dados de um cliente pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(schema = @Schema(implementation = CustomerResponseSchema.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar o cliente",
                    content = @Content)
    })
    ResponseEntity<CustomerResponse> getCustomerById(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador do cliente",
                    example = "665f1a2b3c4d5e6f7890abcd", required = true)
            String id);

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente atualizado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Requisição inválida: nome, CPF e CEP são obrigatórios",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno ao atualizar o cliente",
                    content = @Content)
    })
    ResponseEntity<Void> updateCustomer(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador do cliente",
                    example = "665f1a2b3c4d5e6f7890abcd", required = true)
            String id,
            @Valid
            @RequestBody(required = true, content = @Content(
                    schema = @Schema(implementation = CustomerRequestSchema.class)))
            CustomerRequest customerRequest);

    @Operation(summary = "Excluir cliente", description = "Exclui um cliente pelo identificador.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente excluído",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno ao excluir o cliente",
                    content = @Content)
    })
    ResponseEntity<Void> deleteCustomer(
            @Parameter(name = "id", in = ParameterIn.PATH, description = "Identificador do cliente",
                    example = "665f1a2b3c4d5e6f7890abcd", required = true)
            String id);

    @Schema(name = "CustomerRequest", description = "Dados necessários para cadastrar ou atualizar um cliente")
    class CustomerRequestSchema {
        @Schema(description = "Nome completo do cliente", example = "Maria da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        public String name;

        @Schema(description = "CPF do cliente", example = "12345678909", requiredMode = Schema.RequiredMode.REQUIRED)
        public String cpf;

        @Schema(description = "CEP do endereço do cliente", example = "01001000", requiredMode = Schema.RequiredMode.REQUIRED)
        public String zipCode;
    }

    @Schema(name = "CustomerResponse", description = "Dados do cliente retornados pela API")
    class CustomerResponseSchema {
        @Schema(description = "Identificador do cliente", example = "665f1a2b3c4d5e6f7890abcd")
        public String id;

        @Schema(description = "Nome completo do cliente", example = "Maria da Silva")
        public String name;

        @Schema(description = "CPF do cliente", example = "12345678909")
        public String cpf;

        @Schema(description = "Indica se o CPF foi validado", example = "true")
        public Boolean isValidCpf;

        @Schema(description = "Endereço associado ao CEP informado")
        public AddressSchema address;
    }

    @Schema(name = "CustomerAddress", description = "Endereço do cliente")
    class AddressSchema {
        @Schema(description = "Logradouro", example = "Praça da Sé")
        public String street;

        @Schema(description = "Cidade", example = "São Paulo")
        public String city;

        @Schema(description = "Estado", example = "SP")
        public String state;
    }
}
