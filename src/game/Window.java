package game;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends Canvas {
	private static final long serialVersionUID = 1L;
	String title;
	int height,width;
	Game game;
	JFrame frame;
	
	
	public Window(String title,int width,int height,Game game) {
		this.title=title;
		this.width=width;
		this.height=height;
		this.game=game;
		
		createWindow();
		
	}


	public void createWindow() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setPreferredSize(new Dimension(width,height));
		frame.setMaximumSize(new Dimension(width,height));
		frame.setMinimumSize(new Dimension(width,height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(game);
		
		
	}
	
}
