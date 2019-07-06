package ame.ameroft.twom.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import ame.ameroft.twom.Handler;
import ame.ameroft.twom.entities.Entity;
import ame.ameroft.twom.entities.mob.Player;
import ame.ameroft.twom.entities.mob.PlayerMP;
import ame.ameroft.twom.net.packets.Packet;
import ame.ameroft.twom.net.packets.Packet.PacketTypes;
import ame.ameroft.twom.net.packets.Packet00Login;
import ame.ameroft.twom.net.packets.Packet01Disconnect;
import ame.ameroft.twom.net.packets.Packet02Move;

public class Client  extends Thread{

	private InetAddress aHost;
	private DatagramSocket socket;
	private Handler handler;
	private ArrayList<PlayerMP> connectedPlayers;
	
	public Client(Handler handler, String ipAddress){
		this.handler = handler;
		connectedPlayers = new ArrayList<PlayerMP>();
		try {
			this.socket = new DatagramSocket();
			this.aHost = InetAddress.getByName(ipAddress);
			
			Player p = handler.getWorld().getEntityManager().getPlayer();
			Packet packet = new Packet00Login(p.getName(),(int)p.getX(),(int)p.getY());
			
			DatagramPacket pp = new DatagramPacket(packet.getData(),packet.getData().length,aHost,1022);
			socket.send(pp);
			
		} catch (SocketException e) {

			e.printStackTrace();
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run(){
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data,data.length);
			try {
				socket.receive(packet);
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			//System.out.println("Recieving: " +new String(data).trim());
			
			parsePacket(packet.getData(),packet.getAddress(),packet.getPort());
			
		}
	}
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String info = new String(data).trim();
		Packet packet = null;
		
			PacketTypes type = Packet.lookupPacket(Integer.parseInt(info.substring(0,2)));
			
			switch(type) {
			
			case INVALID:
				break;
			case LOGIN:
				 packet = new Packet00Login(data);
		         handleLogin((Packet00Login) packet, address, port);
		         
				break;
			case DISCONNECT:
				  packet = new Packet01Disconnect(data);
		            System.out.println("[" + address.getHostAddress() + ":" + port + "] "
		                    + ((Packet01Disconnect) packet).getUsername().substring(2) + " has left the world...");
		            
		            this.removeConnection((Packet01Disconnect) packet);
				break;
			 case MOVE:
		            packet = new Packet02Move(data);
		         //  System.out.println(new String(packet.getData()).trim());
		            handleMove((Packet02Move) packet);
		        }
			}

		
	public void sendData(byte[] data){
		DatagramPacket packet = new DatagramPacket(data,data.length,aHost,1022);
		try {
			//System.out.println("[Client] >> " + new String(packet.getData()).trim());
			socket.send(packet);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	} 

    private void handleLogin(Packet00Login packet, InetAddress address, int port) {
        System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername()
                + " has joined the game...");
        
        String  name =  packet.getUsername();
        float x = packet.getX();
        float y = packet.getY();
        
        PlayerMP player = new PlayerMP(handler, ((Packet00Login) packet).getUsername(), 1, x, y, address, port);
        this.connectedPlayers.add(player);
        
        handler.getWorld().getEntityManager().addEntity(new PlayerMP(handler,name,1,x,y,null,-1));
      
    }
    private void handleMove(Packet02Move packet) {
    	int dir = packet.getMovingDir();
    	String name = packet.getUsername();
    	 Entity e = handler.getWorld().getEntityManager().getEntity(name);
    	if(!name.equals(handler.getWorld().getEntityManager().getPlayer().getName())) {
    	
    		((PlayerMP)e).setX(packet.getX());
    		((PlayerMP)e).setY(packet.getY());
    		((PlayerMP)e).setDir(dir);

    	}
    }
	 public void removeConnection(Packet01Disconnect packet) {
		 String name = packet.getUsername().substring(2);
	        this.connectedPlayers.remove(getPlayerIndex(name));
	        Entity e = handler.getWorld().getEntityManager().getEntity(name);
	        handler.getWorld().getEntityManager().getEntities().remove(e);
	    }	

	 public int getPlayerIndex(String username) {
	        int index = 0;

	        Iterator<PlayerMP> iter = this.connectedPlayers.iterator();
	        
			 while (iter.hasNext()) {
			     Entity str = iter.next();
		        	if(str.getName().equals(username))
		        			break;
		        	
		        	index++;
			 }
	        return index;
	    }

}
