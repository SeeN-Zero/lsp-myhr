package com.seen.lspmyhr.controller;

import com.lowagie.text.DocumentException;
import com.seen.lspmyhr.dto.KaryawanDto;
import com.seen.lspmyhr.dto.ReportDto;
import com.seen.lspmyhr.dto.SettingUpdateDto;
import com.seen.lspmyhr.model.Gaji;
import com.seen.lspmyhr.model.Karyawan;
import com.seen.lspmyhr.model.Pekerjaan;
import com.seen.lspmyhr.repository.GajiRepository;
import com.seen.lspmyhr.repository.KaryawanRepository;
import com.seen.lspmyhr.repository.PekerjaanRepository;
import com.seen.lspmyhr.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping
public class HomeController {

    private final PekerjaanRepository pekerjaanRepository;
    private final KaryawanRepository karyawanRepository;
    private final GajiRepository gajiRepository;
    private final ReportService reportService;

    @GetMapping("/")
    public String home(){
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homeView(Model model) {
        List<Pekerjaan> pekerjaanList = pekerjaanRepository.findAll();
        List<Karyawan> karyawanList = karyawanRepository.findAll();
        List<KaryawanDto> karyawanListDto = new ArrayList<>();
        List<Pekerjaan> pekerjaanListEmpty = new ArrayList<>(){{
            add(new Pekerjaan());
            add(new Pekerjaan());
            add(new Pekerjaan());
        }};
        SettingUpdateDto settingUpdateDto = new SettingUpdateDto(pekerjaanListEmpty);

        LocalDate dateNow = LocalDate.now();
        int umur;
        for(Karyawan karyawan:karyawanList){
            umur = Period.between(karyawan.getTanggalLahir().toLocalDate(),dateNow).getYears();
            karyawanListDto.add(new KaryawanDto(karyawan,umur));
        }

        model.addAttribute("report", new ReportDto());
        model.addAttribute("karyawanList", karyawanListDto);
        model.addAttribute("pekerjaanList", pekerjaanList);
        model.addAttribute("pekerjaanListEmpty", settingUpdateDto);
        return "home";
    }

    @PostMapping("/home/setting/save")
    public String updateSetting(@ModelAttribute SettingUpdateDto settingUpdateDto){
        List<Pekerjaan> pekerjaanList = settingUpdateDto.getPekerjaanListDto();
        for(Pekerjaan pekerjaan:pekerjaanList){
            pekerjaanRepository.updateBonusByPekerjaanIgnoreCase(pekerjaan.getBonus(),pekerjaan.getPekerjaan());
        }
        return "redirect:/home";
    }

    @GetMapping("/home/report")
    public void downloadReport(@ModelAttribute ReportDto reportDto, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());
        response.setHeader("Content-Disposition", "attachment; filename=report" + currentDateTime + ".pdf");
        List<Gaji> gajiList = gajiRepository.findByWaktuGajiBetweenOrderByWaktuGajiAsc(reportDto.getGajiStart(), reportDto.getGajiEnd());
        reportService.generatePdf(response,gajiList,reportDto);
    }
}
