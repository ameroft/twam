package ame.ameroft.twom.display;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {

	private JFrame frame;
	private Canvas canvas;
	private String title;
	private int width, height;

	public Display(String title, int width,int height) {
		//Set title, width and height
		this.title = title;
		this.width = width;
		this.height = height;
		//Create the display
		createDisplay();
	}

	private void createDisplay() {
		//Using JFrame create the display
		frame =  new JFrame(title);
		frame.setSize(width, height);
		
	//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//Create the drawing field
		canvas = new Canvas();	
		canvas.setPreferredSize(new Dimension(width,height));
		canvas.setMaximumSize(new Dimension(width,height));
		canvas.setMinimumSize(new Dimension(width,height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
	}
	
	public Canvas getCanvas(){
		return canvas;
	}
	public JFrame getFrame() {
		return frame;
	}
}
