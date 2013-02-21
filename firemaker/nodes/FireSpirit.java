package firemaker.nodes;

import firemaker.interactions.Firemaking;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class FireSpirit extends Node {
    @Override
    public boolean activate() {
        // We only want to click the spirit if it exists and we have less than 24 items.
        return Firemaking.getFireSpirit() != null && Inventory.getCount() < 24;
    }

    @Override
    public void execute() {
        // We define our Fire Spirit.
        NPC fireSpirit = Firemaking.getFireSpirit();

        // If it's on our screen we click it and take the items, otherwise we turn to it.
        if (fireSpirit.isOnScreen()) {
            fireSpirit.interact("Collect-reward");
        } else {
            Camera.turnTo(fireSpirit);
        }
    }
}
