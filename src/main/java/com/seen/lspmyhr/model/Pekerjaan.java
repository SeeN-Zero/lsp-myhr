package com.seen.lspmyhr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pekerjaan")
public class Pekerjaan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "pekerjaan", nullable = false, unique = true, length = 30)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String pekerjaan;

    @Column(name = "bonus", nullable = false)
    @JdbcTypeCode(SqlTypes.DOUBLE)
    private double bonus;

}
