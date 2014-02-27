
/**
* ParkingController est la classe qui gère les collisions entre véhicules.
*
* @author: CLENET Nicolas
* @author: HAUTIER Ludovic
* @author: MAHIEUX Denis
* @author: RAHAMEFY Landry
*
* @version: 2014.02.26
*/

public class ParkingController{

	//tableau à deux dimensions qui représente la grille jeu
	//true -> case occupée
	//false -> case vide
	private boolean [][] tab;

	/** Constructeur de la ParkingController 
	* initialise toutes les cases de tab à false
	*/
	public ParkingController (){
		tab = new boolean [6][6];
		for (int i=0; i< tab.length; i++){
			for (int j=0; j< tab[i].length; j++){
				tab[i][j] = false;
			}
		}
	}

	/** Méthode qui permet de savoir si un déplacement est possible ou non
	* @param: Vehicule v le véhicule qui doit se déplacer
	* @param: String sens le sens du déplacement
	* @param: int dist le nombre de case a parcourir
	* @return: boolean true si le déplacement est possible, false sinon
	*/
	public boolean verifDeplacement (Vehicule v, String sens, int dist){
		boolean res = false;
		String dir;
		
		if (Constants.RIGHT.equals(sens) || Constants.LEFT.equals(sens)){
			dir = Constants.HORIZONTAL;
		}
		else {
			dir = Constants.VERTICAL;
		}
		
		if (v.getDir().equals(dir)){
				res = this.verifCases(v, sens, dist);
		}
		
		return res;
	}

	/** Méthode qui permet de savoir si, lors d'un deplacement, il y aura collision
	* @param: Vehicule v le véhicule qui doit se déplacer
	* @param: String sens le sens du déplacement
	* @param: int dist le nombre de case à parcourir
	* @return: boolean true si le déplacement est possible, false sinon
	*/
	private boolean verifCases (Vehicule v, String sens, int dist){
		boolean res = false;
		boolean tmp = false;
		int posX = (int)((v.getPosX()-Constants.BORDER)/Constants.SQUARE);
		int posY = (int)((v.getPosY()-Constants.BORDER)/Constants.SQUARE);
		int i = posY;
		int j = posX;
		if(sens.equals(Constants.UP) && ((posY-dist)>-1)){
			while ((i>(posY-dist)) && (!tmp)){
				i--;
				tmp = tab[i][j];
			}
			res = !tmp;
	    	}else 	if(sens.equals(Constants.LEFT) && ((posX-dist)>-1)){
		    		while ((j>(posX-dist)) && (!tmp)){
					j--;
					tmp = tab[i][j];
				}
				res = !tmp;
	    		}else 	if (sens.equals(Constants.RIGHT)){
	    				int cmpt=0;
	    				boolean block = false;
	    				if(posY == 2){
	    					cmpt = 4;
	    					block = true;
	    				}
    					if ((posX+(v.getLongueur()-1)+dist)<6){
    						cmpt = posX+dist;
    						block = true;
    					}
    					if(block){
    						j+=(v.getLongueur()-1);
	    					while ((j<=cmpt+(v.getLongueur()-2)) && (!tmp)){
							j++;
							tmp = tab[i][j];
						}
						res = !tmp;
					}	
					
	    			}else{	if (sens.equals(Constants.DOWN)){
	    					if ((posY+(v.getLongueur()-1)+dist)<6){
	    						i+=(v.getLongueur()-1);
							while ((i<=(posY+dist+(v.getLongueur()-2))) && (!tmp)){
								i++;
								tmp = tab[i][j];
							}
							res = !tmp;
						}
					}
	    			}
		return res;
	}
	
	/** Méthode qui permet la modification d'un élément de tab
	* @param: boolean b le nouvel élément
	* @param: int i premier index
	* @param: int j second index
	*/
	private void setTab (boolean b, int i, int j){
		tab[i][j] = b;
	}

	/** Méthode qui permet de passer un element de tab à true
	* @param: int i premier index
	* @param: int j second index
	*/
	public void tabTrue (int i, int j){
		setTab (true, i, j);
	}

	/** Méthode qui permet de passer un element de tab à false
	* @param: int i premier index
	* @param: int j second index
	*/
	public void tabFalse(int i, int j){
		setTab (false, i, j);
	}
}
