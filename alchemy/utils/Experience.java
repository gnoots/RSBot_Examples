package alchemy.utils;

import org.powerbot.game.api.util.SkillData;
import org.powerbot.game.api.util.Time;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Experience {

    // To use SkillData, we need to create a new instance.
    static SkillData sd = new SkillData();

    /**
     * Returns a formatted string for the time until level.
     * @param skill the skill to obtain the TTL for.
     * @return a formatted string for the time until level in the specified skill.
     */
    public static String getTTL(int skill) {
        return formatTTL(sd.timeToLevel(SkillData.Rate.HOUR, skill));
    }

    /**
     * Returns a formatted string for the experience per hour.
     * @param skill the skill to obtain the exp per hour for.
     * @return a formatted string for the experience per hour in the specified skill.
     */
    public static String getXpHr(int skill) {
        return formatXpHr(sd.experience(SkillData.Rate.HOUR, skill));
    }

    /*
    While formatting the TTL and exp/h isn't necessary, 03:30:14 and 76.4K xp/h
    is much more readable than 12614000 and 76400.
     */
    private static String formatTTL(long num) {
        return Time.format(num);
    }

    private static String formatXpHr(int num) {
        if (num < 1000) {
            return Integer.toString(num) + " xp/h";
        } else {
            if (num < 1000000) {
                return num / 1000 + "." + (num % 1000) / 100 + "K xp/h";
            }
            return num / 1000000 + "." + (num % 1000000) / 10000 + "M xp/h";
        }
    }
}
