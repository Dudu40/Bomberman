package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyManager extends KeyAdapter{
	
	Game game;
	public KeyManager(Game game) {
		this.game=game;
	}
	
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
		
	}
	
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}
	
	

}
