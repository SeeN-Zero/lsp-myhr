package com.seen.lspmyhr.service;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.seen.lspmyhr.dto.ReportDto;
import com.seen.lspmyhr.model.Gaji;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

@Service
public class ReportService {
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.gray);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setSize(12);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("Nama Karyawan", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Tanggal", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Gaji Pokok", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Gaji Bonus", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("PPN", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Gaji", font));
        table.addCell(cell);
    }

    private String writeTableData(PdfPTable table, List<Gaji> gajiList) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        PdfPCell cell = new PdfPCell();
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setSize(10);
        int totalGaji = 0;
        for (Gaji gaji : gajiList) {
            totalGaji = totalGaji+gaji.getGajiAkhir();
            cell.setPhrase(new Phrase(gaji.getKaryawan().getNama(), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(String.valueOf(gaji.getWaktuGaji()), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(kursIndonesia.format(gaji.getGajiPokok()), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(kursIndonesia.format(gaji.getGajiBonus()), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(kursIndonesia.format(gaji.getGajiPpn()), font));
            table.addCell(cell);
            cell.setPhrase(new Phrase(kursIndonesia.format(gaji.getGajiAkhir()), font));
            table.addCell(cell);
        }
        return kursIndonesia.format(totalGaji);
    }

    public void generatePdf(HttpServletResponse response, List<Gaji> gajiList, ReportDto reportDto) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setSize(18);
        font.setColor(Color.black);

        Paragraph title = new Paragraph("\"Laporan Akhir [" + reportDto.getGajiStart() + "] - [" + reportDto.getGajiEnd() + "]\"", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{3.5f, 2.0f, 2.5f, 2.5f, 2.5f, 3.0f});
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        writeTableHeader(table);
        String gajiTotal = writeTableData(table, gajiList);
        Paragraph gajiTotalParagraph= new Paragraph("Total Gaji : "+gajiTotal, font);
        gajiTotalParagraph.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(title);
        document.add(gajiTotalParagraph);
        document.add(table);
        document.close();
    }
}
