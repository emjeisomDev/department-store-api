package com.departmentstore.api.application.service;

import com.departmentstore.api.infrastructure.audit.AuditUserInterceptor;
import org.springframework.stereotype.Service;

@Service
public class AuditContextService {

    private final AuditUserInterceptor interceptor;

    public AuditContextService(final AuditUserInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void configureCurrentUser() {
        String currentUser = "system";
        interceptor.setCurrentUser(currentUser);
    }
}