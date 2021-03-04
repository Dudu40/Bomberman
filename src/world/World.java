package world;

import java.awt.Graphics;
import java.util.ArrayList;

import utils.Utils;

public class World {
	
	public static int TAB_HEIGHT;
	public static int TAB_WIDTH;
	int[][] tab;
	String path;
	public static ArrayList<Bloc>liste=new ArrayList<Bloc>();
	public static int xStart,yStart;
	
	public World(String path) {
		this.path=path;
		
		loadingWorld(path);
		
	}
	
	public void loadingWorld(String path) {
		// on va chercher le fichier texte en string
		String txt = Utils.loadFileToString(path);
		// on split tout dans un tab de String
		String[]a=txt.split("\\s+");
		
		// on recup la taille et le point de depart
		TAB_WIDTH=Integer.parseInt(a[0]);
		TAB_HEIGHT=Integer.parseInt(a[1]);
		xStart=Integer.parseInt(a[2]);
		yStart=Integer.parseInt(a[3]);
		
		tab = new int[TAB_WIDTH][TAB_HEIGHT];
		
			
		// on rentre les valeurs des id dans le tableau
		for(int x=xStart;x<TAB_WIDTH;x++) {
			for(int y=yStart;y<TAB_HEIGHT;y++) {
				// on ajoute chaque case tu tableau dans la liste
				addBloc(x,y,Integer.parseInt(a[(y*TAB_HEIGHT+x)+4]));
			}
		}		
		
	}
	
	// retourn le bloc correspondant a l' id dans le tab et le place dans la liste de blocs
	public void  addBloc(int x,int y,int id) {
		Bloc bloc = new Bloc(x*Bloc.BLOC_SIZE,y*Bloc.BLOC_SIZE,id);
		liste.add(bloc);
		
	}
	
	public ArrayList<Bloc> getListe(){
		return liste;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		
		for(int i=0;i<liste.size();i++) {
			liste.get(i).render(g);
		}
		
		
	
	
}
	
	
	
	
	
	
	
	
}
