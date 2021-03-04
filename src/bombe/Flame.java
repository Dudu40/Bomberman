package bombe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import world.Bloc;

public class Flame {
	int xB;
	int yB;
	int puissance,id;
	
	
	public Flame(int xB,int yB ,int puissance, int id,int direction) {
		this.xB=xB;
		this.yB=yB;
		this.puissance=puissance;
		this.id=id;
		
	}
	
	
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(xB-puissance*Bloc.BLOC_SIZE,yB-6,puissance*Bloc.BLOC_SIZE*2+Bombe.RAYON,Bloc.BLOC_SIZE-8);
		g.setColor(Color.cyan);
		g.fillRect(xB-Bloc.BLOC_SIZE/8,yB-puissance*Bloc.BLOC_SIZE,Bloc.BLOC_SIZE-8,puissance*Bloc.BLOC_SIZE*2+Bombe.RAYON);
		
	}	
	
	
	public Rectangle getBounds1() {
		
		Rectangle r = new Rectangle(xB-puissance*Bloc.BLOC_SIZE,yB-6,puissance*Bloc.BLOC_SIZE*2+Bombe.RAYON,Bloc.BLOC_SIZE-8);
		return r;
	}
	
	public Rectangle getBounds2() {
		
		Rectangle r = new Rectangle(xB-Bloc.BLOC_SIZE/8,yB-puissance*Bloc.BLOC_SIZE,Bloc.BLOC_SIZE-8,puissance*Bloc.BLOC_SIZE*2+Bombe.RAYON);
		return r;
	}
}
