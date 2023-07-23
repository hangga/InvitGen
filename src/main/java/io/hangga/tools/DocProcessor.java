package io.hangga.tools;

import org.apache.poi.xwpf.usermodel.*;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class DocProcessor extends SwingWorker<Void, Void> {
    final String PATTERN_NAME = "--nama";
    private String[] names;
    private String templatePath;
    private String outputPath;
    private OnWriting onWriting;

    public DocProcessor replaceName(String[] names, String templatePath, String outputPath, OnWriting onWriting) {
        this.names = names;
        this.templatePath = templatePath;
        this.outputPath = outputPath;
        this.onWriting = onWriting;
        return this;
    }

    public DocProcessor replaceName(List<String> names, String templatePath, String outputPath, OnWriting onWriting) {
        //this.names = names;
        this.names = new String[names.size()];
        for (int i = 0; i < names.size(); i++){
            this.names[i] = names.get(i);
        }
        this.templatePath = templatePath;
        this.outputPath = outputPath;
        this.onWriting = onWriting;
        return this;
    }

    @Override
    protected Void doInBackground() {
        boolean isSucceded = true;
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
            /*System.out.println("cek names.length :" + names.length);
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
                                        System.out.println(i+"Error :"+ e.getMessage());
                                        e.printStackTrace();
                                    }
                                    onProgressDocument.onProgress(x);
                                }
                            }
                        }
                    }
                }
            }*/

            //int tblIdx = 0;
            // jika tak ketemu, mungkin perlu dicari di tabel
            for (XWPFTable tbl : document.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            for (XWPFRun r : p.getRuns()) {
                                String text = r.getText(0);
                                if (text != null && text.contains(PATTERN_NAME)) {
                                    try{
                                        text = text.replace(PATTERN_NAME, names[i]);
                                        System.out.println(i+" Berhasil jadi " + names[i]);
                                        onWriting.onProgress(i, names[i] +" ..."+i);
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
            }

            // Menyimpan dokumen yang telah diubah
            FileOutputStream fos = new FileOutputStream(outputPath);
            document.write(fos);

            fis.close();
            fos.close();
            document.close();
            System.out.println("Penggantian teks berhasil!");
        } catch (Exception e) {
            e.printStackTrace();
            isSucceded = false;
        }
        if (isSucceded) onWriting.onFinished();
        return null;
    }
}
