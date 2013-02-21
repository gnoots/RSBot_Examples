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
import smithing.utils.Smithing;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Smith {

    /**
     * Sets the item to smith to the specified enum.
     * @param s the enum of the selected item.
     */

    public static void setItem(Smithing s) {
        toSmith = s;
    }

    /**
     * Returns the enum of the selected item.
     * @return the enum of the selected item.
     */

    public static Smithing getItemToSmith() {
        return toSmith;
    }

    /**
     * Sets the anvil to the specified id.
     * @param id the id of the anvil to be selected.
     */

    public static void setAnvil(int id) {
        anvil = id;
    }

    /**
     * Returns the id of the selected anvil.
     * @return the id of the selected anvil.
     */

    public static int getAnvilId() {
        return anvil;
    }

    /**
     * Returns the nearest anvil SceneObject of the selected id.
     * @return the nearest anvil SceneObject of the selected id.
     */

    public static SceneObject getAnvil() {
        return SceneEntities.getNearest(getAnvilId());
    }

    /**
     * Walks to the anvil if not near it, otherwise smiths the item of the specified enum.
     * @param sEnum the enum of the item to smith.
     */

    public static void smith(Smithing sEnum) {
        // Our anvil that we'll be referencing.
        SceneObject anvil = getAnvil();

        /*
        Refer to Smelt for an overview on Widgets and what these are used for.
         */
        Widget smithInterface = Widgets.get(1370);
        WidgetChild smithProgressExit = Widgets.get(1251, 56);
        WidgetChild smithButton = Widgets.get(1370, 20);

        // Our timer that we'll use for our dynamic sleeps - refer to Smelt for an overview.
        Timer wait = new Timer(1);

        // We only want to continue if the anvil exists, we aren't moving and we aren't already crafting.
        if (anvil != null && !Players.getLocal().isMoving() && !smithProgressExit.validate()) {
            // If the crafting interface is on the screen...
            if (smithInterface.validate()) {
                // We define a few more Widgets.
                WidgetChild smithItem = Widgets.get(1371, sEnum.getId()).getChildren()[sEnum.getIndex()];
                WidgetChild smithItemName = Widgets.get(1370, 56);
                // Then, if our item and its name are on the screen...
                if (smithItem.validate() && smithItemName.validate()) {
                    // And the item name matches the one in our enum...
                    if (smithItemName.getText().equals(sEnum.getName())) {
                        // We craft it!
                        smithButton.click(true);
                        // And then wait using a dynamic sleep.
                        wait.setEndIn(3000);
                        while (!smithProgressExit.validate() && wait.isRunning()) {
                            Task.sleep(50);
                        }
                    } else {
                        // If our item isn't selected, we select it here.
                        smithItem.click(true);
                    }
                }
            } else {
                // If our anvil is on the screen but the crafting window isn't open yet, we click the anvil.
                if (anvil.isOnScreen()) {
                    anvil.interact("Smith");
                    // Again, we wait using a dynamic sleep.
                    wait.setEndIn(3000);
                    while (!smithInterface.validate() && wait.isRunning()) {
                        Task.sleep(50);
                    }
                } else {
                    // If we're further than 5 tiles away from the anvil we will walk to it.
                    if (Calculations.distanceTo(anvil.getLocation()) > 5) {
                        Walking.walk(anvil.getLocation());
                    } else {
                        // Otherwise, we'll turn to it.
                        Camera.turnTo(anvil);
                        // If it still isn't on our screen, we'll walk to it again.
                        if (!anvil.isOnScreen()) {
                            Walking.walk(anvil.getLocation());
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns true if used all bars on smithing the specified item enum.
     * @param sEnum the enum of the item to check if done smithing.
     * @return true if used all bars on smithing the specified item enum.
     */

    public static boolean isDoneSmithing(Smithing sEnum) {
        return Inventory.getCount(sEnum.getBarId()) < sEnum.getAmount();
    }

    /**
     * Adds the specified amount to the total amount of items smithed.
     * @param amount the amount to be added to the total amount of items smithed.
     */

    public static void addToItemsSmithed(int amount) {
        itemsSmithed += amount;
    }

    /**
     * Returns the total amount of items smithed.
     * @return the total amount of items smithed.
     */

    public static int getItemsSmithed() {
        return itemsSmithed;
    }

    /*
    Our variables that we will be changing with the above methods.
     */
    private static Smithing toSmith;
    private static int anvil;
    private static int itemsSmithed;

}
