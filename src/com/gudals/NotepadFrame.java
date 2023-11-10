package com.gudals;

import javafx.scene.layout.Border;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NotepadFrame extends JFrame {
    private JPanel pan1 = new JPanel();
    private  JPanel pan2 = new JPanel();
    private JMenuBar mb = new JMenuBar();
    private JMenu m1 = new JMenu("  파일  ");
    private JMenu m2 = new JMenu("  편집  ");
    private JMenu m3 = new JMenu("  서식  ");
    private  JMenuItem m1Item1 = new JMenuItem("새파일");
    private  JMenuItem m1Item2 = new JMenuItem("열기");
    private  JMenuItem m1Item3 = new JMenuItem("저장");
    private  JMenuItem m2Item1 = new JMenuItem("복사");
    private  JMenuItem m2Item2 = new JMenuItem("붙여넣기");
    private  JMenuItem m3Item1 = new JMenuItem("글꼴    ");

    // private JComponent lastComp = btn3;
    private JTextArea ta = new JTextArea();
    private JScrollPane scpan = new JScrollPane(ta);
    private JLabel label1 = new JLabel(" ln : 1, col : 1");
    private LineBorder labelBorder = new LineBorder(Color.gray, 1, false);
    public void viewon(){

        // Menu
        //m1
        m1.setFont(new Font("파일", Font.BOLD, 15));
        m1Item1.setFont(new Font("새파일", Font.BOLD, 15));
        m1Item2.setFont(new Font("열기", Font.BOLD, 15));
        m1Item3.setFont(new Font("저장", Font.BOLD, 15));
        m1.add(m1Item1);
        m1.add(m1Item2);
        m1.add(m1Item3);
        //m2
        m2.setFont(new Font("편집", Font.BOLD, 15));
        m2Item1.setFont(new Font("복사", Font.BOLD, 15));
        m2Item2.setFont(new Font("붙여넣기", Font.BOLD, 15));
        m2.add(m2Item1);
        m2.add(m2Item2);
        //m3
        m3.setFont(new Font("서식", Font.BOLD, 15));
        m3Item1.setFont(new Font("글꼴", Font.BOLD, 15));
        m3.add(m3Item1);

        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.setBackground(Color.WHITE);
        setJMenuBar(mb);

        // textArea
        ta.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                JTextArea textArea = (JTextArea) e.getSource();

                int linenum = 1;
                int colnum = 1;

                try {
                    int caretpos = textArea.getCaretPosition();
                    linenum = textArea.getLineOfOffset(caretpos);
                    colnum = caretpos - textArea.getLineStartOffset(linenum);
                    colnum += 1;
                    linenum += 1;

                    label1.setText(" ln : " + linenum + ", col : " + colnum);
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }// careUpdate
        });// addCaretListener
        ta.setFont(new Font("textArea", Font.PLAIN, 30));

        // label
        label1.setFont(new Font("firstLabel", Font.PLAIN, 15));
        label1.setBorder(labelBorder);

        // scroll
        scpan.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scpan.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        // panel
        /*pan1.addComponentListener(new ComponentAdapter() {
            Point prevPoint = null;
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(prevPoint==null || prevPoint.y!=lastComp.getY()){
                    pan1.setPreferredSize(new Dimension(pan1.getPreferredSize().width, lastComp.getY() + lastComp.getHeight()));
                    pan1.updateUI();
                }
                prevPoint = lastComp.getLocation();
            }// componentResized
        });// addComponentListener*/
        pan1.setLayout(new BorderLayout());
        pan1.setSize(1000,550);
        pan2.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

        // pan add
        pan1.add(scpan);
        pan2.add(label1);

        // add
        add(pan1, BorderLayout.CENTER);
        add(pan2, BorderLayout.SOUTH);

        // frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,600);
        setLocationRelativeTo(null);
        setTitle("메모장");
        setVisible(true);
    }// viewon
}// class
