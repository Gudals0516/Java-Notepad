package com.gudals;

import javax.swing.*;
import java.awt.*;

public class NotepadFrame extends JFrame {
    private JPanel pan1 = new JPanel();
    private JPanel pan2 = new JPanel();
    private JButton btn1 = new JButton("파일");
    private JButton btn2 = new JButton("편집");
    private JButton btn3 = new JButton("서식");
    private JTextArea ta = new JTextArea();
    private JScrollPane scpan = new JScrollPane(ta);
    public void viewon(){

        pan1.setLayout(new FlowLayout(FlowLayout.LEFT));
        pan2.setLayout(new BorderLayout());
        pan2.setSize(1000,550);

        pan1.add(btn1);
        pan1.add(btn2);
        pan1.add(btn3);
        pan2.add(scpan);

        add(pan1, BorderLayout.NORTH);
        add(pan2, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,600);
        setLocationRelativeTo(null);
        setTitle("메모장");
        setVisible(true);
    }// viewon
}// class
