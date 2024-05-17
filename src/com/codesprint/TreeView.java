package com.codesprint;
import com.formdev.flatlaf.FlatDarkLaf;
import com.jidesoft.tree.StyledTreeCellRenderer;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Dimension;
import java.io.File;
import java.util.Objects;

public class TreeView {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame mainFrame=new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(new Dimension(1024,786));
        mainFrame.setLocationRelativeTo(null);
        String mainPath="D:\\New folder";
        File mainDirectory=new File(mainPath);
        File[] filesInMainDirectory=mainDirectory.listFiles();
        DefaultMutableTreeNode rootNode=new DefaultMutableTreeNode("Project "+mainPath);
        fillTreeWithFilesAndDirectories(Objects.requireNonNull(filesInMainDirectory),rootNode);
        JTree tree=new JTree(rootNode);
        tree.setCellRenderer(new StyledTreeCellRenderer());
        mainFrame.add(new JScrollPane(tree,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        mainFrame.setVisible(true);
    }

    private static void fillTreeWithFilesAndDirectories(File[] filesInMainDirectory,DefaultMutableTreeNode rootNode) {
        for(File file:filesInMainDirectory){
            if(file.isFile())
                rootNode.add(new DefaultMutableTreeNode(file.getName()));
            else if(file.isDirectory()){
                DefaultMutableTreeNode anotherRoot=new DefaultMutableTreeNode(file.getName());
                rootNode.add(anotherRoot);
                fillTreeWithFilesAndDirectories(Objects.requireNonNull(file.listFiles()),anotherRoot);
            }
        }
    }
}
