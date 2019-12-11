import javafx.geometry.Point3D;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MovingCircle extends JComponent implements ActionListener {
    public static double fi = 0;
    private double scale;
    private Color color;
    private Timer timer;
    public double x =10;
    public double y =10;

    public MovingCircle(Color color, int delay) {
        scale = 1.0;
        timer = new Timer(delay, this);
        this.color = color;
        setPreferredSize(new Dimension(500, 500));
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        int width = 500;
        int height = 500;
        g.fillRect(0, 0, width, height);
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.scale(scale, scale);
        boolean if3DModelling =true;
        if (if3DModelling) {
            ArrayList<Point3D> tops = new ArrayList<>();
            int x0 = 100;
            int y0 = 100;
            int z0 = 0;
            int x1 = 100;
            int y1 = 100;
            int z1 = 100;
            int dx = 0;
            int dy = 0;
            tops.add(new Point3D(x0, y0, z0));
            tops.add(new Point3D(x0+x1, y0, z0));
            tops.add(new Point3D(x0+x1, y0+y1, z0));
            tops.add(new Point3D(x0, y0+y1, z0));
            tops.add(new Point3D(x0+dx, y0+dy, z1));
            tops.add(new Point3D(x0+x1+dx, y0+dy, z1));
            tops.add(new Point3D(x0+x1+dx, y0+y1+dy, z1));
            tops.add(new Point3D(x0+dx, y0+y1+dy, z1));
            My3DPolygon polygon = new My3DPolygon(tops);

            ArrayList<Point> povorotProjection = polygon.povorot(false, 1, 2, 1, MovingCircle.fi);
            for (Point apoint : povorotProjection){
                g2d.drawLine(apoint.x, apoint.y, apoint.x, apoint.y);
            }
            fi+=0.01;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Moving Circle");
                JPanel panel = new JPanel();
                final MovingCircle MovingCircleGreen = new MovingCircle(Color.green, 20);
                panel.add(MovingCircleGreen);
                frame.getContentPane().add(panel);
                final JButton button = new JButton("Start");
                button.addActionListener(new ActionListener() {
                    private boolean pulsing = false;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (pulsing) {
                            pulsing = false;
                            MovingCircleGreen.stop();
                            button.setText("Start");
                        } else {
                            pulsing = true;
                            MovingCircleGreen.start();
                            button.setText("Stop");
                        }
                    }
                });
                panel.add(button);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 550);
                frame.setVisible(true);
            }
        });
    }
}