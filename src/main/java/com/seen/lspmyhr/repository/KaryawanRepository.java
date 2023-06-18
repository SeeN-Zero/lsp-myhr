package com.seen.lspmyhr.repository;

import com.seen.lspmyhr.model.Karyawan;
import com.seen.lspmyhr.model.Pekerjaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.UUID;

public interface KaryawanRepository extends JpaRepository<Karyawan, UUID> {
    @Transactional
    @Modifying
    @Query("""
            update Karyawan k set k.nama = ?1, k.tanggalLahir = ?2, k.telepon = ?3, k.email = ?4, k.pekerjaan = ?5
            where k.id = ?6""")
    int updateAll(@NonNull String nama, @NonNull Date tanggalLahir, @NonNull String telepon, @NonNull String email, @NonNull Pekerjaan pekerjaan, @NonNull UUID id);
}
