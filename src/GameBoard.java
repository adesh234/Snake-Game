import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameBoard extends JPanel implements ActionListener {
    int height = 400;
    int width = 400;
    int x[] = new int[height*width];
    int y[] = new int[height*width];
    int dots;
    int dot_size = 10;
    int applex=100,appley=100;
    Image apple,body,head;

    boolean leftdir = true;
    boolean rightdir = false;
    boolean updir = false;
    boolean downdir = false;

    Timer timer;
    int delay = 100;
    int rand_pos = 39;
    boolean inGame = true;

    public GameBoard(){
        addKeyListener(new Tadapter());
        setFocusable(true);
        setPreferredSize(new Dimension(width,height));
        setBackground(Color.BLACK);
        loadimages();
        initgame();
    }
    public void initgame(){
        dots = 3;
        for(int i=0 ; i<dots ; i++){
            x[i] = 150+i*dot_size;
            y[i] = 150;
        }
        timer = new Timer(delay, this);
        timer.start();
    }
    private void loadimages(){

        ImageIcon imageapp = new ImageIcon("src/resources/apple.png");
        apple = imageapp.getImage();

        ImageIcon imagehead = new ImageIcon("src/resources/head.png");
        head = imagehead.getImage();

        ImageIcon imagebody = new ImageIcon("src/resources/dot.png");
        body = imagebody.getImage();
    }
    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        if(inGame){
            graphics.drawImage(apple, applex, appley, this);
            for(int i=0 ; i<dots ;i++){
                if(i==0){
                    graphics.drawImage(head,x[0],y[0],this);
                }
                else{
                    graphics.drawImage(body,x[i],y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameover(graphics);
        }
    }
    private void move(){
        for(int i=dots-1 ; i>0 ; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftdir){
            x[0] -= dot_size;
        }
        if(rightdir){
            x[0] += dot_size;
        }
        if(updir){
            y[0] += dot_size;
        }
        if(downdir){
            y[0] -= dot_size;
        }
    }
    public void locateapple(){
        int r = (int) (Math.random()*(rand_pos));
        applex = r*dot_size;

        r = (int)(Math.random()*(rand_pos));
        appley = r*dot_size;
    }
    private void checkapple(){
        if(x[0]==applex && y[0]==appley){
            dots++;
            locateapple();
        }
    }
    private void checkcollision(){
        if(x[0]<0){
            inGame = false;
        }
        if(x[0]>=width)
            inGame = false;

        if(y[0]<0)
            inGame = false;

        if(y[0]>=height)
            inGame = false;

        for(int i=dots-1 ; i>=3 ;i--){
            if(x[0]==x[i] && y[0]==y[i]){
                inGame = false;
                break;
            }
        }
    }
    private void gameover(Graphics graphics){
        String msg = "GAME OVER";
        Font small = new Font("Helvetica",Font.BOLD,14);
        FontMetrics metrics = getFontMetrics(small);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.setFont(small);
        graphics.drawString(msg, (width-metrics.stringWidth(msg))/2, height/2);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame){
            checkapple();
            checkcollision();
            move();
        }
        repaint();
    }
    public class Tadapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if((key == KeyEvent.VK_LEFT)&&(!rightdir)){
                leftdir = true;
                updir = false;
                downdir = false;
            }
            if((key == KeyEvent.VK_RIGHT)&&(!leftdir)){
                rightdir = true;
                updir = false;
                downdir = false;
            }
            if((key == KeyEvent.VK_UP)&&(!downdir)){
                updir = true;
                leftdir = false;
                rightdir = false;
            }
            if((key == KeyEvent.VK_DOWN)&&(!updir)){
                downdir = true;
                leftdir = false;
                rightdir = false;
            }
        }
    }
}
