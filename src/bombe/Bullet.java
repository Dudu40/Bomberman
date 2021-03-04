package bombe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import world.Bloc;
import world.World;

public class Bullet {
	int x,y,xB,yB,puissance,idBombe,idJoueur,direction;
	public static int VITESSE=6;
	boolean collision=false;
	int vX=0;
	int vY=0;
	public Bullet(int xB,int yB,int puissance,int direction,int idBombe,int idJoueur) {
		this.xB=xB;
		this.yB=yB;
		this.puissance=puissance;
		this.direction=direction;
		this.idBombe=idBombe;
		this.idJoueur=idJoueur;
		x=xB;
		y=yB;
		
	}
	
	public boolean collisionBlocSolid() {
		boolean res=false;
		for(int i=0;i<World.liste.size();i++) {
			if(World.liste.get(i).isSolide()) {
				if(this.getBounds().intersects(World.liste.get(i).getBounds())) {
	
					res=true;
					
				}
			}
		
		}
	
		return res;
	}
	
	public boolean rangeMax() {
		boolean res=false;
		if (direction==1) {
			if(x<=xB-puissance*Bloc.BLOC_SIZE-Bombe.RAYON) {
				res=true;
			}
			
		}
		else if (direction==2) {
			if(x>=xB+puissance*Bloc.BLOC_SIZE+Bombe.RAYON/3) {
				res=true;
			}
			
		}
		else if (direction==3) {
			if(y<=yB-puissance*Bloc.BLOC_SIZE-Bombe.RAYON) {
				res=true;
			}
			
		}
		else if (direction==4) {
			if(y>=yB+puissance*Bloc.BLOC_SIZE+Bombe.RAYON/3) {
				res=true;
			}
			
		}
		
		
		return res;
	}
	
	public void tick() {
		switch(direction) {
		case 1:
			x+=-vX;
			
		break;
		case 2:
			x+=vX;
		break;
		case 3:
			y+=-vY;
		break;
		case 4:
			y+=vY;
		break;
		}
		
		if (rangeMax() || collisionBlocSolid()){
			collision=true;		
		}		
		
		
		
	}
	
	public void render(Graphics g) {
		if ((vX!=0) || (vY!=0)) {
			switch(idJoueur) {
			case 1:
				g.setColor(Color.blue);
			break;
			
			case 2:
				g.setColor(Color.red);
			break;
			}
			
			g.fillOval(x+Bombe.RAYON/2, y+Bombe.RAYON/2,Bombe.RAYON/2,Bombe.RAYON/2);
		}
		
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle (x+Bombe.RAYON/2, y+Bombe.RAYON/2,Bombe.RAYON/2,Bombe.RAYON/2);
	}

	public boolean getCollision() {
		return collision;
	}
	
	public int  getIdBombe() {
		return idBombe;
	}
	
	public void setVy(int dy) {
		vY=dy;
	}
	
	public int getVx() {
		return vX;
	}
	
	public int getVy() {
		return vY;
	}
	
	public void setVx(int dx) {
		vX=dx;
	}
	

}
