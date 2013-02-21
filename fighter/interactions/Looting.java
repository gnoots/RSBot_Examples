package fighter.interactions;

import fighter.utils.Methods;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.GroundItem;

import java.util.HashSet;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */

public class Looting {

    /**
     * The filter that will determine which loot should be picked up.
     */

    private static final Filter<GroundItem> lootFilter = new Filter<GroundItem>(){
        @Override
        public boolean accept(GroundItem n) {
            return n != null && n.getLocation().canReach() &&
                    getLootList().contains(n.getId()) && Fighting.getNpcArea().contains(n.getLocation());
        }
    };

    /**
     * Sets the id(s) of the loot to picked up.
     * @param ids the id(s) of the loot to be picked up.
     */

    public static void setLootList(int... ids) {
        for (int i : ids) {
            lootList.add(i);
        }
    }

    /**
     * Returns the HashSet containing the selected loot ids.
     * @return the HashSet containing the selected loot ids.
     */

    public static HashSet<Integer> getLootList() {
        return lootList;
    }

    /**
     * Returns the loot filter.
     * @return the loot filter.
     */

    public static Filter<GroundItem> getLootFilter() {
        return lootFilter;
    }

    /**
     * Returns the nearest GroundItem from the loot filter.
     * @return the nearest GroundItem from the loot filter.
     */

    public static GroundItem getItem() {
        return GroundItems.getNearest(getLootFilter());
    }

    /**
     * Returns the loot timer.
     * @return the loot timer.
     */

    public static Timer getLootTimer() {
        return lootTimer;
    }

    /**
     * Returns true if looting is feasible.
     * @return true if looting is feasible.
     */

    public static boolean canLoot() {
        return !lootTimer.isRunning() && !Players.getLocal().isMoving() && Players.getLocal().getInteracting() == null && !Looting.isInvFull();
    }

    /**
     * Loots the nearest item from the loot filter.
     * @return true if loot is successful.
     */

    public static boolean lootItem() {
        /*
        Define the item. By placing this inside a method, the item is "refreshed"
        each time it is called and won't be stuck in the same state.
         */
        GroundItem item = getItem();
        if (Methods.isOnScreen(item)) {
            /*
            Pick up the item. The second parameter for interact is the name of the item.
            This is not always necessary, but if the item is below another in a stack on
            the ground, it will always take the top one. Specifying a name ensures it
            will choose that item from the list. This can also be e.g. "Bones"
            */
            item.interact("Take", item.getGroundItem().getName());
            // Set the loot timer to end in 1.5 seconds so that we don't spam clicks.
            getLootTimer().setEndIn(1500);
            // Return true for the boolean if we have successfully clicked.
            return true;
        } else {
            // Turn to the item if it isn't on the screen.
            Camera.turnTo(item);
            // If the item still isn't on screen after turning to it, we want to walk to it.
            if (!Methods.isOnScreen(item)) {
                Walking.walk(item);
            }
        }
        // Return false for the boolean if we haven't been able to do any of the above.
        return false;
    }

    /**
     * Returns true if inventory is full.
     * @return true if inventory is full.
     */

    public static boolean isInvFull() {
        return invFull;
    }

    /**
     * Sets inventory to full if true.
     * @param b true to set inventory to full.
     */

    public static void setInvFull(boolean b) {
        invFull = b;
    }

    /*
    Our variables that we will be changing with the above methods.
     */
    private static HashSet<Integer> lootList = new HashSet<>();
    private static Timer lootTimer = new Timer(1);
    private static boolean invFull = false;
}
