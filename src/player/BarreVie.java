package player;

import java.awt.Color;
import java.awt.Graphics;

public class BarreVie {
	int x;
	int y;
	int etat;
	int width=Player.RAYON;
	int height=Player.RAYON/6;
	public BarreVie() {
	}
	
	public void render(Graphics g) {
		g.setColor(Color.green);
		switch (etat) {
		case 3:		
			g.fillRect(x, y, width, height);
		break;
		case 2 :
			g.fillRect(x, y, (2*width)/3, height);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x+ (2*width)/3,y,width/3,height);
		break;
		case 1 :
			g.fillRect(x, y, width/3, height);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x+ width/3,y,(2*width)/3,height);
		break;
		case 0 :
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x, y, width, height);
		break;
			
		}
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
