package firemaker.ui;

import firemaker.interactions.Firemaking;
import firemaker.utils.Logs;

import java.awt.*;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class PaintMethods {

    /*
    Here we have a simple method to handle drawing the text, shapes and colors for our buttons.
    This makes it easier than writing every line manually for each button you want to show.
     */

    public static void drawFireButton(String text, int x, int y, int w, int h, Logs log, Font font, Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString(text, (x+w)+5, (y+font.getSize())-(font.getSize()/5));
        /*
         We set the color of the box using a Ternary Operator - if the id of the current selected log matches the id
         of the log specified in the button parameters we make the button green, otherwise it's red.
          */
        g.setColor(Firemaking.getSelectedLogs().getItemId() == log.getItemId() ? Color.GREEN.brighter() : Color.RED);
        g.fillRect(x, y, w, h);
    }

}
