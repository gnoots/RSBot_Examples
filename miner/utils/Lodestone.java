package miner.utils;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 * Teleports using the Lodestone system. Requires the selected destination to be unlocked.
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */


public enum Lodestone {

    LUMBRIDGE(47, 69836),
    DRAYNOR(44, 69833),
    VARROCK(51, 69840),
    FALADOR(46, 69835),
    PORTSARIM(48, 69837),
    EDGEVILLE(45, 69834),
    BURTHORPE(42, 69831),
    ALKHARID(40, 69829),
    TAVERLY(50, 69839),
    CATHERBY(43, 69832),
    SEERSVILLAGE(49, 69838),
    ARDOUGNE(41, 69830),
    YANILLE(52, 69841),
    LUNARISLE(39, 69828),
    BANDITCAMP(7, 69827);

    private final int id;
    private final int ent;

    Lodestone(int id, int ent) {
        this.id = id;
        this.ent = ent;
    }

    /**
     * Returns the WidgetChild id of the selected location.
     * @return WidgetChild id of the Lodestone button on the teleport interface.
     */

    public int getWidgetChildId() {
        return id;
    }

    /**
     * Returns the SceneObject id of the selected location.
     * @return SceneObject id of the Lodestone entity.
     */

    public int getSceneEntityId() {
        return ent;
    }

    /**
     * Returns true if the selected Lodestone is loaded.
     * @return true if the selected Lodestone is loaded.
     */

    public boolean hasArrived() {
        return SceneEntities.getLoaded(ent) != null;
    }

    /**
     * Teleports to the selected location. e.g. Lodestone.LUMBRIDGE.teleport()
     * @return true if the Lodestone is loaded at selected location.
     */

    public boolean teleport() {
        if (Players.getLocal().getAnimation() == -1) {
            Timer wait = new Timer(1);
            final WidgetChild TELEPORT_TAB = Widgets.get(275, 46);
            if (Tabs.ABILITY_BOOK.open() && !TELEPORT_TAB.visible()) {
                WidgetChild magicTabButton = Widgets.get(275, 41);
                magicTabButton.click(true);
            } else {
                final WidgetChild HOME_TELEPORT = Widgets.get(275, 16).getChildren()[155];
                if (!HOME_TELEPORT.visible()) {
                    TELEPORT_TAB.click(true);
                } else {
                    WidgetChild lodeButton = Widgets.get(1092, id);
                    if (!lodeButton.validate()) {
                        HOME_TELEPORT.click(true);
                        wait.setEndIn(Random.nextGaussian(3000,5000,400));
                        while (!lodeButton.validate() && wait.isRunning()) {
                            Task.sleep(25, 50);
                        }
                    } else {
                        lodeButton.click(true);
                        wait.setEndIn(Random.nextGaussian(3000,5000,400));
                        while (wait.isRunning()) {
                            Task.sleep(25, 50);
                        }
                    }
                }
            }
        }
        return hasArrived();
    }

}
