package com.departmentstore.api.infrastructure.persistence.entity;

import com.departmentstore.api.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_natural_person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SequenceGenerator(
        name = "seq_natural_person",
        sequenceName = "seq_natural_person",
        allocationSize = 1
)
public class NaturalPersonEntity {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_natural_person"
    )
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "person_id",
            nullable = false,
            unique = true
    )
    private PersonEntity person;

    @Column(name = "tax_id", nullable = false, unique = true, length = 11)
    private String taxId;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "mothers_name", nullable = false, length = 255)
    private String mothersName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 1)
    private Gender gender;

}
