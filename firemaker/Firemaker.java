package firemaker;

import firemaker.data.Constants;
import firemaker.interactions.Firemaking;
import firemaker.nodes.AddLog;
import firemaker.nodes.DoBanking;
import firemaker.nodes.FireSpirit;
import firemaker.nodes.MakeFire;
import firemaker.utils.Logs;
import firemaker.ui.Paint;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.input.Mouse;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author nootz
 * @version 1.0
 * @since RSBot 4049
 */
@Manifest(authors = { "nootz" }, name = "Firemaker", description = "Example of a firemaking script.", version = 1.0)
public class Firemaker extends ActiveScript implements PaintListener, MessageListener, MouseListener {

    Node[] nodes = {new DoBanking(), new FireSpirit(), new MakeFire(), new AddLog()};

    // onStart is called as soon as the script is started.
    @Override
    public void onStart() {
        // Sets the mouse speed to Fast due to the default speed being somewhat slow.
        Mouse.setSpeed(Mouse.Speed.FAST);

        // Sets the selected logs and area to the specified values.
        Firemaking.setSelectedLogs(Logs.NORMAL);
        Firemaking.setFireArea(Constants.GRAND_EXCHANGE_SOUTHWEST);
    }

    @Override
    public int loop() {
        // If we have no more logs left in the bank, we want to stop the script.
        if (Firemaking.noLogsLeft()) {
            shutdown();
        } else {
            // Otherwise if we have a log and area selected, loop through our nodes array and if any are ready to go, execute them.
            if (Firemaking.getSelectedLogs() != null && Firemaking.getFireArea() != null) {
                for (Node n : nodes) {
                    if (n.activate()) {
                        n.execute();
                    }
                }
            }
        }
        return 50;
    }

    @Override
    public void messageReceived(MessageEvent e) {
        /*
         If a message is received, it will generate a MessageEvent. In this case we've defined it as "e".
         It's always a good idea to check if the id of the sender isn't 2, as this is the id of players
         and if it isn't filtered people may mess with your scripts.
         */
        if (e.getId() != 2 && e.getMessage().contains("You add a")) {
            // Add one to the total amount of logs burnt if the message contains "You add a".
            Firemaking.addToLogsBurnt(1);
        } else if (e.getId() != 2 && e.getMessage().contains("The fire spirit gives you a")) {
            Firemaking.addToSpiritsCaught(1);
        }
    }

    @Override
    public void onRepaint(Graphics g) {
        // Call our paint method from our Paint class.
        Paint.onRepaint(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /*
        NOTE: if you try and click on the applet without disabling input, you will obviously
        interact with the game. Click the "keyboard" icon in the top right corner next to
        the tools menu and select Keyboard or Blocked. You can now click on your paint buttons
        without causing any clicks to register in the game.

        Here we use a method in MouseListener to track our mouse when it's clicked.
        When you click your mouse on the applet it will send an event back,
        much like MessageListener. We can then use this to find where the mouse was clicked.
         */
        /*
        Here we have some rectangles defined. We will use these to check whether our buttons
        (referenced in ui/Paint and ui/PaintMethods) have been clicked on.
         */
        final Rectangle normal = new Rectangle(25, 170, 10, 10);
        final Rectangle oak = new Rectangle(25, 185, 10, 10);
        final Rectangle willow = new Rectangle(25, 200, 10, 10);
        final Rectangle maple = new Rectangle(25, 215, 10, 10);
        final Rectangle yew = new Rectangle(25, 230, 10, 10);
        final Rectangle magic = new Rectangle(25, 245, 10, 10);

        /*
        When the mouse is clicked, we use e.getPoint() to get the location of the click.
        Then we can check if any of our rectangles contained the click and set the selected
        logs to the value we associate that rectangle with.
         */
        if (normal.contains(e.getPoint())) {
            Firemaking.setSelectedLogs(Logs.NORMAL);
        } else if (oak.contains(e.getPoint())) {
            Firemaking.setSelectedLogs(Logs.OAK);
        } else if (willow.contains(e.getPoint())) {
            Firemaking.setSelectedLogs(Logs.WILLOW);
        } else if (maple.contains(e.getPoint())) {
            Firemaking.setSelectedLogs(Logs.MAPLE);
        } else if (yew.contains(e.getPoint())) {
            Firemaking.setSelectedLogs(Logs.YEW);
        } else if (magic.contains(e.getPoint())) {
            Firemaking.setSelectedLogs(Logs.MAGIC);
        }

    }

    /*
    While we won't be using the following methods, by implementing MouseListener we
    have to include them. We'll just leave them blank since nothing needs to be done
    with these events.
     */
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
