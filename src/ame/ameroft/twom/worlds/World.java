package ame.ameroft.twom.worlds;

import java.awt.Graphics;

import ame.ameroft.twom.Handler;
import ame.ameroft.twom.entities.EntityManager;
import ame.ameroft.twom.entities.mob.Player;
import ame.ameroft.twom.entities.statics.Slime;
import ame.ameroft.twom.entities.statics.Tree;
import ame.ameroft.twom.items.ItemManager;
import ame.ameroft.twom.tiles.Tile;
import ame.ameroft.twom.utils.Utils;

public class World {
 private int width,height;
 private int spawnX,spawnY;
 private int[][] tiles;
 private Handler handler;
 private EntityManager entityManager;
 private ItemManager itemManager;
 public World(Handler handler, String path) { 
	 this.handler = handler;
	 int x =  (int)(Math.random() * 200) + 100;
	 int y =  (int)(Math.random() * 200) + 100;
	 entityManager = new EntityManager(handler, new Player(handler,"Steven",1,x,y,null,-1));
	 itemManager = new ItemManager(handler);
	 entityManager.addEntity(new Tree(handler, 100, 200));
	 entityManager.addEntity(new Slime(handler, 300, 100));
	 
	 entityManager.addEntity(new Tree(handler, 400, 200));
	 entityManager.addEntity(new Slime(handler,600, 200));
	 
	 entityManager.addEntity(new Tree(handler, 400, 300));
	 entityManager.addEntity(new Slime(handler, 200, 500));
	 loadWorld(path);
	 
	 //entityManager.getPlayer().setX(x);
	// entityManager.getPlayer().setY(y);
 }
 
 private void loadWorld(String path) {
	 String file = Utils.loadFileAsString(path);
	 String[] tokens = file.split("\\s+");
	 width = Utils.parseInt(tokens[0]);
	 height = Utils.parseInt(tokens[1]);
	 spawnX = Utils.parseInt(tokens[2]);
	 spawnY = Utils.parseInt(tokens[3]);
	 
	 tiles = new int[width][	height];
	 
	 for(int x =0;x<width;x++) {
		 for(int y =0;y<height;y++) {
			 tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
		 }
		 }
 }
 public EntityManager getEntityManager() {
	return entityManager;
}

public void update() {
	 entityManager.update();
	 itemManager.update();
	 
 }
 public void render(Graphics g) {
	 //Efficiency
	 int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH)  ,
	xEnd = (int)Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1) ,
	yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT) ,
	yEnd = (int)Math.min(width, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);
	 
	 
	 for(int y =yStart;y<yEnd;y++) {
		 for(int x =xStart;x<xEnd;x++) {
			getTile(x,y).render(g, (int)(x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset() ), (int)(y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
		 }
	 }
	
	 
	 itemManager.render(g);
	 entityManager.render(g);
 }
 
 public Handler getHandler() {
	return handler;
}

public void setHandler(Handler handler) {
	this.handler = handler;
}

public ItemManager getItemManager() {
	return itemManager;
}

public void setItemManager(ItemManager itemManager) {
	this.itemManager = itemManager;
}

public int getWidth() {
	return width;
}


public int getHeight() {
	return height;
}



public Tile getTile(int x,int y) {
	 if(x < 0 || y < 0 || x >=width || y>= height) 
		 return Tile.grassTile;
	 
	 Tile t =  Tile.tiles[tiles[x][y]];
	 if(t == null) 
		 return Tile.dirtTile;
	  return t;
 }
}
