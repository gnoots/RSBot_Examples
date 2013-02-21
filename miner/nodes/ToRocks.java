package miner.nodes;

import miner.data.Constants;
import miner.interactions.Mining;
import miner.utils.Lodestone;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class ToRocks extends Node {

    /*
    Refer to DoBanking for an overview on Nodes.
    */

    @Override
    public boolean activate() {
        // We only want to go to the NPC area if our inventory isn't full and we aren't already there.
        return !Mining.isInvFull() && !Mining.getMiningArea().contains(Players.getLocal());
    }

    @Override
    public void execute() {
        // While we have the default animation (-1) we will do the following since teleporting changes our animation.
        if (Players.getLocal().getAnimation() == -1) {
            // Teleport to Lumbridge if we aren't there, walk to the area if we are.
            if (!Constants.TO_MINE_AREA.contains(Players.getLocal())) {
                Lodestone.LUMBRIDGE.teleport();
            } else {
                if (!Constants.LUMBRIDGE_SWAMP_MINE.contains(Players.getLocal())) {
                    Walking.newTilePath(Mining.getMiningPath()).traverse();
                    Task.sleep(1000);
                }
            }
        }
    }
}
