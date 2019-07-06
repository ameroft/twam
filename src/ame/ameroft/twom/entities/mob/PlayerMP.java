package ame.ameroft.twom.entities.mob;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.InetAddress;

import ame.ameroft.twom.Game;
import ame.ameroft.twom.Handler;
import ame.ameroft.twom.gfx.Animation;
import ame.ameroft.twom.gfx.Assets;
import ame.ameroft.twom.gfx.Text;
import ame.ameroft.twom.net.packets.Packet02Move;

public class PlayerMP extends Mob {
	private Animation animDown,animUp,animLeft,animRight;
	private int m = 0;
	public  InetAddress ipAddress;
	public int port;
	
	public PlayerMP(Handler handler, String name, int level, float x, float y, InetAddress address, int port) {
		super(handler,name, level, x, y,Mob.DEFAULT_CREATURE_WIDTH,Mob.DEFAULT_CREATURE_HEIGHT);
		
		this.ipAddress = address;
		this.port = port;
		
		dir = -1;
		
	
		
		bounds.x = 4;
		bounds.y = 11;
		bounds.width = 7;
		bounds.height = 8;
		//Millisecond
		animDown = new Animation(100,Assets.playerDown);
		animUp = new Animation(100,Assets.playerUp);
		animLeft = new Animation(100,Assets.playerLeft);
		animRight = new Animation(100,Assets.playerRight);
	}
	
	public void setDir(int dir) {
		this.dir = dir;
	}
	@Override
	public void update() {
		checkMove();
		
		//Movement animation
		animDown.update();
		animUp.update();
		animLeft.update();
		animRight.update();
		
		
		
	
	}
	@Override
	public void die() {
	
		
	}
	
	@Override
	public void render(Graphics g) {
		Text.drawString(g, name, (int)(x- handler.getGameCamera().getxOffset()),(int) (y- handler.getGameCamera().getyOffset() - 10), true, Color.WHITE, Assets.font28);
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

	
	}
	
	public void checkMove() {
		xMove = 0;
		yMove = 0;
		
		
		if(dir ==0) {
			yMove = -speed;
			
		}
		if(dir == 1) {
			yMove = +speed;
			
		}
		if(dir == 2) {
			xMove = -speed;
			
		}
		if(dir == 3) {
			xMove = +speed;
		}
	
		
		if (xMove != 0 || yMove != 0) {
			
			isMoving = true;
			
		} else {
			isMoving = false;
		}
	}
	
	private BufferedImage getCurrentAnimationFrame() {
		
		
		if(xMove <0) {
			m = 1;
			return animLeft.getCurrentFrame();
			
		}
		else if(xMove > 0) {
			m = 2;
			return animRight.getCurrentFrame();
		}
		else if(yMove <0) {
			m = 3;
			return animUp.getCurrentFrame();
		}
		else if(yMove > 0) {
			m = 4;
			return animDown.getCurrentFrame();
		}
		else {
			if(m == 1) {
				return Assets.playerLeft[0];
			}
			else 	if(m == 2) {
				return Assets.playerRight[0];
			}
			else 	if(m == 3) {
				return Assets.playerUp[0];
			}
			else {
				return Assets.playerDown[0];
			}
		}
		
	}

}
