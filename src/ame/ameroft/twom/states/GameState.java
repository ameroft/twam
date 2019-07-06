package ame.ameroft.twom.states;

import java.awt.Graphics;

import ame.ameroft.twom.Handler;
import ame.ameroft.twom.entities.mob.Player;
import ame.ameroft.twom.entities.statics.Tree;
import ame.ameroft.twom.worlds.World;

public class GameState extends State{
	
	private World world;
	
	public GameState(Handler handler) {
		super(handler);
		world = new World(handler, "/worlds/world1.txt");
		handler.setWorld(world);
		
	}

	public void update() {
		world.update();
	
		
	}


	public void render(Graphics g) {
		world.render(g);

		
	}

}
