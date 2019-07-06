package ame.ameroft.twom.entities.statics;

import java.awt.Graphics;

import ame.ameroft.twom.Handler;
import ame.ameroft.twom.gfx.Assets;
import ame.ameroft.twom.items.Item;

public class Slime extends StaticEntity {

	public Slime(Handler handler, float x, float y) {
		super(handler, x, y, 24, 16);
		
		
	}

	@Override
	public void update() {
		
		
	}

	@Override
	public void render(Graphics g) {
	g.drawImage(Assets.slime[0], (int)(x + bounds.x - handler.getGameCamera().getxOffset()),(int)(y + bounds.y - handler.getGameCamera().getyOffset()), null);
		
	}

	public void die() {
		handler.getWorld().getItemManager().AddItem(Item.dirtItem.createNew((int)handler.getWorld().getEntityManager().getPlayer().getX()-20, (int)handler.getWorld().getEntityManager().getPlayer().getY()-30));

		
	}

}
