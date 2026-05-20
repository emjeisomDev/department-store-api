package com.departmentstore.api.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_legal_person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SequenceGenerator(
        name = "seq_legal_person",
        sequenceName = "seq_legal_person",
        allocationSize = 1
)
public class LegalPersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_legal_person")
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    private PersonEntity person;

    @Column(name = "tax_id", nullable = false, unique = true, length = 14)
    private String taxId;

    @Column(name = "corporate_name", nullable = false, length = 255)
    private String corporateName;

    @Column(name = "open_date", nullable = false)
    private LocalDate openDate;

    @Column(name = "share_capital", precision = 18, scale = 2)
    private BigDecimal shareCapital;

    @Column(name = "employees_quant")
    private Integer employeesQuant;

    @OneToMany(mappedBy = "legalPerson", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<LegalPersonResponsiblesEntity> responsibles = new ArrayList<>();

}
