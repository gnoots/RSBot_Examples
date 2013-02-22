package fisher.utils;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.wrappers.Entity;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.interactive.Player;

import java.awt.*;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class GraphicsMethods {

    /**
     * Fills the specified entity with the specified color.
     * @param e the entity.
     * @param c the color.
     * @param g the graphics instance.
     */

    public static void fillEntity(Entity e, Color c, Graphics g) {
        g.setColor(c);
        // Loops through every polygon that the entity is made from and fills it with a color.
        for (Polygon p : e.getBounds()) {
            g.fillPolygon(p);
        }
    }

    /**
     * Draws the wireframe of the specified entity with the specified color.
     * @param e the entity.
     * @param c the color.
     * @param g the graphics instance.
     */

    public static void drawEntity(Entity e, Color c, Graphics g) {
        g.setColor(c);
        // The same as above, but this time we just draw the outline of the polygon.
        for (Polygon p : e.getBounds()) {
            g.drawPolygon(p);
        }
    }

    /**
     * Draws a line from the the player to the npc on the minimap.
     * @param player the player.
     * @param npc the npc.
     * @param c the color.
     * @param g the graphics instance.
     */

    public static void drawLineToNpc(Player player, NPC npc, Color c, Graphics g) {
        g.setColor(c);
        /*
         Here we need to get the location of the player and NPC on the minimap.
         This is made easy by a method in the API that allows us to convert a location
         in the world to a point for use on our screen.
          */
        Point p1 = Calculations.worldToMap(player.getLocation().getX(), player.getLocation().getY());
        Point p2 = Calculations.worldToMap(npc.getLocation().getX(), npc.getLocation().getY());

        // We then draw a  line from the x and y values of the player point to the x and y values of the NPC point.
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    /**
     * Draws the name of the npc on the minimap.
     * @param n the npc.
     * @param c the color.
     * @param g the graphics class.
     */

    public static void drawNpcNameOnMap(NPC n, Color c, Graphics g) {
        g.setColor(c);
        // As above, we get the map point of the NPCs location in the world.
        Point p = Calculations.worldToMap(n.getLocation().getX(), n.getLocation().getY());

        // We then draw the name of the NPC with a "here!" on the end, along with a line to help point to it.
        g.drawString(n.getName() + " here!", p.x+6, p.y-6);
        g.drawLine(p.x, p.y, p.x+5, p.y-5);
    }

    /**
     * Draws a string above the specified entity.
     * @param s the string.
     * @param e the entity.
     * @param c the color.
     * @param f the font.
     * @param g the graphics instance.
     */

    public static void drawNameAbove(String s, Entity e, Color c, Font f, Graphics g) {
        FontMetrics fm = g.getFontMetrics(f);
        int stringWidth = fm.stringWidth(s);
        g.setFont(f);
        g.setColor(c);
        // By using FontMetrics as above, we can figure out how long the string is and divide by 2, letting us center a string.
        g.drawString(s, e.getCentralPoint().x-(stringWidth/2), e.getCentralPoint().y-30);
    }

}
