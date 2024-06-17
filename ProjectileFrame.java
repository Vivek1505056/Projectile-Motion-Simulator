import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

class ProjectileFrame extends JFrame implements ActionListener {
    private JButton startButton, valueButton;
    private JTextField heightField, angleField, velocityField;
    public static boolean start, valid;
    public JLabel displacement, time, maxHeight, finalVelocity;

    public ProjectileFrame() {
        JFrame frame = new JFrame("Projectile Motion Simulator");

        ProjectileMotion center = new ProjectileMotion();
        center.setPreferredSize(new Dimension(1300, 650));
        center.setBackground(new Color(135, 206, 235));
        frame.getContentPane().add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setPreferredSize(new Dimension(1300, 50));
        bottom.setBackground(new Color(126, 200, 80));
        frame.getContentPane().add(bottom, BorderLayout.PAGE_END);

        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(1300, 50));
        top.setBackground(new Color(51, 51, 52));
        frame.getContentPane().add(top, BorderLayout.NORTH);

        JLabel heightLabel = new JLabel("Initial Height (units, 0 - 300): ");
        heightField = new JTextField();
        heightField.setPreferredSize(new Dimension(150, 20));

        JLabel angleLabel = new JLabel("Angle of Projectile (0° - 90°): ");
        angleField = new JTextField();
        angleField.setPreferredSize(new Dimension(150, 20));

        JLabel velocityLabel = new JLabel("Velocity of Projectile (units/s, 0 - 100): ");
        velocityField = new JTextField();
        velocityField.setPreferredSize(new Dimension(150, 20));

        Color white = new Color(255, 255, 255);

        numCheck(heightField);
        numCheck(angleField);
        numCheck(velocityField);

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        
        valueButton = new JButton("Get Values (Upon Landing)");
        valueButton.addActionListener(this);

        bottom.add(heightLabel);
        bottom.add(heightField);
        bottom.add(angleLabel);
        bottom.add(angleField);
        bottom.add(velocityLabel);
        bottom.add(velocityField);
        bottom.add(startButton);

        displacement = new JLabel("Displacement: " + Double.toString(ProjectileMotion.displacement) + "       ");
        time = new JLabel("Time in air: " + Double.toString(ProjectileMotion.airTime) + "        ");
        maxHeight = new JLabel("Max Height: " + Double.toString(ProjectileMotion.maxHeight) + "      ");
        finalVelocity = new JLabel("Final Velocity: " + Double.toString(ProjectileMotion.finalVelocity) + "      ");

        displacement.setFont(new Font("Serif", Font.PLAIN, 21));
        time.setFont(new Font("Serif", Font.PLAIN, 21));
        maxHeight.setFont(new Font("Serif", Font.PLAIN, 21));
        finalVelocity.setFont(new Font("Serif", Font.PLAIN, 21));

        displacement.setForeground(white);
        time.setForeground(white);
        maxHeight.setForeground(white);
        finalVelocity.setForeground(white);

        top.add(displacement);
        top.add(time);
        top.add(maxHeight);
        top.add(finalVelocity);
        top.add(valueButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }

    /*
     * Create function that takes in a field as a parameter
     * Add key listener to the field to check each character
     * Check if the value is a number
     * If the character entered is not a number set editable to false
     * If character is number set editable to true
     * This segment was created by tutorialspoint but modified slightly
     * https://www.tutorialspoint.com/how-can-we-make-jtextfield-accept-only-numbers-in-java
     */
    private void numCheck(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent k) {
                if (k.getKeyChar() >= '0' && k.getKeyChar() <= '9' || k.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    field.setEditable(true);
                } else {
                    field.setEditable(false);
                }
            }
        });
    }

    /*
     * Check if each value is within the limit
     * if the value is not within the limit then set value to false
     * elsewise set it to true
     */
    public void validValues(){
        if(Double.valueOf(heightField.getText()) > 300 || Double.valueOf(angleField.getText()) > 90 || Double.valueOf(velocityField.getText()) > 100){
            valid = false;
        }else{
            valid = true;
        }
    }

    /*
     * call validValues to see if the field number is within the limit
     * get values from each text field
     * set start to true
     * set the values of x, y, and time, to original values
     * start the timer
     * if value button is pressed set the labels to the values and only display 2 decimal points
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        validValues();

        if (e.getSource() == startButton && valid) {
            Ball.height = Double.valueOf(heightField.getText());
            Ball.angle = Double.valueOf(angleField.getText());
            Ball.velocity = Double.valueOf(velocityField.getText());
            start = true;
            ProjectileMotion.yInitial = (int) (650 - Ball.height - ProjectileMotion.radius);
            ProjectileMotion.time = 0;
            ProjectileMotion.maxHeight = ProjectileMotion.y;
            ProjectileMotion.startTimer();
        }

        if(e.getSource() == valueButton && start == false){
            displacement.setText("Displacement: " + String.format("%.2f", ProjectileMotion.displacement) + " units       ");
            time.setText("Time in air: " + String.format("%.2f", ProjectileMotion.airTime) + "s        ");
            maxHeight.setText("Max Height: " + String.format("%.2f", ProjectileMotion.maxHeight) + " units      ");
            finalVelocity.setText("Final Velocity: " + String.format("%.2f", ProjectileMotion.finalVelocity) + " units/s        ");
        }
    }

    public static void main(String[] args) {
        new ProjectileFrame();
    }
}
