package ame.ameroft.twom.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {
	private static final int pWIDTH = 14,pHEIGHT = 21;
	public static BufferedImage[] playerDown,playerUp,playerLeft,playerRight,menu,slime;
	public static BufferedImage[][] items;
	public static Font font28;

	public static BufferedImage grass,dirt,stone,tree,mCursor,fountain,inventoryScreen;
	public static void init() {
		playerDown = new BufferedImage[6];
		playerUp = new BufferedImage[6];
		playerLeft =  new BufferedImage[6];
		playerRight = new BufferedImage[6];
		menu = new BufferedImage[5];
		font28 = FontLoader.loadFont("fonts/slkscr.ttf", 28);
		SpriteSheet playerDownStrip = new SpriteSheet(ImageLoader.loadImage("/textures/spr_player_down_strip.png"));
		SpriteSheet playerUpStrip = new SpriteSheet(ImageLoader.loadImage("/textures/spr_player_up_strip.png"));
		SpriteSheet playerLeftStrip = new SpriteSheet(ImageLoader.loadImage("/textures/spr_player_left_strip.png"));
		SpriteSheet playerRightStrip = new SpriteSheet(ImageLoader.loadImage("/textures/spr_player_right_strip.png"));
		SpriteSheet itemsSheet = new SpriteSheet(ImageLoader.loadImage("/items/items.png"));
		SpriteSheet slimeSheet = new SpriteSheet(ImageLoader.loadImage("/items/slime.png"));
		SpriteSheet trees = new SpriteSheet(ImageLoader.loadImage("/textures/tree-variations.png"));
		dirt = ImageLoader.loadImage("/textures/dirt.png");
		grass = ImageLoader.loadImage("/textures/grass.png");
		fountain = ImageLoader.loadImage("/items/fountain.gif");
		tree = trees.crop(64*6,160, 64*2, 64*2);
		
		inventoryScreen = ImageLoader.loadImage("/textures/inventoryScreen.png");
		
		mCursor = ImageLoader.loadImage("/ui/mouseCursor.gif");
		menu[0] = ImageLoader.loadImage("/ui/box.png");
		menu[1] = ImageLoader.loadImage("/ui/box_lit.png");
		menu[2] = ImageLoader.loadImage("/ui/hand.png");
		menu[3] = ImageLoader.loadImage("/ui/background.png");
		menu[4]  = ImageLoader.loadImage("/ui/hand_fire.png");
		
		items = new BufferedImage[13][14];
		slime = new BufferedImage[2];
		for(int i =0;i<2;i++) {
		slime[i] = slimeSheet.crop(24*i, 0, 24, 16);
		}
	
		
		for(int i =0;i<13;i++) {
			for(int y = 0;y<14;y++) {
			items[i][y] = itemsSheet.crop(i*16, y*16, 16, 16);
		}
		}
		
		
		
		for(int i = 0;i<playerDown.length;i++) {
			playerDown[i] = playerDownStrip.crop(14*i, 0, pWIDTH, pHEIGHT);
			playerUp[i] = playerUpStrip.crop(14*i, 0, pWIDTH, pHEIGHT);
			playerLeft[i] = playerLeftStrip.crop(14*i, 0, pWIDTH, pHEIGHT);
			playerRight[i] = playerRightStrip.crop(14*i, 0, pWIDTH, pHEIGHT);
			
		}
	}
}
