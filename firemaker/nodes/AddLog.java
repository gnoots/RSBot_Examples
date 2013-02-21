package firemaker.nodes;

import firemaker.interactions.Firemaking;
import org.powerbot.core.script.job.state.Node;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
public class AddLog extends Node {
    @Override
    public boolean activate() {
        // We only want to add a log to the fire if a fire exists.
        return Firemaking.getFire() != null;
    }

    @Override
    public void execute() {
        // We just add a log! Refer to the method in Firemaking to see how it works.
        Firemaking.addLog();
    }
}
