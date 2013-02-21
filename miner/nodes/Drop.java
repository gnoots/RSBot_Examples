package miner.nodes;

import miner.interactions.Mining;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Drop extends Node {

    /*
    Refer to DoBanking for an overview on Nodes.
    */

    @Override
    public boolean activate() {
        // We only want to drop our items if the inventory is full.
        return Mining.isInvFull();
    }

    @Override
    public void execute() {
        // We loop through our inventory...
        for (Item i : Inventory.getItems()) {
            // Then if the id of our item matches an id in our list...
            if (Mining.getInvOreIds().contains(i.getId())) {
                // We drop it!
                i.getWidgetChild().interact("Drop", i.getName());
            }
        }
        // Finally, we check if the inventory has no more ores in it and set the inventory boolean to false.
        Mining.setInvFull(Inventory.getCount(Mining.getInvOresIdsAsArray()) < 1);
    }
}
