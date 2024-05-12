package com.codesprint.user_interface;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.jidesoft.swing.JideTabbedPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class EditorPage{
    public EditorPage(){
        FlatDarkLaf.setup();
        JFrame frame=new JFrame();
        frame.setIconImage(new FlatSVGIcon(getClass().getResource("/mainIcon.svg")).getImage());
        JMenuBar menuBar=new JMenuBar();
        menuBar.setBorder(new MatteBorder(0,0,1,0,Color.gray));
        JMenu file=new JMenu("File");
        menuBar.setForeground(Color.green);
        JMenu edit=new JMenu("Edit");
        JMenu view=new JMenu("View");
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(view);
        frame.setJMenuBar(menuBar);
        frame.setSize(1280,786);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JideTabbedPane tabbedPane = getJideTabbedPane();
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private static JideTabbedPane getJideTabbedPane() {
        JideTabbedPane tabbedPane=new JideTabbedPane();
        tabbedPane.setShowCloseButtonOnTab(true);
        for(int loop=1;loop<=10;loop++)
            tabbedPane.add("Main"+loop+".java ",new JTextArea());
        return tabbedPane;
    }
}
