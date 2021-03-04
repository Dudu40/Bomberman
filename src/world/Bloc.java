package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bloc {

	int x,y,id;
	public static int BLOC_SIZE=65;
	public static int nbreItem=4;
	private boolean isSolide;
	private Item item;
	
	public Bloc(int x,int y,int id) {
		this.x=x;
		this.y=y;
		this.id=id;
		generateItem();
		determineForme();
		
	}
	
	private void determineForme(){
		switch(id) {
		case 0:
			isSolide=false;
		break;
		case 1 :
			isSolide=true;
		break;
		
		case 2:
			isSolide=true;
		break;
		
		case 3:
			isSolide=false;
		break;
		
		}
		
	}
	
	// on genere unitem d'id aleatoire
	public void generateItem() {
		item= new Item(x+BLOC_SIZE/3,y+BLOC_SIZE/3);
		if (this.id==1) {
			
			int randomId =(int)(Math.random() * ((nbreItem)+1));
			item.setId(randomId);	
		}
		else {
			item.setId(0);
		}
		
		
	}
	
	public Item getItem() {
		return item;
	}
	
	public void tick() {
		
	}
	
	public void renderTest(Graphics g) {
		int vX[]= { 10*6, 30*6, 40*6, 50*6, 110*6, 140*6 }; 
		int vY[]= { 140*6, 110*6, 50*6, 40*6, 30*6, 10*6 }; 
		g.setColor(Color.blue);
		g.fillPolygon(vX, vY, 6);
		
	}
	
	public void render(Graphics g) {
		switch(this.id) {
		case 0:
			g.setColor(new Color(253, 191, 183));	
		break;
		
		case 1 :
			g.setColor(new Color(239, 155, 15));
		break;
		case 2 :
			g.setColor(new Color(145, 40, 59));
		break;
		case 3 :
			g.setColor(Color.orange);
		break;
		}	
		g.fillRect(this.x, this.y,BLOC_SIZE,BLOC_SIZE);
		g.setColor(Color.black);
		g.drawRect(x,y,BLOC_SIZE+1/8,BLOC_SIZE+1/8);
		
		if ((id==0) && (item.getId()>0)) {
			item.render(g);
		}
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,BLOC_SIZE,BLOC_SIZE);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isSolide() {
		return isSolide;
	}
	
	public void setSolide(boolean s) {
		isSolide=s;
	}
	
	 
	

}
