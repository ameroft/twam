package ame.ameroft.twom.entities.mob;

import ame.ameroft.twom.Handler;
import ame.ameroft.twom.entities.Entity;
import ame.ameroft.twom.tiles.Tile;

public abstract class Mob extends Entity {

	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 14,DEFAULT_CREATURE_HEIGHT = 21;
	protected int numSteps = 0;
	protected boolean isMoving;
	protected int dir = 1;
	//Characteristics of each mob
	protected int level;
	protected String tag;

	protected float speed;
	protected float xMove,yMove;
	public Mob(Handler handler,String name,int level, float x, float y,int width,int height) {
		super(handler,x, y, width, height);

		this.level = level;
		this.name = name;

		speed = DEFAULT_SPEED;
		xMove =0;
		yMove =0;
		tag = "#0000";
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void move() {
		if(!checkEntityCollision(xMove,0f))
			moveX();
		if(!checkEntityCollision(0f,yMove))
			moveY();
	}
	
	public void moveX() {
		if(xMove > 0){//Moving right
			dir = 3;

			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;

			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
				x += xMove;
			}else{
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			}

		}else if(xMove < 0){//Moving left
			dir = 2;

			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;

			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
				x += xMove;
			}else{
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
			}

		}

	}
	public void moveY() {
		if(yMove < 0){//Up
			dir = 0;
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)){
				y += yMove;
			}else{
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			}

		}else if(yMove > 0){//Down
			dir = 1;

			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;

			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
					!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)){
				y += yMove;
			}else{
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			}

		}
	}
	protected boolean collisionWithTile(int x,int y ) {
		return handler.getWorld().getTile(x, y).isSolid();
	}
	public float getxMove() {
		return xMove;
	}
	public void setxMove(float xMove) {
		this.xMove = xMove;
	}
	public float getyMove() {
		return yMove;
	}
	public void setyMove(float yMove) {
		this.yMove = yMove;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
