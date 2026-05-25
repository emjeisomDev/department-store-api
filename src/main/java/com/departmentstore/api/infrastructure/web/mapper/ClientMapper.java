package com.departmentstore.api.infrastructure.web.mapper;

import com.departmentstore.api.application.command.client.RegisterClientCommand;
import com.departmentstore.api.domain.entity.Client;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.infrastructure.web.dto.request.ClientRequestDto;
import com.departmentstore.api.infrastructure.web.dto.response.ClientResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public RegisterClientCommand toCommand(final ClientRequestDto dto) {
        return new RegisterClientCommand(
                dto.personId(),
                dto.clientCode(),
                dto.clientRank().name()
        );
    }

    public ClientResponseDto toResponseDto(final Client client, final Person person) {
        return new ClientResponseDto(
                client.getId(),
                person.getName(),
                client.getClientCode(),
                client.getClientRank().name(),
                client.getStatus().name()
        );
    }




}
