package com.departmentstore.api.infrastructure.persistence.entity;


import com.departmentstore.api.domain.enums.ClientRank;
import com.departmentstore.api.domain.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SequenceGenerator(
        name = "seq_client",
        sequenceName = "seq_client",
        allocationSize = 1
)
public class ClientEntity {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_client"
    )
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "person_id",
            nullable = false,
            unique = true
    )
    private PersonEntity person;

    @Column(name = "client_code", nullable = false, unique = true)
    private String clientCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_rank", nullable = false)
    private ClientRank clientRank;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ClientStatus status;


}
