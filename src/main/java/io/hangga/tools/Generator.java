package io.hangga.tools;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Generator extends SwingWorker<Void, Void> {

    private String templatePath;
    private String outputPath;
    private int count;
    OnCopying onCopying;
    public Generator setPath(String templatePath, String outputPath, int count, OnCopying onCopying) {
        this.templatePath = templatePath;
        this.outputPath = outputPath;
        this.count = count;
        this.onCopying = onCopying;
        return this;
    }

    private void copyTable(XWPFTable source, XWPFTable target) {
        CTTbl sourceCTTbl = source.getCTTbl();
        CTTbl targetCTTbl = target.getCTTbl();
        targetCTTbl.setTblPr(sourceCTTbl.getTblPr());
        targetCTTbl.setTrArray(sourceCTTbl.getTrArray());
    }

    private void copyParagraph(XWPFParagraph target, XWPFParagraph source) {
        CTPPr pPr = target.getCTP().isSetPPr() ? target.getCTP().getPPr() : target.getCTP().addNewPPr();
        pPr.set(source.getCTP().getPPr());
        for (XWPFRun r : source.getRuns()) {
            XWPFRun nr = target.createRun();
            cloneRun(nr, r);
        }
    }

    private void cloneRun(XWPFRun target, XWPFRun source) {
        CTRPr rPr = target.getCTR().isSetRPr() ? target.getCTR().getRPr() : target.getCTR().addNewRPr();
        rPr.set(source.getCTR().getRPr());
        target.setText(source.getText(0));
    }

    @Override
    protected Void doInBackground() throws Exception {
        boolean isSukses = false;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(templatePath);
            XWPFDocument document = new XWPFDocument(fis);

            for (int i = 1; i < count; i++) {
                copyTable(document.getTables().get(0), document.createTable());
                document.createParagraph();
                copyTable(document.getTables().get(1), document.createTable());
                document.createParagraph();
                copyTable(document.getTables().get(2), document.createTable());
                document.createParagraph();
                copyTable(document.getTables().get(3), document.createTable());
                document.createParagraph();
                copyTable(document.getTables().get(4), document.createTable());
                document.createParagraph();
                document.createParagraph();
                document.createParagraph();
                onCopying.OnCopyProgress(i, "Copying...");
            }

            FileOutputStream fos = new FileOutputStream(outputPath);
            document.write(fos);

            fis.close();
            fos.close();
            document.close();
            isSukses = true;
        } catch (IOException e) {
            onCopying.OnError(e.getMessage());
            throw new RuntimeException(e);
        }
        if (isSukses) onCopying.OnCopyFinish(outputPath);
        return null;
    }
}
