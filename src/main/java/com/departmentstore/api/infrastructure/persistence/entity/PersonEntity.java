package com.departmentstore.api.infrastructure.persistence.entity;

import com.departmentstore.api.domain.enums.PersonType;
import com.departmentstore.api.infrastructure.persistence.converter.PersonTypeConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SequenceGenerator(
        name = "seq_person",
        sequenceName = "seq_person",
        allocationSize = 1
)
public class PersonEntity {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_person"
    )
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Convert(converter = PersonTypeConverter.class)
    @Column(name = "person_type", nullable = false, length = 1)
    private PersonType personType;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "audit_id",
            nullable = false,
            unique = true
    )
    private AuditTrailEntity auditTrail;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private NaturalPersonEntity naturalPerson;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private LegalPersonEntity legalPerson;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private EmployeeEntity employee;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private ClientEntity client;

}
