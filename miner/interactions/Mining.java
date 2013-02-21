package miner.interactions;

import miner.utils.Methods;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.ArrayList;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Mining {

    /**
     * Filter for determining which rocks should be targeted.
     */

    private static Filter<SceneObject> rockFilter = new Filter<SceneObject>(){
        @Override
        public boolean accept(SceneObject o) {
            return getOreIds().contains(o.getId());
        }
    };

    /**
     * Sets the ids of the ores to mine.
     * @param ids the ids of the ores to mine.
     */

    public static void setOreIds(int... ids) {
        for (int i : ids) {
            getOreIds().add(i);
        }
    }

    /**
     * Sets the ids of the ores in inventory to drop or bank.
     * @param ids the ids of the ores in inventory to drop or bank.
     */

    public static void setInvOreIds(int... ids) {
        for (int i : ids) {
            getInvOreIds().add(i);
        }
    }

    /**
     * Returns the ArrayList containing the ore ids.
     * @return the ArrayList containing the ore ids.
     */

    public static ArrayList<Integer> getOreIds() {
        return oreIds;
    }

    /**
     * Returns the ArrayList containing the inventory ore ids.
     * @return the ArrayList containing the inventory ore ids.
     */

    public static ArrayList<Integer> getInvOreIds() {
        return invOreIds;
    }

    /**
     * Returns an array of the invOreIds ArrayList.
     * @return an array of the invOreIds ArrayList.
     */

    public static int[] getInvOresIdsAsArray() {
        int[] a = new int[getInvOreIds().size()];
        for (int i = 0; i < getInvOreIds().size(); i++) {
            a[i] = getInvOreIds().get(i);
        }
        return a;
    }

    /**
     * Sets the area to mine in.
     * @param a the area to mine in.
     */

    public static void setMiningArea(Area a) {
        miningArea = a;
    }

    /**
     * Sets the path to the mining area.
     * @param t the path to the mining area.
     */

    public static void setMiningPath(Tile[] t) {
        miningPath = t;
    }

    /**
     * Returns the mining area.
     * @return the mining area.
     */

    public static Area getMiningArea() {
        return miningArea;
    }

    /**
     * Returns the path to the mining area.
     * @return the path to the mining area.
     */

    public static Tile[] getMiningPath() {
        return miningPath;
    }

    /**
     * Returns the filter that determines which rock is most suitable.
     * @return the filter that determines which rock is most suitable.
     */

    public static Filter<SceneObject> getRockFilter() {
        return rockFilter;
    }

    /**
     * Returns the nearest rock as determined by the rock filter.
     * @return the nearest rock as determined by the rock filter.
     */

    public static SceneObject getRock() {
        return SceneEntities.getNearest(getRockFilter());
    }

    /**
     * Returns the tile directly in front of the player in the direction they are facing.
     * @return the tile directly in front of the player in the direction they are facing.
     */

    public static Tile getTileFromOrientation() {
        Tile pLoc = Players.getLocal().getLocation();
        int o = Players.getLocal().getOrientation();
        Tile t = new Tile(0, 0, 0);

        /*
         getOrientation starts at 0 degrees for East and goes counter-clockwise,
         so 90 degrees is North etc.
          */

        if (o == 0) {
            t = new Tile(pLoc.getX()+1, pLoc.getY(), pLoc.getPlane());
        } else if (o == 90) {
            t = new Tile(pLoc.getX(), pLoc.getY()+1, pLoc.getPlane());
        } else if (o == 180) {
            t = new Tile(pLoc.getX()-1, pLoc.getY(), pLoc.getPlane());
        } else if (o == 270) {
            t = new Tile(pLoc.getX(), pLoc.getY()-1, pLoc.getPlane());
        }
        return t;
    }

    /**
     * Mines a rock if it is on the screen and finds one otherwise.
     */

    public static void mineRock() {
        // The rock we want to be mining.
        SceneObject rock = getRock();

        // If we're not already moving and our animation is the default we want to proceed.
        if (!Players.getLocal().isMoving() && Players.getLocal().getAnimation() == -1) {
            // If our rock is on the screen we'll mine it.
            if (Methods.isOnScreen(rock)) {
                rock.interact("Mine");
            } else {
                // Turn to the rock if it isn't on the screen.
                Camera.turnTo(rock);
                // Walk to the rock using the minimap if it still isn't on the screen.
                if (!Methods.isOnScreen(rock)) {
                    Walking.walk(rock.getLocation());
                }
            }
        }
    }

    /**
     * Returns the amount of ores that have been banked.
     * @return the amount of ores that have been banked.
     */

    public static int getOresBanked() {
        return oresBanked;
    }

    /**
     * Adds the specified amount to the banked ores total.
     * @param amt the specified amount to be added to the banked ores total.
     */

    public static void setOresBanked(int amt) {
        oresBanked+=amt;
    }

    /**
     * Sets the inventory to full if true.
     * @param b true to set the inventory to full.
     */

    public static void setInvFull(boolean b) {
        invFull = b;
    }

    /**
     * Returns true if inventory is full.
     * @return true if inventory is full.
     */

    public static boolean isInvFull() {
        return invFull;
    }

    /*
    Our variables that we will be changing with the above methods.
     */
    private static ArrayList<Integer> oreIds = new ArrayList<>();
    private static ArrayList<Integer> invOreIds = new ArrayList<>();
    private static Area miningArea;
    private static Tile[] miningPath;
    private static int oresBanked = 0;
    private static boolean invFull = false;

}
