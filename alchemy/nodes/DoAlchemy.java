package alchemy.nodes;

import alchemy.interactions.Magic;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class DoAlchemy extends Node {

    @Override
    public boolean activate() {
        // We only want to use alchemy if we have the necessary items.
        return Magic.hasAlchItems();
    }

    @Override
    public void execute() {
        // If the ability bar isn't up, we'll click the maximise button.
        if (!Magic.isAbilityBarUp()) {
            // The maximise button.
            Widgets.get(640, 3).click(true);
        } else {
            // We alchemise an item with a 0.6 second delay.
            Magic.alchemiseItem(600);
            // Another dynamic sleep. While we're alchemising an item, we sleep.
            Timer wait = new Timer(Random.nextGaussian(2500, 3000, 300));
            while (Magic.isAlchemising() && wait.isRunning()) {
                Task.sleep(50);
            }
        }
    }
}
