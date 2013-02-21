package smithing.nodes;

import org.powerbot.core.script.job.state.Node;
import smithing.interactions.Smith;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class DoSmithing extends Node {
    @Override
    public boolean activate() {
        // Again, we only want to activate if we're not done smithing.
        return !Smith.isDoneSmithing(Smith.getItemToSmith());
    }

    @Override
    public void execute() {
        // Smith our item. Refer to the smith method in Smith for an in depth explanation.
        Smith.smith(Smith.getItemToSmith());
    }
}
