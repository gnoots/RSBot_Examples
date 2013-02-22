package fisher.utils;

import org.powerbot.game.api.methods.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.awt.Graphics;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class MouseTrail {

    private final ArrayList<Point> mousePoints = new ArrayList<>();

    /**
     * Adds the point to the point array.
     * @param p the point to add.
     */

    private void add(Point p) {
        mousePoints.add(p);
    }

    /**
     * Removes the point at the selected index in the point array.
     * @param index the index of the point to remove.
     */

    private void remove(int index) {
        if (mousePoints.size() > 0) {
            mousePoints.remove(index);
        }
    }

    /**
     * Draws the mouse trail. Also includes adding and removing of points.
     * @param color the color of the trail.
     * @param length the length of the trail.
     * @param width the width of the trail.
     * @param fade the length at which the trail starts to fade.
     * @param g the graphics instance.
     */

    public void draw(Color color, int length, int width, int fade, Graphics g) {
        Graphics2D g1 = (Graphics2D)g;
        // If the array is longer than the length, we remove the oldest point.
        if (mousePoints.size() > length) {
            remove(0);
        } else {
            // Otherwise, add another point of the current location of the mouse.
            Point m = Mouse.getLocation();
            add(m);
        }
        // Loop through all the points in the array...
        for (int i = 0; i < mousePoints.size()-1; i++) {
            // Create two points which we'll draw a line between.
            Point p = mousePoints.get(i);
            Point p2 = mousePoints.get(i+1);
            int alpha;
            // This handles the fading of the line. While not necessary, it looks a bit nicer.
            if (fade != 0) {
                if (255-((length-i)*fade) >= 0) {
                    alpha = 255-((length-i)*fade);
                } else {
                    alpha = 0;
                }
            } else {
                alpha = 255;
            }
            // Set our color to the color specified in the parameter, but override the alpha with our faded one.
            g1.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
            // Sets the width of the line (and therefore the trail)
            g1.setStroke(new BasicStroke(width));
            // Draws a line between the two points.
            g1.drawLine(p.x, p.y, p2.x, p2.y);
            // Sets the stroke width back to 1.
            g1.setStroke(new BasicStroke(1));
        }
        /*
        Then we move onto the next pair of points to draw a line between.

        In this implementation of a mouse trail, the lines are drawn between points to give the illusion
        of a singular, curved line. By moving the mouse very quickly you can see the trail become sharp instead of
        smooth. This is because the Paint only updates roughly 20 times a second and will begin to miss some locations
        of the mouse if you move it too fast. This is only a minor drawback, as it gives a decent result and is fairly
        easy to understand.
         */
    }

}

