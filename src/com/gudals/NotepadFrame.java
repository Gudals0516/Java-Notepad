package com.gudals;

import javafx.scene.layout.Border;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NotepadFrame extends JFrame {
    private Frame frame = new Frame("메모장");
    private FileDialog saveDialog = new FileDialog(frame, "저장", FileDialog.SAVE);
    private  FileDialog openDialog = new FileDialog(frame, "열기", FileDialog.LOAD);
    private JPanel pan1 = new JPanel();
    private  JPanel pan2 = new JPanel();
    private JPanel popPan = new JPanel();
    private JMenuBar mb = new JMenuBar();
    private JMenu m1 = new JMenu("  파일  ");
    private JMenu m2 = new JMenu("  편집  ");
    private JMenu m3 = new JMenu("  서식  ");
    private JMenuItem m1Item1 = new JMenuItem("새파일");
    private JMenuItem m1Item2 = new JMenuItem("열기");
    private JMenuItem m1Item3 = new JMenuItem("저장");
    private JMenuItem m2Item1 = new JMenuItem("복사");
    private JMenuItem m2Item2 = new JMenuItem("붙여넣기");
    private JMenuItem m2Item3 = new JMenuItem("자르기");
    private JMenuItem m3Item1 = new JMenuItem("글꼴");
    private JTextArea ta = new JTextArea();
    private JScrollPane scpan = new JScrollPane(ta);
    private JLabel label1 = new JLabel(" ln : 1, col : 1");
    private LineBorder labelBorder = new LineBorder(Color.gray, 1, false);
    private String firstTxt = "";
    private String lastName = "제목 없음";
    private String fileName = "제목 없음";
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
        m2Item3.setFont(new Font("자르기", Font.BOLD, 15));
        m2.add(m2Item1);
        m2.add(m2Item2);
        m2.add(m2Item3);
        //m3
        m3.setFont(new Font("서식", Font.BOLD, 15));
        m3Item1.setFont(new Font("글꼴", Font.BOLD, 15));
        m3.add(m3Item1);

        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.setBackground(Color.WHITE);
        setJMenuBar(mb);

        MenuActionListener listener = new MenuActionListener();
        m1Item1.addActionListener(listener);
        m1Item2.addActionListener(listener);
        m1Item3.addActionListener(listener);

        m2Item1.addActionListener(listener);
        m2Item2.addActionListener(listener);
        m2Item3.addActionListener(listener);

        m3Item1.addActionListener(listener);
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
        pan1.setLayout(new BorderLayout());
        pan1.setSize(1000,550);
        pan2.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

        // pan add
        pan1.add(scpan);
        pan2.add(label1);

        // add
        add(pan1, BorderLayout.CENTER);
        add(pan2, BorderLayout.SOUTH);

        //exit
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if(ta.getText().equals(firstTxt)){
                    System.out.println("같음");
                    System.exit(0);
                }else {
                    String[] answer = new String[]{"저장", "취소"};
                    int result = JOptionPane.showOptionDialog(null, "종료하시겠습니까?", "Option"
                        , JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, answer, null);
                    if(result==0){
                        saveDialog.setLocationRelativeTo(null);
                        saveDialog.setVisible(true);
                        String data = saveDialog.getDirectory()+ saveDialog.getFile();
                        if(saveDialog.getFile()==null){
                            System.exit(0);
                        }else {
                            try {
                                BufferedWriter bw = new BufferedWriter(new FileWriter(data + ".txt"));
                                String str = ta.getText();
                                for (int i = 0; i < str.length(); i++) {
                                    if (str.charAt(i) == '\n') {
                                        bw.newLine();
                                    } else {
                                        bw.write(str.charAt(i));
                                    }
                                }// for
                                bw.close();
                                System.exit(0);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }else {
                        System.exit(0);
                    }// saveDialog if
                }// popWindow
            }// windowClosing
        });// addWindowListener
        // frame
        setSize(600,600);
        setLocationRelativeTo(null);
        setTitle(fileName);
        setVisible(true);
    }// viewon

    class MenuActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("새파일")){
                ta.setText("");
                fileName = "제목 없음";
                firstTxt = "";
                setTitle(fileName);
            }else if(e.getActionCommand().equals("열기")){
                openDialog.setLocationRelativeTo(null);
                openDialog.setVisible(true);
                String data = openDialog.getDirectory() + openDialog.getFile();
                lastName = fileName;
                fileName = openDialog.getFile();
                setTitle(fileName);
                if(openDialog.getFile()==null){
                    setTitle(lastName);
                    fileName = lastName;
                }else {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(data));
                        String str = br.readLine();
                        ta.setText(str);
                        firstTxt = str;
                        br.close();
                    } catch (FileNotFoundException ex) {
                        setTitle(lastName);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }else if(e.getActionCommand().equals("저장")){
                saveDialog.setVisible(true);
                String data = saveDialog.getDirectory() + saveDialog.getFile();
                fileName = saveDialog.getFile();
                setTitle(fileName+".txt");
                firstTxt=ta.getText();
                if(saveDialog.getFile()==null){
                    System.exit(0);
                }else {
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(data + ".txt"));
                        String str = ta.getText();
                        for (int i = 0; i < str.length(); i++) {
                            if (str.charAt(i) == '\n') {
                                bw.newLine();
                            } else {
                                bw.write(str.charAt(i));
                            }
                        }
                        bw.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }else if(e.getActionCommand().equals("복사")){
                ta.copy();
            }else if(e.getActionCommand().equals("붙여넣기")){
                ta.paste();
            }else if(e.getActionCommand().equals("자르기")){
                ta.copy();
                ta.replaceRange("", ta.getSelectionStart(), ta.getSelectionEnd());
            }else if(e.getActionCommand().equals("글꼴")){
                FontSetting fontSetting = new FontSetting(NotepadFrame.this);
                fontSetting.setVisible(true);
            }
        }// actionPerformed
    }// ActionListener class

    class FontSetting extends JFrame implements ActionListener{
        private JComboBox comboBox;
        private JPanel pan1 = new JPanel();
        private JPanel pan2 = new JPanel();
        private JScrollPane scrollFont, scrollStyle, scrollSize;
        private JList<String> fontList, styleList, sizeList;
        private JButton confirm = new JButton("확인");
        private JButton cancel = new JButton("취소");
        NotepadFrame note;
        public FontSetting(NotepadFrame note){
            this.note = note;
            setTitle("글꼴");
            setSize(400, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            setResizable(false);
            setVisible(false);

            comboBox = new JComboBox();

            String[] fonts = {"굴림체", "궁서체", "굴림체", "D2Coding"};
            fontList = new JList<>(fonts);
            fontList.setFont(new Font("글씨체", Font.BOLD, 20));
            scrollFont = new JScrollPane(fontList);
            scrollFont.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            String[] styles = {"PLAIN", "BOLD", "ITALIC"};
            styleList = new JList<>(styles);
            styleList.setFont(new Font("글씨스타일", Font.BOLD, 15));
            scrollStyle = new JScrollPane(styleList);
            scrollStyle.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            String[] sizes = {"12", "14", "16", "18", "20", "24", "28", "32"
                                , "36", "40", "52", "64", "76", "88", "100"};
            sizeList = new JList<>(sizes);
            sizeList.setFont(new Font("글씨크기", Font.BOLD, 15));
            scrollSize = new JScrollPane(sizeList);
            scrollSize.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

            confirm.addActionListener(this);
            cancel.addActionListener(this);

            pan1.setLayout(new GridLayout(1,3));
            pan2.setLayout(new FlowLayout(FlowLayout.RIGHT));

            pan1.add(scrollFont);
            pan1.add(scrollStyle);
            pan1.add(scrollSize);
            pan2.add(confirm);
            pan2.add(cancel);

            add(pan1, BorderLayout.CENTER);
            add(pan2, BorderLayout.SOUTH);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("확인")){
                String font = fontList.getSelectedValue();
                int style = styleList.getSelectedIndex();
                int size = Integer.parseInt(sizeList.getSelectedValue());

                Font f = new Font(font, style, size);
                FontSetting.this.note.ta.setFont(f);
                setVisible(false);
            }else if(e.getActionCommand().equals("취소")){
                setVisible(false);
            }
        }// actionPerformed
    }// FontSetting class
}// Frame class
