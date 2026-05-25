package com.departmentstore.api.infrastructure.web.controller;

import com.departmentstore.api.application.port.in.ManageClientUseCase;
import com.departmentstore.api.application.port.in.ManagePersonUseCase;
import com.departmentstore.api.domain.entity.Client;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.infrastructure.web.dto.request.ClientRequestDto;
import com.departmentstore.api.infrastructure.web.dto.request.UpdateClientRankRequestDto;
import com.departmentstore.api.infrastructure.web.dto.response.ApiResponseDto;
import com.departmentstore.api.infrastructure.web.dto.response.ClientResponseDto;
import com.departmentstore.api.infrastructure.web.mapper.ClientMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ManageClientUseCase useCase;
    private final ManagePersonUseCase managePersonUseCase;
    private final ClientMapper mapper;

    public ClientController(
            final ManageClientUseCase useCase,
            final ManagePersonUseCase managePersonUseCase,
            final ClientMapper mapper
    ) {
        this.useCase = useCase;
        this.managePersonUseCase = managePersonUseCase;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ApiResponseDto<ClientResponseDto>
    findById(
            @PathVariable final Long id
    ) {

        Client client = useCase.findById(id);
        Person person = managePersonUseCase.findById(client.getPersonId()).orElseThrow();

        return new ApiResponseDto<>(
                true,
                mapper.toResponseDto(client, person),
                "Client found successfully",
                LocalDateTime.now()
        );
    }

    @GetMapping
    public ApiResponseDto<List<ClientResponseDto>> findAll(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size
    ) {

        List<ClientResponseDto> response =
                useCase.findActive(page, size).stream().map(client -> {
                    Person person = managePersonUseCase.findById(client.getPersonId()).orElseThrow();
                    return mapper.toResponseDto(client, person);
                }).toList();

        return new ApiResponseDto<>(
                true,
                response,
                "Clients retrieved successfully",
                LocalDateTime.now()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<ClientResponseDto> register(
            @Valid @RequestBody final ClientRequestDto request) {

        Client client = useCase.register(mapper.toCommand(request));
        Person person = managePersonUseCase.findById(client.getPersonId()).orElseThrow();

        return new ApiResponseDto<>(
                true,
                mapper.toResponseDto(client, person),
                "Client registered successfully",
                LocalDateTime.now()
        );
    }

    @PatchMapping("/{id}/rank")
    public ApiResponseDto<Void> updateRank(
            @PathVariable final Long id,
            @Valid @RequestBody final UpdateClientRankRequestDto request
    ) {
        useCase.updateRank(id, request.rank());
        return new ApiResponseDto<>(
                true,
                null,
                "Client rank updated successfully",
                LocalDateTime.now()
        );
    }

    @PatchMapping("/{id}/deactivate")
    public ApiResponseDto<Void> deactivate(@PathVariable final Long id) {
        useCase.deactivate(id);
        return new ApiResponseDto<>(
                true,
                null,
                "Client deactivated successfully",
                LocalDateTime.now()
        );
    }



}