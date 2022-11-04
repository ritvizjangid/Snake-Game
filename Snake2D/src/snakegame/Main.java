package snakegame;
import javax.swing.JFrame;
import java.awt.*;

public class Main
{
    public static void main(String[] args)
    {
        JFrame frame=new JFrame("Snake Game");
        frame.setBounds(10,10,905,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel panel=new GamePanel();
        panel.setBackground(Color.darkGray);
        frame.add(panel);
        frame.setVisible(true);
    }
}