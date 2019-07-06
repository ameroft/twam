package ame.ameroft.twom.entities.statics;

import java.awt.Graphics;

import ame.ameroft.twom.Handler;
import ame.ameroft.twom.gfx.Assets;
import ame.ameroft.twom.items.Item;
import ame.ameroft.twom.tiles.Tile;

public class Tree extends StaticEntity{

	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, 64*2, 64*2);
		
		bounds.x = width/2-22;
		bounds.y = (height/2)+15;
		bounds.width = width-80;
		bounds.height =  (height/2)-20;
	}

	@Override
	public void update() {
	
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int)(x - handler.getGameCamera().getxOffset()),(int)(y - handler.getGameCamera().getyOffset()),null);
		//g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()),(int)(y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
	}

	public void die() {
		handler.getWorld().getItemManager().AddItem(Item.swordItem.createNew((int)handler.getWorld().getEntityManager().getPlayer().getX()-20, (int)handler.getWorld().getEntityManager().getPlayer().getY()-30));
		
	}

}
