package io.hangga;

import io.hangga.tools.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private final List<String> arrNames;
    private final JFileChooser outputChooser;
    private final JProgressBar progressBar;
    private DefaultTableModel tableModel;
    private JButton btnResetExcel;
    private JButton btnChooseExcel;
    private JButton btnChooseTemplate;
    private JButton btnGenerate;
    private String templatePath;
    private JRadioButton rbSingle;
    private JRadioButton rbMultiple;

    private boolean isMultiple = true;
    private final ActionListener rbListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            outputChooser.setSelectedFile(null);
            isMultiple = rbMultiple.isSelected();
        }
    };

    public MainFrame() {
        arrNames = new ArrayList<>();
        templatePath = Invigen.templatePath;
        outputChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);

        initUI();
        initAction();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    private void initUI() {
        setTitle("InviGen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel midlePanel = new JPanel();
        midlePanel.setLayout(new BoxLayout(midlePanel, BoxLayout.X_AXIS));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("No");
        tableModel.addColumn("Nama");

        JTable jTable = new JTable(tableModel);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        jTable.getColumnModel().getColumn(0).setMaxWidth(50);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTable.getColumnModel().getColumn(1).setMaxWidth(200);

        JScrollPane scroll = new JScrollPane(jTable);
        midlePanel.add(scroll);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(300, 200));

        JLabel leftLabel = new JLabel("Daftar Nama Undangan");
        leftLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        leftPanel.add(leftLabel);
        leftPanel.add(scroll);

        btnGenerate = new JButton("Generate");
        btnChooseTemplate = new JButton("Pilih Template");
        btnChooseExcel = new JButton("Load .xls File");
        btnResetExcel = new JButton("Reset");
        btnResetExcel.setEnabled(false);

        JPanel pnlBottom = new JPanel();
        pnlBottom.setLayout(new FlowLayout());
        pnlBottom.add(progressBar);
        pnlBottom.add(btnResetExcel);
        pnlBottom.add(btnChooseExcel);
        pnlBottom.add(btnChooseTemplate);

        rbSingle = new JRadioButton("1 File");
        rbMultiple = new JRadioButton("Multiple File");
        rbMultiple.setSelected(true);

        JPanel pnlGenerate = new JPanel();
        pnlGenerate.setLayout(new FlowLayout());

        ButtonGroup rbGroup = new ButtonGroup();
        rbGroup.add(rbMultiple);
        rbGroup.add(rbSingle);

        pnlGenerate.add(rbMultiple);
        pnlGenerate.add(rbSingle);
        pnlGenerate.add(btnGenerate);
        pnlBottom.add(pnlGenerate);

        leftPanel.add(pnlBottom);
        midlePanel.add(leftPanel);

        add(midlePanel);
        setVisible(true);
    }

    private void initAction() {
        rbMultiple.addActionListener(rbListener);
        rbSingle.addActionListener(rbListener);
        btnResetExcel.addActionListener(e -> resetExcelData());
        btnChooseExcel.addActionListener(e -> chooseExcelFile());
        btnChooseTemplate.addActionListener(e -> chooseTemplateFile());
        btnGenerate.addActionListener(e -> generateDocument());
    }

    private void resetExcelData() {
        int opsi = JOptionPane.showConfirmDialog(null, "Benarkah anda ingin menghapus data ini ?", "Penghapusan Data", JOptionPane.YES_NO_OPTION);
        if (opsi == JOptionPane.YES_OPTION) {
            tableModel.setRowCount(0);
            arrNames.clear();
            btnResetExcel.setEnabled(false);
        }
    }

    private void chooseExcelFile() {
        JFileChooser fileExcelChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileExcelChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Excel .xlsx", "xlsx"));
        fileExcelChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Excel 97- 2003 .xls", "xls"));

        if (fileExcelChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String excelPath = fileExcelChooser.getSelectedFile().getAbsolutePath();
            readExcelFile(excelPath);
        }
    }

    private void readExcelFile(String excelPath) {
        new ExcelReader().setFilePath(excelPath, new ExcelListener() {
            @Override
            public void OnGetNames(List<String> anames, String names) {
                arrNames.clear();
                arrNames.addAll(anames);
                btnResetExcel.setEnabled(arrNames.size() > 0);
            }

            @Override
            public void OnGetNameAt(String name) {
                tableModel.insertRow(tableModel.getRowCount(), new Object[]{tableModel.getRowCount() + 1, name});
            }
        }).execute();
    }

    private void chooseTemplateFile() {
        JFileChooser fileTemplateChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .docx", "docx"));
        fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .doc", "doc"));
        fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("LibreOffice Documents .odt", "odt"));

        if (fileTemplateChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            templatePath = fileTemplateChooser.getSelectedFile().getAbsolutePath();
        }
    }

    private void generateDocument() {

        if (arrNames.size() == 0) {
            showInfo();
            return;
        }

        progressBar.setMaximum(arrNames.size());
        setupOutputChooser();


        if (outputChooser.getSelectedFile() != null) {
            setButtonStatus();
            progressBar.setVisible(true);

            if (isMultiple) {
                processDocumentCopy(templatePath);
            } else {
                new Generator().setPath(templatePath, getOutputFilePath(), arrNames.size(), new GeneratorListener() {
                    @Override
                    public void OnCopyProgress(int progress, String status) {
                        updateProgressBar("Tunggu...", progress);
                    }

                    @Override
                    public void OnCopyFinish(String copyOutput) {
                        processDocumentCopy(copyOutput);
                    }

                    @Override
                    public void OnError(String errMsg) {
                    }
                }).execute();
            }
        } else {
            outputChooser.setSelectedFile(new File("output-invigen.docx"));

            if (outputChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                setButtonStatus();
                progressBar.setVisible(true);

                if (isMultiple) {
                    processDocumentCopy(templatePath);
                } else {
                    new Generator().setPath(templatePath, getOutputFilePath(), arrNames.size(), new GeneratorListener() {
                        @Override
                        public void OnCopyProgress(int progress, String status) {
                            updateProgressBar("Menggandakan...", progress);
                        }

                        @Override
                        public void OnCopyFinish(String copyOutput) {
                            processDocumentCopy(copyOutput);
                        }

                        @Override
                        public void OnError(String errMsg) {
                        }
                    }).execute();
                }

            }
        }
    }

    private void processDocumentCopy(String pathSrc) {

        new DocProcessor().replaceName(arrNames, pathSrc, getOutputFilePath(), new DocListener() {
            @Override
            public void onProgress(int progress, String status) {
                updateProgressBar("Menulis...", progress);
            }

            @Override
            public void onFinished() {
                onFinishedUI();
            }
        }).setMultipleFile(isMultiple).execute();
    }

    private void setButtonStatus() {
        btnChooseTemplate.setEnabled(false);
        btnChooseExcel.setEnabled(false);
        btnResetExcel.setEnabled(false);
        btnGenerate.setEnabled(false);
    }

    private void updateProgressBar(String status, int progress) {
        progressBar.setString(status);
        progressBar.setValue(progress);
    }

    private void setupOutputChooser() {
        if (isMultiple) {
            outputChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            outputChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }
        outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .docx", "docx"));
        outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .doc", "doc"));
        outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("LibreOffice Documents .odt", "odt"));
    }

    private String getOutputFilePath() {
        if (outputChooser.getSelectedFile() != null) {
            return outputChooser.getSelectedFile().getAbsolutePath();
        } else {
            outputChooser.setSelectedFile(new File("output-invigen.docx"));
            if (outputChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                return outputChooser.getSelectedFile().getAbsolutePath();
            }
        }
        return null;
    }

    private void showInfo() {
        JOptionPane.showMessageDialog(this, "Anda belum memilih file daftar nama .xls.", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onFinishedUI() {
        progressBar.setVisible(false);
        btnChooseTemplate.setEnabled(true);
        btnChooseExcel.setEnabled(true);
        btnResetExcel.setEnabled(true);
        btnGenerate.setEnabled(true);
        JOptionPane.showMessageDialog(this, "Buka berkas disini :" + getOutputFilePath(), "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }
}
