package rush.hour;
import java.io.*;
import javax.swing.*;

import java.util.*;

/**
* Player est la classe qui permet de gérer un joueur et son score.
*
* @author: CLENET Nicolas
* @author: HAUTIER Ludovic
* @author: MAHIEUX Denis
* @author: RAHAMEFY Landry
*
* @version: 2014.02.26
*/

public class Player implements Serializable{

	// Player unique, utilisation d'un Singleton
	private static Player playerSingleton;
	// nom du joueur actif
	private String nom;
	// liste des joueurs enregistrés
	private ArrayList <String> players;
	// liste des scores du joueur actif en fonction du niveau de difficulté
	private Hashtable <String, String[]> scores;

	/** Constructeur de la classe Player 
	*/
	private Player () {
		try{
			players = chargerPlayers();
		}
		catch (Exception e){
			players = new ArrayList <String>();
			try{
				this.sauverPlayers();
			}
			catch (Exception exception){JOptionPane.showMessageDialog(null, exception.getMessage(), "avertissement", JOptionPane.WARNING_MESSAGE);}
		}
		scores = new Hashtable <String, String[]>();
	}

	/** Permet d'avoir une référence sur Player
	* @return: Player le Singleton
	*/
	public static Player getJoueur(){
		if(playerSingleton == null) {
			playerSingleton = new Player ();
		}
		return playerSingleton;
	    }

	/** Permet de créer un nouveau joueur et de l'enregistrer
	* @param: String nom nom du nouveau joueur
	*/
	public void addPlayer (String nom){
		if (!this.players.contains(nom)){
			this.players.add(nom);
			try {
				this.sauverPlayers();
			}catch (Exception e){JOptionPane.showMessageDialog(null, e.getMessage(), "avertissement", JOptionPane.WARNING_MESSAGE);}
		}
		this.setNom(nom);
		for (int i=0; i<Constants.levels.length; i++){
			String[] tmp = new String [ParkingFactory.getParkFactor().getNbrNiv(Constants.levels[i])];
			for(int j = 0; j<tmp.length; j++){
				tmp[j]="";
			}
			
			this.scores.put(Constants.levels[i], tmp);
		}
		try{
			this.sauverJoueur(nom);
		}
		catch (Exception e){JOptionPane.showMessageDialog(null, e.getMessage(), "avertissement", JOptionPane.WARNING_MESSAGE);}
	}

	/** Permet d'ajouter un score au joueur actif, en fonction de la difficulté et du niveau du parking actif
	* @param: int newScor le nouveau score
	*/
	public void addScore (int newScor){
		
		int lastScor = -5;
		boolean modif = false;
		int niv = ParkingFactory.getParkFactor().getNiv();
		String dif = ParkingFactory.getParkFactor().getDif(); 
		String[] tab = scores.get(dif);
	
		if (tab[niv].length() !=0){
			lastScor = Integer.parseInt(tab[niv]);
		}else{ 
			scores.get(dif)[niv]=newScor+"";
			modif = true;
		}
	
		if((lastScor != -5) && ((lastScor > newScor)||(lastScor==-1))){
			scores.get(dif)[niv]=newScor+"";
			modif = true;
		}
		
		if (modif){
			IHM.getIHM().afficheScores();
		
			try{
				this.sauverJoueur(this.nom);
			
			}
			catch (NullPointerException npe){}
			catch (Exception e){JOptionPane.showMessageDialog(null, e.getMessage(), "avertissement", JOptionPane.WARNING_MESSAGE);}
		}
	}

	/** Permet de charger un joueur depuis un fichier
	* @param: String nom le nom du joueur à charger
	*/
	public void chargerJoueur(String nom) throws Exception {
		
		FileInputStream in= new FileInputStream("../data/save/Players/"+nom+".bin");
		ObjectInputStream flux =new ObjectInputStream(in);
		Player res=(Player) flux.readObject();
		this.setScores(res.getScores());
		this.setNom(nom);
		
	}

	/** Permet de sauvegarder un joueur dans un fichier
	* @param: String nom le nom du joueur à sauvegarder
	*/	
	public void sauverJoueur(String nom) throws Exception{
		
		FileOutputStream out= new FileOutputStream("../data/save/Players/"+nom+".bin");
		ObjectOutputStream flux =new ObjectOutputStream(out);
		flux.writeObject(this);
		out.close();
		
	}

	/** Permet de charger la liste des joueurs enregistrés depuis un fichier
	* @return: ArrayList <String> la liste des joueurs
	*/
	public ArrayList <String> chargerPlayers() throws Exception {
		FileInputStream in= new FileInputStream("../data/save/Players.bin");
		ObjectInputStream flux =new ObjectInputStream(in);
		ArrayList <String> list = (ArrayList <String>) flux.readObject();
		return list;
	}

	/** Permet de sauvegarder la liste des joueurs enregistrés sur un fichier
	*/	
	public void sauverPlayers() throws Exception{
		
		FileOutputStream out= new FileOutputStream("../data/save/Players.bin");
		ObjectOutputStream flux =new ObjectOutputStream(out);
		flux.writeObject(this.players);
		
	}

	/** Donne accès aux scores du joueur actif
	* @return: Hashtable <String, String[]> la liste des scores
	*/
	public Hashtable <String, String[]> getScores(){
		return this.scores;
	}

	/** Modifie la liste des scores du joueur actif
	* @param: Hashtable <String, String[]> newScores la nouvelle liste des scores
	*/
	private void setScores (Hashtable <String, String[]> newScores){
		this.scores = newScores;
	}

	/** Donne accès au nom du joueur actif
	* @return: String le nom du joueur actif
	*/
	public String getNom(){
		return this.nom;
	}

	/** Modifie le nom du joueur actif
	* @param: String newNom le nouveau nom
	*/
	private void setNom (String newNom){
		this.nom = newNom;
	}

	/** Donne accès à la liste des joueurs enregistrés
	* @return: ArrayList <String> la liste des joueurs enregistrés
	*/
	public ArrayList <String> getPlayers (){
		return this.players;
	}
}
