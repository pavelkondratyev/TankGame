import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;


@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable, KeyListener{

    public static final int WIDTH = 1024; //x
    public static final int HEIGHT = 640; //y

    int x = 0;
    int y = 0;
    Boolean isRunning, yes;
    Graphics2D g;
    BufferedImage image;
    Thread thread;
    private int FPS = 120; // frames per second
    private long targetTime = 1000 / FPS;

    LinkedList<Missile> m;
    TankOne t;
    TankTwo s;
    LinkedList<Map> maps;
    Map map;
    private int TankOneWins = 0;
    private int TankTwoWins = 0;
    private Boolean TankOneAlive, TankTwoAlive;

    public Game(){
        super();
        TankOneWins = 0;
        TankTwoWins = 0;
        yes = true;
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    private void update() {
        //m.update();
        s.update();
        t.update();
        {
            for( int i = 0; i < m.size(); i++){
                if(m.get(i).getBounces() == 5){
                    m.remove(i);
                }
            }
            try {
                for (Missile mis : m)
                    mis.update();
            }
            catch (Exception e){

            }

        }
    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    public void draw() {

        //g.fillRect(0, 0, WIDTH, HEIGHT);  //TEMP while we work on background
        //g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        map.drawTiles(g);
        //m.draw(g);
        s.draw(g);
        t.draw(g);
        try {
            for (Missile mis : m)
                mis.draw(g);
        }
        catch (Exception e){

        }
    }

    public Boolean checkTankTwo(){
        Boolean dead = false;
        try {
            for (Missile mis : m) {
                if ((int) s.x / 32 == (int) mis.x / 32 && (int) s.y / 32 == (int) mis.y / 32) {
                    dead = true;
                }
            }
        }
        catch (Exception e){

        }
        return dead;
    }

    public Boolean checkTankOne(){
        Boolean dead = false;
        try {
            for (Missile mis : m) {
                if ((int) t.x / 32 == (int) mis.x / 32 && (int) t.y / 32== (int) mis.y / 32) {
                    dead = true;
                }
            }
        }
        catch (Exception e){

        }
        return dead;
    }

     public void gameOver() {
         if(checkTankTwo()) {
             TankTwoAlive = false; // green tank dies
         }
         if(checkTankOne()) {
             TankOneAlive = false;// red tank dies
         }
     }

    public void run() {
        while (yes) {
            init();
            long startTime;
            long elapsedTime;
            long waitTime;
            while ( TankTwoAlive && TankOneAlive) {
                startTime = System.nanoTime();
                draw();
                update();
                drawToScreen();
                gameOver();
                elapsedTime = System.nanoTime() - startTime;
                waitTime = targetTime - elapsedTime / 100000;
                try {
                    if (waitTime < 0)
                        waitTime = 0;
                    Thread.sleep(waitTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(TankOneAlive){
                System.out.println("Green Tank wins this round");
                TankOneWins ++;
            }
            if(TankTwoAlive){
                System.out.println("Red Tank wins this round");
                TankTwoWins ++;
            }
            System.out.println( "Green Tank: "+ TankOneWins);
            System.out.println( "Red Tank: "+ TankTwoWins);
            System.out.println( "=============================");

        }
    }
    public void init(){
        isRunning = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        //m = new Missile(this);
        Random r = new Random();
        maps = new LinkedList<Map>();
        for (int i = 0; i < 5; i++)
            maps.add(new Map("map" + i + ".dat"));
        map = maps.get(r.nextInt(5));
        map.drawTiles(g);
        t = new TankOne(this, map);
        s = new TankTwo(this, map);
        TankOneAlive = true;
        TankTwoAlive = true;
        m = new LinkedList<Missile>();
    }

    private void drawToScreen(){
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        t.keyReleased(e);
        s.keyReleased(e);
    }


    public void keyPressed(KeyEvent e) {
        t.keyPressed(e);
        s.keyPressed(e);
        //if e.getKeyCode() == KeyEvent.VK_SPACE
    }

}


