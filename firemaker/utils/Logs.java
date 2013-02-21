package firemaker.utils;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public enum Logs {

    NORMAL(1511, 70755, "Logs"),
    ACHEY(2862, 70756, "Achey tree logs"),
    OAK(1521, 70757, "Oak logs"),
    WILLOW(1519, 70758, "Willow logs"),
    TEAK(6333, 70759, "Teak Logs"),
    ARCTIC_PINE(10810, 70760, "Arctic pine logs"),
    MAPLE(1517, 70761, "Maple logs"),
    MAHOGANY(6332, 70762, "Mahogany logs"),
    EUCALYPTUS(12581, 70763, "Eucalyptus logs"),
    YEW(1515, 70764, "Yew logs"),
    MAGIC(1513, 70765, "Magic logs");

    int itemId;
    int fireId;
    String name;

    Logs(int itemId, int fireId, String name) {
        this.itemId = itemId;
        this.fireId = fireId;
        this.name = name;
    }

    /**
     * Returns the item id of the selected log.
     * @return the item id of the selected log.
     */

    public int getItemId() {
        return itemId;
    }

    /**
     * Returns the fire id that the selected log creates.
     * @return the fire id that the selected log creates.
     */

    public int getFireId() {
        return fireId;
    }

    /**
     * Returns the name of the log.
     * @return the name of the log.
     */

    public String getName() {
        return name;
    }

    /**
     * Returns a string with details of the specified log.
     * @return a string with details of the specified log.
     */

    public String toString() {
        return getName() + " | ID: " + getItemId() + " | Fire ID: " + getFireId();
    }

}
