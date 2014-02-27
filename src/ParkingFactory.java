
import java.util.ArrayList;
import java.util.Hashtable;
import java.io.*;
import javax.swing.JOptionPane;
import java.util.Scanner;

/**
* ParkingFACTORY est la classe qui permet de récupérer les joueurs et scores 
* enregistrés dans un fichier de sauvegarde
* Elle permet également de générer le parking en fonction du niveau choisi.
*
* @author: CLENET Nicolas
* @author: HAUTIER Ludovic
* @author: MAHIEUX Denis
* @author: RAHAMEFY Landry
*
* @version: 2014.02.26
*/
public class ParkingFactory{

	//ParkingFactory unique, utilisation d'un Singleton
	private static ParkingFactory ParkFactorSingleton;
	//niveau du parking actif
	private int niv;
	//difficulte du parking actif
	private String dif;
	//nombre de mouvement effectués dans la partie en cours
	private int nbrMove = 0;
	//véhicules chargés depuis le fichier
	private Hashtable <String, String[]> vehicles;
	//niveaux de parking chargés depuis le fichier
	private static Hashtable <String, ArrayList < String []> > lvl;
	//parking actif
	private Parking p;

	/** Constructeur de la classe ParkingFactory 
	*/
	private ParkingFactory(){
		Grid myGrid = new Grid();
		vehicles  = new Hashtable < String, String []> ();
		lvl = new Hashtable <String, ArrayList < String [] > > ();
		this.chargerVehicles("../data/vehicles.dat");
		for (int i=0; i<Constants.levels.length; i++){
			this.chargerLvl(Constants.levels[i]);
		}
	}

	/** Permet d'avoir une référence sur le ParkingFactory
	* @return: ParkingFactory le Singleton ParkFactorSingleton
	*/
	public static ParkingFactory getParkFactor(){
		if(ParkFactorSingleton == null) {
			ParkFactorSingleton = new ParkingFactory ();
		}
		return ParkFactorSingleton;
	    }

	/** Charge les véhicules depuis un fichier, puis les place dans un tableau
	* @param: String fichier le nom du fichier
	*/
	private void chargerVehicles(String fichier){
		try {	FileReader reader = new FileReader (fichier);
			Scanner in = new Scanner (reader);
			while (in.hasNextLine()){
				String tmp= in.nextLine();
				String [] tab = new String [2];
				tab [0] = tmp.substring(1, 2);
				tab [1] =tmp.substring(2);
				vehicles.put (tmp.substring(0, 1), tab);
			}
		}
		catch(FileNotFoundException e) {JOptionPane.showMessageDialog(null, e.getMessage(), "avertissement", JOptionPane.WARNING_MESSAGE);}
	}

	/** Charge les niveaux de parking depuis un fichier, puis les place dans une Hashtable
	* @param: String fichier le nom du fichier
	*/
	private void chargerLvl(String fichier){
		ArrayList < String []> stock = new ArrayList < String []> ();
		try {	FileReader reader = new FileReader (("../data/"+fichier+".cfg"));
			Scanner in = new Scanner (reader);
			while (in.hasNextLine()){
				String tmp= in.nextLine();
				String [] tab = tmp.split(" ");
				stock.add (tab);
			}
		}
		catch(FileNotFoundException e) {JOptionPane.showMessageDialog(null, e.getMessage(), "avertissement", JOptionPane.WARNING_MESSAGE);}
		lvl.put(fichier, stock);
	}
	
	/** Permet la création d'un parking en fonction de la difficulté et du niveau
	*@param: String dif la difficulté
	*@param: int niv le niveau
	*/
	public void creerParking (String dif, int niv){
		this.dif = dif;
		this.niv = niv;
		p = new Parking ();
		String [] vehicules = lvl.get(dif).get(niv);
		
		for(int i = 0; i<vehicules.length; i++){
			String [] tmp = vehicles.get(vehicules[i].substring(0, 1));
			
			p.add(new Vehicule(	vehicules[i].charAt(0),     //char nom
						Integer.parseInt(tmp[0]),     //int longueur
						vehicules[i].substring(1, 2),     //String direction
						new Slot(Integer.parseInt(vehicules[i].substring(2, 3)),Integer.parseInt(vehicules[i].substring(3))),     // Slot position
						tmp[1]));     //String couleur
		}
	}

	/** Lance le déplacement d'un vehicule
	*@param: String deplacement la commande entrée par l'utilisateur
	*/
	public synchronized void move (String deplacement){
		p.move(deplacement);
	}

	/** Valide le déplacement du véhicule, met donc à jour le nombre de deplacements et l'affichage
	*@param: String deplacement la commande entrée par l'utilisateur
	*/
	public void moveValide(String deplacement){
		nbrMove++;
		IHM.getIHM().modifAffGauche(nbrMove, deplacement);
	}

	/** Permet la récuperation d'un véhicule, du parking actif, via son nom
	*@param: String nom le nom du véhicule
	*@return: Vehicule le véhicule portant le nom passé en paramètre
	*/
	public Vehicule get (String nom){
		return p.get(nom);
	}

	/** Donne le nombre de niveaux liés a une difficulté
	*@param: String dif la difficulte
	*@return: int le nombre de niveaux liés à la difficulté passée en paramètre
	*/
	public int getNbrNiv (String dif){
		return lvl.get(dif).size();
	}

	/** Donne le nombre de mouvements effectués au cours de cette partie
	*@return: int le nombre de mouvement
	*/
	public int getNbrMove(){
		return this.nbrMove;
	}

	/** Méthode permettant de connaître l'état du jeu (victoire ou en cours)
	* @return: boolean le marqueur victoire
	*/
	public boolean getVic(){
		return p.getVic();
	}

	/** Méthode donnant accès à la difficulté de la partie en cours
	* @return: String  la difficulté
	*/
	public String getDif(){
		return this.dif;
	}

	/** Méthode donnant accès au niveau de la partie en cours
	* @return: int le niveau
	*/
	public int getNiv(){
		return this.niv;
	}

	/** Méthode permettant de valider la victoire
	*/
	public void victory(){
		p.victory();
	}
}
