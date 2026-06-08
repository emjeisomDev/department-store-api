package com.departmentstore.api.infrastructure.web.controller;

import com.departmentstore.api.application.command.employee.TerminateEmployeeCommand;
import com.departmentstore.api.application.port.in.ManageEmployeeUseCase;
import com.departmentstore.api.application.port.in.ManagePersonUseCase;
import com.departmentstore.api.domain.entity.Employee;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.infrastructure.web.dto.request.EmployeeRequestDto;
import com.departmentstore.api.infrastructure.web.dto.request.TerminateEmployeeRequestDto;
import com.departmentstore.api.infrastructure.web.dto.request.UpdateEmployeeRoleRequestDto;
import com.departmentstore.api.infrastructure.web.dto.response.ApiResponseDto;
import com.departmentstore.api.infrastructure.web.dto.response.EmployeeResponseDto;
import com.departmentstore.api.infrastructure.web.mapper.EmployeeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(
        name = "Funcionários",
        description = "Operações relacionadas aos funcionários"
)
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

    @GetMapping("/{id}")
    public ApiResponseDto<EmployeeResponseDto> findById(@PathVariable final Long id) {

        Employee employee = useCase.findById(id);
        Person person = managePersonUseCase.findById(employee.getPersonId()).orElseThrow();

        return new ApiResponseDto<>(
                true,
                mapper.toResponseDto(employee, person),
                "Employee found successfully",
                LocalDateTime.now()
        );
    }

    @GetMapping
    public ApiResponseDto<List<EmployeeResponseDto>> findAll(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size
    ) {

        List<EmployeeResponseDto> response =
                useCase.findActive(page, size).stream().map(employee -> {
                    Person person = managePersonUseCase.findById(employee.getPersonId()).orElseThrow();
                    return mapper.toResponseDto(employee, person);
                }).toList();

        return new ApiResponseDto<>(
                true,
                response,
                "Employees retrieved successfully",
                LocalDateTime.now()
        );
    }

    @Operation(summary = "Contratar funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Funcionário criado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
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

    @Operation(summary = "Desligar funcionário")
    @PatchMapping("/{id}/terminate")
    public ApiResponseDto<Void> terminate(
            @PathVariable final Long id,
            @Valid @RequestBody final TerminateEmployeeRequestDto request
    ) {

        useCase.terminate(
                new TerminateEmployeeCommand(
                        id, request.terminationDate(), request.terminationReason()
                ));

        return new ApiResponseDto<>(
                true,
                null,
                "Employee terminated successfully",
                LocalDateTime.now()
        );
    }

    @Operation(summary = "Alterar cargo do funcionário")
    @PatchMapping("/{id}/role")
    public ApiResponseDto<Void> updateRole(
            @PathVariable final Long id,
            @Valid @RequestBody final UpdateEmployeeRoleRequestDto request) {

        useCase.updateRole(id, request.role());

        return new ApiResponseDto<>(
                true,
                null,
                "Employee role updated successfully",
                LocalDateTime.now()
        );
    }




}