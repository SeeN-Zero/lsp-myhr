package com.seen.lspmyhr.repository;

import com.seen.lspmyhr.model.Pekerjaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface PekerjaanRepository extends JpaRepository<Pekerjaan, UUID> {
    @Transactional
    @Modifying
    @Query("update Pekerjaan p set p.bonus = ?1 where upper(p.pekerjaan) = upper(?2)")
    int updateBonusByPekerjaanIgnoreCase(@NonNull double bonus, @NonNull String pekerjaan);
}
