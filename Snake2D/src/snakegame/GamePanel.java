package snakegame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private final int[] snakeXLength=new int[750];
    private final int[] snakeYLength=new int[750];
    private int lengthOfSnake=3;

    private final int[] xPos={25,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private final int[] yPos={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    private final Random random=new Random();
    private int enemyX,enemyY;
    private int score=0;

    private boolean left=false;
    private boolean right=true;
    private boolean up=false;
    private boolean down=false;

    private int moves=0;private boolean gameOver=false;
    private final ImageIcon snakeTitle=new ImageIcon(Objects.requireNonNull(getClass().getResource("snakeTitle.jpg")));
    private final ImageIcon leftMouth=new ImageIcon(Objects.requireNonNull(getClass().getResource("leftMouth.png")));
    private final ImageIcon rightMouth=new ImageIcon(Objects.requireNonNull(getClass().getResource("rightMouth.png")));
    private final ImageIcon upMouth=new ImageIcon(Objects.requireNonNull(getClass().getResource("upMouth.png")));
    private final ImageIcon downMouth=new ImageIcon(Objects.requireNonNull(getClass().getResource("downMouth.png")));
    private final ImageIcon snakeImage=new ImageIcon(Objects.requireNonNull(getClass().getResource("snakeImage.png")));
    private final ImageIcon enemy=new ImageIcon(Objects.requireNonNull(getClass().getResource("enemy.png")));

    private final Timer timer;

    GamePanel(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeys();

        int delay = 100;
        timer=new Timer(delay,this);
        timer.start();

        newEnemy();
    }

    private void setFocusTraversalKeys() {}

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);
        snakeTitle.paintIcon(this, g, 25,11);
        g.setColor(Color.BLACK);
        g.fillRect(25,75,850,575);

        if(moves==0){
            snakeXLength[0]=100;
            snakeXLength[1]=75;
            snakeXLength[2]=50;

            snakeYLength[0]=100;
            snakeYLength[1]=100;
            snakeYLength[2]=100;
        }

        if(left) {
            leftMouth.paintIcon(this,g,snakeXLength[0],snakeYLength[0]);
        }
        if(right) {
            rightMouth.paintIcon(this,g,snakeXLength[0],snakeYLength[0]);
        }
        if(up) {
            upMouth.paintIcon(this,g,snakeXLength[0],snakeYLength[0]);
        }
        if(down) {
            downMouth.paintIcon(this,g,snakeXLength[0],snakeYLength[0]);
        }
        for(int i=1;i<lengthOfSnake;i++){
            snakeImage.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);
        }
        enemy.paintIcon(this,g,enemyX,enemyY);
        if(gameOver){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.BOLD,50));
            g.drawString("Game Over",300,300);
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Press SPACE to Restart",320,350);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN,14));
        g.drawString("Score: "+score,750,30);
        g.drawString("Length: "+lengthOfSnake,750,50);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthOfSnake-1;i>0;i--){
            snakeXLength[i]=snakeXLength[i-1];
            snakeYLength[i]=snakeYLength[i-1];
        }
        if(left){
            snakeXLength[0]-=25;
        }
        if(right){
            snakeXLength[0]+=25;
        }
        if(up){
            snakeYLength[0]-=25;
        }
        if(down){
            snakeYLength[0]+=25;
        }

        if(snakeXLength[0]>850){
            snakeXLength[0]=25;
        }
        if(snakeXLength[0]<25){
            snakeXLength[0]=850;
        }
        if(snakeYLength[0]>625){
            snakeYLength[0]=75;
        }
        if(snakeYLength[0]<75){
            snakeYLength[0]=625;
        }
        collidesWithEnemy();
        collidesWithBody();
        repaint();
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            restart();
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right)){
            left=true;
            up=false;down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left)){
            right=true;
            up=false;down=false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && (!down)){
            left=false;right=false;
            up=true;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)){
            left=false;right=false;
            down=true;
            moves++;
        }
    }
    public void keyReleased(KeyEvent e){

    }
    public void keyTyped(KeyEvent e){

    }
    private void newEnemy() {
        enemyX=xPos[random.nextInt(33)];
        enemyY=yPos[random.nextInt(22)];

        for(int i=lengthOfSnake-1;i>=0;i--){
            if(snakeXLength[i]==enemyX && snakeYLength[i]==enemyY){
                newEnemy();
            }
        }
    }
    private void collidesWithEnemy(){
        if(snakeXLength[0]==enemyX && snakeYLength[0]==enemyY){
            newEnemy();
            lengthOfSnake++;
            score++;
        }
    }
    private void collidesWithBody(){
        for(int i=lengthOfSnake-1;i>0;i--){
            if(snakeXLength[i]==snakeXLength[0] && snakeYLength[i]==snakeYLength[0]){
                timer.stop();
                gameOver=true;
            }
        }
    }
    private void restart(){
        gameOver=false;
        moves=0;
        score=0;
        left=false;right=true;
        up=false;down=false;
        lengthOfSnake=3;
        timer.start();
        repaint();newEnemy();
    }
}