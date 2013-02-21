package smithing.nodes;

import org.powerbot.core.script.job.state.Node;
import smithing.interactions.Smelt;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class DoSmelting extends Node {
    @Override
    public boolean activate() {
        // We only want to activate if we're not yet done smelting.
        return !Smelt.isDoneSmelting(Smelt.getBarToSmelt());
    }

    @Override
    public void execute() {
        // Smelt our selected item. Refer to the smelt method in Smelt for an in depth explanation.
        Smelt.smelt(Smelt.getBarToSmelt());
    }
}
