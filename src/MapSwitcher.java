/*
import java.util.ArrayList;
import java.awt.*;
import java.util.LinkedList;
//Hi Inan
public class MapSwitcher {

    private LinkedList<Map> Maps;
    private PlayerOne p1;
    private PlayerTwo p2;
    private int currentLevel;
    private Graphics2D g;
    public static final int MENU = 0;
    public static final int LEVEL1 = 1;


    public MapSwitcher(PlayerOne p,PlayerTwo q, Graphics2D graphics) {
        g = graphics;
        Maps = new LinkedList<Map>();
        Maps.add(new Menu(this));
        p1 = p;
        p2 = q;
        Maps.add(new Map(p, q, g));
        //Maps.add(new Credits(this));
        //currentLevel = MENU;
        //Game.menu = true;

    }

    public LinkedList<Map> getLevels(){
        return Maps;
    }
/*
    public void setlevel(int level){
        currentLevel = level;
        //player.setXY(0,0);
        Maps.get(currentLevel).init();
    }

    public void update() {
        Maps.get(currentLevel).update();
        if (Maps.get(currentLevel).getNextLevel())
            setlevel(currentLevel+1);
    }
*/
/*
    public void draw(java.awt.Graphics2D g) {
        if (Maps.get(currentLevel) != null)
            Maps.get(currentLevel).draw(g);
        else {
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        }
    }

    public void keyPressed(int k) {
        if (Maps.get(currentLevel) instanceof Menu)
            Maps.get(currentLevel).keyPressed(k);
        else
            (Maps.get(currentLevel)).keyPressed(k);

    }

    public Map getCurrentLevel() {
        return Maps.get(currentLevel);
    }

    public PlayerOne getPlayer1() {
        return p1;
    }



    public void keyReleased(int k) {
        Maps.get(currentLevel).keyReleased(k);
      }
    }
*/
