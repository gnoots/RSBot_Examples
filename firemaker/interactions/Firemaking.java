package firemaker.interactions;

import firemaker.utils.Logs;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

import java.util.ArrayList;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Firemaking {

    /**
     * Filter for determining which fires we should use.
     */

    private static Filter<SceneObject> fireFilter = new Filter<SceneObject>(){
        @Override
        public boolean accept(SceneObject o) {
            return getFireIdsAsList().contains(o.getId()) && getFireArea().contains(o);
        }
    };

    /**
     * Sets the logs to be used to the specified Logs enum.
     * @param l the Logs enum to be used.
     */

    public static void setSelectedLogs(Logs l) {
        selectedLogs = l;
    }

    /**
     * Returns the Logs enum of the selected logs.
     * @return the Logs enum of the selected logs.
     */

    public static Logs getSelectedLogs() {
        return selectedLogs;
    }

    /**
     * Sets the area to light fires in to the specified area.
     * @param a the area to light fires in.
     */

    public static void setFireArea(Area a) {
        fireArea = a;
    }

    /**
     * Returns the area to light fires in.
     * @return the area to light fires in.
     */

    public static Area getFireArea() {
        return fireArea;
    }

    /**
     * Returns the fire filter.
     * @return the fire filter.
     */

    public static Filter<SceneObject> getFireFilter() {
        return fireFilter;
    }

    /**
     * Creates a fire if the player is in the fire area.
     */

    public static void makeFire() {
        Item log = getLog();

        if (Players.getLocal().getAnimation() == -1 && Firemaking.getFireArea().contains(Players.getLocal())) {
            log.getWidgetChild().interact("Light");
        }
    }

    /**
     * Adds a log to an existing fire.
     */

    public static void addLog() {
        // We define our fire and log here.
        SceneObject fire = getFire();
        Item log = getLog();

        /*
        Passive animations are separate from normal animations. When viewing players are viewed
        with the built in tool on the client, players will have two numbers above their head:
        A: 1234 | ST: 4321
        A is the animation, ST is the passive animation. We'll use the passive animation that
        the player shows when lighting a log, as the normal animation doesn't change.
         */
        if (Players.getLocal().getPassiveAnimation() != 16701 && log != null) {
            // If the fire isn't on the screen we'll turn to it.
            if (!fire.isOnScreen()) {
                Camera.turnTo(fire);
            } else {
                // Otherwise, we select our log in our inventory, then interact with the fire and use the log on it.
                if (Inventory.selectItem(log)) {
                    fire.interact("Use", getSelectedLogs().getName() + " -> Fire");
                }
                // Again, we use a dynamic sleep to make sure we don't click too much.
                Timer wait = new Timer(3000);
                while (Players.getLocal().getPassiveAnimation() != 16701 && wait.isRunning()) {
                    Task.sleep(50);
                }
            }
        }
    }

    /**
     * Returns an inventory item of the selected log.
     * @return an inventory item of the selected log.
     */

    public static Item getLog() {
        return Inventory.getItem(getSelectedLogs().getItemId());
    }

    /**
     * Returns the nearest fire as determined by the fire filter.
     * @return the nearest fire as determined by the fire filter.
     */

    public static SceneObject getFire() {
        return SceneEntities.getNearest(getFireFilter());
    }

    /**
     * Returns all the log ids for the Logs enums as an array.
     * @return all the log ids for the Logs enums as an array.
     */

    public static int[] getAllLogIds() {
        ArrayList<Integer> list = new ArrayList<>();
        for (Logs l : Logs.values()) {
            list.add(l.getItemId());
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * Returns all the fire ids for the Logs enums as an array.
     * @return all the fire ids for the Logs enums as an array.
     */

    public static int[] getAllFireIds() {
        ArrayList<Integer> list = new ArrayList<>();
        for (Logs l : Logs.values()) {
            list.add(l.getFireId());
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * Returns all the fire ids for the Logs enums as an ArrayList.
     * @return all the fire ids for the Logs enums as an ArrayList.
     */

    public static ArrayList<Integer> getFireIdsAsList() {
        ArrayList<Integer> list = new ArrayList<>();
        for (Logs l : Logs.values()) {
            list.add(l.getFireId());
        }
        return list;
    }

    /**
     * Stops the script if set to true.
     * @param b true to stop the script.
     */

    public static void setNoLogsLeft(boolean b) {
        noLogs = b;
    }

    /**
     * Returns true if script should be stopped.
     * @return true if script should be stopped.
     */

    public static boolean noLogsLeft() {
        return noLogs;
    }

    /**
     * Adds the specified amount to total logs burnt.
     * @param amount adds to total logs burnt.
     */

    public static void addToLogsBurnt(int amount) {
        logsBurnt += amount;
    }

    /**
     * Returns the total amount of logs burnt.
     * @return the total amount of logs burnt.
     */

    public static int getLogsBurnt() {
        return logsBurnt;
    }

    /**
     * Returns the nearest Fire Spirit.
     * @return the nearest Fire Spirit.
     */

    public static NPC getFireSpirit() {
        return NPCs.getNearest(15451);
    }

    /**
     * Adds the specified amount to the total amount of spirits caught.
     * @param amount the amount to add to the total amount of spirits caught.
     */

    public static void addToSpiritsCaught(int amount) {
        spiritsCaught += amount;
    }

    /**
     * Returns the number of spirits caught.
     * @return the number of spirits caught.
     */

    public static int getSpiritsCaught() {
        return spiritsCaught;
    }

    private static Logs selectedLogs;
    private static Area fireArea;
    private static boolean noLogs;
    private static int logsBurnt;
    private static int spiritsCaught;

}
