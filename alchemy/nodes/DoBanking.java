package alchemy.nodes;

import alchemy.interactions.Magic;
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
        // We only want to use the bank if we don't have the required items for alchemy.
        return !Magic.hasAlchItems();
    }

    @Override
    public void execute() {
        if (Bank.open()) {
            /*
            If we have:
                no items in our bank or inventory OR
                no nature runes in our bank or inventory OR
                no fire runes in our bank or inventory
            we want to stop the script.
             */
            if ((Magic.getBankItem() == null && Magic.getItem() == null)
                    || (Bank.getItemCount(true, Magic.getNatureRuneId()) < 1 && Inventory.getCount(true, Magic.getNatureRuneId()) < 1)
                    || (Bank.getItemCount(true, Magic.getFireRuneId()) < 5 && Inventory.getCount(true, Magic.getFireRuneId()) < 5)) {
                Magic.stop(true);
            } else {
                // Otherwise, we deposit our inventory.
                Bank.depositInventory();
                // Set the bank to withdraw as notes.
                Bank.setWithdrawNoted(true);
                // Then, while we haven't got our required items...
                while (!Magic.hasAlchItems()) {
                    // Withdraw our item.
                    if (Magic.getBankItem() != null) {
                        Bank.withdraw(Magic.getBankItem().getId(), 500);
                    }
                    // Withdraw our nature runes.
                    Bank.withdraw(Magic.getNatureRuneId(), 500);
                    // Withdraw our fire runes.
                    Bank.withdraw(Magic.getFireRuneId(), 2500);
                }
                // And finish up by closing the bank.
                Bank.close();
            }
        }
    }
}
