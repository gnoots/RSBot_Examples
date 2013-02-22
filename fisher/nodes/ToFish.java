package fisher.nodes;

import fisher.interactions.Fishing;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.map.TilePath;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class ToFish extends Node {
    @Override
    public boolean activate() {
        // We only want to walk to the fishing area if we aren't already there and our inventory isn't full.
        return !Fishing.getFishArea().contains(Players.getLocal()) && !Fishing.isInvFull();
    }

    @Override
    public void execute() {
        // Define our path to the fish.
        TilePath path = Walking.newTilePath(Fishing.getFishPath());
        // Then we just need to traverse it.
        path.traverse();
    }
}
