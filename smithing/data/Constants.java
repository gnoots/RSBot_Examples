package smithing.data;

import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Constants {

    /*
        Tiles for checking our location and the path to walk from the furnace to the bank.
     */

    public static final Tile BANK_TILE = new Tile(3270 + Random.nextInt(-2, 2), 3166 + Random.nextInt(-2, 2), 0);
    public static final Tile FURNACE_TILE = new Tile(3276 + Random.nextInt(-1, 1), 3190 + Random.nextInt(-1, 1), 0);

    public static final Tile[] WALKING_PATH = new Tile[] { new Tile(3270, 3167, 0), new Tile(3275, 3169, 0), new Tile(3275, 3174, 0),
            new Tile(3275, 3179, 0), new Tile(3277, 3184, 0), new Tile(3279, 3189, 0),
            new Tile(3274, 3190, 0) };

    // Timer that will begin running when the script is starting, counting up from 0 in milliseconds
    public static final Timer RUN_TIME = new Timer(0);

}
