package fisher.nodes;

import fisher.interactions.Fishing;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class CatchFish extends Node {
    @Override
    public boolean activate() {
        // We only want to try and catch fish if we're in the fishing area, we aren't already fishing and our inventory isn't full.
        return Fishing.getFishArea().contains(Players.getLocal()) && !Fishing.isFishing() && !Fishing.isInvFull();
    }

    @Override
    public void execute() {
        // If our inventory is full, set our inventory boolean to true.
        if (Inventory.isFull()) {
            Fishing.setInvFull(true);
        } else {
            // Otherwise catch a fish if we aren't moving.
            if (!Players.getLocal().isMoving()) {
                // Refer to the method in interactions/Fishing for more information.
                Fishing.catchFish();
            }
        }
    }
}
