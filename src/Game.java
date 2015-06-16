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

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 640;

    int x = 0;
    int y = 0;
    Boolean isRunning, displayMenu;
    Graphics2D g;
    BufferedImage image;
    Thread thread;
    private int FPS = 120; // frames per second
    private long targetTime = 1000 / FPS;

    ArrayList<Missile> m;
    TankOne t;
    TankTwo s;
    Map map;

    public Game(){
        super();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    private void update() {
        //m.update();
        s.update();
        t.update();
        {
            for (Missile mis : m)
                mis.update();
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
        for (Missile mis : m)
            mis.draw(g);
    }

   /* public void gameOver() {
        if (t.x == m.x && t.y == m.y) {
            JOptionPane.showMessageDialog(this, "GG", "GG", JOptionPane.YES_NO_OPTION);
            System.exit(ABORT);
        }
    }*/

    public void run(){
        init();
        long startTime;
        long elapsedTime;
        long waitTime;
        while(isRunning){
            startTime = System.nanoTime();
            update();
            draw();
            //gameOver();
            drawToScreen();

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
    }
    public void init(){
        isRunning = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        //m = new Missile(this);
        map = new Map();
        map.drawTiles(g);
        t = new TankOne(this, map);
        s = new TankTwo(this, map);
        m = new ArrayList<Missile>();
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


