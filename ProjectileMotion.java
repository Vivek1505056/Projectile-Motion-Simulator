import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ProjectileMotion extends JPanel implements ActionListener {
    public static Ball ball;
    public static int radius = 15;
    public static double x = radius;
    public static double y = 650 - radius - Ball.height;

    protected static Timer timer;
    public static double time = 0;
    public static double xInitial = x;
    public static double yInitial = y;
    public double gravity = 9.80665;

    public static double maxHeight, displacement, finalVelocity, airTime;

    public ProjectileMotion() {
        timer = new Timer(20, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        ball = new Ball(x, y, radius);
        ball.draw(g);
    }

    /*
     * Calculate movement for x and y direction
     * Increment time to cause movement
     * Call get values for calculations
     * Check when ball hits the ground and stop the timer
     * Set x and y of ball class
     */
    public void move() {
        time += 0.1;
        y = yInitial - (Ball.velocity * time * Math.sin(Math.toRadians(Ball.angle))) + (0.5 * gravity * time * time);
        x = xInitial + Ball.velocity * time * Math.cos(Math.toRadians(Ball.angle));
        getValues();

        if (y >= getHeight() - radius) {
            timer.stop();
            y = getHeight() - radius;
            maxHeight = 635 - maxHeight;
            ProjectileFrame.start = false;
        }

        ball.setX(x);
        ball.setY(y);
    }

    public static void getValues(){
        //Time calculation
        double vYInitial = Ball.velocity * Math.sin(Math.toRadians(Ball.angle));
        
        double value1 = (-(-vYInitial) + Math.sqrt((-vYInitial) * (-vYInitial) - 4 * 4.9 * (yInitial - y))) / (2 * 4.9);
        double value2 = (-(-vYInitial) - Math.sqrt((-vYInitial) * (-vYInitial) - 4 * 4.9 * (yInitial - y))) / (2 * 4.9);

        airTime = Math.max(value1, value2);

        //Displacement calculation
        double velocityX = Ball.velocity * Math.cos(Math.toRadians(Ball.angle));
        displacement = velocityX * airTime;

        //Max height calculation
        if(maxHeight > y){
            maxHeight = y;
        }

        //Final velocity calculation
        double vYFinal = Math.sqrt(Math.pow(vYInitial, 2) + (-19.613) * (yInitial - y));
        finalVelocity = Math.sqrt(Math.pow(vYFinal, 2) + Math.pow(velocityX, 2));

    }

    public static void startTimer() {
        if (ProjectileFrame.start) {
            timer.start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
}
