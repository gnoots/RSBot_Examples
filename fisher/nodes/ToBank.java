package fisher.nodes;

import fisher.data.Constants;
import fisher.interactions.Fishing;
import fisher.utils.Lodestone;
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
    @Override
    public boolean activate() {
        // We only want to go to the bank if we aren't there and our inventory is full.
        return !Constants.BANK_AREA.contains(Players.getLocal()) && Fishing.isInvFull();
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
