package io.hangga;

import io.hangga.tools.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    //static JLabel l;
    static JFileChooser outputChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
    private static String excelPath = Invigen.excelPath;
    private static String templatePath = Invigen.templatePath;

    public MainFrame() {
    }

    public static void main(String[] args) {
        //void init(){
        // frame to contains GUI elements
        JFrame frame = new JFrame("InviGen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(430, 400);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);

        Container mainPanel = frame.getContentPane();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JButton btnResetExcel = new JButton("Reset");
        btnResetExcel.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnResetExcel.setFont(new Font("Serif", Font.PLAIN, 12));
        btnResetExcel.setEnabled(false);

        JButton btnChooseExcel = new JButton("Load .xls File");
        btnChooseExcel.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnChooseExcel.setFont(new Font("Serif", Font.PLAIN, 12));

        JButton btnChooseTemplate = new JButton("Pilih Template");
        btnChooseTemplate.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnChooseTemplate.setFont(new Font("Serif", Font.PLAIN, 12));

        frame.setFont(new Font("Serif", Font.PLAIN, 11));

        JPanel midlePanel = new JPanel();
        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("No");
        tableModel.addColumn("Nama");
        JTable jTable = new JTable(tableModel);
        jTable.setSize(200, 100);
        TableColumnModel columnModel = jTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(0).setMaxWidth(50);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(1).setMaxWidth(200);
        JScrollPane scroll = new JScrollPane(jTable);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(300, 200));

        JLabel leftLabel = new JLabel("Daftar Nama Undangan");
        leftLabel.setFont(new Font("Serif", Font.PLAIN, 12));
        leftPanel.add(leftLabel);

        leftPanel.add(scroll);

        JButton btnGenerate = new JButton("Generate");
        btnGenerate.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JPanel panelLeftBottom = new JPanel();
        panelLeftBottom.setLayout(new FlowLayout());
        panelLeftBottom.add(progressBar);
        panelLeftBottom.add(btnResetExcel);
        panelLeftBottom.add(btnChooseExcel);
        panelLeftBottom.add(btnChooseTemplate);
        panelLeftBottom.add(btnGenerate);
        leftPanel.add(panelLeftBottom);

        midlePanel.setLayout(new BoxLayout(midlePanel, BoxLayout.X_AXIS));
        midlePanel.add(leftPanel);
        midlePanel.setBackground(Color.CYAN);
        mainPanel.add(midlePanel);




        frame.setVisible(true);

        List<String> arrNames = new ArrayList<>();

        btnResetExcel.addActionListener(e -> {
            int opsi = JOptionPane.showConfirmDialog(null, "Benarkah anda ingin menghapus data ini ?", "Penghapusan Data", JOptionPane.YES_NO_OPTION);
            if (opsi == JOptionPane.YES_OPTION) {
                tableModel.setRowCount(0);
                arrNames.clear();
                btnResetExcel.setEnabled(false);
            }
        });

        btnChooseExcel.addActionListener(e -> {
            JFileChooser fileExcelChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
            fileExcelChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Excel .xlsx", "xlsx"));
            fileExcelChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Excel 97- 2003 .xls", "xls"));

            if (fileExcelChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                excelPath = fileExcelChooser.getSelectedFile().getAbsolutePath();

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
        });

        btnChooseTemplate.addActionListener(actionEvent -> {
            JFileChooser fileTemplateChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());

            fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .docx", "docx"));
            fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .doc", "doc"));
            fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("LibreOffice Documents .odt", "odt"));

            if (fileTemplateChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                templatePath = fileTemplateChooser.getSelectedFile().getAbsolutePath();
            }
        });

        btnGenerate.addActionListener(actionEvent -> {

            btnChooseTemplate.setEnabled(false);
            btnChooseExcel.setEnabled(false);
            btnResetExcel.setEnabled(false);
            btnGenerate.setEnabled(false);

            if (arrNames.size() == 0) {
                showInfo(frame);
                return;
            }

            progressBar.setMaximum(arrNames.size());

            outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .docx", "docx"));
            outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .doc", "doc"));
            outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("LibreOffice Documents .odt", "odt"));

            if (outputChooser.getSelectedFile() != null) {
                progressBar.setVisible(true);
                new Generator().setPath(templatePath, outputChooser.getSelectedFile().getAbsolutePath(), arrNames.size(), new OnCopying() {
                    @Override
                    public void OnCopyProgress(int progress, String status) {
                        progressBar.setString("Tunggu...");
                        progressBar.setValue(progress);
                    }

                    @Override
                    public void OnCopyFinish(String copyOutput) {
                        new DocProcessor().replaceName(arrNames, copyOutput, outputChooser.getSelectedFile().getAbsolutePath(), new OnWriting() {
                            @Override
                            public void onProgress(int progress, String status) {
                                progressBar.setString("Menulis Nama...");
                                progressBar.setValue(progress);
                            }

                            @Override
                            public void onFinished() {
                                progressBar.setVisible(true);
                                btnChooseTemplate.setEnabled(true);
                                btnChooseExcel.setEnabled(true);
                                btnResetExcel.setEnabled(true);
                                btnGenerate.setEnabled(true);
                            }
                        }).execute();
                    }

                    @Override
                    public void OnError(String errMsg) {
                        new Dialog(frame, errMsg).setVisible(true);
                    }
                }).execute();

            } else {
                outputChooser.setSelectedFile(new File("output-invigen.docx"));

                if (outputChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {

                    progressBar.setVisible(true);
                    new Generator().setPath(templatePath, outputChooser.getSelectedFile().getAbsolutePath(), arrNames.size(), new OnCopying() {
                        @Override
                        public void OnCopyProgress(int progress, String status) {
                            progressBar.setString("Tunggu...");
                            progressBar.setValue(progress);
                        }

                        @Override
                        public void OnCopyFinish(String copyOutput) {
                            new DocProcessor().replaceName(arrNames, copyOutput, outputChooser.getSelectedFile().getAbsolutePath(), new OnWriting() {
                                @Override
                                public void onProgress(int progress, String status) {
                                    progressBar.setValue(progress);
                                    progressBar.setString("Menulis Nama...");
                                }

                                @Override
                                public void onFinished() {
                                    progressBar.setVisible(false);
                                    btnChooseTemplate.setEnabled(true);
                                    btnChooseExcel.setEnabled(true);
                                    btnResetExcel.setEnabled(true);
                                    btnGenerate.setEnabled(true);
                                }
                            }).execute();
                        }

                        @Override
                        public void OnError(String errMsg) {

                        }
                    }).execute();

                }
            }

        });

    }


    static void showInfo(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Anda belum memilih file daftar nama .xls.", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }
}
