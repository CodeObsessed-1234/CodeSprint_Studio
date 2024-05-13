package com.codesprint.user_interface;

import com.codesprint.constants.Constants;
import com.codesprint.file_explorer.FileExplorer;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.jidesoft.swing.JideTabbedPane;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.File;

public class EditorPage{

  
    private static final JTextArea fileViewRightSideEditor = new JTextArea();
    private static JSplitPane splitPane;

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
        JSplitPane functionSplitView = functionView();
        fileExplorerView();
        frame.add(tabbedPane);
        frame.add(functionSplitView);
        frame.setVisible(true);
    }

    private static JSplitPane functionView(){
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setRightComponent(EditorPage.fileViewRightSideEditor);
        return splitPane;
    }

    private static void fileExplorerView(){


        JButton openFolderButton= new JButton("Open Folder");
        openFolderButton.setFont(Constants.fontButton);
        openFolderButton.setMargin(new Insets(10, 20, 10, 20));
        openFolderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openFolderButton.setBackground(Color.PINK);
        openFolderButton.setForeground(Color.DARK_GRAY);

        splitPane.setLeftComponent(openFolderButton);



        openFolderButton.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);


            int result = fileChooser.showOpenDialog(fileViewRightSideEditor);


            if (result == JFileChooser.APPROVE_OPTION) {

                File selectedFolder = fileChooser.getSelectedFile();
                openFolderButton.setVisible(false);
                JScrollPane fileExplorerTreeView = new JScrollPane(FileExplorer.createDirectoryTree(selectedFolder));
                fileExplorerTreeView.setMinimumSize(new Dimension(200,splitPane.getHeight()));
                splitPane.setLeftComponent(fileExplorerTreeView);
            }

        });


    }

    private static JideTabbedPane getJideTabbedPane() {
        JideTabbedPane tabbedPane=new JideTabbedPane();
        tabbedPane.setShowCloseButtonOnTab(true);
        for(int loop=1;loop<=10;loop++)
            tabbedPane.add("Main"+loop+".java ",new JTextArea());
        return tabbedPane;
    }
}