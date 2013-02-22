package alchemy.interactions;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Magic {

    private static final int ALCHEMY_ANIMATION_ID = 713;
    private static final int NATURE_RUNE = 561;
    private static final int FIRE_RUNE = 554;

    /**
     * Sets the ids of the items to use alchemy on.
     * @param ids the ids of the items to use alchemy on.
     */

    public static void setItems(int... ids) {
        for (int i : ids) {
            getItemsList().add(i);
        }
    }

    /**
     * Returns the ArrayList containing alchemy item ids.
     * @return the ArrayList containing alchemy item ids.
     */

    public static ArrayList<Integer> getItemsList() {
        return items;
    }

    /**
     * Returns an item in inventory that matches an alchemable item.
     * @return an item in inventory that matches an alchemable item.
     */

    public static Item getItem() {
        for (Item i : Inventory.getItems()) {
            if (getItemsList().contains(i.getId())) {
                return i;
            }
        }
        return null;
    }

    /**
     * Returns a bank item that matches an alchemable item.
     * @return a bank item that matches an alchemable item.
     */

    public static Item getBankItem() {
        for (Item i : Bank.getItems()) {
            if (getItemsList().contains(i.getId())) {
                return i;
            }
        }
        return null;
    }

    /**
     * Uses the specified ability hotkey.
     * @param ch the character on the ability bar to use.
     * @return true if successfully pressed the key.
     */

    public static boolean useAbilityKey(char ch) {
        if (isAbilityBarUp()) {
            Keyboard.pressKey(ch, Random.nextInt(75, 125), 0);
            Keyboard.releaseKey(ch, Random.nextInt(75, 125), 0);
            return true;
        }
        return false;
    }

    /**
     * Alchemises an inventory item with the given delay.
     * @param delay delay in milliseconds before returning true if successful.
     * @return true if alchemy was used.
     */

    public static boolean alchemiseItem(double delay) {
        Item item = getItem();

        if (item != null) {
            if (useAbilityKey('1') && item.getWidgetChild().interact("Cast", "High Level Alchemy -> " + item.getName())) {
                Task.sleep(Random.nextGaussian((int) (delay*0.8), (int) (delay*1.2), (int) (delay*0.3)));
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if animation matches alchemy animation.
     * @return true if animation matches alchemy animation.
     */

    public static boolean isAlchemising() {
        return Players.getLocal().getAnimation() == ALCHEMY_ANIMATION_ID;
    }

    /**
     * Adds the specified amount to total amount of alchemies completed.
     * @param amount amount to add to alchemies completed.
     */

    public static void addToAlchemiesDone(int amount) {
        alchemiesDone += amount;
    }

    /**
     * Adds the specified amount to total alchemy profit.
     * @param amount amount to add to alchemy profit.
     */

    public static void addToAlchemyProfit(int amount) {
        alchemyProfit += amount;
    }

    /**
     * Returns total amount of alchemies done.
     * @return total amount of alchemies done.
     */

    public static int getAlchemiesDone() {
        return alchemiesDone;
    }

    /**
     * Returns total amount of alchemy profit.
     * @return total amount of alchemy profit.
     */

    public static int getAlchemyProfit() {
        return alchemyProfit;
    }

    /**
     * Sets the price of a nature rune to the specified value.
     * @param price price of nature rune.
     */

    public static void setNatureRunePrice(int price) {
        natureRunePrice = price;
    }

    /**
     * Adds the price of an item to the price map.
     * @param key id of item.
     * @param value price of item.
     */

    public static void setItemPrice(int key, int value) {
        getPriceMap().put(key, value);
    }

    /**
     * Returns the price HashMap.
     * @return the price HashMap.
     */

    public static HashMap<Integer, Integer> getPriceMap() {
        return priceMap;
    }

    /**
     * Returns the nature rune price.
     * @return the nature rune price.
     */

    public static int getNatureRunePrice() {
        return natureRunePrice;
    }

    /**
     * Returns the price of the specified item id.
     * @param id the id of the item.
     * @return price of item. 0 if not in price map.
     */

    public static int getItemPrice(int id) {
        if (getPriceMap().containsKey(id)) {
            return getPriceMap().get(id);
        }
        return 0;
    }

    /**
     * Returns the nature rune id.
     * @return the nature rune id.
     */

    public static int getNatureRuneId() {
        return NATURE_RUNE;
    }

    /**
     * Returns the fire rune id.
     * @return the fire rune id.
     */

    public static int getFireRuneId() {
        return FIRE_RUNE;
    }

    /**
     * Stops the script if set to true.
     * @param b true to stop script.
     */

    public static void stop(boolean b) {
        stop = b;
    }

    /**
     * Returns true if script should stop.
     * @return true if script should stop.
     */

    public static boolean shouldStop() {
        return stop;
    }

    /**
     * Returns true if the ability bar is maximised.
     * @return true if the ability bar is maximised.
     */

    public static boolean isAbilityBarUp() {
        WidgetChild abilityMinimise = Widgets.get(640, 30);
        return abilityMinimise.isOnScreen();
    }

    /**
     * Returns true if inventory contains necessary items for high alchemy.
     * @return true if inventory contains necessary items for high alchemy.
     */

    public static boolean hasAlchItems() {
        return Magic.getItem() != null && Inventory.getCount(true, Magic.getItem().getId()) > 0
                && Inventory.getCount(true, Magic.getNatureRuneId()) > 0
                && Inventory.getCount(true, Magic.getFireRuneId()) > 4;
    }

    private static ArrayList<Integer> items = new ArrayList<>();
    private static HashMap<Integer, Integer> priceMap = new HashMap<>();
    private static int alchemiesDone;
    private static int alchemyProfit;
    private static int natureRunePrice;
    private static boolean stop;

}
