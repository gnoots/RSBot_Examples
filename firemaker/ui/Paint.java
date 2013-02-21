package firemaker.ui;

import firemaker.data.Constants;
import firemaker.interactions.Firemaking;
import firemaker.utils.Logs;
import org.powerbot.game.api.methods.input.Mouse;

import java.awt.*;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */

public class Paint {

    // The settings for antialiasing (smoothing) on our shapes and text. While not necessary, it looks much nicer.
    private static final RenderingHints ANTIALIASING = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // The fonts we will be using. It's recommended to use fonts that will be compatible with all operating systems.
    private static final Font LARGE_FONT = new Font("Lucida Sans Unicode", 0, 20);
    private static final Font SMALL_FONT = new Font("Lucida Sans Unicode", 0, 10);

    // A custom color for the rectangle. The max alpha is 255, 225 will allow some transparency.
    private static final Color BLACK = new Color(0, 0, 0, 225);

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
        g.fillRoundRect(20, 90, 150, 180, 10, 10);

        // Draws our large title of "Firemaker".
        g.setFont(LARGE_FONT);
        g.setColor(Color.ORANGE);
        g.drawString("Firemaker", 25, 110);

        // Draws some information on top of our rectangle in a smaller font.
        g.setFont(SMALL_FONT);
        g.setColor(Color.WHITE);
        g.drawString("Run Time: " + Constants.RUN_TIME.toElapsedString(), 25, 125);
        g.drawString("Logs burnt: " + Firemaking.getLogsBurnt(), 25, 140);
        g.drawString("Spirits caught: " + Firemaking.getSpiritsCaught(), 25, 155);

        /*
        Here are our buttons. As you can see, they use the drawFireButton method from PaintMethods,
        which allows us to easily draw several buttons and have it remain readable.
         */
        PaintMethods.drawFireButton("Normal logs", 25, 170, 10, 10, Logs.NORMAL, SMALL_FONT, g);
        PaintMethods.drawFireButton("Oak logs", 25, 185, 10, 10, Logs.OAK, SMALL_FONT, g);
        PaintMethods.drawFireButton("Willow logs", 25, 200, 10, 10, Logs.WILLOW, SMALL_FONT, g);
        PaintMethods.drawFireButton("Maple logs", 25, 215, 10, 10, Logs.MAPLE, SMALL_FONT, g);
        PaintMethods.drawFireButton("Yew logs", 25, 230, 10, 10, Logs.YEW, SMALL_FONT, g);
        PaintMethods.drawFireButton("Magic logs", 25, 245, 10, 10, Logs.MAGIC, SMALL_FONT, g);

    }

}
