package com.codesprint.splash_screen;

import com.codesprint.constants.Constants;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import java.awt.Color;
import java.awt.Font;

public class SplashScreen extends JWindow {
    public SplashScreen(){
        this.init();
    }
    private void init(){
        this.setSize(Constants.SPLASH_SCREEN_WIDTH,Constants.SPLASH_SCREEN_HEIGHT);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        JLabel backgroundLabel=new JLabel(new ImageIcon("C:\\Users\\USER\\IdeaProjects\\CodeSprint Studio\\assets\\splash_screen.png"));
        backgroundLabel.setBounds(0,0,800,500);
        add(backgroundLabel);
        JLabel editorName=new JLabel("CodeSprint STUDIO");
        editorName.setForeground(Color.WHITE);
        editorName.setFont(new Font("inter",Font.BOLD,50));
        editorName.setBounds(51,72,478,61);
        backgroundLabel.add(editorName);
        JLabel companyEditorName=new JLabel("CodeSprint IDE");
        companyEditorName.setBounds(51,155,173,29);
        companyEditorName.setFont(new Font("inter",Font.PLAIN,24));
        companyEditorName.setForeground(Color.decode("#F4E9EE"));
        backgroundLabel.add(companyEditorName);
        JLabel versionHistory=new JLabel("Community 2024.1");
        versionHistory.setBounds(51,408,175,24);
        versionHistory.setFont(new Font("inter",Font.PLAIN,20));
        versionHistory.setForeground(Color.decode("#F4E9EE"));
        backgroundLabel.add(versionHistory);
        JLabel companyName1=new JLabel("CODE");
        companyName1.setBounds(680,355,83,41);
        companyName1.setForeground(Color.decode("#EDEDED"));
        companyName1.setFont(new Font("inter",Font.BOLD,20));
        backgroundLabel.add(companyName1);
        JLabel companyName2=new JLabel("SPRINT");
        companyName2.setBounds(680,378,83,41);
        companyName2.setForeground(Color.decode("#EDEDED"));
        companyName2.setFont(new Font("inter",Font.BOLD,20));
        backgroundLabel.add(companyName2);
    }
}
