package miner.nodes;

import miner.data.Constants;
import miner.utils.Lodestone;
import miner.interactions.Mining;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class ToBank extends Node {

    /*
    Refer to DoBanking for an overview on Nodes.
    */

    @Override
    public boolean activate() {
        // We only want to go to the bank if our inventory is full and we aren't already there.
        return Mining.isInvFull() && !Constants.BANK_AREA.contains(Players.getLocal());
    }

    @Override
    public void execute() {
        // While we have the default animation (-1) we will do the following since teleporting changes our animation.
        if (Players.getLocal().getAnimation() == -1) {
            // Teleport to Edgeville if we aren't there, walk to the bank if we are.
            if (!Constants.TO_BANK_AREA.contains(Players.getLocal())) {
                Lodestone.EDGEVILLE.teleport();
            } else {
                if (!Constants.BANK_AREA.contains(Players.getLocal())) {
                    Walking.newTilePath(Constants.TO_BANK).traverse();
                    Task.sleep(1000);
                }
            }
        }
    }
}
