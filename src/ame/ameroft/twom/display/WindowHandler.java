package ame.ameroft.twom.display;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import ame.ameroft.twom.Game;
import ame.ameroft.twom.Handler;
import ame.ameroft.twom.net.packets.Packet01Disconnect;


public class WindowHandler implements WindowListener {


    private final Handler handler;
    public WindowHandler(Handler handler, Display display) {
      
        this.handler = handler;
        display.getFrame().addWindowListener(this);
    }

    @Override
    public void windowActivated(WindowEvent event) {
    }

    @Override
    public void windowClosed(WindowEvent event) {
    	System.exit(1);
    }

    @Override
    public void windowClosing(WindowEvent event) {
    
        Packet01Disconnect packet = new Packet01Disconnect(handler.getWorld().getEntityManager().getPlayer().getName());
        packet.writeData(handler.getClient());
        System.exit(1);
    }

    @Override
    public void windowDeactivated(WindowEvent event) {
    }

    @Override
    public void windowDeiconified(WindowEvent event) {
    }

    @Override
    public void windowIconified(WindowEvent event) {
    }

    @Override
    public void windowOpened(WindowEvent event) {
    }

}
