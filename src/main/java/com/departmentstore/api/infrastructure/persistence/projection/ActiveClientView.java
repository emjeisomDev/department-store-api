package com.departmentstore.api.infrastructure.persistence.projection;

public interface ActiveClientView {
    Long getClientId();
    String getClientName();
    String getClientCode();
    String getStatus();
}
