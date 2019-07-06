package ame.ameroft.twom.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import ame.ameroft.twom.Handler;
import ame.ameroft.twom.entities.mob.Player;

public class EntityManager {
	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	//Defines a comparator that sorts the listed entities array.
	private Comparator<Entity> rSort = new Comparator<Entity>() {

		@Override
		public int compare(Entity e1, Entity e2) {
			if(e1.getY() +e1.getHeight() < e2.getY() + e2.getHeight()) {
				return -1;
			}
			
			return 1;
		}
		
	};
	/**
	 * Controls and contains all entities in the game
	 * @param handler
	 * @param player
	 */
	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		//Initializes hte entity array and adds the player
		entities = new ArrayList<Entity>();
		entities.add(player);
	}
	//Every update the iterator cycles through all the entities and updates them.
	public void update() {
		Iterator<Entity> it = entities.iterator();
	while(it.hasNext()) {
			Entity e = it.next();
			e.update();
			//If the entities active state is false then it is removed.
			if(!e.isActive()) {
				it.remove();
			}
		}
	//Sorts the array again
		entities.sort(rSort);
		
		
			}
	/**
	 * Renders all the entities
	 * @param g
	 */
	public void render(Graphics g) {
		for(Entity e:entities) {
		
			e.render(g);
		}
		player.postRender(g);
		
	}
	public void addEntity(Entity e) {
		entities.add(e);
	}
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
		
	}
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	//Using an Iterator, searches and returns the specified entity.
	 public Entity getEntity(String username) {
		 Iterator<Entity> iter = getEntities().iterator();

		 while (iter.hasNext()) {
		     Entity str = iter.next();
	        	if(str.getName().equals(username))
	        			return str;
		 }
	        return null;
	    }
}
