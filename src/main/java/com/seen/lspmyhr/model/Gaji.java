package com.seen.lspmyhr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "gaji")
public class Gaji {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "gaji_pokok", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer gajiPokok;

    @Column(name = "gaji_bonus", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer gajiBonus;

    @Column(name = "gaji_pph", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer gajiPph;

    @Column(name = "gaji_akhir", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer gajiAkhir;

    @Column(name = "waktu_gaji", nullable = false)
    @JdbcTypeCode(SqlTypes.DATE)
    private Date waktuGaji;

    @ManyToOne(optional = false)
    @JoinColumn(name = "karyawan_id", nullable = false)
    private Karyawan karyawan;

}
