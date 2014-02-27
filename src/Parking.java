
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Parking is the playfield class, containing the Vehicules and moving them
 *
 * @author: Romain Bressan
 * @author: Jean-Baptiste Carlus
 * @author: Siham Ben Arbiya
 * @author: Anas Alaoui M'Darhri
 *
 * @author: CLENET Nicolas
 * @author: HAUTIER Ludovic
 * @author: MAHIEUX Denis
 * @author: RAHAMEFY Landry
 *
 * @version: 2014.02.26
 */

public class Parking{

	// the Collection containing all the vehicules on the field
	private ArrayList<Vehicule> liste_vehicules;
	// reference sur le ParkingController
	private ParkingController pc;
	// indication de fin de partie
	private boolean victoire;
	
	
	/**  Constructor of the Parking object, instanciating the Grid (making the display happen) and the vehicules ArrayList.
	*/
	public Parking(){ 
		this.victoire = false;
		Grid myGrid = new Grid();
		liste_vehicules = new ArrayList<Vehicule>();
		this.pc = new ParkingController();
	}

	/** Add in collection method 
	 * @param: Vehicule vehicule The Vehicule to add
	 */
	public void add(Vehicule mon_vehicule)
	{
		liste_vehicules.add(mon_vehicule);
		// y->i et x->j afin de reproduire au mieux la grille de jeu dans un espace 2D
		int i = (int)((mon_vehicule.getPosY()-Constants.BORDER)/Constants.SQUARE);
		int j = (int)((mon_vehicule.getPosX()-Constants.BORDER)/Constants.SQUARE);
		
		if (Constants.HORIZONTAL.equals(mon_vehicule.getDir ())){
			for (int iter=j; iter<(j+mon_vehicule.getLongueur()); iter++){
				pc.tabTrue (i, iter);
			}
		}
		else{
			for (int iter=i; iter<(i+mon_vehicule.getLongueur()); iter++){
				pc.tabTrue (iter, j);
			}
		}
	}

	/** Get from collection method
	 * @param: String name The name of the Vehicule
	 * @return: Vehicule vehicule The vehicule corresponding to the name
	 */
	public Vehicule get(String name)
	{
		for(Vehicule v : liste_vehicules)
			if(v.getNom().equals(name))
				return v;
		return null;
	}

	/** Move vehicule method
	 * @param: String order The moving order, i.e. "XU2" to make the X car go 2 slots up
	 * @throws: IllegalArgumentException if the order isn't fitting the spec.
	 */
	public void move(String order)
	{
		if(order.length() != 3)JOptionPane.showMessageDialog(null, "Les données de déplacement sont incorrectes", "avertissement", JOptionPane.WARNING_MESSAGE);
		Vehicule mon_vehicule = get(order.charAt(0) + "");
		int dist = Integer.parseInt(order.charAt(2) + "");
		if (pc.verifDeplacement (mon_vehicule, order.charAt(1)+"", dist)) {
			int lastPosI = (int)((mon_vehicule.getPosY()-Constants.BORDER)/Constants.SQUARE);
			int lastPosJ = (int)((mon_vehicule.getPosX()-Constants.BORDER)/Constants.SQUARE);
			mon_vehicule.move(order.charAt(1)+"", dist);
			int newPosI = (int)((mon_vehicule.getPosY()-Constants.BORDER)/Constants.SQUARE);
			int newPosJ = (int)((mon_vehicule.getPosX()-Constants.BORDER)/Constants.SQUARE);
			ParkingFactory.getParkFactor().moveValide(order);
			if (Constants.HORIZONTAL.equals(mon_vehicule.getDir ())){
				for (int iter=lastPosJ; iter<(lastPosJ+mon_vehicule.getLongueur()); iter++){
					pc.tabFalse (lastPosI, iter);
				}
				for (int iter=newPosJ; iter<(newPosJ+mon_vehicule.getLongueur()); iter++){
					pc.tabTrue (newPosI, iter);
				}
			}
			else{
				for (int iter=lastPosI; iter<(lastPosI+mon_vehicule.getLongueur()); iter++){
					pc.tabFalse (iter, lastPosJ);
				}
				for (int iter=newPosI; iter<(newPosI+mon_vehicule.getLongueur()); iter++){
					pc.tabTrue (iter, newPosJ);
				}
			}
		}
		else JOptionPane.showMessageDialog(null, "Déplacement impossible", "avertissement", JOptionPane.WARNING_MESSAGE);
	}

	/** A method to know the state of the game (victory or in progess)
	* @return: boolean the victory marker
	*/
	public boolean getVic(){
		return this.victoire;
	}

	/** A method used to validate the victory
	*/
	public void victory(){
		this.victoire = true;
	}
}
