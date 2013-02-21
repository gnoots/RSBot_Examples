package miner.nodes;

import miner.interactions.Mining;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class Mine extends Node {

    /*
    Refer to DoBanking for an overview on Nodes.
    */

    @Override
    public boolean activate() {
        /*
         We only want our character to try and mine a rock if we aren't already mining and our inventory isn't full.
         */
        return Mining.getRock() != null && Mining.getMiningArea().contains(Players.getLocal()) && !Mining.getRock().getLocation().equals(Mining.getTileFromOrientation()) && !Mining.isInvFull();
    }

    @Override
    public void execute() {
        // If our inventory is full, set the inventory boolean to true.
        if (Inventory.isFull()) {
            Mining.setInvFull(true);
        } else {
            // Otherwise, we just need to mine a rock.
            Mining.mineRock();
        }
    }
}
