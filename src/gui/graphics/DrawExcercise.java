package gui.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class DrawExcercise extends JFrame {
    public DrawExcercise() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Ukazka grafiky");
        getContentPane().add(new Canvass());
        pack();
    }

    static void main() {
        new  DrawExcercise().setVisible(true);
    }
}

class Canvass extends JPanel {
    final int H = 400;
    final int W = 600;

    Canvass() {
        setPreferredSize(new Dimension(W, H));
    }

    @Override
    public void paint(Graphics g) {
        int x = 50;
        int y = 50;
        int len = 50;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.black);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawShape(g2, x, y, len);
    }

    void drawShape(Graphics2D g2, int x, int y, int len) {
        g2.setStroke(new BasicStroke(3));

        Path2D shape = new Path2D.Double();

        shape.moveTo(x, y);
        shape.lineTo(x + len*2, y + len);
        shape.lineTo(x + len*2, y);
        shape.lineTo(x, y + len);
        shape.closePath();

        g2.setColor(Color.MAGENTA);
        g2.draw(shape);

    }
}