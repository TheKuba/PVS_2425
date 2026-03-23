package gui.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class Drawing extends JFrame {
    Drawing() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Ukazka grafiky");
        getContentPane().add(new Canvas());
        pack();
    }

    public static void main(String[] args) {
        new Drawing().setVisible(true);
    }
}

class Canvas extends JPanel {
    final int H = 400;
    final int W = 600;

    Canvas() {
        setPreferredSize(new Dimension(W, H));
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.black);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        drawBasics(g2);
//        drawShapes(g2);
//        drawArrow(g2, 100, 100, 500, 200, 40);
        drawCross(g2, 100, 100, 100);
    }

    void drawBasics(Graphics2D g2){
        g2.drawLine(0, 0, getWidth(), getHeight());
        g2.drawRect(100, 100, 150, 150);

        g2.drawOval(100, 100, 150, 150);
        int width = 160;
        int height = 160;
        g2.drawOval((getWidth() / 2) - (width / 2), (getHeight() / 2) - (height / 2), width, height);

        width = 240;
        height = 240;
        g2.drawOval((getWidth() / 2) - (width / 2), (getHeight() / 2) - (height / 2), width, height);
    }

    void drawShapes(Graphics2D g2){
        g2.setStroke(new BasicStroke(3));

        Path2D triangle = new Path2D.Double();

        triangle.moveTo(100, 420);
        triangle.lineTo(160, 360);
        triangle.lineTo(220, 420);
        triangle.closePath();
        g2.setColor(Color.BLUE);
        g2.draw(triangle);

        Path2D shape = new Path2D.Double();

        shape.moveTo(300, 380);
        shape.lineTo(350, 340);
        shape.lineTo(420, 360);
        shape.lineTo(400, 420);
        shape.lineTo(320, 420);
        shape.closePath();

        g2.setColor(Color.MAGENTA);
        g2.draw(shape);

    }
    void drawArrow(Graphics2D g, double x1, double y1, double x2, double y2, double arrowLength) {
        double vx = x2 - x1;
        double vy = y2 - y1;

//        double vLength = Math.sqrt((vx*vx) + (vy*vy));
        double vLength = Math.hypot(vx, vy);

        double vNormX = vx / vLength;
        double vNormY = vy / vLength;

        double vArrowX = arrowLength * vNormX;
        double vArrowY = arrowLength * vNormY;

        //kolmy vektor
        double kx = -vArrowY;
        double ky = vArrowX;

        //relativni dylka
        kx *= 0.25;
        ky *= 0.25;

        //hlavni cast sipky
        g.draw(new Line2D.Double(x1, y1, x2, y2));

        //bocni casti
        g.draw(new Line2D.Double(x2, y2, x2 - vArrowX + kx, y2 - vArrowY + ky));
        g.draw(new Line2D.Double(x2, y2, x2 - vArrowX - kx, y2 - vArrowY - ky));
    }

    void drawCross(Graphics2D g, int x, int y, int len) {
        Path2D cross = new Path2D.Double();

        cross.moveTo(x, y);
        cross.lineTo(x+2*len, y+len);
        cross.lineTo(x+2*len, y);
        cross.lineTo(x, y+len);
        cross.closePath();
        g.setColor(Color.BLUE);
        g.draw(cross);


    }

}