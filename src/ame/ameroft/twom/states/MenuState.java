package ame.ameroft.twom.states;

import java.awt.Graphics;

import ame.ameroft.twom.Handler;
import ame.ameroft.twom.gfx.Assets;
import ame.ameroft.twom.ui.ClickListener;
import ame.ameroft.twom.ui.UIImageButton;
import ame.ameroft.twom.ui.UIManager;

public class MenuState extends State {
	private UIManager uiManager;

	
	public MenuState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		
		uiManager.addObject(new UIImageButton(300-(290/2),(150-40)/2,290,40,Assets.menu,new ClickListener() {
			
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
				
			}
			
		}));
		uiManager.addObject(new UIImageButton(300-(290/2),((150-40)/2)*2,290,40,Assets.menu,new ClickListener() {
			
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				System.exit(1);
				
			}
			
		}));
		handler.getMouseManager().setUIManager(uiManager);
	}
	@Override
	public void update() {
		uiManager.update();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.menu[3], 0,0,600,300,null);
		uiManager.render(g);
		if(((UIImageButton)(uiManager.getObjects().get(0))).isHovering() == true)
		((UIImageButton)(uiManager.getObjects().get(0))).drawAddOn(g, 0);
		else
		((UIImageButton)(uiManager.getObjects().get(1))).drawAddOn(g, 0);
	}

}
