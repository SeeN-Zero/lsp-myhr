package com.seen.lspmyhr;

import com.seen.lspmyhr.model.Gaji;
import com.seen.lspmyhr.model.Karyawan;
import com.seen.lspmyhr.model.Pekerjaan;
import com.seen.lspmyhr.model.UserApp;
import com.seen.lspmyhr.repository.GajiRepository;
import com.seen.lspmyhr.repository.KaryawanRepository;
import com.seen.lspmyhr.repository.PekerjaanRepository;
import com.seen.lspmyhr.repository.UserAppRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class AppStartRunner implements ApplicationRunner {

    public final PekerjaanRepository pekerjaanRepository;
    public final UserAppRepository userAppRepository;
    public final PasswordEncoder passwordEncoder;
    private final KaryawanRepository karyawanRepository;
    private final GajiRepository gajiRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Pekerjaan manager = new Pekerjaan(null, "Manager", 0.5);
        Pekerjaan supervisor = new Pekerjaan(null, "Supervisor", 0.4);
        Pekerjaan staff = new Pekerjaan(null, "Staff", 0.3);
        UserApp userApp = new UserApp(null, "sennaannaba", passwordEncoder.encode("Keren-2001"));
        Date tanggalLahir = new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2001-07-03").getTime());
        Karyawan karyawan = new Karyawan(null, "Senna Annaba Ahmad", tanggalLahir, "081317813443", "sennaannaba@gmail.com", manager, null);

        //Initialize Pekerjaan
        List<Pekerjaan> pekerjaanList = new ArrayList<>() {{
            add(manager);
            add(supervisor);
            add(staff);
        }};
        pekerjaanRepository.saveAll(pekerjaanList);

        //Initialize User
        userAppRepository.save(userApp);

        //Initialize Karyawan
        karyawan = karyawanRepository.save(karyawan);

        //Initialize Gaji
        int gajiPokok = 8000000;
        int gajiBonus = (int) Math.round(gajiPokok * karyawan.getPekerjaan().getBonus());
        int gajiPpn = (int) Math.round(((gajiBonus + gajiPokok) * 0.05));
        Integer gajiAkhir = gajiPokok + gajiBonus - gajiPpn;
        Date waktuGaji = new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2023-06-15").getTime());
        Gaji gaji = new Gaji(null, gajiPokok, gajiBonus, gajiPpn, gajiAkhir, waktuGaji, karyawan);
        gajiRepository.save(gaji);

    }
}
