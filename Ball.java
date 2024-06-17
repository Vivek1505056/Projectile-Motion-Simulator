import java.awt.Color;
import java.awt.Graphics;

class Ball{
    private double x, y;
    private int radius;
    public static double angle, height, velocity = 0;

    public Ball(double x, double y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((int)(x - radius), (int)(y - radius), radius*2, radius*2);
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

}
