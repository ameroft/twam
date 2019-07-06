package ame.ameroft.twom.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ame.ameroft.twom.ui.Chat;
import javafx.scene.input.KeyCode;

public class KeyManager implements KeyListener {

	private boolean[] keys,justPressed,cantPress;
	public boolean up,down,left,right;
	public boolean aUp, aDown,aLeft,aRight;
	public boolean F1,F2,F3,F4,F5,F6,F7,F8,F9,F10,F11,F12;
	public String keyCache;
	public int rKey;
	public boolean one;
	public KeyManager() {
		keys = new boolean [256];
		justPressed= new boolean[keys.length];
		cantPress = new boolean[keys.length];
		keyCache = "";
		rKey = -1;
	}
	public boolean keyJustPressed(int keyCode){
		if(keyCode < 0 || keyCode >= keys.length)
			return false;
		return justPressed[keyCode];
	}
	public void update() {
		for(int i =0;i<keys.length;i++) {
			if(cantPress[i] && !keys[i]) {
				cantPress[i] = false;
			}
			else if(justPressed[i]) {
				cantPress[i] = true;
				justPressed[i] = false;
			}
			if(!cantPress[i] && keys[i]) {
				justPressed[i] = true;
			}
			
		}

		
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		
		aUp = keys[KeyEvent.VK_UP];
		aDown = keys[KeyEvent.VK_DOWN];
		aLeft = keys[KeyEvent.VK_LEFT];
		aRight = keys[KeyEvent.VK_RIGHT];
		
		F5 = keyJustPressed(KeyEvent.VK_F5);
		
		one = keyJustPressed(KeyEvent.VK_1);
		
	
			
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
			return;
		
		keys[e.getKeyCode()] = true;
		Chat.getKey(e.getKeyChar());
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;

		rKey = e.getKeyCode();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		keyCache +=e.getKeyChar();
		
	}

}
