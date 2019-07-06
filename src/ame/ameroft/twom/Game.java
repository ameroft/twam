package ame.ameroft.twom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JOptionPane;

import ame.ameroft.twom.display.Display;
import ame.ameroft.twom.display.WindowHandler;
import ame.ameroft.twom.entities.mob.PlayerMP;
import ame.ameroft.twom.gfx.Assets;
import ame.ameroft.twom.gfx.GameCamera;
import ame.ameroft.twom.gfx.Text;
import ame.ameroft.twom.input.KeyManager;
import ame.ameroft.twom.input.MouseManager;
import ame.ameroft.twom.net.Client;
import ame.ameroft.twom.states.GameState;
import ame.ameroft.twom.states.MenuState;
import ame.ameroft.twom.states.State;


public class Game implements Runnable {
	//ENABLE OR DISABLE DEBUG MODE
	public static boolean DEBUG = false;
	private Display display;
	private String title;
	
	private int width,height;
	private boolean running = false;
	private Thread thread;


	private BufferStrategy bs;
	private Graphics g;


	public State gameState;
	public State menuState;

	private GameCamera gameCamera;

	private Handler handler;
	//frames
	private int dfps;

	private KeyManager keyManager;
	private MouseManager mouseManager;

	private Client client;
	private WindowHandler windowHandler;



	public Game(String title,int width, int height) {
		/**
		 * Set the Width, Height and the Title of the Game.
		 * Initialize the KeyManager and MouseManager Objects.
		 */
		this.width = width;
		this.height = height;
		this.title =  title;
		keyManager =  new KeyManager();
		mouseManager = new MouseManager();
	}
	//Called every frame
	private void update() {
		//Calls the KeyManager Updater
		keyManager.update();
		//Always checks if the game is in a state, if it is update the game state.
		if(State.getState() !=null)
			State.getState().update();

		//If debug is enabled
		if(DEBUG) {
			
		}

	}
	//Initializes the Game
	private void init() {
		/**
		 * Initializes the Display Object, sets the various listeners for the window.
		 */
		display = new Display(title,width,height);

		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		//Initializes the Assets
		Assets.init();
		//Game Handler is Initialized and passes the game
		handler = new Handler(this);

		
	
		 // Following ask for the users name and then sets the game camera, state and menu state always passing handler. 
		
		String name = JOptionPane.showInputDialog("NAME?");

		gameCamera = new GameCamera(handler,0,0);
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		// Sets the state
		State.setState(gameState);
		//Gets the entity manager and sets the players name
		handler.getWorld().getEntityManager().getPlayer().setName(name);
		//Client initialization for multiplier, sets ip.
client = new Client(handler,"localhost");
		client.start();
		handler.setClient(client);
		//Initializes the window.
		windowHandler = new WindowHandler(handler,display);
//System.out.println("I am " + name);
//System.out.println(handler.getWorld().getEntityManager().getPlayer().getX() + "," + handler.getWorld().getEntityManager().getPlayer().getY());


	}
	//Called every frame
	private void render() {
		//Sets the bufferer 
		bs = display.getCanvas().getBufferStrategy();
		//If there is no bufferer create one then exit.
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		//Initializes the buffer graphics
		g = bs.getDrawGraphics();
		//Clear screen
		g.clearRect(0, 0, width, height);

		//Draw here!
		//Checks if the state is not null and then calls the render method of whatever state is set.
		if(State.getState() !=null)
			State.getState().render(g);
		//If debug mode is enabled
		if(DEBUG) {
			//Draws FPS
		Text.drawString(g, String.valueOf(dfps), display.getCanvas().getWidth()  - 50,50, false,Color.white,Assets.font28);
		}
		
		//End drawing!
		
		bs.show();
		g.dispose();
	}
	//The engine of everything
	public void run() {
		//Calls the initialize method
		init();
		//Sets the fps 
		int fps = 60;
		
		double nano = 1000000000/fps;
		double delta = 0f;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int frames = 0;
		/**
		 * The following is the Game loop, one of the most popular versions.
		 */
		while(running) {
			now = System.nanoTime();
			delta +=(now-lastTime)/nano;
			timer += now - lastTime;
			lastTime = now;

			if(delta >= 1) {
				update();
				render();
				delta--;
				frames+=1;

			}
			if(timer >=1000000000) {
				dfps = frames;
				frames = 0;
				timer = 0;
			}


		}
		stop();
	}
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	public KeyManager getKeyManager() {
		return keyManager;
	}
	public GameCamera getGameCamera() {
		return gameCamera;
	}
	public Display getDisplay() {
		return display;
	}
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	//Multithreading the game, so things can run simultaneously 
	public synchronized void start() {
		if(running)
			return;

		running = true;
		thread = new Thread(this,"main");
		thread.start();



	}
	public synchronized void stop() {
		if(!running)
			return;

		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
