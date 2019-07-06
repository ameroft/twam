package ame.ameroft.twom.entities.mob;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.InetAddress;

import ame.ameroft.twom.Game;
import ame.ameroft.twom.Handler;
import ame.ameroft.twom.entities.Entity;
import ame.ameroft.twom.gfx.Animation;
import ame.ameroft.twom.gfx.Assets;
import ame.ameroft.twom.gfx.Text;
import ame.ameroft.twom.inventory.Inventory;
import ame.ameroft.twom.net.packets.Packet02Move;
import javafx.scene.input.KeyCode;


public class Player extends Mob{
	private Animation animDown,animUp,animLeft,animRight;
	private int m = 0;
	private long lastAttackTimer,AttackCoolDown = 100,attackTimer = AttackCoolDown;
	private Inventory inventory;
	public  InetAddress ipAddress;
	public int port;
	private boolean isMoving;
	
	public Player(Handler handler,String name,int level,float x, float y, InetAddress address, int port) {
		super(handler,name, level, x, y,Mob.DEFAULT_CREATURE_WIDTH,Mob.DEFAULT_CREATURE_HEIGHT);
		isMoving = false;
		this.ipAddress = address;
		this.port = port;
		
		bounds.x = 4;
		bounds.y = 11;
		bounds.width = 7;
		bounds.height = 8;
		//Millisecond
		animDown = new Animation(100,Assets.playerDown);
		animUp = new Animation(100,Assets.playerUp);
		animLeft = new Animation(100,Assets.playerLeft);
		animRight = new Animation(100,Assets.playerRight);
		
		inventory = new Inventory(handler);
		
	}
	public Inventory getInventory() {
		return inventory;
		
	}
	@Override
	public void update() {
		//Movement animation
		animDown.update();
		animUp.update();
		animLeft.update();
		animRight.update();
	//Movement
	getInput();
	move();
	handler.getGameCamera().centerOnEntity(this);
	//Attack
	checkAttacks();
	//Inventory
	inventory.update();
	}
	
	private void getInput() {
		xMove = 0;
		yMove = 0;
		
		if(inventory.isActive()) {
			return;
		}
		
		if(handler.getKeyManager().up) {
			yMove = -speed;
			dir = 0;
		}
		if(handler.getKeyManager().down) {
			yMove = +speed;
			dir = 1;
		}
		if(handler.getKeyManager().left) {
			xMove = -speed;
			dir = 2;
		}
		
		if(handler.getKeyManager().right) {
			xMove = +speed;
			dir =3;
		}
		
		if(handler.getKeyManager().F5) {
			Game.DEBUG = !Game.DEBUG;
		}
		if(handler.getKeyManager().one) {
			switch(dir) {
			case 0:
				y -=50;
				break;
			case 1:
				y +=50;
				break;
			case 2:
				x -=50;
				break;
			case 3:
				x +=50;
				break;
			}
			
		}
		Packet02Move packet  = null;
		switch(handler.getKeyManager().rKey) {
		
		case KeyEvent.VK_W:
			 packet = new Packet02Move(name, this.x,this.y, -1);
			packet.writeData(handler.getClient());
			break;
		case KeyEvent.VK_S:
			 packet = new Packet02Move(name, this.x, this.y, -1);
			packet.writeData(handler.getClient());
			break;
		case KeyEvent.VK_A:
			 packet = new Packet02Move(name, this.x, this.y, -1);
			packet.writeData(handler.getClient());
			break;
		case KeyEvent.VK_D:
			 packet = new Packet02Move(name, this.x, this.y, -1);
			packet.writeData(handler.getClient());
			break;
		}
		if(checkEntityCollision(xMove,0f)) {
			xMove =0;
		}
		else if(checkEntityCollision(0f,yMove)) {
			yMove = 0;
		}
		if (xMove != 0 || yMove != 0) {
			
			isMoving = true;
			 packet = new Packet02Move(name, this.x, this.y, dir);
			packet.writeData(handler.getClient());
			
		} else {
			isMoving = false;
			
		}
		
	}
	@Override
	public void render(Graphics g) {
		Text.drawString(g, name, (int)(x- handler.getGameCamera().getxOffset()),(int) (y- handler.getGameCamera().getyOffset() - 10), true, Color.WHITE, Assets.font28);
		if(handler.getKeyManager().one) {
			g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		}
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
	}
	public void postRender(Graphics g) {
		if(Game.DEBUG) {
			
			Text.drawString(g, "[" +x +","+y + "]", 400, 60+Assets.font28.getSize(), false, Color.WHITE, Assets.font28);
			g.setColor(Color.red);
			g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height);
		}
		inventory.render(g);
		
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
	private void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		
		if(attackTimer < AttackCoolDown) {
			return;
		}
		if(inventory.isActive()) {
			return;
		}
		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = 20;
		ar.width = arSize;
		ar.height = arSize;
		
		if(handler.getKeyManager().aUp) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		}
		else if(handler.getKeyManager().aDown) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		}
		else if(handler.getKeyManager().aLeft) {
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		}
		else if(handler.getKeyManager().aRight) {
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		}
		else {
			return;
		}
		attackTimer = 0;
		for(Entity e: handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)){
				continue;
			}
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(1);
				return;
			}
				
		}
	}
	@Override
	public void die() {
		System.out.println("Player has died!");
		
	}
	
	

}
