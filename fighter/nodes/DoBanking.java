package fighter.nodes;

import fighter.data.Constants;
import fighter.interactions.Looting;
import fighter.utils.Methods;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */

public class DoBanking extends Node {

    /*
    A brief overview to Nodes:
        Nodes are an easy way to keep track of the different actions we need to take in our script.
        They are extended from our class and contain two methods.

        activate() checks for whether the conditions are met and will then (as the name implies)
        activate the execute() method.
        execute() calls all the methods and/or instructions contained within it. This may be just once
        or every time activate() is called.

        It's a good idea to make sure your activation conditions are exclusive to each class so that
        your script will only be trying to execute one a time.
    */

    @Override
    public boolean activate() {
        // We only want to do our banking if we're in the banking area and our inventory is full.
        return Looting.isInvFull() && Constants.BANK_AREA.contains(Players.getLocal());
    }

    @Override
    public void execute() {
        // The ID of the banker we will be using to properly locate the bank.
        final int BANKER_ID = 2759;
        /*
        Defines "banker" as an NPC. Due to this being placed inside execute(),
        the state of "banker" will be updated each time execute() is called
        which means it will not get stuck in a state such as null.
         */
        NPC banker = NPCs.getNearest(BANKER_ID);

        // Checking if the banker is not null. A NullPointerException may be thrown otherwise.
        if (banker != null) {
            // Turn to the banker if it isn't on our screen.
            if (!Methods.isOnScreen((banker))) {
                Camera.turnTo(banker);
            } else {
                // If the banker is on our screen and we're closer than 10 tiles away, we do the following.
                if (Calculations.distanceTo(banker.getLocation()) < 10) {
                    /*
                    Bank.open() will open the bank and also return true if the bank is open.
                    Therefore, we can use "if (Bank.open())" to open the bank if it's closed
                    continue if it isn't. This saves a check of Bank.isOpen().
                    */
                    if (Bank.open()) {
                        // Deposits the entire inventory using the Deposit All button.
                        Bank.depositInventory();
                        /*
                        Here we must remember to set our inventory boolean back to false
                        since we aren't using Inventory.isFull() in our other classes.
                        */
                        if (!Inventory.isFull()) {
                            Looting.setInvFull(false);
                        }
                        // Remember to close the bank!
                        Bank.close();
                    }
                }
            }
        }
    }
}
