package fisher.nodes;

import fisher.interactions.Fishing;
import org.powerbot.core.script.job.state.Node;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class DropFish extends Node {
    @Override
    public boolean activate() {
        // We only want to drop fish while our inventory is full.
        return Fishing.isInvFull();
    }

    @Override
    public void execute() {
        // Drop a fish! Refer to the method in interactions/Fishing for more information.
        Fishing.dropFish();
    }
}
