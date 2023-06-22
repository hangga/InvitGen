package io.hangga;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame implements ActionListener {

    static JLabel l;

    MainForm(){}

    public static void main(String args[]){
    //void init(){
        // frame to contains GUI elements
        JFrame frame = new JFrame("InviGen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        Container pane = frame.getContentPane();
        JLabel label = new JLabel("Tulis Nama-nama Peserta (pisahkan dengan koma , )");
        pane.add(label, BorderLayout.PAGE_START);
        JTextArea ta = new JTextArea();
        pane.add(ta, BorderLayout.CENTER);

        JPanel panel = new JPanel(); // the panel is not visible in output

        //JTextField tf = new JTextField(10); // accepts upto 10 characters
        //JTextArea tf = new JTextArea(); // accepts upto 10 characters
        JButton btnGenerate = new JButton("Generate");
        JButton btnReset = new JButton("Reset");
        panel.add(btnGenerate);
        panel.add(btnReset);
        pane.add(panel, BorderLayout.PAGE_END);


       /* JMenuBar ob = new JMenuBar();
        JMenu ob1 = new JMenu("FILE");
        JMenu ob2 = new JMenu("Help");
        ob.add(ob1);
        ob.add(ob2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        ob1.add(m11);
        ob1.add(m22);*/




        //panel.add(label); // Components Added using Flow Layout
        //panel.add(tf);
        //panel.add(label); // Components Added using Flow Layout

        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                //fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int r = fileChooser.showSaveDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {

                } else {

                }
            }
        });

        //panel.add(btnSend);
        //panel.add(btnReset);
        //JTextArea ta = new JTextArea();

        //frame.getContentPane().add(BorderLayout.SOUTH, panel);
        //frame.getContentPane().add(BorderLayout.NORTH, ta);
        //frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
        //if (!f.isShowing()) f.show();
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String com = actionEvent.getActionCommand();

        if (com.equals("save")) {
            // create an object of JFileChooser class
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            // invoke the showsSaveDialog function to show the save dialog
            int r = j.showSaveDialog(null);

            // if the user selects a file
            if (r == JFileChooser.APPROVE_OPTION) {
                // set the label to the path of the selected file
                l.setText(j.getSelectedFile().getAbsolutePath());
            } else
                l.setText("the user cancelled the operation");// if the user cancelled the operation
        }

        // if the user presses the open dialog show the open dialog
        else {
            // create an object of JFileChooser class
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            // invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);

            // if the user selects a file
            if (r == JFileChooser.APPROVE_OPTION){
                // set the label to the path of the selected file
                l.setText(j.getSelectedFile().getAbsolutePath());
            }   else
                l.setText("the user cancelled the operation"); // if the user cancelled the operation
        }
    }
}
