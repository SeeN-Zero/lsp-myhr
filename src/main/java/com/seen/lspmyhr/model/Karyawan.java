package com.seen.lspmyhr.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "karyawan")
public class Karyawan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "nama", nullable = false, length = 75)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String nama;

    @Column(name = "tanggal_lahir", nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date tanggalLahir;

    @Column(name = "telepon", nullable = false, unique = true, length = 15)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String telepon;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pekerjaan_id", nullable = false)
    private Pekerjaan pekerjaan;

    @OneToMany(mappedBy = "karyawan", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Gaji> gaji = new ArrayList<>();
}
