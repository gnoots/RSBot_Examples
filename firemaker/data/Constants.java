package firemaker.data;

import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Constants {

    // Here we have our area that we will be using. Remember that fires can't be lit near a bank, so keep your areas a small distance away.

    public static final Area GRAND_EXCHANGE_SOUTHWEST = new Area(new Tile(3139, 3475, 0), new Tile(3153, 3475, 0), new Tile(3153, 3468, 0),
            new Tile(3143, 3468, 0), new Tile(3139, 3471, 0));

    // Timer that will begin running when the script is starting, counting up from 0 in milliseconds
    public static final Timer RUN_TIME = new Timer(0);

}
