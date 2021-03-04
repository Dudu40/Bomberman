package bombe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import player.Player;
import world.Bloc;
import world.World;


public class Bombe {
	int x,y,id,idJoueur;
	public static int RAYON=Player.RAYON-Player.RAYON/4;
	// savoir si la bombe a explosé
	private int explosionPhase=0;
	private  ArrayList<Bloc> listeBlocTouches = new ArrayList<Bloc>();
	Flame flame;
	// savoir si la bombe est solide ou pas
	private boolean isSolid=false;
	public Bombe(int x,int y,int idJoueur,int id) {
		this.x=x;
		this.y=y;
		this.idJoueur=idJoueur;
		
	}
	public void render(Graphics g) {
		 if (explosionPhase==0) {
				g.setColor(Color.black);
				g.fillOval(x, y,RAYON,RAYON);
		 }
		 else if (explosionPhase==1){
			 if (flame!=null) {
				 flame.render(g);
			 }			
		 }
	}
	
		 
	public Flame getFlame() {
		return flame;
	}
		
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,RAYON,RAYON);
	}
	
	public int getExplosion() {
		
		return explosionPhase;
		
	}

	// explose toutes les cases fin de la bombe
public void exploseCases() {
	for(int i=0;i<listeBlocTouches.size();i++) {
		if (listeBlocTouches.get(i).getId()==1) {
			
			listeBlocTouches.get(i).setId(0);
			listeBlocTouches.get(i).setSolide(false);
			
		}
	}
}

// timer durant laquelle il ya une flamme
private void timerExplosion() {
		
		Timer time = new Timer();
		
		time.schedule(new TimerTask() {
			int timerExplosion=1;
			@Override
			public void run() {
				timerExplosion--;
				
				if (timerExplosion==0) {
					exploseCases();
					explosionPhase=2;
					cancel();
					
				}		
				
			}
			
		}, 1000,1000);
		
	}


// regarde si il ya un des cases a exploser et les affichent

public boolean pasDeBriqueVertical(Bloc bloc) {
	boolean res=true;
	for(int i=0;i<World.liste.size();i++) {
		// si l'on trouve un bloc solide a la meme coordonnée x du bloc
		if ((World.liste.get(i).isSolide()) && (World.liste.get(i).getX()==bloc.getX())) {
			if ((World.liste.get(i).getY()<bloc.getY()) && (World.liste.get(i).getY()>y) || (World.liste.get(i).getY()<y && (World.liste.get(i).getY()>bloc.getY()))) {
				
				res=false;
			
			}
		
		}
	
	}
	return res;
}

public boolean pasDeBriqueHorizontal(Bloc bloc) {
	boolean res=true;
	for(int i=0;i<World.liste.size();i++) {
		// si l'on trouve un bloc solide a la meme coordonnée x du bloc
		if ((World.liste.get(i).isSolide()) && (World.liste.get(i).getY()==bloc.getY())) {
			if ((World.liste.get(i).getX()<bloc.getX()) && (World.liste.get(i).getX()>x) || (World.liste.get(i).getX()<x && (World.liste.get(i).getX()>bloc.getX()))) {
				
				res=false;
			
			}
		
		}
	
	}
	return res;
}

public boolean totalConditionsVerticale(Bloc bloc,int puissance) {
	
	boolean res=false;
	
		// si les coordonnées de la bombe correspondent a celle de la colonne a exploser c'est a dire si coorx egale
		if( (bloc.getX()>=x-Bloc.BLOC_SIZE/2) && (bloc.getX()<=x+Bloc.BLOC_SIZE/2)){
			// si la caisse se situe sur un rayon definit par la puissance
			if( (bloc.getY()>=y-puissance*Bloc.BLOC_SIZE-Player.RAYON/2) && (bloc.getY()<=y+puissance*Bloc.BLOC_SIZE+Player.RAYON/2)){
				// si pas de brique entre le bloc choisi et la bombe
				if (pasDeBriqueVertical(bloc)) {
					res=true;
					
				}
					
			}
		}	
		return res;	
}

public boolean totalConditionsHorizontal(Bloc bloc,int puissance) {
	boolean res=false;
	//si les coordonnées de la bombe correspondent a celle de la colonne a exploser c'est a dire si coord y egale
	if( (bloc.getY()>=y-Bloc.BLOC_SIZE/2) && (bloc.getY()<=y+Bloc.BLOC_SIZE/2)){
		// si la caisse se situe sur un rayon definit par la puissance
		if( (bloc.getX()>=x-puissance*Bloc.BLOC_SIZE-Player.RAYON/2) && (bloc.getX()<=x+puissance*Bloc.BLOC_SIZE+Player.RAYON/2)){
			// si pas de blocs solides entre le bloc choisi et la bombe
			if (pasDeBriqueHorizontal(bloc)) {
				res=true;
				
			}
		}
	}
	return res;
	
}





public void selectionneCasesTouches(int puissance) {
	
	for(int i=0;i<World.liste.size();i++) {
		if (World.liste.get(i).getId()==1) {
			if ((totalConditionsVerticale(World.liste.get(i),puissance)) || (totalConditionsHorizontal(World.liste.get(i),puissance))) {
				// pour avoir tous les blocs on doit 
				// on a chaque caisses touchés
				// on ajoute chaque 
				listeBlocTouches.add(World.liste.get(i));
			}
			
		}
	}

}
	
	public void lanceTimerBombe(int puissance) {
		
		Timer time = new Timer();
		time.schedule(new TimerTask() {
			int timerBombe=2;
			@Override
			public void run() {
				if (timerBombe==2) {
					isSolid=true;
				}
				timerBombe--;
				
				if (timerBombe==0) {
//					 flame=new Flame(x,y,puissance,id,1);
					explosionPhase=1;
					selectionneCasesTouches(puissance);
					timerExplosion();
					
					
					cancel();
					
				}		
				
			}
			
		}, 1000,1000);
		
	}
	
	
	public void setExplosion(int bol) {
		
		explosionPhase=bol;
		
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
	public int getIdJoueur() {
		return idJoueur;
	}
	public void setIdJoueur(int id) {
		this.idJoueur = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isSolid() {
		return isSolid;
		
	}
	
	public void setSolid(boolean b) {
		this.isSolid=b;
		
	}
	
	

}
