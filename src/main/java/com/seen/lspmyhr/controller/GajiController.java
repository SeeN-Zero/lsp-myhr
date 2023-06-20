package com.seen.lspmyhr.controller;

import com.seen.lspmyhr.model.Gaji;
import com.seen.lspmyhr.model.Karyawan;
import com.seen.lspmyhr.repository.GajiRepository;
import com.seen.lspmyhr.repository.KaryawanRepository;
import com.seen.lspmyhr.service.GajiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping
public class GajiController {

    private final KaryawanRepository karyawanRepository;
    private final GajiRepository gajiRepository;
    private final GajiService gajiService;

    @GetMapping("karyawan/{karyawanId}/gaji/add")
    public String addView(@PathVariable UUID karyawanId, Model model){
        Karyawan karyawan = karyawanRepository.getReferenceById(karyawanId);
        model.addAttribute("karyawan", karyawan);
        model.addAttribute("gajiNew", new Gaji());
        return "add-gaji";
    }

    @GetMapping("karyawan/{karyawanId}/gaji/update/{gajiId}")
    public String updateView(@PathVariable UUID karyawanId, @PathVariable UUID gajiId, Model model){
        Karyawan karyawan = karyawanRepository.getReferenceById(karyawanId);
        Gaji gaji = gajiRepository.getReferenceById(gajiId);
        model.addAttribute("karyawan", karyawan);
        model.addAttribute("gaji", gaji);
        model.addAttribute("gajiNew", new Gaji());
        return "update-gaji";
    }

    @PostMapping ("karyawan/{karyawanId}/gaji/add")
    public String add(@PathVariable UUID karyawanId, @ModelAttribute Gaji gaji){
        Karyawan karyawan = karyawanRepository.getReferenceById(karyawanId);
        gajiRepository.save(gajiService.calculateGaji(gaji, karyawan));
        return "redirect:/karyawan/"+karyawanId;
    }

    @PostMapping("karyawan/{karyawanId}/gaji/update/{gajiId}")
    public String update(@PathVariable UUID karyawanId, @PathVariable UUID gajiId, @ModelAttribute Gaji gaji){
        Karyawan karyawan = karyawanRepository.getReferenceById(karyawanId);
        Gaji calculated = gajiService.calculateGaji(gaji, karyawan);
        gajiRepository.updateAll(calculated.getGajiPokok(), calculated.getGajiBonus(), calculated.getGajiPph(), calculated.getGajiAkhir(), calculated.getWaktuGaji(), gajiId);
        return "redirect:/karyawan/"+karyawanId;
    }

    @DeleteMapping("karyawan/{karyawanId}/gaji/update/{gajiId}")
    public String delete(@PathVariable UUID karyawanId, @PathVariable UUID gajiId, @ModelAttribute Gaji gaji){
        gajiRepository.deleteById(gajiId);
        return "redirect:/karyawan/"+karyawanId;
    }
}
