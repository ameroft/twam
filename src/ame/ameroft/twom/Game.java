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
		this.width = width;
		this.height = height;
		this.title =  title;
		keyManager =  new KeyManager();
		mouseManager = new MouseManager();
	}

	private void update() {
		keyManager.update();
		if(State.getState() !=null)
			State.getState().update();

		
		if(DEBUG) {
			
		}

	}
	private void init() {
		display = new Display(title,width,height);

		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();

		handler = new Handler(this);

		

		String name = JOptionPane.showInputDialog("NAME?");

		gameCamera = new GameCamera(handler,0,0);
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		State.setState(gameState);
		
		handler.getWorld().getEntityManager().getPlayer().setName(name);
client = new Client(handler,"localhost");
		client.start();
		handler.setClient(client);

		windowHandler = new WindowHandler(handler,display);
//System.out.println("I am " + name);
//System.out.println(handler.getWorld().getEntityManager().getPlayer().getX() + "," + handler.getWorld().getEntityManager().getPlayer().getY());


	}
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear screen
		g.clearRect(0, 0, width, height);

		//Draw here!

		if(State.getState() !=null)
			State.getState().render(g);
		
		if(DEBUG) {
		Text.drawString(g, String.valueOf(dfps), display.getCanvas().getWidth()  - 50,50, false,Color.white,Assets.font28);
		}
		
		//End drawing!

		bs.show();
		g.dispose();
	}
	public void run() {

		init();
		int fps = 60;
		double nano = 1000000000/fps;
		double delta = 0f;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int frames = 0;
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
