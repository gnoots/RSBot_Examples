package fisher.utils;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public enum Fish {

    RAW_SHRIMP(317, 327, false, "Net", "Raw shrimps"),
    RAW_SARDINE(327, 327, false, "Bait", "Raw sardine"),
    RAW_HERRING(345, 327, false, "Bait", "Raw herring"),
    RAW_ANCHOVIES(321, 327, false, "Net", "Raw anchovies"),
    RAW_TROUT(335, 328, true, "Lure", "Raw trout"),
    RAW_SALMON(331, 328, true, "Lure", "Raw salmon"),
    RAW_TUNA(359, 324, false, "Harpoon", "Raw tuna"),
    RAW_LOBSTER(377, 324, false, "Cage", "Raw lobster"),
    RAW_SWORDFISH(371, 324, false, "Harpoon", "Raw swordfish");

    int itemId;
    int spotId;
    boolean feather;
    String action;
    String name;

    Fish(int itemId, int spotId, boolean feather, String action, String name) {
        this.itemId = itemId;
        this.spotId = spotId;
        this.feather = feather;
        this.action = action;
        this.name = name;
    }

    /**
     * Returns the item id for the fish.
     * @return the item id for the fish.
     */

    public int getItemId() {
        return itemId;
    }

    /**
     * Returns the fishing spot id for the fish.
     * @return the fishing spot id for the fish.
     */

    public int getSpotId() {
        return spotId;
    }

    /**
     * Returns true if the fish needs feathers.
     * @return true if the fish needs feathers.
     */

    public boolean getFeather() {
        return feather;
    }

    /**
     * Returns the action used by the fishing spot for the fish.
     * @return the action used by the fishing spot for the fish.
     */

    public String getAction() {
        return action;
    }

    /**
     * Returns the name of the fish.
     * @return the name of the fish.
     */

    public String getName() {
        return name;
    }

    /**
     * Returns a string containing information on the fish.
     * @return a string containing information on the fish.
     */

    public String toString() {
        return "Fish: " + getName() + " Item ID: " + getItemId() + " Spot ID: " + getSpotId() + " Action: " + getAction() + ".";
    }

}
