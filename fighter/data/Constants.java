package fighter.data;

import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class Constants {

    /*
    Areas and paths to help find our way to the bank or to our chosen NPC. BANK_AREA is the Edgeville bank.
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

    public static final Tile[] TO_GOBLINS = new Tile[] { new Tile(3236, 3221, 0), new Tile(3233, 3225, 0), new Tile(3230, 3229, 0),
            new Tile(3226, 3232, 0), new Tile(3223, 3236, 0), new Tile(3221, 3241, 0),
            new Tile(3218, 3245, 0), new Tile(3213, 3247, 0), new Tile(3208, 3247, 0),
            new Tile(3203, 3247, 0), new Tile(3198, 3247, 0), new Tile(3193, 3245, 0),
            new Tile(3188, 3244, 0), new Tile(3183, 3245, 0) };

    public static final Area GOBLIN_AREA = new Area(new Tile(3173, 3258, 0), new Tile(3189, 3258, 0), new Tile(3189, 3249, 0),
            new Tile(3197, 3249, 0), new Tile(3197, 3233, 0), new Tile(3173, 3233, 0));

    public static final Area TO_GOBLINS_AREA = new Area(new Tile(3186, 3259, 0), new Tile(3232, 3259, 0), new Tile(3232, 3236, 0),
            new Tile(3238, 3226, 0), new Tile(3238, 3213, 0), new Tile(3225, 3213, 0),
            new Tile(3225, 3228, 0), new Tile(3218, 3236, 0), new Tile(3186, 3236, 0));

    // Timer that will begin running when the script is starting, counting up from 0 in milliseconds
    public static final Timer RUN_TIME = new Timer(0);

}
