package com.codesprint.file_explorer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;

public class FileExplorer {

    private static void loadDirectoryContents(DefaultMutableTreeNode parent, JTree tree) {
        SwingWorker<Void, DefaultMutableTreeNode> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                FileTreeNode node = (FileTreeNode) parent.getUserObject();
                File[] files = node.file().listFiles();
                if (files != null) {
                    for (File file : files) {
                        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileTreeNode(file));
                        publish(childNode);
                        if (file.isDirectory()) {
                            loadDirectoryContents(childNode, tree);
                        }
                    }
                }
                return null;
            }

            @Override
            protected void process(java.util.List<DefaultMutableTreeNode> chunks) {
                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                for (DefaultMutableTreeNode node : chunks) {
                    model.insertNodeInto(node, parent, parent.getChildCount());
                }
            }
        };

        worker.execute();
    }


    public static JTree createDirectoryTree(File selectedFolder){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new FileTreeNode(new File(selectedFolder.toString())));
        JTree tree = new JTree(root);
       loadDirectoryContents(root,tree);
        tree.setCellRenderer(new CustomTreeCellRenderer());

        return tree;
    }


}


class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
    private final Icon folderIcon;
    private final Icon fileIcon;

    public CustomTreeCellRenderer() {
        ImageIcon originalFolderIcon = new ImageIcon("assets/folder.png");
        ImageIcon originalFileIcon = new ImageIcon("assets/leaf-icon.png");

        folderIcon = new ImageIcon(originalFolderIcon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        fileIcon = new ImageIcon(originalFileIcon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        if (value instanceof DefaultMutableTreeNode node) {
            Object userObject = node.getUserObject();
            if (userObject instanceof FileTreeNode fileNode) {

                if (fileNode.file().isDirectory()) {
                    setIcon(folderIcon);
                } else {
                    setIcon(fileIcon);
                }
            }
        }
        return this;
    }
}

