package ame.ameroft.twom.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	public static Tile[] tiles = new Tile[256];
	public static Tile grassTile = new GrassTile(0);
	public static Tile dirtTile =  new DirtTile(1);
	public static Tile treeTile = new TreeTile(2);
	
	protected BufferedImage texture;
	protected final int ID;
	public static final int TILEWIDTH = 64,TILEHEIGHT =  64;
	public Tile(BufferedImage texture,int id) {
		this.texture = texture;
		ID = id;
		
		tiles[id] = this;
	}
	public void update() {
		
	}
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y,TILEWIDTH,TILEHEIGHT, null);
	}
	public boolean isSolid() {
		return false;
	}
	public int getId() {
		return ID;
	}
}
