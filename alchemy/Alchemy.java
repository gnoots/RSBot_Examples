package alchemy;

import alchemy.interactions.Magic;
import alchemy.nodes.DoAlchemy;
import alchemy.nodes.DoBanking;
import alchemy.ui.Paint;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.net.GeItem;

import java.awt.*;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
@Manifest(authors = { "nootz" }, name = "Alchemy", description = "Example of an alchemy/magic script.", version = 1.0)
public class Alchemy extends ActiveScript implements PaintListener, MessageListener {

    Node[] nodes = {new DoBanking(), new DoAlchemy()};

    @Override
    public void onStart() {
        Mouse.setSpeed(Mouse.Speed.FAST);

        /*
        GeItem allows us to fetch the price of an item by using its id.
        If too many items are requested too quickly, the connection will be
        blocked and all further items will return a price of 0. This can be
        solved by using a custom method but won't be outlined here.
         */
        // We define the GeItem for our nature rune and look up the price.
        GeItem natRune = GeItem.lookup(Magic.getNatureRuneId());
        // We then set the price of the nature rune.
        Magic.setNatureRunePrice(natRune.getPrice());

        // The ids of the items we will be alchemising. In this case, it's just rune arrows.
        final int ALCHEMY_ITEM_IDS[] = {892};

        // Here we'll loop through our alchemy items array and get their prices.
        for (int i : ALCHEMY_ITEM_IDS) {
            // For every id in our array, we look up that item.
            GeItem alchemyItem = GeItem.lookup(i);
            // A price of 0 will be returned for items that can't be traded...
            if (alchemyItem.getPrice() != 0) {
                /*
                So if the price isn't 0, we add it to our HashMap.
                HashMaps are very handy for storing the prices of our items, as
                we can look them up easily later as long as we know our item id.
                This means we don't have to get the price each time we want to know it.
                To get the price once it's in the HashMap, we'll use Magic.getItemPrice(itemId).
                 */
                Magic.setItemPrice(alchemyItem.getId(), alchemyItem.getPrice());
            }
        }

        // Sets the items to alchemise to the following ids.
        Magic.setItems(ALCHEMY_ITEM_IDS);
    }

    @Override
    public int loop() {
        // If we should stop the script (from Magic.stop(true)), we'll stop the script.
        if (Magic.shouldStop()) {
            shutdown();
        } else {
            // Otherwise, loop through our nodes array and execute any that activate if we have items in our list.
            if (!Magic.getItemsList().isEmpty()) {
                for (Node n : nodes) {
                    if (n.activate()) {
                        n.execute();
                    }
                }
            }
        }
        return 50;
    }

    @Override
    public void messageReceived(MessageEvent e) {
        // If the message we received isn't from a player and contains the following text, we continue.
        if (e.getId() != 2 && e.getMessage().contains("coins have been added")) {
            /*
            Here we want to get the amount of coins that were added. We'll use a regex pattern.
            The pattern "[^0-9]" in replaceAll replaces everything other than a number with nothing (or "").
             */
            int alch = Integer.parseInt(e.getMessage().replaceAll("[^0-9]", ""));
            // If our item isn't null...
            if (Magic.getItem() != null) {
                // We subtract the sum of the item price and nature rune price from the coins we just received.
                // Then it's added to the the total amount of alchemy profit.
                Magic.addToAlchemyProfit(alch - (Magic.getItemPrice(Magic.getItem().getId()) + Magic.getNatureRunePrice()));
            }
            // We add one to the amount of alchemies done.
            Magic.addToAlchemiesDone(1);
        }
    }

    @Override
    public void onRepaint(Graphics g) {
        Paint.onRepaint(g);
    }
}
