import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame frame=new JFrame();
        frame.setSize(1280,786);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
