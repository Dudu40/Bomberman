package bombe;

import java.awt.Graphics;
import java.util.ArrayList;

public class BombeTotale {
	Bombe bombe;
	ArrayList<Bullet> listeBullet;
	public BombeTotale(Bombe bombe,ArrayList<Bullet> listeBullet) {
		this.bombe=bombe;
		this.listeBullet=listeBullet;
		
	}
	
	public void exploseBullet() {
		if (listeBullet.size()>0) {
			for(int i=0;i<listeBullet.size();i++) {
				if (listeBullet.get(i).getCollision()) {
					listeBullet.remove(i);
				}
			}
			
		}
		
	}
	
	public void tickListeBullet() {
		if (listeBullet!=null) {
			for(int i=0;i<listeBullet.size();i++) {
				listeBullet.get(i).tick();
			}
			
		}		
	}
	
	
	public void tick() {
		tickListeBullet();
		exploseBullet();
	}
	
	public void renderListBullet(Graphics g) {
		if (listeBullet!=null) {
			for(int i=0;i<listeBullet.size();i++) {
				listeBullet.get(i).render(g);
			}
			
		}		
	}
	
	public void render(Graphics g) {
		bombe.render(g);
		renderListBullet(g);
	}
	
	public Bombe getBombe() {
		return bombe;
	}
	
	public ArrayList<Bullet> getListeBullet() {
		return listeBullet;
	}
	
	
}
