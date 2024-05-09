import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame frame=new JFrame();
        JMenuBar menuBar=new JMenuBar();
        JMenu file=new JMenu("File");
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
