package gui.graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class PathAnimation extends JPanel {

    private Path2D shape;

    private double x = 100;
    private double y = 300;

    private double angle = 0;

    private double dx = 3;
    private double dy = 2;

    public PathAnimation() {

        setBackground(Color.WHITE);

        createShape();

        Timer timer = new Timer(8, e -> updateAnimation());
        timer.start();
    }

    private void createShape() {

        shape = new Path2D.Double();

        shape.moveTo(-30, 30);
        shape.lineTo(0, -40);
        shape.lineTo(30, 30);
        shape.closePath();
    }

    private void updateAnimation() {

        // pohyb
       x += dx;
       dy += dx;
        // bounce - uz zname
        if (x > getWidth() - 50 || x < 50) {
            dx = -dx;
        }

        if (y > getHeight() - 50 || y < 50) {
            dy = -dy;
        }

        // rotace
        angle += 0.10;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Zapamatovat si puvodni stav
        AffineTransform old = g2.getTransform();
        // pohyb smerem
        g2.translate(x, y);

        // rotace, samotna je jen sama nad sebou
        g2.rotate(angle);

        g2.setColor(Color.BLUE);
        g2.fill(shape);
        g2.setColor(Color.BLACK);
        g2.draw(shape);

        //puvodni stav
        g2.setTransform(old);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Showcase - animace");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new PathAnimation());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
