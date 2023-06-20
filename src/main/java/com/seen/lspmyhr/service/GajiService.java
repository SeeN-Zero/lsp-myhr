package com.seen.lspmyhr.service;

import com.seen.lspmyhr.model.Gaji;
import com.seen.lspmyhr.model.Karyawan;
import org.springframework.stereotype.Service;

@Service
public class GajiService {

    public Gaji calculateGaji (Gaji gaji, Karyawan karyawan){
        int gajiBonus = (int) Math.round(gaji.getGajiPokok() * karyawan.getPekerjaan().getBonus());
        int gajiPph = (int) Math.round((gajiBonus + gaji.getGajiPokok()) * 0.05);
        Integer gajiAkhir = gaji.getGajiPokok() + gajiBonus - gajiPph;
        gaji.setGajiBonus(gajiBonus);
        gaji.setGajiPph(gajiPph);
        gaji.setGajiAkhir(gajiAkhir);
        gaji.setKaryawan(karyawan);
        return gaji;
    }
}
