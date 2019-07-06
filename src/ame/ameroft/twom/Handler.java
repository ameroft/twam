package ame.ameroft.twom;

import ame.ameroft.twom.gfx.GameCamera;
import ame.ameroft.twom.input.KeyManager;
import ame.ameroft.twom.input.MouseManager;
import ame.ameroft.twom.net.Client;
import ame.ameroft.twom.worlds.World;

public class Handler {
	private Game game;
	private World world;
	private Client client;
 public Handler(Game game) {
	 this.game = game;
 }
 public GameCamera getGameCamera() {
	 return game.getGameCamera();
 }
 public KeyManager getKeyManager() {
	 return game.getKeyManager();
 }
 public int getWidth() {
	 return game.getWidth();
 }
 public int getHeight() {
	 return game.getHeight();
 }
public Game getGame() {
	return game;
}
public void setGame(Game game) {
	this.game = game;
}
public World getWorld() {
	return world;
}
public MouseManager getMouseManager() {
	return game.getMouseManager();
}
public void setWorld(World world) {
	this.world = world;
}
public void setClient(Client client) {
	this.client = client;
	
}
public Client getClient(){
	return client;
}
}
