package fisher;

import fisher.data.Constants;
import fisher.interactions.Fishing;
import fisher.nodes.*;
import fisher.ui.Paint;
import fisher.utils.Fish;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.util.net.GeItem;

import java.awt.*;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
@Manifest(authors = { "nootz" }, name = "Fisher", description = "Example of a fishing script.", version = 1.0)
public class Fisher extends ActiveScript implements PaintListener, MessageListener {

    // Two sets of nodes we could use. The banking set is used in this case.
    Node[] bankingNodes = {new DoBanking(), new ToBank(), new ToFish(), new CatchFish()};
    Node[] droppingNodes = {new DropFish(), new CatchFish()};

    @Override
    public void onStart() {
        Mouse.setSpeed(Mouse.Speed.FAST);

        // An enum array containing the fish types we want to catch.
        final Fish[] FISH_TO_CATCH = {Fish.RAW_TROUT, Fish.RAW_SALMON};
        // An int array containing the fishing spot id from a Fish enum.
        // In this case we only need trout since salmon and trout come from the same spot.
        final int[] FISHING_SPOTS = {Fish.RAW_TROUT.getSpotId()};

        // Retrieve the prices for our fish and add them to the price map.
        for (Fish f : FISH_TO_CATCH) {
            GeItem fish = GeItem.lookup(f.getItemId());
            if (fish.getPrice() != 0) {
                Fishing.setItemPrice(f.getItemId(), fish.getPrice());
            }
        }

        // Sets our fish, fishing spots, fish area and fish path to the selected values.
        Fishing.setFish(FISH_TO_CATCH);
        Fishing.setFishSpot(FISHING_SPOTS);
        Fishing.setFishArea(Constants.FLY_FISHING_AREA);
        Fishing.setFishPath(Constants.FLY_FISHING_PATH);
    }

    @Override
    public int loop() {
        // If we have an area to fish in, loop through our nodes and see if any are ready to execute.
        if (Fishing.getFishArea() != null) {
            for (Node n : bankingNodes) {
                if (n.activate()) {
                    n.execute();
                }
            }
        }
        return 50;
    }

    @Override
    public void messageReceived(MessageEvent e) {
        // Add one to our total fish caught if we received a message containing "You catch" that didn't come from a player.
        if (e.getId() != 2 && e.getMessage().contains("You catch")) {
            Fishing.addToFishCaught(1);
        }
    }

    @Override
    public void onRepaint(Graphics g) {
        Paint.onRepaint(g);
    }
}
