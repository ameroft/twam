package ame.ameroft.twom.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIObject{
	private BufferedImage[] images;
	private ClickListener clicker;
	public UIImageButton(float x, float y, int width, int height,BufferedImage[] images,ClickListener clicker) {
		super(x, y, width, height);
		this.images = images;
		this.clicker = clicker;
	}

	@Override
	public void update() {
	
		
	}

	@Override
	public void render(Graphics g) {
	
		if(hovering)
			g.drawImage(images[1],(int)x,(int)y,width,height,null);
		else
			g.drawImage(images[0], (int)x, (int)y, null);
	}

	@Override
	public void onClick() {
		clicker.onClick();
		
	}
	public void drawAddOn(Graphics g, int type) {
	if(type == 0) {
		if(hovering)
			g.drawImage(images[4],(int)((x-images[0].getWidth() + 80)/2), (int)y,null);
		else
			g.drawImage(images[2],(int)((x-images[0].getWidth() + 60)/2), (int)y,null);
	}
	}

}
