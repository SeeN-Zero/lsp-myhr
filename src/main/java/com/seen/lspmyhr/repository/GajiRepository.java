package com.seen.lspmyhr.repository;

import com.seen.lspmyhr.model.Gaji;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public interface GajiRepository extends JpaRepository<Gaji, UUID> {
    List<Gaji> findByWaktuGajiBetweenOrderByWaktuGajiAsc(@NonNull Date waktuGajiStart, @NonNull Date waktuGajiEnd);
    @Transactional
    @Modifying
    @Query("""
            update Gaji g set g.gajiPokok = ?1, g.gajiBonus = ?2, g.gajiPph = ?3, g.gajiAkhir = ?4, g.waktuGaji = ?5
            where g.id = ?6""")
    int updateAll(Integer gajiPokok, Integer gajiBonus, Integer gajiPph, Integer gajiAkhir, Date waktuGaji, @NonNull UUID id);
}
