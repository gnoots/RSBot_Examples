package miner.data;

import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Constants {

    /*
    Areas and paths to help find our way to the bank or to our chosen ores. BANK_AREA is the Edgeville bank.
     */

    public static final Area BANK_AREA = new Area(new Tile(3087, 3502, 0), new Tile(3100, 3502, 0), new Tile(3100, 3485, 0),
            new Tile(3087, 3485, 0));

    public static final Area TO_BANK_AREA = new Area(new Tile(3058, 3511, 0), new Tile(3074, 3511, 0), new Tile(3074, 3506, 0),
            new Tile(3100, 3506, 0), new Tile(3100, 3487, 0), new Tile(3089, 3487, 0),
            new Tile(3089, 3489, 0), new Tile(3081, 3489, 0), new Tile(3081, 3496, 0),
            new Tile(3058, 3496, 0));

    public static final Tile[] TO_BANK = new Tile[] { new Tile(3068, 3505, 0), new Tile(3073, 3503, 0), new Tile(3077, 3500, 0),
            new Tile(3082, 3500, 0), new Tile(3087, 3500, 0), new Tile(3092, 3501, 0),
            new Tile(3094, 3496, 0) };

    public static final Area LUMBRIDGE_SWAMP_MINE = new Area(new Tile(3219, 3154, 0), new Tile(3235, 3154, 0), new Tile(3235, 3143, 0),
            new Tile(3219, 3143, 0));

    public static final Area TO_MINE_AREA = new Area(new Tile(3229, 3227, 0), new Tile(3241, 3227, 0), new Tile(3241, 3204, 0),
            new Tile(3252, 3204, 0), new Tile(3252, 3188, 0), new Tile(3245, 3188, 0),
            new Tile(3245, 3161, 0), new Tile(3237, 3149, 0), new Tile(3229, 3149, 0));

    public static final Tile[] TO_MINE_PATH = new Tile[] { new Tile(3232, 3220, 0), new Tile(3232, 3215, 0), new Tile(3234, 3210, 0),
            new Tile(3235, 3205, 0), new Tile(3237, 3200, 0), new Tile(3241, 3196, 0),
            new Tile(3242, 3191, 0), new Tile(3241, 3186, 0), new Tile(3239, 3181, 0),
            new Tile(3238, 3176, 0), new Tile(3237, 3171, 0), new Tile(3237, 3166, 0),
            new Tile(3235, 3161, 0), new Tile(3233, 3156, 0), new Tile(3230, 3152, 0),
            new Tile(3227, 3148, 0) };

    // Timer that will begin running when the script is starting, counting up from 0 in milliseconds
    public static final Timer RUN_TIME = new Timer(0);

}
