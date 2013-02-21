package fighter.nodes;

import fighter.interactions.Fighting;
import fighter.interactions.Looting;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */

public class Loot extends Node {

    /*
    Refer to DoBanking for an overview on Nodes.
    */

    @Override
    public boolean activate() {
        // We only want to loot if we're in the combat area, we can loot and an item exists.
        return Fighting.getNpcArea().contains(Players.getLocal()) && Looting.canLoot() && Looting.getItem() != null;
    }

    @Override
    public void execute() {
        // Check if our inventory is full, otherwise call our loot method.
        if (Inventory.isFull()) {
            Looting.setInvFull(true);
        } else {
            Looting.lootItem();
        }
    }
}
