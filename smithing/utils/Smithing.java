package smithing.utils;

/**
 * Provides an easy way to select items to smith.
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public enum Smithing {

    BRONZE_DAGGER(44, 2, 2349, 1, "Bronze dagger"),
    BRONZE_HELM(44, 10, 2349, 1, "Bronze helm"),
    BRONZE_HATCHET(44, 14, 2349, 1, "Bronze hatchet"),
    BRONZE_MACE(44, 18, 2349, 1, "Bronze mace"),
    BRONZE_SWORD(44, 30, 2349, 1, "Bronze sword"),
    BRONZE_SCIMITAR(44, 54, 2349, 2, "Bronze scimitar");

    int id;
    int index;
    int bar;
    int amount;
    String name;

    Smithing(int id, int index, int bar, int amount, String name) {
        this.id = id;
        this.index = index;
        this.bar = bar;
        this.amount = amount;
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
     * Returns the index of the WidgetChild for the item in the item window.
     * @return the index of the WidgetChild for the item in the item window.
     */

    public int getIndex() {
        return index;
    }

    /**
     * Returns the id of the bar needed to craft the item.
     * @return the id of the bar needed to craft the item.
     */

    public int getBarId() {
        return bar;
    }

    /**
     * Returns the amount of bars needed to craft the item.
     * @return the amount of bars needed to craft the item.
     */

    public int getAmount() {
        return amount;
    }

    /**
     * Returns the name of the item.
     * @return the name of the item.
     */

    public String getName() {
        return name;
    }

}
