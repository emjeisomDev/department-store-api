package com.departmentstore.api.infrastructure.persistence.projection;

public interface ActiveEmployeeView {
    Long getEmployeeId();
    String getEmployeeName();
    String getRegistrationNumber();
    String getStatus();
}
