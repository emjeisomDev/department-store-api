package com.departmentstore.api.infrastructure.persistence.projection;

public interface PersonCompleteView {
    Long getPersonId();
    String getName();
    String getPersonType();
    String getTaxId();
}
