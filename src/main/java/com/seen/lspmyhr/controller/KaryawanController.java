package com.seen.lspmyhr.controller;

import com.seen.lspmyhr.model.Gaji;
import com.seen.lspmyhr.model.Karyawan;
import com.seen.lspmyhr.model.Pekerjaan;
import com.seen.lspmyhr.repository.GajiRepository;
import com.seen.lspmyhr.repository.KaryawanRepository;
import com.seen.lspmyhr.repository.PekerjaanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping
public class KaryawanController {

    private final KaryawanRepository karyawanRepository;
    private final GajiRepository gajiRepository;
    private final PekerjaanRepository pekerjaanRepository;

    @GetMapping("/karyawan/{id}")
    public String karyawanView(Model model, @PathVariable UUID id) {
        Karyawan karyawan = karyawanRepository.getReferenceById(id);
        model.addAttribute("karyawan", karyawan);
        return "karyawan";
    }

    @GetMapping("/karyawan/add")
    public String addView(Model model) {
        List<Pekerjaan> pekerjaanList = pekerjaanRepository.findAll();
        model.addAttribute("karyawan", new Karyawan());
        model.addAttribute("pekerjaanList", pekerjaanList);
        return "add-karyawan";
    }

    @GetMapping("karyawan/update/{id}")
    public String updateView(Model model, @PathVariable UUID id) {
        Karyawan karyawan = karyawanRepository.getReferenceById(id);
        List<Pekerjaan> pekerjaanList = pekerjaanRepository.findAll();
        model.addAttribute("karyawan", karyawan);
        model.addAttribute("pekerjaanList", pekerjaanList);
        model.addAttribute("karyawanNew", new Karyawan());
        return "update-karyawan";
    }

    @PostMapping("/karyawan/add")
    public String add(@ModelAttribute Karyawan karyawan, RedirectAttributes redirectAttributes) {
        try{
            List<Gaji> gaji = karyawan.getGaji();
            int gajiBonus = (int) Math.round(gaji.get(0).getGajiPokok() * karyawan.getPekerjaan().getBonus());
            int gajiPph = (int) Math.round((gajiBonus + gaji.get(0).getGajiPokok()) * 0.05);
            Integer gajiAkhir = (gaji.get(0).getGajiPokok() + gajiBonus) - gajiPph;
            gaji.get(0).setGajiBonus(gajiBonus);
            gaji.get(0).setGajiPph(gajiPph);
            gaji.get(0).setGajiAkhir(gajiAkhir);
            gaji.get(0).setKaryawan(karyawan);
            karyawanRepository.save(karyawan);
            gajiRepository.save(gaji.get(0));
            return "redirect:/home";
        }catch (org.springframework.dao.DataIntegrityViolationException e){
            redirectAttributes.addFlashAttribute("duplicateKey","Telepon dan Email Harus Unik");
            return "redirect:/karyawan/add";
        }
    }

    @PostMapping("/karyawan/update/{id}")
    public String update(@ModelAttribute("karyawanNew") Karyawan karyawan, @PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try{
            karyawanRepository.updateAll(karyawan.getNama(), karyawan.getTanggalLahir(), karyawan.getTelepon(), karyawan.getEmail(), karyawan.getPekerjaan(),id);
            return "redirect:/home";
        }catch (org.springframework.dao.DataIntegrityViolationException e){
            redirectAttributes.addFlashAttribute("duplicateKey","Telepon dan Email Harus Unik");
            return "redirect:/karyawan/update/"+id;
        }

    }

    @GetMapping("/karyawan/delete/{id}")
    public String delete(@PathVariable UUID id){
        karyawanRepository.deleteById(id);
        return "redirect:/home";
    }

}
