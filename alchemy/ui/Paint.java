package alchemy.ui;

import alchemy.interactions.Magic;
import alchemy.utils.Experience;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;

import java.awt.*;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */

public class Paint {

    private static final Timer RUN_TIME = new Timer(0);

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
        g.fillRoundRect(20, 90, 170, 105, 10, 10);

        // Draws our large title of "Alchemy".
        g.setFont(LARGE_FONT);
        g.setColor(Color.YELLOW);
        g.drawString("Alchemy", 25, 110);

        // Draws some information on top of our rectangle in a smaller font.
        g.setFont(SMALL_FONT);
        g.setColor(Color.WHITE);
        g.drawString("Run Time: " + RUN_TIME.toElapsedString(), 25, 125);
        // The total amount of profit and alchemies completed.
        g.drawString("Alchemy profit: " + Magic.getAlchemyProfit(), 25, 140);
        g.drawString("Alchemies done: " + Magic.getAlchemiesDone(), 25, 155);
        // Here we use the methods from the Experience class to get the XP/H and TTL for Magic.
        g.drawString("Experience per hour: " + Experience.getXpHr(Skills.MAGIC), 25, 170);
        g.drawString("Time till level: " + Experience.getTTL(Skills.MAGIC), 25, 185);

    }
}
