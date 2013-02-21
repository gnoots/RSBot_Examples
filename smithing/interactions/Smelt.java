package smithing.interactions;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import smithing.data.Constants;
import smithing.utils.Smelting;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Smelt {

    /**
     * Sets the bar to smelt.
     * @param s the enum of the bar.
     */

    public static void setBarToSmelt(Smelting s) {
        barToSmelt = s;
    }

    /**
     * Returns the enum of the selected bar.
     * @return the enum of the selected bar.
     */

    public static Smelting getBarToSmelt() {
        return barToSmelt;
    }

    /**
     * Sets the furnace id.
     * @param id the furnace id.
     */

    public static void setFurnace(int id) {
        furnace = id;
    }

    /**
     * Returns the furnace id.
     * @return the furnace id.
     */

    public static int getFurnaceId() {
        return furnace;
    }

    /**
     * Returns the nearest furnace SceneObject with the set id.
     * @return the nearest furnace SceneObject with the set id.
     */

    public static SceneObject getFurnace() {
        return SceneEntities.getNearest(getFurnaceId());
    }

    /**
     * Returns true if done smelting the ores for the specified bars.
     * @param s the enum of the selected bar.
     * @return true if done smelting ores in inventory.
     */

    public static boolean isDoneSmelting(Smelting s) {
        if (s.getOtherOreId() != 0) {
            if (Inventory.getCount(s.getOreId()) > 1 && Inventory.getCount(s.getOtherOreId()) > s.getCoalAmount()) {
                return false;
            }
        } else {
            return Inventory.getCount(s.getOreId()) < 1;
        }
        return true;
    }

    /**
     * Walks to the furnace if not there, otherwise smelts the specified bar.
     * @param sEnum the enum of the specified bar.
     */

    public static void smelt(Smelting sEnum) {
        // Our furnace - easy!
        SceneObject furnace = getFurnace();

        /*
        Here are our Widgets and WidgetChilds. These are elements on the screen, such as buttons
        and images. We can check if a Widget is visible and on the screen by using validate(), which
        allows to check if our interface is open. Some will be defined later on the method, as they
        will throw a null pointer exception otherwise due to their parent not existing (like checking
        for a word on a page that doesn't exist).
         */

        /*
         This is one part of the main crafting window. While it isn't the "whole" window, it will only show
         while the crafting window is open, so it can be used as a check to see if we're in the crafting
         interface.
          */
        Widget smeltInterface = Widgets.get(1370);
        // This is the "exit" button for the in-progress crafting window - it will be using to check if we're currently crafting something.
        WidgetChild smeltProgressExit = Widgets.get(1251, 56);
        // This is the big blue button on the crafting window that says "Smelt". We'll be clicking this to start our smelting.
        WidgetChild smeltButton = Widgets.get(1370, 20);

        /*
        This is a timer we will be using for our dynamic sleeps.
        An overview on dynamic sleeps:
            A dynamic sleep is designed to wait until the specified condition has been
            completed. This allows you to wait like a human would - as soon as the condition
            has been completed, you will continue the script and wait for a little while otherwise.
            For example, you might wait for a Widget to become visible before clicking it.
            If you used static sleeps (e.g. Task.sleep(500)), you might waste time waiting after
            it has already appeared, or try to click before it appears due to lag.

            Dynamic sleeps solve this problem by only continuing once the condition passes or
            the timer runs out. By having the timer run out, this allows the script to try again
            if it gets held up due to lag, for example.
         */
        Timer wait = new Timer(1);

        // We only want to continue with this method if the furnace exists, we aren't moving and the in-progress interface isn't open.
        if (furnace != null && !Players.getLocal().isMoving() && !smeltProgressExit.validate()) {
            // If our crafting window is open, continue.
            if (smeltInterface.validate()) {
                /*
                Here we define two more WidgetChilds - earlier we either had no use for them or,
                in the case of smithItem, they would throw an exception due to their parent not existing.
                 */
                WidgetChild smithItem = Widgets.get(1371, sEnum.getId()).getChildren()[sEnum.getIndex()];
                WidgetChild smithItemName = Widgets.get(1370, 56);
                // If both the "picture" of our item and the name of the item is on the screen, continue.
                if (smithItem.validate() && smithItemName.validate()) {
                    // If the name of the selected item is equal to the item name in our enum, continue.
                    // Remember to use .equals on text, == will not work for what we want to do.
                    if (smithItemName.getText().equals(sEnum.getName())) {
                        // Click the "Smelt" button if everything is good to go.
                        smeltButton.click(true);
                        /*
                        Here is a dynamic sleep. As you can see, while the in-progress crafting
                        window (smeltProgressExit) is not on the screen, we will click once and wait
                        3 seconds. Usually this will open the window on the first try but if, for example,
                        you lag it will click again after the timer runs out (3 seconds).
                         */
                        wait.setEndIn(3000);
                        while (!smeltProgressExit.validate() && wait.isRunning()) {
                            Task.sleep(50);
                        }
                    } else {
                        /*
                        If the name on the interface isn't the name of the item we want,
                        we'll click the "picture" of that item.
                         */
                        smithItem.click(true);
                    }
                }
            } else {
                // If the furnace is on the screen but our crafting window isn't, we want to open it up.
                if (furnace.isOnScreen()) {
                    furnace.interact("Smelt");
                    /*
                    Another dynamic sleep. This will allow it to continue almost instantly once the
                    crafting window pops up. This eliminates any time spent waiting.
                     */
                    wait.setEndIn(3000);
                    while (!smeltInterface.validate() && wait.isRunning()) {
                        Task.sleep(50);
                    }
                } else {
                    /*
                     If we aren't at the furnace yet, we'll start walking there.
                     Note that this path isn't reversed like in DoBanking. This is because
                     the path was generated from the bank to the furnace, so we want to
                     walk forwards.
                      */
                    if (Calculations.distanceTo(Constants.FURNACE_TILE) > 5) {
                        Walking.newTilePath(Constants.WALKING_PATH).traverse();
                    } else {
                        // Turn to the furnace if we're closer than 5 tiles away.
                        Camera.turnTo(furnace);
                        // Otherwise, walk to it using the minimap if it still isn't on our screen.
                        if (!furnace.isOnScreen()) {
                            Walking.walk(furnace.getLocation());
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds the specified to the total bars smelted.
     * @param amount the amount to add to total bars smelted.
     */

    public static void addToBarsSmelted(int amount) {
        barsSmelted += amount;
    }

    /**
     * Returns the total amount of bars smelted.
     * @return the total amount of bars smelted.
     */

    public static int getBarsSmelted() {
        return barsSmelted;
    }

    /*
    Our variables that we will be changing with the above methods.
     */
    private static int furnace;
    private static Smelting barToSmelt;
    private static int barsSmelted;

}
