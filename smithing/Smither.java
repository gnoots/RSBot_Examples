package smithing;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;
import smithing.data.Variables;
import smithing.interactions.Smelt;
import smithing.interactions.Smith;
import smithing.nodes.DoBanking;
import smithing.nodes.DoSmelting;
import smithing.nodes.DoSmithing;
import smithing.ui.Paint;
import smithing.utils.Smelting;
import smithing.utils.Smithing;

import java.awt.*;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */

// The Manifest is necessary for the script to show up on the script selection screen.
@Manifest(authors = { "nootz" }, name = "Smither", description = "Example of a smithing/smelting script.", version = 1.0)

/*
  Our class which will extend ActiveScript.
  We will also implement PaintListener and MessageListener because we will be drawing a paint and using received messages.
  */
public class Smither extends ActiveScript implements PaintListener, MessageListener {

    Node[] smithingNodes = {new DoBanking(), new DoSmithing()};
    Node[] smeltingNodes = {new DoBanking(), new DoSmelting()};

    // onStart is called as soon as the script is started.
    @Override
    public void onStart() {
        // Sets the mouse speed to Fast due to the default speed being somewhat slow.
        Mouse.setSpeed(Mouse.Speed.FAST);

        // Set our smithing boolean to true - this means we will be smithing.
        Variables.smithing = false;

        /*
         Sets the item, anvil, bar and furnace to the selected settings.
         The anvil id is for the anvils outside Varrock West Bank. The furnace is in Al Kharid.
          */
        Smith.setItem(Smithing.BRONZE_SWORD);
        Smith.setAnvil(2783);
        Smelt.setBarToSmelt(Smelting.BRONZE_BAR);
        Smelt.setFurnace(76293);
    }

    @Override
    public int loop() {
        // If our stop boolean is set to true, we shut down the script. This could be because we've run out of bars, for example.
        if (Variables.stop) {
            shutdown();
        }
        // Loop through our Nodes array, if any of them activate they will be executed. In this case it is the smelting version.
        if (Smith.getItemToSmith() != null && Smelt.getBarToSmelt() != null) {
            // If smithing is true use the smithing nodes, otherwise use the smelting nodes. This is a Ternary Operator.
            for (Node n : Variables.smithing ? smithingNodes : smeltingNodes) {
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

    @Override
    public void messageReceived(MessageEvent e) {
        /*
         If a message is received, it will generate a MessageEvent. In this case we've defined it as "e".
         It's always a good idea to check if the id of the sender isn't 2, as this is the id of players
         and if it isn't filtered people may mess with your scripts.
         */
        if (e.getId() != 2 && e.getMessage().contains("You make a")) {
            // Add one to the total amount of items smithed if the message contains "You make a".
            Smith.addToItemsSmithed(1);
        } else if (e.getId() != 2 && e.getMessage().contains("You retrieve")) {
            // Again, one to the total amount of bars smelted if the message contains "You retrieve".
            Smelt.addToBarsSmelted(1);
        }
    }
}
