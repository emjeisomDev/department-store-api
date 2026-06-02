package com.departmentstore.api.infrastructure.persistence.repository.adapter;

import com.departmentstore.api.application.port.out.PersonSoftDeletePort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class PersonSoftDeleteAdapter implements PersonSoftDeletePort {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void softDelete(Long personId, String deletedBy) {
        entityManager.createNativeQuery("CALL sp_soft_delete_person(:id,:user)")
                .setParameter("id", personId)
                .setParameter("user", deletedBy)
                .executeUpdate();
    }

    @Override
    public void restore(Long personId) {
        entityManager.createNativeQuery("CALL sp_restore_person(:id)")
                .setParameter("id", personId)
                .executeUpdate();
    }
}
