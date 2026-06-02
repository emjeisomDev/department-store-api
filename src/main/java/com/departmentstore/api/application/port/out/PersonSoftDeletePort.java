package com.departmentstore.api.application.port.out;

public interface PersonSoftDeletePort {
    void softDelete(Long personId, String deletedBy);
    void restore(Long personId);
}
