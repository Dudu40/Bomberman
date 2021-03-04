package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import bombe.Bombe;
import bombe.BombeTotale;
import bombe.Bullet;
import bombe.Flame;
import game.Game;
import world.Bloc;
import world.World;

public class Player{
	
	int x,y,id;
	int puissance=1;
	int vitesse=Bloc.BLOC_SIZE/16;
	int nbBombe=1;
	int vX,vY;
	int compteur=0;
	int timerBombe=100;
	int vie=3;
	boolean killed=false;
	boolean mort =false;
	boolean poseBombe=false;
	boolean up=false;
	boolean down=false;
	boolean left=false;
	boolean right=false;
	public static int RAYON=Bloc.BLOC_SIZE-16;
	private World world;
	ArrayList<Bloc> listeBloc=World.liste;
	ArrayList<BombeTotale> listeBombe= new ArrayList<BombeTotale>();
	BarreVie barreVie = new BarreVie();
	
	public Player(int x,int y,int id) {
		this.x=x;
		this.y=y;
		this.id=id;
		
	}
	
	public int getNbBombes() {
		int compteur=0;
		if (listeBombe.size()>0) {
			for(int i=0;i<listeBombe.size();i++) {
				// on compte que ls bombes qui n'ont pas explosés
				if (listeBombe.get(i).getBombe().getExplosion()<2) {
					compteur++;
				}
			}
		}
		
		return compteur;
	}
	
	// on cree une bombeTotale avec la liste des bulet et la bombe en elle meme
	public void poseBombe() {
		if (getNbBombes()<=this.getNbBombe()) {
			
			BombeTotale bT;
			Bombe b = new Bombe(x+Bloc.BLOC_SIZE/10+1,y+Bloc.BLOC_SIZE/10+1,id,listeBombe.size());
		
			ArrayList<Bullet> listeBullet=new ArrayList<Bullet>();
			// si la bombe est enflammé et qu'il n'ya pas de bullet on lance des bullet
			if ((listeBullet.size()==0)){
				listeBullet.add(new Bullet(b.getX(),b.getY(),puissance,1,listeBombe.size()-1,id));
				listeBullet.add(new Bullet(b.getX(),b.getY(),puissance,2,listeBombe.size()-1,id));
				listeBullet.add(new Bullet(b.getX(),b.getY(),puissance,3,listeBombe.size()-1,id));
				listeBullet.add(new Bullet(b.getX(),b.getY(),puissance,4,listeBombe.size()-1,id));
	
							
			}
			// on met la bombe et la liste de projectile dans la superBombe
			bT=new BombeTotale(b,listeBullet);
			bT.getBombe().lanceTimerBombe(puissance);
			listeBombe.add(bT);
		}
		
		
	}
	
	// On active la vitesse de chaque bulle tde la liste
	public void setBulletsVitesseBombe(ArrayList<Bullet> liste) {
		for(int i=0;i<liste.size();i++) {
				if ((liste.get(i).getVx()==0) && (liste.get(i).getVy()==0)){
					liste.get(i).setVx(Bullet.VITESSE);
					liste.get(i).setVy(Bullet.VITESSE);
				}
				
		}
	}
	
	// si la bombe explose on la supprime si les flammes apparaissent on lance les bullet
	public void exploseBombes() {
		for(int i=0;i<listeBombe.size();i++) {	
			// si la bombe a explosé et enflammé on la supprimeet mais on supprime pas  les bullet
			if ((listeBombe.get(i).getBombe().getExplosion()==2) && (listeBombe.size()!=0)){
			
				listeBombe.get(i).getBombe().setExplosion(3);	
				
			}
			// si l'explosion de la bome est esgale a 1 alors on update la vitesse de la liste de bullet
			else if ((listeBombe.get(i).getBombe().getExplosion()==1) && (listeBombe.size()!=0)) {
					
					setBulletsVitesseBombe(listeBombe.get(i).getListeBullet());		

			}
		}
			
	}
	
	
	
	public ArrayList<BombeTotale> getListBombe(){
		return listeBombe;
	}
	
	// timer pour le delai de mort
	public void lanceTimerMort() {
		Timer time = new Timer();
		
		time.schedule(new TimerTask() {
			int timerMort=1;
			@Override
			public void run() {
				if (timerMort==0) {
					mort=true;
					cancel();
					
				}
				timerMort--;
				
						
				
			}
			
		}, 200,200);
	}
	
	
	// detecte une collision entre unprojectile et le joueur
	public boolean collisionBullet(ArrayList <Bullet> listeBullet) {
		boolean res=false;
		for(int i=0;i<listeBullet.size();i++) {
			if((this.getBounds().intersects(listeBullet.get(i).getBounds()))) {
				res=true;
			}
		}
		return res;
	
}
	
	// si le joueur touche une bombe il est bloqué
	public void collisionBombe(Bombe bombe) {
			// si il y a intersection
			if(this.getBounds().intersects(bombe.getBounds())) {
				// si la distance entre le centre de la bombe et le joueur > RAyonJ+RayonB
				int distance=(int) Math.sqrt(((x-bombe.getX())*(x-bombe.getX()))+((y-bombe.getY())*(y-bombe.getY())));
				if(bombe.isSolid()) {
					if(distance<=RAYON+Bombe.RAYON) {
						x+=-vX;
						y+=-vY;			
					
					}
					
					
				}				
				
			}
			

	}
	
	// si le joueur touche un item augmente la capacité du joueur et supprime l'item
	public void collisionItem() {
		for(int i=0;i<listeBloc.size();i++) {
			if(this.getBounds().intersects(listeBloc.get(i).getItem().getBounds())) {
				switch(listeBloc.get(i).getItem().getId()) {
				case 1:
					puissance++;
				break;
				case 2:
					if (vie<3) {
						vie++;
					}
				break;
				case 3:
					nbBombe++;
				break;
				case 4:
					vitesse++;
				break;
				}
				listeBloc.get(i).getItem().setId(0);
			}
		}
		
	}
	
	// si un joueur touche un bloc il est coincé
	public void collisionBloc() {
		for(int i=0;i<listeBloc.size();i++) {
			if (listeBloc.get(i).isSolide()) {
				if(this.getBounds().intersects(listeBloc.get(i).getBounds())) {
						x+=-vX;
						y+=-vY;
								
				
				}
				
			}
			
		}
		
		
	}
	
	
	public void updateBarreVie() {
		barreVie.setEtat(vie);
		barreVie.setX(x);
		barreVie.setY(y-barreVie.getHeight());
	}
	
	public void tick() {
		
		updateBarreVie();
		// deplace joueur
		x+=vX;
		y+=vY;
		
		// check colision block/joueur
		collisionBloc();
		// check collision item/joueur
		collisionItem();
		
		// actionne les bullet si explose 1 
		// detruit la bombe si explose 2
		exploseBombes();
		
		for(int i=0;i<listeBombe.size();i++) {
			// annime la bombe (bulletList tick)
			listeBombe.get(i).tick();
		
		}
		
	
		
		
		
	}
	
	
	
	public void render(Graphics g) {
		// desiine le joueur
		switch(id) {
		case 1:
			g.setColor(Color.blue);
		break;
			
		case 2:
			g.setColor(Color.red);
		break;
		}
	
		g.fillOval(x, y,RAYON,RAYON);
		
		
		barreVie.render(g);
		// desiine les bombesTotales (Bullet + bombe)
		for(int i=0;i<listeBombe.size();i++) {
			listeBombe.get(i).render(g);
		}	
	}
	
	public int getvX() {
		return vX;
	}

	public void setvX(int vX) {
		this.vX = vX;
	}

	public int getvY() {
		return vY;
	}

	public void setvY(int vY) {
		this.vY = vY;
	}
	
	public int getX() {
		return x;
		
	}
	
	public void setX(int dx) {
		x=dx;
	}
	public void setY(int dy) {
		y=dy;
	}
	
	public int getY() {
		return y;
		
	}
	
	
	public int getPuissance() {
		return puissance;
	}

	public void setPuissance(int puissance) {
		this.puissance = puissance;
	}

	public int getVitesse() {
		return vitesse;
	}
	
	public int getNbBombe() {
		return nbBombe;
	}
	
	public void setNbBombe(int newNb) {
		nbBombe=newNb;
		
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public Rectangle getBounds() {
		return new Rectangle(x,y,RAYON,RAYON);
	}
	public boolean getMort() {
	 return mort;

	}

	public boolean isPoseBombe() {
		return poseBombe;
	}

	public void setPoseBombe(boolean poseBombe) {
		this.poseBombe = poseBombe;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
	public int getId() {
		return id;
	}
	
	public void setMort(boolean t) {
		mort=t;
	}
	
	public int getVie() {
		return vie;
	}
	
	public void setVie(int s) {
		vie=s;
	}
	
	public void setKilled(boolean b) {
		killed=b;
		
	}
	
	public boolean getKilled() {
		return killed;
		
	}
	
	
}