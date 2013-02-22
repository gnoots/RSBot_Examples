package fisher.ui;

import fisher.interactions.Fishing;
import fisher.utils.GraphicsMethods;
import fisher.utils.MouseTrail;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.Player;

import java.awt.*;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */

public class Paint {

    private static Timer RUN_TIME = new Timer(0);

    // The settings for antialiasing (smoothing) on our shapes and text. While not necessary, it looks much nicer.
    private static final RenderingHints ANTIALIASING = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // The fonts we will be using. It's recommended to use fonts that will be compatible with all operating systems.
    private static final Font LARGE_FONT = new Font("Lucida Sans Unicode", 0, 20);
    private static final Font SMALL_FONT = new Font("Lucida Sans Unicode", 0, 10);

    // A custom color for the rectangle. The max alpha is 255, 225 will allow some transparency.
    private static final Color BLACK = new Color(0, 0, 0, 225);

    private static MouseTrail trail = new MouseTrail();

    public static void onRepaint(Graphics g1) {

        // Anything we paint will be drawn in order. Items at the start will be drawn under items at the end.

        // Create a new Graphics2D object so we can draw our items.
        Graphics2D g = (Graphics2D)g1;
        // Enables the antialiasing.
        g.setRenderingHints(ANTIALIASING);

        // Draws a "plus" sign at the location of the mouse.
        g.setColor(Color.CYAN);
        g.drawLine(Mouse.getX()-5, Mouse.getY(), Mouse.getX()+5, Mouse.getY());
        g.drawLine(Mouse.getX(), Mouse.getY()-5, Mouse.getX(), Mouse.getY()+5);

        // Draws us a rectangle for the background of our paint information.
        g.setColor(BLACK);
        g.fillRoundRect(20, 90, 150, 75, 10, 10);

        // Draws our large title of "Fisher".
        g.setFont(LARGE_FONT);
        g.setColor(Color.CYAN.brighter());
        g.drawString("Fisher", 25, 110);

        // Draws some information on top of our rectangle in a smaller font.
        g.setFont(SMALL_FONT);
        g.setColor(Color.WHITE);
        g.drawString("Run Time: " + RUN_TIME.toElapsedString(), 25, 125);
        g.drawString("Fish caught: " + Fishing.getFishCaught(), 25, 140);
        g.drawString("Total profit: " + Fishing.getTotalProfit(), 25, 155);

        /*
         Here we do some fancier stuff.
         Check out the methods in utils/GraphicsMethods for an indepth explanation
         on how they achieve the effect.
          */

        // Draws our mouse trail with the specified color, length, width and fade.
        trail.draw(Color.CYAN, 10, 1, 35, g);
        // Fills our player with the specified color.
        GraphicsMethods.fillEntity(Players.getLocal(), Color.GREEN.brighter(), g);
        // Draws a box around the tile of nearest fishing spot.
        if (Fishing.getFishSpot() != null && Fishing.getFishArea().contains(Players.getLocal())) {
            g.setColor(Color.YELLOW);
            // This sets the width of the line to the specified value.
            g.setStroke(new BasicStroke(2));
            Fishing.getFishSpot().getLocation().draw(g);
        }
        // Sets the stroke width back to 1.
        g.setStroke(new BasicStroke(1));
        if (Fishing.getFishSpot() != null) {
            // Draws a line to the nearest fishing spot on our minimap.
            GraphicsMethods.drawLineToNpc(Players.getLocal(), Fishing.getFishSpot(), Color.RED, g);
            // Draws the name of an NPC (our fishing spot) on the minimap.
            GraphicsMethods.drawNpcNameOnMap(Fishing.getFishSpot(), Color.CYAN, g);
        }
        // Draws the name and level above each player on the screen.
        for (Player p : Players.getLoaded()) {
            if (p.isOnScreen()) {
                GraphicsMethods.drawNameAbove(p.getName() + " Level: " + p.getLevel(), p, Color.RED, SMALL_FONT, g);
            }
        }
    }
}
