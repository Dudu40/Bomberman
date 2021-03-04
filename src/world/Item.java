package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import player.Player;

public class Item {
	int x,y,id;
	public static int RAYON=Player.RAYON/2;
	public Item(int x,int y) {
		this.x=x;
		this.y=y;
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
			switch(id) {
			// puissance
			case 1:
				g.setColor(new Color(255, 0, 127));
				//vie
			break;
			case 2:
				g.setColor(new Color(20, 148, 20));
			break;
			//bombe
			case 3:
				g.setColor(Color.black);
			break;
			case 4:
				g.setColor(Color.cyan);
			break;
				
			}
			g.fillOval(x,y,RAYON,RAYON);
				
	}
	public Rectangle getBounds() {
		return new Rectangle(x,y,RAYON,RAYON);
		
	}
	public int getId() {
		return id;
	}
	public void setId(int newId) {
		this.id=newId;
		
	}

}
