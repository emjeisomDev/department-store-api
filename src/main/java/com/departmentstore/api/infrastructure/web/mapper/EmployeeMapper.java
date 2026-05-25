package com.departmentstore.api.infrastructure.web.mapper;

import com.departmentstore.api.application.command.employee.HireEmployeeCommand;
import com.departmentstore.api.domain.entity.Employee;
import com.departmentstore.api.domain.entity.Person;
import com.departmentstore.api.infrastructure.web.dto.request.EmployeeRequestDto;
import com.departmentstore.api.infrastructure.web.dto.response.EmployeeResponseDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public HireEmployeeCommand toCommand(final EmployeeRequestDto dto) {
        return new HireEmployeeCommand(
                dto.personId(),
                dto.registrationNumber(),
                dto.hireDate(),
                dto.employeeRole().name()
        );
    }

    public EmployeeResponseDto toResponseDto(final Employee employee, final Person person) {
        return new EmployeeResponseDto(
                employee.getId(),
                person.getName(),
                employee.getRegistrationNumber(),
                employee.getHireDate(),
                employee.getStatus().name(),
                employee.getEmployeeRole().name()
        );
    }





}
