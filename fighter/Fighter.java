package fighter;

import fighter.data.Constants;
import fighter.interactions.Fighting;
import fighter.interactions.Looting;
import fighter.nodes.*;
import fighter.ui.Paint;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;

import java.awt.*;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */

// The Manifest is necessary for the script to show up on the script selection screen.
@Manifest(authors = { "nootz" }, name = "Fighter", description = "Example of a combat script.", version = 1.0)

// Our class which will extend ActiveScript. We will also implement PaintListener because we will be drawing a paint.
public class Fighter extends ActiveScript implements PaintListener {

    // An array of our Nodes. We will be using this in our loop method.
    Node[] nodes = { new DoBanking(), new ToBank(), new ToNpc(), new Loot(), new Fight() };

    // onStart is called as soon as the script is started.
    @Override
    public void onStart() {
        // Sets the mouse speed to Fast due to the default speed being somewhat slow.
        Mouse.setSpeed(Mouse.Speed.FAST);

        // Defines the NPCs that will be attacked. All of these are the Goblin variants.
        int npcs[] = {11234, 11238, 12357, 12353, 12355, 11240, 11236, 11232};
        // Defines the loot ids to pick up. In this case it's Bones (526) and Grapes (1987).
        int loot[] = {526, 1987};

        // Sets the NPC, area, path and loot to the selected settings.
        Fighting.setNpc(npcs);
        Fighting.setNpcArea(Constants.GOBLIN_AREA);
        Fighting.setNpcPath(Constants.TO_GOBLINS);
        Looting.setLootList(loot);
    }

    @Override
    public int loop() {
        // Make sure we've got an area to go to first!
        if (Fighting.getNpcArea() != null) {
            // Loop through our Nodes array, if any of them activate they will be executed.
            for (Node n : nodes) {
                if (n.activate()) {
                    n.execute();
                }
            }
        }
        // Sleep for 50 milliseconds before starting the loop again. This means the script will loop 20 times a second.
        return 50;
    }

    @Override
    public void onRepaint(Graphics g) {
        // Call our paint method from our Paint class.
        Paint.onRepaint(g);
    }
}
