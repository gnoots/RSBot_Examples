package firemaker.nodes;

import firemaker.interactions.Firemaking;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class DoBanking extends Node {
    @Override
    public boolean activate() {
        // We only want to bank if we have no logs in our inventory.
        return Inventory.getCount(Firemaking.getSelectedLogs().getItemId()) < 1;
    }

    @Override
    public void execute() {
        if (Bank.open()) {
            // If our bank contains no more logs we stop the script.
            if (Bank.getItemCount(true, Firemaking.getSelectedLogs().getItemId()) < 1) {
                Firemaking.setNoLogsLeft(true);
            } else {
                // Otherwise we deposit any items we might have in our inventory and withdraw more logs.
                if (Inventory.getCount() > 0) {
                    Bank.depositInventory();
                }
                Bank.withdraw(Firemaking.getSelectedLogs().getItemId(), 28);
            }
            // Close the bank!
            Bank.close();
        }
    }
}
