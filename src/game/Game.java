package game;

// pour le canvas de la fenetre 
import java.awt.Canvas;
// our les couleurs
import java.awt.Color;
import java.awt.Font;
// pour dessiner 
import java.awt.Graphics;
// pour l'ensemble des interactions claviers
import java.awt.event.KeyEvent;
// pour les buffer de la fenetre graphique
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import bombe.BombeTotale;
import bombe.Bullet;
import player.Player;
import world.Bloc;
import world.World;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	boolean running=false;
	Thread thread;
	Window window;
	BufferStrategy bs;
	Graphics g;
	ArrayList<Player> listePlayer= new ArrayList<Player>();  
	World world;
	public static int nbreCaseX=15;
	public static int nbreCaseY=15;
	public static int WIDTH=nbreCaseX*Bloc.BLOC_SIZE;
	public static int HEIGHT=nbreCaseY*Bloc.BLOC_SIZE;
	
	
	public Game() {
		window = new Window("Bomberman",WIDTH,HEIGHT+40,this);
		
		// cree un mode de 5*5 cubes Bleus
		world= new World("src/worlds/world1.txt");
		Player player1 = new Player(Bloc.BLOC_SIZE+Bloc.BLOC_SIZE/16,Bloc.BLOC_SIZE+Bloc.BLOC_SIZE/16,1);
		listePlayer.add(player1);
		Player player2 = new Player(15*Bloc.BLOC_SIZE-2*Bloc.BLOC_SIZE,15*Bloc.BLOC_SIZE-2*Bloc.BLOC_SIZE,2);
		listePlayer.add(player2);
		addKeyListener(new KeyManager(this)); 
		this.setFocusable(true);
		
		
	}
	
	public synchronized void start() {
		if (running) {
			return;
		}
		running=true;
		thread=new Thread(this);
		thread.start();	
	}
	
	public synchronized void stop() {
		if(!running) {
			return;
		}
		running=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void moveplayer() {
		for(int i=0;i<listePlayer.size();i++) {
			Player player =listePlayer.get(i);
			
		if (player.isPoseBombe())  {
			player.setPoseBombe(false);
			player.poseBombe();		
		}
			
		if (player.isUp()){
			player.setvY(-player.getVitesse());
		}
		else if (!player.isDown()) {
			player.setvY(0);
			
		}	
		if (player.isDown()){
			player.setvY(player.getVitesse());
		}
		else if (!player.isUp()) {
			player.setvY(0);
			
		}
		
		if (player.isLeft()) {
			player.setvX(-player.getVitesse());
		}
		else if (!player.isRight()) {
			player.setvX(0);
			
		}
		
		if(player.isRight()) {
			player.setvX(player.getVitesse());
		}
		else if (!player.isLeft()) {
			player.setvX(0);
			
		}
			
		}
		
			
	}
	
	public void lanceTimerInvincible(Player player) {
		Timer time = new Timer();
		
		time.schedule(new TimerTask() {
			int timer=1;
			@Override
			public void run() {
				if (timer==0) {
					player.setKilled(false);
					cancel();
					
				}
				timer--;
				
						
				
			}
			
		}, 1000,1000);
	}
	
	public void collisionAllPlayerBombes(Player player) {
		for(int i=0;i<listePlayer.size();i++) {
			ArrayList<BombeTotale> bt = listePlayer.get(i).getListBombe();
			for(int j=0;j<bt.size();j++) {
			
				
			ArrayList<Bullet> bullets = bt.get(j).getListeBullet();
			
			if (bt.get(j).getBombe().getExplosion()==0) {
				player.collisionBombe(bt.get(j).getBombe());
				
			}
			
			else if ((bt.get(j).getBombe().getExplosion()==1) && (!player.getKilled())) {
				
				if (player.collisionBullet(bullets)) {
					// provisoire
//					if (player.getId()!=listePlayer.get(i).getId()) {
					player.setVie(player.getVie()-1);
					
					if (player.getVie()==0) {
						player.lanceTimerMort();
					}
					player.setKilled(true);
					lanceTimerInvincible(player);
					
				}
			
				
			}	
			
				
		}
	
	}	
}
	
	public void tick() {
		for(int i=0;i<listePlayer.size();i++) {
			// deplace joueur
			moveplayer();
			// detecte toues collision du joueurs avec l'environnement exterieur
			// explosion bombes
			listePlayer.get(i).tick();
			// detecte toutes les colisions avec les autres joueurs et les bombes

			collisionAllPlayerBombes(listePlayer.get(i));
						
		}
	}
	

	public boolean testFin() {
		boolean res=false;
		for(int i=0;i<listePlayer.size();i++) {
			if(listePlayer.get(i).getMort()) {
				res=true;
			}
		}
		return res;
	}
	
	public int joueurPerdant() {
		int res=0;
		for(int i=0;i<listePlayer.size();i++) {
			if(listePlayer.get(i).getMort()) {
				res=listePlayer.get(i).getId();
			}
		}
		return res;
	}
	
	public void render() {
		// on va chercher les buffer de la fenetre
		bs=this.getBufferStrategy();
		if (bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		g=bs.getDrawGraphics();
		
		// on dessine la map
		world.render(g);
		// on dessine chaque joueur
		for(int i=0;i<listePlayer.size();i++) {
			if (testFin()) {
				// fond Partie perdue
				g.clearRect(0, 0, WIDTH, HEIGHT);
				g.setColor(Color.black);
				g.fillRect(0,0,WIDTH,HEIGHT);
				g.setColor(Color.red);
				g.setFont(new Font("impact", Font.BOLD, 64));
				g.drawString("GAME OVER", WIDTH/4, HEIGHT/2);
				g.setFont(new Font("impact", Font.BOLD, 34));
				g.drawString("LE JOUEUR "+joueurPerdant()+" A PERDU  !", WIDTH/4, HEIGHT/3);
				Timer time = new Timer();
				time.schedule(new TimerTask() {
					int timerBombe=1;
					@Override
					public void run() {
						
						timerBombe--;
						
						if (timerBombe==0) {
							running=false;								
							cancel();
							
						}		
						
					}
					
				}, 1000,1000);
				
			}
			else {
				listePlayer.get(i).render(g);
			}
		}	
		
		g.dispose();
		bs.show();
		
		
	}

	@Override
public void run() {
		
		
		long lastTime=System.nanoTime();
		int fps =60;
		double amontOfTick=1000000000/fps;
		double delta=0;
		long now;
		long timer = System.currentTimeMillis();
		
		while(running) {
			
			now=System.nanoTime();
			delta+=(now-lastTime)/amontOfTick;
			lastTime=now;
			
			if (delta>=1) {
				// update toute les milisecondes
				// 1000 updtaes/secondes
					tick();
					render();
					delta--;
						
			}
						
			if(System.currentTimeMillis()-timer>1000) {
				timer+=1000;
				
			}
			
		}
		stop();
		
		
	}
	
	
	public void keyPressed(KeyEvent e) {
		// on recup la touche actuellemen tenfoncée
		int key=e.getKeyCode();
		System.out.println(key);
		
		for(int i=0;i<listePlayer.size();i++) {
			
			if (listePlayer.get(i).getId()==1) {
				if (key==KeyEvent.VK_Z)   {
					listePlayer.get(i).setUp(true);
					
				}
				if (key==KeyEvent.VK_S) {
					listePlayer.get(i).setDown(true);
					
				}
				if (key==KeyEvent.VK_Q) {
					listePlayer.get(i).setLeft(true);
					
				}
				if (key==KeyEvent.VK_D) {
					listePlayer.get(i).setRight(true);			
				}
	
				if (key==KeyEvent.VK_SPACE){
					listePlayer.get(i).setPoseBombe(true);
					listePlayer.get(i).setKilled(false);
				}
				 	
			}
			else if (listePlayer.get(i).getId()==2) {
				
				if (key==KeyEvent.VK_UP)   {
					listePlayer.get(i).setUp(true);
					
				}
				if (key==KeyEvent.VK_DOWN) {
					listePlayer.get(i).setDown(true);
					
				}
				if (key==KeyEvent.VK_LEFT) {
					listePlayer.get(i).setLeft(true);
					
				}
				if (key==KeyEvent.VK_RIGHT) {
					listePlayer.get(i).setRight(true);			
				}
	
				if (key==KeyEvent.VK_ENTER){
					listePlayer.get(i).setPoseBombe(true);
					listePlayer.get(i).setKilled(false);
				}
				
			}
			else if (listePlayer.get(i).getId()==3) {
				if (key==KeyEvent.VK_8)   {
					listePlayer.get(i).setUp(true);
					
				}
				if (key==KeyEvent.VK_5) {
					listePlayer.get(i).setDown(true);
					
				}
				if (key==KeyEvent.VK_4) {
					listePlayer.get(i).setLeft(true);
					
				}
				if (key==KeyEvent.VK_6) {
					listePlayer.get(i).setRight(true);			
				}
	
				if (key==KeyEvent.VK_PLUS){
					listePlayer.get(i).setPoseBombe(true);
					listePlayer.get(i).setKilled(false);
				}
				
			}
		}
		 
		
	}


	public void keyReleased(KeyEvent e) {
		// on recup la touche actuellemen tenfoncée
		int key=e.getKeyCode();
		
		for(int i=0;i<listePlayer.size();i++) {
			
			if (listePlayer.get(i).getId()==1) {
				if (key==KeyEvent.VK_Z)   {
					listePlayer.get(i).setUp(false);
					
				}
				if (key==KeyEvent.VK_S) {
					listePlayer.get(i).setDown(false);
					
				}
				if (key==KeyEvent.VK_Q) {
					listePlayer.get(i).setLeft(false);
					
				}
				if (key==KeyEvent.VK_D) {
					listePlayer.get(i).setRight(false);			
				}
	
				 	
			}
			else if (listePlayer.get(i).getId()==2) {
				
				if (key==KeyEvent.VK_UP)   {
					listePlayer.get(i).setUp(false);
					
				}
				if (key==KeyEvent.VK_DOWN) {
					listePlayer.get(i).setDown(false);
					
				}
				if (key==KeyEvent.VK_LEFT) {
					listePlayer.get(i).setLeft(false);
					
				}
				if (key==KeyEvent.VK_RIGHT) {
					listePlayer.get(i).setRight(false);			
				}
				
			}
			else if (listePlayer.get(i).getId()==3) {
				if (key==KeyEvent.VK_8)   {
					listePlayer.get(i).setUp(false);
					
				}
				if (key==KeyEvent.VK_5) {
					listePlayer.get(i).setDown(false);
					
				}
				if (key==KeyEvent.VK_4) {
					listePlayer.get(i).setLeft(false);
					
				}
				if (key==KeyEvent.VK_6) {
					listePlayer.get(i).setRight(false);			
				}
				
			}
		}
		 
		
	}
	
	
}
