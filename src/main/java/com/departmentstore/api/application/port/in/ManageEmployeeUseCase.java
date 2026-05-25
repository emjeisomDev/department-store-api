package com.departmentstore.api.application.port.in;

import com.departmentstore.api.application.command.employee.HireEmployeeCommand;
import com.departmentstore.api.application.command.employee.TerminateEmployeeCommand;
import com.departmentstore.api.domain.entity.Employee;
import com.departmentstore.api.domain.enums.EmployeeRole;

import java.util.List;

public interface ManageEmployeeUseCase {
    Employee findById(Long employeeId);
    Employee hire(HireEmployeeCommand command);
    void terminate(TerminateEmployeeCommand command);
    List<Employee> findActive(int page, int size);
    void updateRole(Long employeeId, EmployeeRole role);
}
