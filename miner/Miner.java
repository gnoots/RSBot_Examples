package miner;

import miner.data.Constants;
import miner.interactions.Mining;
import miner.nodes.*;
import miner.ui.Paint;
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
@Manifest(authors = { "nootz" }, name = "Miner", description = "Example of a mining script.", version = 1.0)

// Our class which will extend ActiveScript. We will also implement PaintListener because we will be drawing a paint.
public class Miner extends ActiveScript implements PaintListener {

    /*
    Two arrays of Nodes that we could use. One will bank our ores, the other will just drop them.
    The order in which the Nodes are listed in the array is generally the order they will be executed in,
    so put the most important ones at the start. For example, if you have a full inventory you will want
    to drop your items before you try and mine again, so Drop() comes first.
     */
    Node[] bankingNodes = {new DoBanking(), new ToBank(), new ToRocks(), new Mine()};
    Node[] droppingNodes = {new Drop(), new Mine()};

    // onStart is called as soon as the script is started.
    @Override
    public void onStart() {
        // Sets the mouse speed to Fast due to the default speed being somewhat slow.
        Mouse.setSpeed(Mouse.Speed.FAST);

        // Defines the ores that will be mined. These are Copper and Tin.
        int[] ores = {3027, 3229, 3038, 3245};
        // Defines the ids of the ores that will be dropped or banked. Again, Copper and Tin.
        int[] invOres = {436, 438};

        // Sets the ores, area and path to the selected settings.
        Mining.setOreIds(ores);
        Mining.setInvOreIds(invOres);
        Mining.setMiningPath(Constants.TO_MINE_PATH);
        Mining.setMiningArea(Constants.LUMBRIDGE_SWAMP_MINE);
    }

    @Override
    public int loop() {
        // Make sure we've got an area to go to first!
        if (Mining.getMiningArea() != null) {
            // Loop through our Nodes array, if any of them activate they will be executed. In this case it is the banking version.
            for (Node n : bankingNodes) {
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
