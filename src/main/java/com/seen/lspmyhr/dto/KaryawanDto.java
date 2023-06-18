package com.seen.lspmyhr.dto;

import com.seen.lspmyhr.model.Gaji;
import com.seen.lspmyhr.model.Karyawan;
import com.seen.lspmyhr.model.Pekerjaan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KaryawanDto {
    private UUID id;
    private String nama;
    private int umur;
    private String email;
    private String telepon;
    private Pekerjaan pekerjaan;
    private List<Gaji> gaji = new ArrayList<>();

    public KaryawanDto(Karyawan karyawan, int umur) {
        this.id = karyawan.getId();
        this.nama = karyawan.getNama();
        this.umur = umur;
        this.email = karyawan.getEmail();
        this.telepon = karyawan.getTelepon();
        this.pekerjaan = karyawan.getPekerjaan();
        this.gaji = karyawan.getGaji();
    }
}
