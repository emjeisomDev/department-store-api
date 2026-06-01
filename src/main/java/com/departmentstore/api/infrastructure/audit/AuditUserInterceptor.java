package com.departmentstore.api.infrastructure.audit;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class AuditUserInterceptor {
    @PersistenceContext
    private EntityManager entityManager;

    public void setCurrentUser(
            final String username
    ) {
        entityManager.createNativeQuery("SET app.current_user = '" +  username + "'")
                     .executeUpdate();
    }
}
