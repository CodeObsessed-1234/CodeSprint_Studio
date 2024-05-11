package com.codesprint.user_interface;

import com.codesprint.constants.Constants;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class EditorPage{
    public EditorPage(){
        FlatDarkLaf.setup();
        JFrame frame=new JFrame();
        frame.setIconImage(Constants.mainIcon.getImage());
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
        frame.setVisible(true);
    }
}
