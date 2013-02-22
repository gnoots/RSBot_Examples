package fisher.data;

import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Constants {

    /*
    Bank areas and path. For use in conjunction with Lodestone.
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

    /*
    Fishing areas and their respective paths.
     */

    public static final Area FLY_FISHING_AREA = new Area(new Tile(3097, 3438, 0), new Tile(3112, 3438, 0), new Tile(3112, 3428, 0),
            new Tile(3107, 3424, 0), new Tile(3107, 3421, 0), new Tile(3097, 3421, 0));

    public static final Tile[] FLY_FISHING_PATH = new Tile[] { new Tile(3092, 3492, 0), new Tile(3087, 3491, 0), new Tile(3083, 3488, 0),
            new Tile(3080, 3484, 0), new Tile(3078, 3479, 0), new Tile(3078, 3474, 0),
            new Tile(3078, 3469, 0), new Tile(3081, 3465, 0), new Tile(3086, 3464, 0),
            new Tile(3088, 3459, 0), new Tile(3088, 3454, 0), new Tile(3087, 3449, 0),
            new Tile(3088, 3444, 0), new Tile(3091, 3440, 0), new Tile(3095, 3437, 0),
            new Tile(3099, 3434, 0), new Tile(3104, 3432, 0) };
}
