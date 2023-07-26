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
    private DocListener docListener;

    private boolean isMultipleFile = false;

    public DocProcessor setMultipleFile(boolean multipleFile) {
        isMultipleFile = multipleFile;
        return this;
    }

    public DocProcessor replaceName(String[] names, String templatePath, String outputPath, DocListener docListener) {
        this.names = names;
        this.templatePath = templatePath;
        this.outputPath = outputPath;
        this.docListener = docListener;
        return this;
    }

    public DocProcessor replaceName(List<String> names, String templatePath, String outputPath, DocListener docListener) {
        //this.names = names;
        this.names = new String[names.size()];
        for (int i = 0; i < names.size(); i++){
            this.names[i] = names.get(i);
        }
        this.templatePath = templatePath;
        this.outputPath = outputPath;
        this.docListener = docListener;
        return this;
    }

    private XWPFDocument openDocument() throws Exception {
        FileInputStream fis = new FileInputStream(templatePath);
        return new XWPFDocument(fis);
    }

    void processSingle(){ // sttable
        boolean isSucceded = true;
        try {

            System.out.println("Yak, replaceName()");

            FileInputStream fis = new FileInputStream(templatePath);
            XWPFDocument document = new XWPFDocument(fis);

            System.out.println("isMultipleFile : "+ isMultipleFile);

            int i = 0;
            // Mendapatkan semua paragraf dalam dokumen
                /*for (XWPFParagraph p : document.getParagraphs()) {
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
                                        docListener.onProgress(i, names[i] +" ..."+i);
                                        r.setText(text, 0);
                                        System.out.println(names[i]+"-"+outputPath);
                                        i++;
                                    }catch (Exception e){
                                        System.out.println("ADA Error kok yaaa-->"+e.getMessage());
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }
                    }
                }
            }

            FileOutputStream fos = new FileOutputStream(outputPath);
            document.write(fos);
            fos.close();
            fis.close();

            System.out.println("Penggantian teks berhasil!");
        } catch (Exception e) {
            e.printStackTrace();
            isSucceded = false;
        }
        if (isSucceded) docListener.onFinished();

    }

    void processMultiple(){
        boolean isSucceeded = true;

        try {
            for (int x = 0; x < names.length; x++) {
                XWPFDocument document = openDocument();
                System.out.println("Mulai dari : " + names[x]);

                for (XWPFParagraph p : document.getParagraphs()) {
                    for (XWPFRun run : p.getRuns()) {
                        String text = run.getText(0);
                        if (text != null && text.contains(PATTERN_NAME)) {
                            text = text.replace(PATTERN_NAME, names[x]);
                            run.setText(text, 0);
                        }
                    }
                }

                for (XWPFTable tbl : document.getTables()) {
                    for (XWPFTableRow row : tbl.getRows()) {
                        for (XWPFTableCell cell : row.getTableCells()) {
                            for (XWPFParagraph p : cell.getParagraphs()) {
                                for (XWPFRun r : p.getRuns()) {
                                    String text = r.getText(0);
                                    if (text != null && text.contains(PATTERN_NAME)) {
                                        text = text.replace(PATTERN_NAME, names[x]);
                                        r.setText(text, 0);
                                    }
                                }
                            }
                        }
                    }
                }

                //FileOutputStream fos = new FileOutputStream(outputPath.replace(".docx", "-" + names[x] + ".docx"));
                FileOutputStream fos = new FileOutputStream(outputPath + "/" + names[x] + ".docx");
                document.write(fos);
                document.close();
                fos.close();

                docListener.onProgress(x, names[x] + " ..." + x);
            }

            System.out.println("Penggantian teks berhasil!");
        } catch (Exception e) {
            e.printStackTrace();
            isSucceeded = false;
        }

        if (isSucceeded) {
            docListener.onFinished();
        }
    }


    @Override
    protected Void doInBackground() {
        //process();
        if (isMultipleFile) {
            processMultiple();
        } else {
            processSingle();
        }
        return null;
    }
}
