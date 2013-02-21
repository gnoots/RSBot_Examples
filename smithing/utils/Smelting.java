package smithing.utils;

/**
 * Presets for the smelting component of smithing.
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public enum Smelting {

    BRONZE_BAR(44, 2, 436, 14, 438, 14, 0, 2349, "Bronze bar"),
    IRON_BAR(44, 10, 440, 28, 0, 0, 0, 2351, "Iron bar"),
    SILVER_BAR(44, 14, 442, 28, 0, 0, 0, 2355, "Silver bar"),
    STEEL_BAR(44, 18, 440, 9, 453, 18, 2, 2353, "Steel bar"),
    GOLD_BAR(44, 22, 444, 28, 0, 0, 0, 2357, "Gold bar"),
    MITHRIL_BAR(44, 30, 447, 5, 453, 20, 4, 2359, "Mithril bar"),
    ADAMANT_BAR(44, 34, 449, 4, 453, 20, 6, 2361, "Adamant bar"),
    RUNE_BAR(44, 58, 451, 3, 453, 8, 8, 2363, "Rune bar");

    int id;
    int index;
    int oreId;
    int withdrawAmount;
    int otherOreId;
    int otherWithdrawAmount;
    int coalAmount;
    int bar;
    String name;

    Smelting(int id, int index, int oreId, int withdrawAmount, int otherOreId, int otherWithdrawAmount, int coalAmount, int bar, String name) {
        this.id = id;
        this.index = index;
        this.oreId = oreId;
        this.withdrawAmount = withdrawAmount;
        this.otherOreId = otherOreId;
        this.otherWithdrawAmount = otherWithdrawAmount;
        this.coalAmount = coalAmount;
        this.bar = bar;
        this.name = name;
    }

    /**
     * Returns the WidgetChild id of the item window in the crafting interface.
     * @return the WidgetChild id of the item window.
     */

    public int getId() {
        return id;
    }

    /**
     * Returns the index of the WidgetChild for the bar in the item window.
     * @return the index of the WidgetChild for the bar in the item window.
     */

    public int getIndex() {
        return index;
    }

    /**
     * Returns the id of the main ore used in the selected bar.
     * @return the id of the main ore used in the selected bar.
     */

    public int getOreId() {
        return oreId;
    }

    /**
     * Returns the amount of main ore that should be withdrawn for the selected bar.
     * @return the amount of main ore that should be withdrawn for the selected bar.
     */

    public int getWithdrawAmount() {
        return withdrawAmount;
    }

    /**
     * Returns the id of the companion ore to the main ore.
     * @return the id of the companion ore to the main ore. 0 if main ore has no companion.
     */

    public int getOtherOreId() {
        return otherOreId;
    }

    /**
     * Returns the amount of companion ore that should be withdrawn.
     * @return the amount of companion ore that should be withdrawn. 0 if no companion ore.
     */

    public int getOtherWithdrawAmount() {
        return otherWithdrawAmount;
    }

    /**
     * Returns the amount of coal that the selected ore needs to be smelted into a bar.
     * @return the amount of coal that the selected ore needs to be smelted into a bar. 0 if no coal.
     */

    public int getCoalAmount() {
        return coalAmount;
    }

    /**
     * Returns the id of the selected bar once it is smelted.
     * @return the id of the selected bar once it is smelted.
     */

    public int getBar() {
        return bar;
    }

    /**
     * Returns the name of the bar.
     * @return the name of the bar.
     */

    public String getName() {
        return name;
    }

}
