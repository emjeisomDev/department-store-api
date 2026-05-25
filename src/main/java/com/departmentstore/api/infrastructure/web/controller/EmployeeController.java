package com.departmentstore.api.infrastructure.web.controller;

import com.departmentstore.api.application.port.in.ManageEmployeeUseCase;
import com.departmentstore.api.application.port.in.ManagePersonUseCase;
import com.departmentstore.api.domain.entity.Employee;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.infrastructure.web.dto.request.EmployeeRequestDto;
import com.departmentstore.api.infrastructure.web.dto.response.ApiResponseDto;
import com.departmentstore.api.infrastructure.web.dto.response.EmployeeResponseDto;
import com.departmentstore.api.infrastructure.web.mapper.EmployeeMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final ManageEmployeeUseCase useCase;
    private final ManagePersonUseCase managePersonUseCase;
    private final EmployeeMapper mapper;

    public EmployeeController(
            final ManageEmployeeUseCase useCase,
            final ManagePersonUseCase managePersonUseCase,
            final EmployeeMapper mapper
    ) {
        this.useCase = useCase;
        this.managePersonUseCase = managePersonUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<EmployeeResponseDto> hire(
            @Valid @RequestBody final EmployeeRequestDto request) {

        Employee employee = useCase.hire(mapper.toCommand(request));
        Person person = managePersonUseCase.findById(employee.getPersonId()).orElseThrow();

        return new ApiResponseDto<>(
                true,
                mapper.toResponseDto(employee, person),
                "Employee created successfully",
                LocalDateTime.now()
        );
    }
}