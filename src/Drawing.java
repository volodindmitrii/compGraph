import javax.swing.*;
import java.util.ArrayList;

public class Drawing {
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Рисунок");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawingPanel panel = new DrawingPanel();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { createAndShowGUI(); }
        });
    }
}
