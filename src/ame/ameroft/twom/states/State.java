package ame.ameroft.twom.states;

import java.awt.Graphics;

import ame.ameroft.twom.Game;
import ame.ameroft.twom.Handler;

public abstract class State {
	
private static State currentState = null;

public static void setState(State state) {
	currentState = state;
}
public static State getState() {
	return currentState;
}
//CLASS
protected Game game;
protected Handler handler;
public State(Handler handler) {
	this.handler = handler;
}
public abstract void update();
public abstract void render(Graphics g);
	

}
