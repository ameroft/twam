package ame.ameroft.twom.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import ame.ameroft.twom.Handler;
import ame.ameroft.twom.gfx.Assets;
import ame.ameroft.twom.gfx.Text;


public class Chat {
	private static boolean active = false;
	private Handler handler;
	private String[] chatItems;
	private static  String message;
	private int alpha = 127; // 50% transparent
	private Color myColour = new Color(105,105,105, alpha);
	public Chat(Handler handler) {

		this.handler = handler;
		message = "";
	}
	public void render(Graphics g) {
		if(!active)
			return;
		
		
		g.setColor(myColour);
		g.fillRect(0, 440, 240, 40);
		g.setColor(Color.white);
		Text.drawString(g, message, 5, 475, false, Color.white, Assets.font28);
	}	
	public void update() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)) 
			active = !active;
		
		
	}
	public  static void getKey(char c) {
		if(active) {
		message +=c;
		}
	}
	public boolean isActive() {
		return active;
	}

}
