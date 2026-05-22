package com.departmentstore.api.infrastructure.persistence.entity;

import com.departmentstore.api.domain.enums.ResponsibilityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_legal_person_responsibles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SequenceGenerator(
        name = "seq_legal_person_responsibles",
        sequenceName = "seq_legal_person_responsibles",
        allocationSize = 1
)
public class LegalPersonResponsiblesEntity {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_legal_person_responsibles"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "legal_person_id",
            nullable = false
    )
    private LegalPersonEntity legalPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "natural_person_id",
            nullable = false
    )
    private NaturalPersonEntity naturalPerson;

    @Enumerated(EnumType.STRING)
    @Column(name = "responsibility_type", nullable = false, length = 50)
    private ResponsibilityType responsibilityType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;


}
