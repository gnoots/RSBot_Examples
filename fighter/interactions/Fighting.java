package fighter.interactions;

import fighter.utils.Methods;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;

import java.util.HashSet;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */

public class Fighting {

    /**
     * Filter for determining which NPCs should be targeted.
     */

    private static final Filter<NPC> npcFilter = new Filter<NPC>(){
        @Override
        public boolean accept(NPC n) {
            return n != null && (n.getHealthRatio() > 0 && n.getLocation().canReach() && getNpcArea().contains(n.getLocation())  &&
                    ((getNpcList().contains(n.getId()) && !n.getLocation().equals(Players.getLocal().getLocation()) && !n.isInCombat()) || n.getInteracting().equals(Players.getLocal())));
        }
    };

    /**
     * Sets the id(s) of the NPCs to attack.
     * @param ids the id(s) of the NPCs to attack.
     */

    public static void setNpc(int... ids) {
        for (int i : ids) {
            npcIds.add(i);
        }
    }

    /**
     * Sets the area that NPCs will be attacked in.
     * @param a the area that NPCs will be attacked in.
     */

    public static void setNpcArea(Area a) {
        npcArea = a;
    }

    /**
     * Sets the path that will be travelled to get to the NPC.
     * @param t the path to the NPC.
     */

    public static void setNpcPath(Tile[] t) {
        npcPath = t;
    }

    /**
     * Returns a HashSet (list) of selected NPC ids.
     * @return the HashSet of selected NPC ids.
     */

    public static HashSet<Integer> getNpcList() {
        return npcIds;
    }

    /**
     * Returns selected area for NPC attacking.
     * @return the selected area for NPC attacking.
     */

    public static Area getNpcArea() {
        return npcArea;
    }

    /**
     * Returns selected path to NPC.
     * @return the selected path to NPC.
     */

    public static Tile[] getNpcPath() {
        return npcPath;
    }

    /**
     * Returns the filter that chooses NPCs.
     * @return the filter that chooses NPCs.
     */

    public static Filter<NPC> getNpcFilter() {
        return npcFilter;
    }

    /**
     * Returns the nearest NPC from the NPC filter.
     * @return the nearest NPC from the NPC filter.
     */

    public static NPC getNpc() {
        return NPCs.getNearest(getNpcFilter());
    }

    /**
     * Attacks the nearest NPC from the NPC filter.
     * @return true if attack is successful.
     */

    public static boolean attackNpc() {
        /*
        Define the NPC. By placing this inside a method, the NPC is "refreshed"
        each time it is called and won't be stuck in the same state.
         */
        NPC npc = getNpc();
        if (Methods.isOnScreen(npc)) {
            /*
            Interact with the NPC, choosing "Attack". npc.getName() makes sure that the
            NPC being targeted is the one we want.
            */
            npc.interact("Attack", npc.getName());
            // Return true for the boolean if we have successfully clicked.
            return true;
        } else {
            // Turn to the item if it isn't on the screen.
            Camera.turnTo(npc);
            // If the item still isn't on screen after turning to it, we want to walk to it.
            if (!Methods.isOnScreen(npc)) {
                Walking.walk(npc);
            }
        }
        // Return false for the boolean if we haven't been able to do any of the above.
        return false;
    }

    /*
    Our variables that we will be changing with the above methods.
     */
    private static HashSet<Integer> npcIds = new HashSet<>();
    private static Area npcArea;
    private static Tile[] npcPath;

}
