import javafx.scene.transform.Scale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGame extends JPanel implements ActionListener {
    public static JFrame jFrame;
    public static final int SCALE = 32;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static int speed = 30;


    Snake snake = new Snake(6,7,7,7);
    Apple apple = new Apple((int) (Math.random() * SnakeGame.WIDTH - 1), (int) (Math.random() * SnakeGame.HEIGHT - 1));
    Timer timer = new Timer(3000/speed, this);


    public SnakeGame(){
        timer.start();
        addKeyListener(new KeyBoard());
        setFocusable(true);
    }


    public void paint (Graphics g){
        g.setColor(Color.white);
        g.fillRect(0,0, WIDTH * SCALE, HEIGHT * SCALE);

        g.setColor(Color.red);
        g.fillOval(apple.positionX * SCALE + 4, apple.positionY * SCALE + 4, SCALE - 8, SCALE - 8);

        for (int x = 0; x < WIDTH * SCALE; x += 2 * SCALE){
            g.setColor(Color.black);
            g.drawLine(x, 0, x, HEIGHT * SCALE);
        }

        for (int x = SCALE; x <= WIDTH * SCALE - SCALE; x += 2 * SCALE){
            g.setColor(Color.blue);
            g.drawLine(x, 0, x, HEIGHT * SCALE);
        }

        for(int y = 0; y < HEIGHT * SCALE; y += 2 * SCALE){
            g.setColor(Color.black);
            g.drawLine(0, y, WIDTH * SCALE, y);
        }
        for (int y = SCALE; y <= HEIGHT * SCALE - SCALE; y += 2 * SCALE){
            g.setColor(Color.blue);
            g.drawLine(0, y, WIDTH * SCALE, y);
        }

        for (int i = 0; i < snake.length; i++){
            g.setColor(Color.blue);
            g.fillRect(snake.sX[0] * SCALE + 2, snake.sY[0] * SCALE + 2, SCALE - 3, SCALE - 3);
            g.setColor(Color.green);
            g.fillRect(snake.sX[i] * SCALE + 2, snake.sY[i] * SCALE + 2, SCALE - 3, SCALE - 3);
        }
    }


    public static void main(String[] args) {
        jFrame = new JFrame("Welcome");
        jFrame.setSize(WIDTH * SCALE + 6, HEIGHT * SCALE + SCALE);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.add(new SnakeGame());
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snake.move();
        if ((snake.sX[0] == apple.positionX) && snake.sY[0] == apple.positionY) {
            apple.setRandomPosition();
            snake.length++;
        }
        for (int i = 1; i < snake.length; i++){
            if ((snake.sX[i] == apple.positionX) && (snake.sY[i] == apple.positionY) ){
                apple.setRandomPosition();
            }
            if ((snake.sX[0] == snake.sX[i]) && (snake.sY[0] == snake.sY[i])){
                timer.stop();
                JOptionPane.showMessageDialog(null,"Ooops, u lose");
                jFrame.setVisible(false);
                snake.length = 2;
                snake.direction = 0;
                apple.setRandomPosition();
                jFrame.setVisible(true);
                timer.start();
            }
        }
        repaint();
    }
    public class KeyBoard extends KeyAdapter{
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if ((key == KeyEvent.VK_UP) && (snake.direction != 2)) snake.direction = 0;
            if ((key == KeyEvent.VK_DOWN) && (snake.direction != 0)) snake.direction = 2;
            if ((key == KeyEvent.VK_LEFT) && (snake.direction != 1)) snake.direction = 3;
            if ((key == KeyEvent.VK_RIGHT) && (snake.direction != 3)) snake.direction = 1;
        }
    }
}
