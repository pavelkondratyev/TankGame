import java.awt.*;
import javax.swing.*;

public class Missile {
        double x = 0;
        double y = 0;
        double xa = 0;
        double ya = 0;
        double speed = 3;
        private Game game;


        public Missile(Game game, double xSpawn, double ySpawn, double xdir, double ydir) {
            x = xSpawn;
            y = ySpawn;
            xa = xdir * speed;
            ya = ydir * speed;
            this.game= game;
        }

        void update() {
            int projx = (int) Math.round((x + xa)) / 32;
            int projy = (int) Math.round((y - ya)) / 32;
            double cx = game.map.getTileMap()[projy][projx].getX();
            double cy = game.map.getTileMap()[projy][projx].getY();
            if (!game.map.isTileSolid(projx, projy)) {
                x += xa;
                y -= ya;
            }
            if (game.map.isTileSolid(projx, projy)) {
                if (Math.abs(xa) < Math.abs(cx - x) || Math.abs(ya) < Math.abs(cy - y)) {
                    xa = Math.abs(xa * (cx - x)) / x;
                    ya = Math.abs(ya * (cy - y)) / y;
                }

            if (x < cx) {
                x -= xa;
                y -= ya;
            }
            if (x > cx) {
                    x -= xa;
                    y -= ya;
                }
            else if (y <= cy ) {
                    x += xa;
                    y += ya;
                }
            else if (y >= cy ) {
                    x += xa;
                    y += ya;
                }
            }
        }




        public void draw(Graphics2D g) {
            g.fillOval((int)(x),(int)(y), 10, 10);
        }
    }
