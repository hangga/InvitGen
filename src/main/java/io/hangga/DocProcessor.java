package io.hangga;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DocProcessor {
    final String PATTERN_NAME = "--nama";

    void replaceName(String[] names, String templatePath, String outputPath) {
        try {
            System.out.println("Yak, replaceName()");

            FileInputStream fis = new FileInputStream(templatePath);
            XWPFDocument document = new XWPFDocument(fis);

            int i = 0;
            // Mendapatkan semua paragraf dalam dokumen
            for (XWPFParagraph p : document.getParagraphs()) {
                // Mendapatkan semua run (teks) dalam paragraf
                for (XWPFRun run : p.getRuns()) {
                    String text = run.getText(0);

                    if (text != null && text.contains(PATTERN_NAME)) {
                        //System.out.println("Yak, ketemu --nama");
                        // Mengganti teks lama dengan teks baru
                        text = text.replace(PATTERN_NAME, names[i]);
                        i++;
                        run.setText(text, 0);
                        //System.out.println("Berhasil jadi " + names[i]);
                        System.out.println();
                    }
                }
            }

            for (int x = 0; x < names.length; x++){
                for (XWPFTableRow row : document.getTables().get(x).getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                String text = r.getText(0);
                                if (text != null && text.contains(PATTERN_NAME)) {
                                    try{
                                        text = text.replace(PATTERN_NAME, names[x]);
                                        System.out.println(i+" Berhasil jadi " + names[x]);
                                        //i++;
                                        r.setText(text, 0);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }
                    }
                }
            }

            //int tblIdx = 0;
            // jika tak ketemu, mungkin perlu dicari di tabel
            /*for (XWPFTable tbl : document.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                String text = r.getText(0);
                                if (text != null && text.contains(PATTERN_NAME)) {
                                    try{
                                        text = text.replace(PATTERN_NAME, names[i]);
                                        System.out.println(i+" Berhasil jadi " + names[i]);
                                        i++;
                                        r.setText(text, 0);
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }
                    }
                }
            }*/

            // Menyimpan dokumen yang telah diubah
            FileOutputStream fos = new FileOutputStream(outputPath);
            document.write(fos);

            fis.close();
            fos.close();
            document.close();

            System.out.println("Penggantian teks berhasil!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
