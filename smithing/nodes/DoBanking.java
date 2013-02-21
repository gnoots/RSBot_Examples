package smithing.nodes;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.interactive.NPC;
import smithing.data.Constants;
import smithing.data.Variables;
import smithing.interactions.Smelt;
import smithing.interactions.Smith;
import smithing.utils.Smelting;
import smithing.utils.Smithing;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class DoBanking extends Node {
    @Override
    public boolean activate() {
        // We want to check if we're done smithing if Variables.smithing is true, otherwise we check if we're done smelting.
        return Variables.smithing ? Smith.isDoneSmithing(Smith.getItemToSmith()) : Smelt.isDoneSmelting(Smelt.getBarToSmelt());
    }

    @Override
    public void execute() {
        // Define the banker id - if we're smithing it is 2759, smelting is 496. This is because the bankers in Varrock and Al Kharid have different ids.
        final int BANKER_ID = Variables.smithing ? 2759 : 496;
        NPC banker = NPCs.getNearest(BANKER_ID);

        /*
         Here we define our item or bar we want to make. Notice they are enums of the Smithing or Smelting variety, and not an Item.
         This is because we can get our ids for our items from the enums. While defining these isn't necessary, it saves some time
         typing out the getter each time and allows us to change the definition later easily.
          */
        Smithing smithItem = Smith.getItemToSmith();
        Smelting smeltItem = Smelt.getBarToSmelt();

        // We don't want to do anything if we're moving!
        if (!Players.getLocal().isMoving()) {
            /*
            If we're further than 5 tiles away from the banker, we want to make our way to them.
            If we're smithing, the Varrock bankers are on the minimap while at the anvils so we
            can just use the minimap to walk to them.
            If we're smelting, the bankers are a bit further away so we use a path. Note that this path
            is reversed, as it starts at the bank and goes to the furnace. By reversing it, we can walk backwards
            from the furnace to the bank.
             */
            if (Calculations.distanceTo(Variables.smithing ? banker.getLocation() : Constants.BANK_TILE) > 5) {
                if (Variables.smithing) {
                    Walking.walk(banker.getLocation());
                } else {
                    Walking.newTilePath(Constants.WALKING_PATH).reverse().traverse();
                }
            } else {
                /*
                As shown in the previous examples, Bank.open() will open the bank and also return true if
                the bank is open, saving us having to check Bank.isOpen().
                 */
                if (Bank.open()) {
                    // Deposit our whole inventory while we still have items in it.
                    while (Inventory.getCount() > 0) {
                        Bank.depositInventory();
                        // Sleeps for between 300 - 600 milliseconds.
                        Task.sleep(Random.nextInt(300, 600));
                    }
                    /*
                    If we're smithing, we just need to withdraw some more bars.
                    If we have more bars than is required to make our selected item, we withdraw them,
                    otherwise the script will stop.
                    Note that we can figure out how many bars we need for the item since we've specified
                    it in the enum.
                     */
                    if (Variables.smithing) {
                        if (Bank.getItemCount(true, smithItem.getBarId()) > smithItem.getAmount()) {
                            Bank.withdraw(smithItem.getBarId(), 28);
                        } else {
                            Variables.stop = true;
                        }
                    } else {
                        /*
                        If we're smelting, things get slightly more complicated due to different bars needing
                        different companion ores.
                        First we check if we have enough of our main ores to withdraw (e.g. mithril, runite).
                         */
                        if (Bank.getItemCount(true, smeltItem.getOreId()) > smeltItem.getWithdrawAmount()) {
                            /*
                            Then, if our "other" (e.g. coal, tin) ore is not specified as 0 in the enum, we check
                            if we have enough and withdraw the amount specified in the enum. (e.g. 4 coal per mithril)
                            If we don't have enough of our companion ore, we stop the script.
                             */
                            if (smeltItem.getOtherOreId() != 0) {
                                if (Bank.getItemCount(true, smeltItem.getOtherOreId()) > smeltItem.getOtherWithdrawAmount()) {
                                    Bank.withdraw(smeltItem.getOtherOreId(), smeltItem.getOtherWithdrawAmount());
                                    Bank.withdraw(smeltItem.getOreId(), smeltItem.getWithdrawAmount());
                                } else {
                                    Variables.stop = true;
                                }
                            } else {
                                /*
                                If our "other" ore is defined as 0, all we need to do is withdraw the amount specified in the enum.
                                 */
                                Bank.withdraw(smeltItem.getOreId(), smeltItem.getWithdrawAmount());
                            }
                        } else {
                            // If we don't have any ores at all, we stop!
                            Variables.stop = true;
                        }
                    }
                    // Again, remember to close the bank.
                    Bank.close();
                }
            }
        }
    }
}
