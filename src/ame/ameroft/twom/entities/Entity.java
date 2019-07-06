package ame.ameroft.twom.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import ame.ameroft.twom.Handler;

public abstract class Entity {
	
	public static final int  DEFAULT_HEALTH = 10;
	
	protected Handler handler;
	protected  float x ,y;
	protected int width,height;
	protected Rectangle bounds;
	protected int hp;
	private boolean active = true;
	protected String name;
	
	//Is it active on the screen
	public boolean isActive() {
		return active;
	}
	public Entity(Handler handler, float x,float y,int width,int height) {
		//Sets the default attributes of any entity
		this.handler = handler;
		this.x = x;
		this.y = y;
		hp = DEFAULT_HEALTH;
		this.width = width;
		this.height = height;
		this.name = "STATIC";
		bounds  = new Rectangle(0,0,width,height);
	}
	public abstract void update();
	
	//Checks entity collision, cycles through all entities and checks if any of them intersect with each other.
	public boolean checkEntityCollision(float xOffset,float yOffset) {
		for(Entity e:handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
				return true;
			}
		
				
			
		}
		return false;
		
	}
	public abstract void die();
	//If the entity is attacked
	public void hurt(int dmg) {
		hp -=dmg;
		if(hp <=0) {
			active = false;
			die();
		}
	}
	//Return the collision bounds of the specified entity.
	public Rectangle getCollisionBounds(float xOffset,float yOffset) {
		return new Rectangle((int)(x + bounds.x + xOffset),(int)(y + bounds.y + yOffset),bounds.width,bounds.height);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public abstract void render(Graphics g);
}
