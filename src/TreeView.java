import com.formdev.flatlaf.FlatDarkLaf;
import com.jidesoft.tree.StyledTreeCellRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TreeView {
    private static JTree tree;
    private static DefaultMutableTreeNode rootNode;

    public static void main(String[] args) throws Exception {
        FlatDarkLaf.setup();
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(new Dimension(1024, 786));
        mainFrame.setLocationRelativeTo(null);
        String mainPath = "D:\\New folder";
        File mainDirectory = new File(mainPath);
        File[] filesInMainDirectory = mainDirectory.listFiles();
        rootNode = new DefaultMutableTreeNode("Project " + mainPath);
        fillTreeWithFilesAndDirectories(Objects.requireNonNull(filesInMainDirectory), rootNode);
        tree = new JTree(rootNode);
        tree.setCellRenderer(new StyledTreeCellRenderer());
        mainFrame.add(new JScrollPane(tree, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        mainFrame.setVisible(true);
        startFileWatcher(mainPath);
    }

    private static void fillTreeWithFilesAndDirectories(File[] filesInMainDirectory, DefaultMutableTreeNode rootNode) {
        for (File file : filesInMainDirectory) {
            if (file.isFile()) {
                rootNode.add(new DefaultMutableTreeNode(file.getName()));
            } else if (file.isDirectory()) {
                DefaultMutableTreeNode anotherRoot = new DefaultMutableTreeNode(file.getName());
                rootNode.add(anotherRoot);
                if (file.exists()) {
                    fillTreeWithFilesAndDirectories(Objects.requireNonNull(file.listFiles()), anotherRoot);
                }
            }
        }
    }

    private static void startFileWatcher(String directoryPath) throws IOException {
        Executor executor = Executors.newSingleThreadExecutor();
        Path path = FileSystems.getDefault().getPath(directoryPath);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        registerDirectoryAndSubdirectories(path, watchService);
        executor.execute(() -> {
            WatchKey key;
            while (true) {
                try {
                    if ((key = watchService.take()) == null) break;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println("Event: " + event.kind() + " - " + event.context());
                    Path filePath = (Path) event.context();
                    File file = filePath.toFile();
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        addNodeToTree(file);
                    } else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                        removeNodeFromTree(file);
                    } else if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        updateNodeInTree(file);
                    }
                }
                key.reset();
            }
        });
    }

    private static void registerDirectoryAndSubdirectories(Path path, WatchService watchService) throws IOException {
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for (Path subPath : directoryStream) {
                if (Files.isDirectory(subPath)) {
                    registerDirectoryAndSubdirectories(subPath, watchService);
                }
            }
        }
    }

    private static void addNodeToTree(File file) {
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file.getName());
        DefaultMutableTreeNode parent = rootNode;
        if (file.getParentFile() != null) {
            String parentPath = file.getParentFile().getPath();
            TreePath parentPathTree = findTreePath(rootNode, parentPath);
            if (parentPathTree != null) {
                parent = (DefaultMutableTreeNode) parentPathTree.getLastPathComponent();
            } else {
                // If the parent directory doesn't exist in the tree, create it
                DefaultMutableTreeNode parentDirectory = new DefaultMutableTreeNode(file.getParentFile().getName());
                rootNode.add(parentDirectory);
                parent = parentDirectory;
            }
        }
        parent.add(newNode);
        SwingUtilities.invokeLater(() -> {
            try {
                tree.updateUI();
            } catch (Exception ignored) {
            }
        });
    }

    private static void removeNodeFromTree(File file) {
        TreePath filePathTree = findTreePath(rootNode, file.getPath());
        if (filePathTree != null) {
            DefaultMutableTreeNode nodeToRemove = (DefaultMutableTreeNode) filePathTree.getLastPathComponent();
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) nodeToRemove.getParent();
            parent.remove(nodeToRemove);
            SwingUtilities.invokeLater(() -> {
                try {
                    tree.updateUI();
                } catch (Exception ignored) {
                }
            });
        }
    }

    private static void updateNodeInTree(File file) {
        TreePath filePathTree = findTreePath(rootNode, file.getPath());
        if (filePathTree != null) {
            DefaultMutableTreeNode nodeToUpdate = (DefaultMutableTreeNode) filePathTree.getLastPathComponent();
            nodeToUpdate.setUserObject(file.getName());
            SwingUtilities.invokeLater(() -> {
                try {
                    tree.updateUI();
                } catch (Exception ignored) {
                }
            });
        }
    }


    private static TreePath findTreePath(DefaultMutableTreeNode root, String path) {
        Stack<DefaultMutableTreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            DefaultMutableTreeNode node = stack.pop();
            if (node.toString().equals(path)) {
                return new TreePath(node.getPath());
            }
            for (int i = node.getChildCount() - 1; i >= 0; i--) {
                stack.push((DefaultMutableTreeNode) node.getChildAt(i));
            }
        }
        return null;
    }


}