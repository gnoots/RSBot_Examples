package fighter.nodes;

import fighter.interactions.Fighting;
import fighter.interactions.Looting;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */

public class Fight extends Node {

    /*
    Refer to DoBanking for an overview on Nodes.
    */

    @Override
    public boolean activate() {
        // We only want to fight if we're not already fighting, in the combat area, an NPC exists, an item doesn't exist and our inventory isn't full.
        return Players.getLocal().getInteracting() == null &&
                Fighting.getNpcArea().contains(Players.getLocal()) && Fighting.getNpc() != null &&
                Looting.getItem() == null && !Looting.isInvFull();
    }

    @Override
    public void execute() {
        // Simply call the attack method from Fighting.
        Fighting.attackNpc();
    }
}
