package fighter.utils;

import org.powerbot.game.api.wrappers.Entity;

import java.awt.*;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */

public class Methods {

    /**
     * Returns true if Entity is on screen.
     * @param e entity to check - NPC, GroundItem, SceneObject etc.
     * @return true if Entity is on screen.
     */

    public static boolean isOnScreen(Entity e) {
        final Rectangle view = new Rectangle(4, 54, 513, 258);
        if (e != null) {
            for (Polygon p : e.getBounds()) {
                if (view.contains(p.getBounds())) {
                    return true;
                }
            }
        }
        return false;
    }

}
