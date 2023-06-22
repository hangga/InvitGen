package io.hangga;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class MainForm extends JFrame {

    static JLabel l;
    static JFileChooser outputChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
    private static String template = Main.template;


    MainForm() {
    }

    public static void main(String[] args) {
        //void init(){
        // frame to contains GUI elements
        JFrame frame = new JFrame("InviGen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        Container pane = frame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel();
        JButton btnChooseTemplate = new JButton("Pilih Template");
        JLabel lblTemplate = new JLabel("Template : " + Main.template);
        topPanel.add(btnChooseTemplate, BorderLayout.LINE_START);
        topPanel.add(lblTemplate, BorderLayout.CENTER);
        pane.add(topPanel);

        JLabel lblTulisNama = new JLabel("Tulis Nama-nama Peserta (pisahkan dengan koma , )");
        JPanel topSubPanel = new JPanel();
        topSubPanel.add(lblTulisNama, BorderLayout.LINE_START);
        topSubPanel.add(new JPanel(), BorderLayout.LINE_END);
        topSubPanel.setSize(500, 100);
        pane.add(topSubPanel);
        JTextArea ta = new JTextArea();
        pane.add(ta, BorderLayout.CENTER);


        JButton btnGenerate = new JButton("Generate");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnGenerate, BorderLayout.LINE_END);
        pane.add(bottomPanel);

        btnChooseTemplate.addActionListener(actionEvent -> {
            JFileChooser fileTemplateChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());


            fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .docx", "docx"));
            fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .doc", "doc"));
            fileTemplateChooser.addChoosableFileFilter(new FileNameExtensionFilter("LibreOffice Documents .odt", "odt"));

            if (fileTemplateChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                template = fileTemplateChooser.getSelectedFile().getAbsolutePath();
                lblTemplate.setText("Template : " + template);
            }
        });


        btnGenerate.addActionListener(actionEvent -> {

            if (ta.getText().trim().length() == 0){
                showInfo(frame, "Ketik nama-nama dan pisahkan dengan [,]");
                return;
            }

            outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .docx", "docx"));
            outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("Microsoft Word Documents .doc", "doc"));
            outputChooser.addChoosableFileFilter(new FileNameExtensionFilter("LibreOffice Documents .odt", "odt"));


            if (outputChooser.getSelectedFile() != null) {
                new Invigen().generate(ta.getText(), template, outputChooser.getSelectedFile().getAbsolutePath(), new InvigenListener() {
                    @Override
                    public void onSuccess(String outputPath) {
                        showInfo(frame, "Buka disini : " + outputPath);
                    }
                });
            } else {
                outputChooser.setSelectedFile(new File("output-invigen.docx"));

                if (outputChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    new Invigen().generate(ta.getText(), template, outputChooser.getSelectedFile().getAbsolutePath(), new InvigenListener() {
                        @Override
                        public void onSuccess(String outputPath) {
                            showInfo(frame, "Buka disini : " + outputPath);
                        }
                    });
                }
            }

        });
        frame.setVisible(true);
    }


    static void showInfo(JFrame frame, String msg){
        JOptionPane.showMessageDialog(frame,
                msg,
                "Berhasil",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
