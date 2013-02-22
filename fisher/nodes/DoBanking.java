package fisher.nodes;

import fisher.data.Constants;
import fisher.interactions.Fishing;
import fisher.utils.Fish;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class DoBanking extends Node {
    @Override
    public boolean activate() {
        // We only want to bank if we're at the bank and our inventory is full.
        return Constants.BANK_AREA.contains(Players.getLocal()) && Fishing.isInvFull();
    }

    @Override
    public void execute() {
        final int BANKER_ID = 2759;
        NPC banker = NPCs.getNearest(BANKER_ID);

        if (banker != null) {
            if (!banker.isOnScreen()) {
                Camera.turnTo(banker);
            } else {
                if (Bank.open()) {
                    // Here we loop through all of our inventory items...
                    for (Item i : Inventory.getItems()) {
                        // Then through all the Fish enums in our Fish list...
                        for (Fish f : Fishing.getFishList()) {
                            // And if there are any matches, we add their price to the total profit.
                            if (i.getId() == f.getItemId()) {
                                Fishing.addToTotalProfit(Fishing.getItemPrice(f.getItemId()));
                            }
                        }
                    }
                    // Then we're done with our inventory, so we deposit it.
                    Bank.depositInventory();
                    // We loop through our Fish list again...
                    for (Fish f : Fishing.getFishList()) {
                        // If any of our selected fish need feathers, we withdraw some feathers.
                        if (f.getFeather() && Inventory.getCount(Fishing.getFeatherId()) < 1) {
                            Bank.withdraw(Fishing.getFeatherId(), 500);
                        }
                    }
                    // Then we finish up by setting our inventory to "empty" and close the bank.
                    Fishing.setInvFull(false);
                    Bank.close();
                }
            }
        }

    }
}
