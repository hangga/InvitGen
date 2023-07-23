package io.hangga;

import io.hangga.tools.*;
import io.hangga.ui.ImagePanel;

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
        frame.setSize(600, 400);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);


        Container mainPanel = frame.getContentPane();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        /* Panel Atas */
        //JPanel topPanel = new JPanel();
        JButton btnChooseExcel = new JButton("Load .xls File");
        //btnChooseExcel.setPreferredSize(new Dimension(500, 10));
        btnChooseExcel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnChooseExcel.setFont(new Font("Serif", Font.PLAIN, 12));
        //btnChooseExcel.setMargin(new Insets(0,0,0,0));
        JButton btnChooseTemplate = new JButton("Pilih Template");
        btnChooseTemplate.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnChooseTemplate.setFont(new Font("Serif", Font.PLAIN, 12));
        //JLabel lblTemplate = new JLabel("Template : " + Invigen.templatePath);
        frame.setFont(new Font("Serif", Font.PLAIN, 11));
        //topPanel.add(lblTemplate, BorderLayout.LINE_START);
        //topPanel.add(btnChooseTemplate, BorderLayout.CENTER);
        //topPanel.add(btnChooseExcel, BorderLayout.CENTER);

        //mainPanel.add(topPanel);

        //JLabel lblTulisNama = new JLabel("Tulis Nama-nama Peserta (pisahkan dengan koma , )");
        /*JPanel topSubPanel = new JPanel();
        //topSubPanel.add(lblTulisNama, BorderLayout.LINE_START);
        topSubPanel.add(new JPanel(), BorderLayout.LINE_END);
        topSubPanel.setSize(500, 100);
        mainPanel.add(topSubPanel);*/


        //JTextArea ta = new JTextArea(20, 4);

        JPanel midlePanel = new JPanel();



        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("No");
        tableModel.addColumn("Nama");
        JTable jTable = new JTable(tableModel);
        jTable.setSize(200, 100);
        TableColumnModel columnModel = jTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setMaxWidth(40);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(1).setMaxWidth(100);
        JScrollPane scroll = new JScrollPane(jTable);
        //scroll.setSize(new Dimension(200, 100));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(300, 200));
        leftPanel.add(scroll);
        leftPanel.add(btnChooseExcel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setPreferredSize(new Dimension(300, 200));
        final ImagePanel[] imagePanel = {new ImagePanel(Invigen.userDir + "/empty.png")};
        rightPanel.add(imagePanel[0]);
        rightPanel.add(btnChooseTemplate);

        midlePanel.setLayout(new BoxLayout(midlePanel, BoxLayout.X_AXIS));
        midlePanel.add(leftPanel);
        midlePanel.add(rightPanel);
        midlePanel.setBackground(Color.CYAN);
        mainPanel.add(midlePanel);


        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);


        JButton btnGenerate = new JButton("Generate");
        btnGenerate.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(600, 0));
        bottomPanel.setBackground(Color.BLUE);
        bottomPanel.add(progressBar);
        bottomPanel.add(btnGenerate, BorderLayout.LINE_END);
        mainPanel.add(bottomPanel);
        frame.setVisible(true);

        List<String> arrNames = new ArrayList<>();

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
                    }

                    @Override
                    public void OnGetNameAt(String name) {
                        tableModel.getRowCount();
                        tableModel.insertRow(tableModel.getRowCount(), new Object[]{tableModel.getRowCount() + 1, name});
                        //ta.append(name + ", ");
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
                rightPanel.remove(1);
                rightPanel.add(new ImagePanel(Invigen.userDir +"/filled.png"),1);
                //lblTemplate.setText("Template : " + templatePath);
            }
        });

        btnGenerate.addActionListener(actionEvent -> {

            if (arrNames.size() == 0) {
                showInfo(frame);
                return;
            }
            //String[] arrNames = ta.getText().trim().split(",");
            //int max = arrNames.length;

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
                                progressBar.setVisible(false);
                            }
                        }).execute();
                    }

                    @Override
                    public void OnError(String errMsg) {

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
        JOptionPane.showMessageDialog(frame,
                "Ketik nama-nama dan pisahkan dengan [,]",
                "Berhasil",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
