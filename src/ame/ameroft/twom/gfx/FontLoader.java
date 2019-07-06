package ame.ameroft.twom.gfx;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
	public static Font loadFont(String path,float size) {
		try {
	
		
			FontLoader fn = new FontLoader();
			
			return Font.createFont(Font.TRUETYPE_FONT, fn.getClass().getClassLoader().getResourceAsStream(path)).deriveFont(Font.PLAIN,size);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.exit(1);
		}
		return null;
	}
}
