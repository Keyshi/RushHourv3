package rush.hour;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

import java.util.ArrayList;

import java.lang.Thread;

/**
* BouttonsCtrl est la classe qui permet de gérer les réactions aux clics boutons.
*
* @author: CLENET Nicolas
* @author: HAUTIER Ludovic
* @author: MAHIEUX Denis
* @author: RAHAMEFY Landry
*
* @version: 2014.02.26
*/
 
public class BouttonsCtrl implements ActionListener {

	//identifiant du bouton active
	private int a;

	/** Constructeur de la classe BouttonsCtrl 
	* @param: int action identifiant du bouton
	*/
	public BouttonsCtrl (int action){
		this.a=action;
	}

	/** La méthode actionPerformed provient de ActionListener.
	* Elle se déclenche à chaque clic sur le bouton auquel ce listener est affecté.
	* @param: ActionEvent e évènement déclencheur de l'action
	*/
	public void actionPerformed ( ActionEvent e ) {
        	
		switch (a){
		
			//réaction au clic sur le boutton "Nouveau Joueur"
			case Constants.NEWPLAYER:
		
				String s = (String)JOptionPane.showInputDialog(
						    null,
						    "Chargement du joueur: ",
						    "Choix du joueur",
						    JOptionPane.PLAIN_MESSAGE,
						    null,
						    null,
						    "Player");
						    
				if ((s != null)){
					if((s.replaceAll(" ","").length() > 0) && (!Player.getJoueur().getPlayers().contains(s))) {
						Player.getJoueur().addPlayer(s);
						IHM.getIHM().afficheScores();
					}else JOptionPane.showMessageDialog(null, "Ce nom de joueur n'est pas disponible", "avertissement", JOptionPane.WARNING_MESSAGE);
				}
				break;
		

			//réaction au clic sur le bouton "Charger Joueur"
			case Constants.LOADPLAYER:
				ArrayList list = Player.getJoueur().getPlayers();
		
				String [] players = new String[list.size()];
		
				for (int i = 0; i<players.length; i++){
					players[i] = (String) list.get(i);
				}
		
				if ((players.length != 0) && (players[0] != null)){
					String nom = (String)JOptionPane.showInputDialog(
							    null,
							    "Choix du nom de joueur: ",
							    "Choix du joueur",
							    JOptionPane.PLAIN_MESSAGE,
							    null,
							    players,
							    players[0]);

					if ((nom != null) && (nom.length() > 0)) {
						try{
							Player.getJoueur().chargerJoueur(nom);
							IHM.getIHM().afficheScores();
						}
						catch (Exception exception){
							Player.getJoueur().addPlayer(nom);
							try{
								Player.getJoueur().chargerJoueur(nom);
								IHM.getIHM().afficheScores();
							}catch (Exception exception2){JOptionPane.showMessageDialog(null, exception2.getMessage(), "avertissement", JOptionPane.WARNING_MESSAGE);}
						}
				
					}
				}else JOptionPane.showMessageDialog(null, "Aucun joueur n'est enregistré", "avertissement", JOptionPane.WARNING_MESSAGE);
				break;

			//réaction au clic sur le bouton "Nouvelle partie"
			case Constants.NEWGAME:
				if (Player.getJoueur().getNom()!=null){
					String lvl = (String)JOptionPane.showInputDialog(
								    null,
								    "Choisissez la difficulté: ",
								    "Choix de la difficulté",
								    JOptionPane.PLAIN_MESSAGE,
								    null,
								    Constants.levels,
								    Constants.levels[0]);

						if ((lvl != null) && (lvl.length() > 0)) {
							String [] tab = new String[ParkingFactory.getParkFactor().getNbrNiv(lvl)];
							for (int i = 0; i<tab.length; i++){
								tab[i]=(""+(i+1));
							}
							String sNiv = (String) JOptionPane.showInputDialog(
								    null,
								    "Choisissez le niveau: ",
								    "Choix du niveau",
								    JOptionPane.PLAIN_MESSAGE,
								    null,
								    tab,
								    tab[0]);
							int niv = 0;
							if (sNiv!=null) niv = Integer.parseInt(sNiv);
							if (niv>0){
								ParkingFactory.getParkFactor().creerParking (lvl, niv-1);
								IHM.getIHM().modifAffGauche(0, "");
							}
						}
				}else JOptionPane.showMessageDialog(null, "Aucun joueur sélectionné", "avertissement", JOptionPane.WARNING_MESSAGE);
				break;


			//réaction au clic sur le bouton "Sauvegarder"
			case Constants.SAVE:
				try{
					if(!ParkingFactory.getParkFactor().getVic()) Player.getJoueur().addScore(-1);
				}
				catch (NullPointerException npe){}
				break;
		
		
			//réaction au clic sur le bouton "Valider"
			case Constants.VALIDER:
				Thread t = new Thread() {
					public void run() {
						if (ParkingFactory.getParkFactor().getDif()!=null){
							boolean res = false;
							System.out.println(((String) IHM.getIHM().getCommande().getValue()));
							String str = (String) IHM.getIHM().getCommande().getValue();
							IHM.getIHM().getCommande().setText("");
							if (str != null && str.length()==3){
								if (ParkingFactory.getParkFactor().get(str.substring(0, 1))!= null && Integer.parseInt(str.substring(2))>0){
									res = true;
								}
							}
							if(res) {
								try{
									ParkingFactory.getParkFactor().move(str);
								}catch (Exception except){
									JOptionPane.showMessageDialog(null, "VICTOIRE");
									ParkingFactory.getParkFactor().victory();
									ParkingFactory.getParkFactor().moveValide(str);
									Player.getJoueur().addScore(ParkingFactory.getParkFactor().getNbrMove());
								}
							}else JOptionPane.showMessageDialog(null, "Entrée non valide", "avertissement", JOptionPane.WARNING_MESSAGE);
						}else{ 
							JOptionPane.showMessageDialog(null, "Aucune partie lancée", "avertissement", JOptionPane.WARNING_MESSAGE);
							IHM.getIHM().getCommande().setText("");
						}
					}
				};
				t.start();
				break;
		}
		
	}
}
