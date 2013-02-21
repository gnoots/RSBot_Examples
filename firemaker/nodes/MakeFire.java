package firemaker.nodes;

import firemaker.interactions.Firemaking;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class MakeFire extends Node {
    @Override
    public boolean activate() {
        // We only want to make a new fire if no fire exists.
        return Firemaking.getFire() == null;
    }

    @Override
    public void execute() {
        // If we're not in the appropriate area to light a fire...
        if (!Firemaking.getFireArea().contains(Players.getLocal())) {
            // We'll walk there.
            Tile t = Firemaking.getFireArea().getNearest();
            // If the tile we want to walk to is on the screen, we'll click it.
            if (t.isOnScreen()) {
                t.click(true);
            } else {
                // Otherwise, we'll use the minimap to get there.
                Walking.walk(t);
            }
            /*
            Here we have a dynamic sleep - it will end as soon as we enter the fire area
            or try again after 3 seconds.
             */
            Timer wait = new Timer(3000);
            while (!Firemaking.getFireArea().contains(Players.getLocal()) && wait.isRunning()) {
                Task.sleep(50);
            }
        } else {
            // If we are in the area, we'll go ahead and light a fire.
            Firemaking.makeFire();
            /*
             Again, we use a dynamic sleep. The timer on this one is higher than usual due to
             fires sometimes taking a while light. It will end as soon as a fire exists.
              */
            Timer wait = new Timer(8000);
            while (Firemaking.getFire() == null && wait.isRunning()) {
                Task.sleep(50);
            }
        }
    }
}
