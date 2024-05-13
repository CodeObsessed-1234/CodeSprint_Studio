package com.codesprint.user_interface;

import com.codesprint.constants.Constants;
import com.codesprint.file_explorer.FileExplorer;
import com.codesprint.file_explorer.FileTreeNode;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.jidesoft.swing.JideTabbedPane;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EditorPage{


    private static JSplitPane splitPane;
    private static JScrollPane fileEditorViewTextArea;
    private static JideTabbedPane tabOfOpenFile;


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
//        JideTabbedPane tabbedPane = getJideTabbedPane();
        JSplitPane functionSplitView = functionView();
        fileExplorerView();
//        frame.add(tabbedPane);
        frame.add(functionSplitView);
        frame.setVisible(true);
    }

    private static JSplitPane functionView(){
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JLabel welcomeLabel = new JLabel("Welcome Back",SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial",Font.BOLD,45));
        splitPane.setRightComponent(welcomeLabel);
        splitPane.setBackground(new Color(27,27,29));
        return splitPane;
    }


    private static void openFile(File file,JTextArea fileViewRightSideEditor) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
            fileViewRightSideEditor.setText(text.toString());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(splitPane, "Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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

            tabOfOpenFile = new JideTabbedPane();
            splitPane.setRightComponent(tabOfOpenFile);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);


            int result = fileChooser.showOpenDialog(splitPane);

            if (result == JFileChooser.APPROVE_OPTION) {

                File selectedFolder = fileChooser.getSelectedFile();
                openFolderButton.setVisible(false);
                JTree tree = FileExplorer.createDirectoryTree(selectedFolder);
                JScrollPane fileExplorerTreeView = new JScrollPane(tree);
                fileExplorerTreeView.setMinimumSize(new Dimension(200,splitPane.getHeight()));
                splitPane.setLeftComponent(fileExplorerTreeView);


                // Open and check for which file is chosen from the Directory
                tree.addTreeSelectionListener(event -> {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if (selectedNode != null ) {
                        FileTreeNode fileNode = (FileTreeNode) selectedNode.getUserObject();
                        if(!fileNode.getFile().isDirectory()) {
                            File selectedFile = fileNode.getFile();

                            //manages the Opening of TextArea and Menu Panel

                            JTextArea fileViewRightSideEditor=new JTextArea() ;
                            openFile(selectedFile,fileViewRightSideEditor);
                            fileViewRightSideEditor.setForeground(Color.WHITE);
                            fileViewRightSideEditor.setBackground(new Color(27,27,29));
                            fileViewRightSideEditor.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                            fileViewRightSideEditor.setFont(new Font("Arial", Font.PLAIN,17));
                            fileViewRightSideEditor.setMargin(new Insets(10,20,10,20));
                            fileEditorViewTextArea = new JScrollPane(fileViewRightSideEditor);

                            tabOfOpenFile.setShowCloseButtonOnTab(true);
                            tabOfOpenFile.add(fileNode.getSpecificFileName(selectedFile.toString()),fileEditorViewTextArea);
                            try {
                                tabOfOpenFile.setSelectedIndex(tabOfOpenFile.getSelectedIndex() + 1);
                            }
                            catch (IndexOutOfBoundsException ignored){
                                System.out.println("ignore this");
                            }
                        }
                    }
                });
            }
            else if(result==JFileChooser.ERROR){
                JOptionPane.showMessageDialog(splitPane,"Error in Opening File","Error", JOptionPane.ERROR_MESSAGE);
            }

        });
    }


}