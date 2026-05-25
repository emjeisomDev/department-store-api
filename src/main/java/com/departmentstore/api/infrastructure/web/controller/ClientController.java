package com.departmentstore.api.infrastructure.web.controller;

import com.departmentstore.api.application.port.in.ManageClientUseCase;
import com.departmentstore.api.application.port.in.ManagePersonUseCase;
import com.departmentstore.api.domain.entity.Client;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.infrastructure.web.dto.request.ClientRequestDto;
import com.departmentstore.api.infrastructure.web.dto.response.ApiResponseDto;
import com.departmentstore.api.infrastructure.web.dto.response.ClientResponseDto;
import com.departmentstore.api.infrastructure.web.mapper.ClientMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
}