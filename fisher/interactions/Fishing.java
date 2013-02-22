package fisher.interactions;

import fisher.utils.Fish;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Fishing {

    /*

    NOTE: Fishing spots are not SceneObjects! They are NPCs, so all methods and filters below
    return NPCs.

     */

    private static final int FEATHER_ID = 314;

    /**
     * The filter that determines what fishing spot will be best.
     */

    private static final Filter<NPC> spotFilter = new Filter<NPC>(){
        @Override
        public boolean accept(NPC s) {
            return getFishSpotList().contains(s.getId()) && getFishArea().contains(s.getLocation());
        }
    };

    /**
     * Sets the fish to catch to the specified enum(s).
     * @param fish the Fish enum.
     */

    public static void setFish(Fish... fish) {
        for (Fish f : fish) {
            getFishList().add(f);
        }
    }

    /**
     * Sets the fishing spots to the specified id(s).
     * @param ids the fishing spot id(s).
     */

    public static void setFishSpot(int... ids) {
        for (int i : ids) {
            getFishSpotList().add(i);
        }
    }

    /**
     * Sets the price of the specified item id.
     * @param id the item id.
     * @param price the price.
     */

    public static void setItemPrice(int id, int price) {
        if (!getPriceMap().containsKey(id)) {
            getPriceMap().put(id, price);
        }
    }

    /**
     * Returns the price HashMap.
     * @return the price HashMap.
     */

    public static HashMap<Integer, Integer> getPriceMap() {
        return priceMap;
    }

    /**
     * Returns a price for the specified item id in the price map.
     * @param id the item id.
     * @return a price for the specified item id in the price map.
     */

    public static int getItemPrice(int id) {
        return getPriceMap().get(id);
    }

    /**
     * Returns the Fish ArrayList.
     * @return the Fish ArrayList.
     */

    public static ArrayList<Fish> getFishList() {
        return fish;
    }

    /**
     * Returns the fishing spot list.
     * @return the fishing spot list.
     */

    public static ArrayList<Integer> getFishSpotList() {
        return fishSpot;
    }

    /**
     * Returns the fishing spot filter.
     * @return the fishing spot filter.
     */

    public static Filter<NPC> getFishSpotFilter() {
        return spotFilter;
    }

    /**
     * Returns the nearest suitable fishing spot.
     * @return the nearest suitable fishing spot.
     */

    public static NPC getFishSpot() {
        return NPCs.getNearest(getFishSpotFilter());
    }

    /**
     * Sets the path to the fish to the specified tile array.
     * @param path the path to the fish.
     */

    public static void setFishPath(Tile[] path) {
        fishPath = path;
    }

    /**
     * Returns the path to the fish.
     * @return the path to the fish.
     */

    public static Tile[] getFishPath() {
        return fishPath;
    }

    /**
     * Sets the fishing area.
     * @param area the fishing area.
     */

    public static void setFishArea(Area area) {
        fishArea = area;
    }

    /**
     * Returns the fishing area.
     * @return the fishing area.
     */

    public static Area getFishArea() {
        return fishArea;
    }

    /**
     * Returns the id of feathers.
     * @return the id of feathers.
     */

    public static int getFeatherId() {
        return FEATHER_ID;
    }

    /**
     * Adds the specified amount to the total amount of fish caught.
     * @param amount the amount to add to total fish caught.
     */

    public static void addToFishCaught(int amount) {
        fishCaught += amount;
    }

    /**
     * Returns the total amount of fish caught.
     * @return the total amount of fish caught.
     */

    public static int getFishCaught() {
        return fishCaught;
    }

    /**
     * Sets the inventory full boolean to the specified value.
     * @param b true for inventory full.
     */

    public static void setInvFull(boolean b) {
        invFull = b;
    }

    /**
     * Returns true if inventory is set to full.
     * @return true if inventory is set to full.
     */

    public static boolean isInvFull() {
        return invFull;
    }

    /**
     * Returns true if players animation is not default.
     * @return true if players animation is not default.
     */

    public static boolean isFishing() {
        return Players.getLocal().getAnimation() != -1;
    }

    /**
     * Returns true if any selected fish require feathers.
     * @return true if any selected fish require feathers.
     */

    public static boolean isUsingFeathers() {
        for (Fish f : getFishList()) {
            if (f.getFeather()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if able to catch a fish.
     * @return true if able to catch a fish.
     */

    public static boolean catchFish() {
        NPC spot = getFishSpot();

        if (spot != null) {
            if (spot.isOnScreen()) {
                // First we loop through all the actions on our fishing spot (e.g. "Lure", "Bait").
                for (String s : spot.getActions()) {
                    // Then for each spot action, we see if any of our selected fish use that action.
                    for (Fish f : getFishList()) {
                        // If there's a match, we interact with the fishing spot.
                        if (s.equals(f.getAction())) {
                            spot.interact(f.getAction());
                            return true;
                        }
                    }
                }
            } else {
                Camera.turnTo(spot);
            }
        }
        return false;
    }

    /**
     * Drops fish from the inventory.
     */

    public static void dropFish() {
        // First we loop through all of our inventory items.
        for (Item i : Inventory.getItems()) {
            // Then through all the Fish enums in our Fish list.
            for (Fish f : getFishList()) {
                // If the id of the item matches any ids from the Fish list we drop it.
                if (i.getId() == f.getItemId()) {
                    i.getWidgetChild().interact("Drop");
                }
            }
        }
        // If we're using feathers, we set the inventory to "empty" if we only 1 item left (the feathers) otherwise when we 0 items left.
        if (Fishing.isUsingFeathers()) {
            Fishing.setInvFull(Inventory.getCount() > 1);
        } else {
            Fishing.setInvFull(Inventory.getCount() > 0);
        }
    }

    /**
     * Adds the specified amount to the total profit made.
     * @param amount the amount to add to total profit.
     */

    public static void addToTotalProfit(int amount) {
        totalProfit += amount;
    }

    /**
     * Returns the total profit made.
     * @return the total profit made.
     */

    public static int getTotalProfit() {
       return totalProfit;
    }

    private static ArrayList<Fish> fish = new ArrayList<>();
    private static ArrayList<Integer> fishSpot = new ArrayList<>();
    private static HashMap<Integer, Integer> priceMap = new HashMap<>();
    private static int fishCaught;
    private static int totalProfit;
    private static Tile[] fishPath;
    private static Area fishArea;
    private static boolean invFull;

}
