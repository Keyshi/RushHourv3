
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

import java.text.ParseException;

import java.util.Hashtable;

/**
* IHM est la classe qui gère l'affichage et tout ce qui est graphique.
*
* @author: CLENET Nicolas
* @author: HAUTIER Ludovic
* @author: MAHIEUX Denis
* @author: RAHAMEFY Landry
*
* @version: 2014.02.26
*/

public class IHM extends JFrame{

	//IHM unique, utilisation d'un Singleton
	private static IHM IHMSingleton;
	
	//Barre de menu
	private JMenuBar bar;
	private JMenu menuJeu;
	private JMenuItem itemNewPlayer, itemLoadPlayer, itemNewGame, itemSave;
	
	//affichage gauche
	private JFormattedTextField commande;
	private JLabel label;
	private JButton valider;
	private JTextArea saisies;
	
	
	//affichage droit
	private JTextArea scores;


	/** Constructeur de la classe IHM 
	* @param: JPanel canvas, le panel centrale de l'IHM
	*/
	private IHM(JPanel canvas){
		this.creerInterface();
		this.attacherReactions();
		canvas.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		this.setCenterPane(canvas);
	}

	/** Permet d'avoir une référence sur l'IHM
	* @return: IHM l'interface graphique
	*/
	public static IHM getIHM() {
		return IHMSingleton;
	}

	/** Permet d'avoir une référence sur l'IHM
	* @param: JPanel canvas, le panel centrale de l'IHM
	* @return: IHM l'interface graphique
	*/
	public static IHM getIHM(JPanel canvas) {
		if(IHMSingleton == null) {
			IHMSingleton = new IHM (canvas);
		}
		
		return IHMSingleton;
	}
	
	/** Méthode principale de création de l'interface graphique
	*/
	private void creerInterface(){
		this.setLayout(new BorderLayout());
		this.creerMenu();
		this.creerAffGauche();
		this.creerAffDroit();
	}
	
	/** Création du menu, en haut a gauche de la fenêtre
	*/
	private void creerMenu (){
		bar = new JMenuBar();
		menuJeu = new JMenu("Jeu");
		itemNewPlayer = new JMenuItem("Nouveau Joueur");
		itemLoadPlayer = new JMenuItem("Charger Joueur");
		itemNewGame = new JMenuItem("Nouvelle partie");
		itemSave = new JMenuItem("Sauvegarder");
		
		menuJeu.add(itemNewPlayer);
		menuJeu.add(itemLoadPlayer);
		menuJeu.add(itemNewGame);
		menuJeu.add(itemSave);
		
		bar.add(menuJeu);
		
		this.setJMenuBar(bar);
	}

	/** Création de l'affichage gauche de la fenêtre
	* espace de saisie d'un déplacement
	* bouton de validation du déplacement
	* affichage du score et des déplacements déjà effectués
	*/
	private void creerAffGauche(){
	
		try{
			MaskFormatter mask = new MaskFormatter("UU#");
			commande = new JFormattedTextField(mask);
			commande.setColumns (4);
		}
		catch (ParseException e){}
		saisies = new JTextArea();
		saisies.setColumns(20);
		saisies.setEditable(false);
		saisies.setFont(new Font("Arial", Font.BOLD, 11));
		/*saisies.setWrapStyleWord(true); //ne fonctionne pas*/
		valider = new JButton("Valider");
		label = new JLabel ("Déplacement : ");
		
		JPanel corps = new JPanel(new GridLayout(0,1,0,5));
		JPanel corpsHaut = new JPanel(new GridLayout(0,1,0,5));
		JPanel corpsHautHaut = new JPanel();
		JPanel corpsHautBas = new JPanel();
		
		corps.setBackground(Color.WHITE);
		corpsHaut.setBackground(Color.WHITE);
		corpsHautHaut.setBackground(Color.WHITE);
		corpsHautBas.setBackground(Color.WHITE);
		
		corpsHautHaut.add(label);
		corpsHautHaut.add(commande);
		corpsHautBas.add(valider);
		
		corpsHaut.add(corpsHautHaut);
		corpsHaut.add(corpsHautBas);
		
		corps.add(corpsHaut);
		corps.add(saisies);
		
		this.add(corps, BorderLayout.WEST);
	}

	/** Création de l'affichage droit de la fenêtre
	* nom du joueur actif
	* scores du joueur actif
	*/
	private void creerAffDroit () {
		scores = new JTextArea();
		scores.setWrapStyleWord(true);
		scores.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		scores.setColumns(20);
		scores.setEditable(false);
        	scores.setFont(new Font("Arial", Font.BOLD, 11));
		JScrollPane zoneScrolable = new JScrollPane(scores);
		this.add(zoneScrolable, BorderLayout.EAST);
	}

	/** Créer les listener et les attache aux boutons de l'IHM
	*/
	private void attacherReactions () {
		itemNewPlayer.addActionListener(new BouttonsCtrl (Constants.NEWPLAYER));
		itemLoadPlayer.addActionListener(new BouttonsCtrl (Constants.LOADPLAYER));
		itemNewGame.addActionListener(new BouttonsCtrl (Constants.NEWGAME));
		itemSave.addActionListener(new BouttonsCtrl (Constants.SAVE));
		valider.addActionListener(new BouttonsCtrl (Constants.VALIDER));
	}

	/** Modifie le panel central de l'IHM
	* @param: JPanel canvas, le nouveau panel central de l'IHM
	*/
	private void setCenterPane(JPanel canvas){
		this.add(canvas, BorderLayout.CENTER);
	}

	/** Donne accès au JFormattedTextField de l'IHM
	* @return: JFormattedTextField l'espace de saisie des déplacements
	*/
	public JFormattedTextField getCommande(){
		return this.commande;
	}

	/** Récupère les scores du joueur actif et les affiche à l'ecran
	*/
	public void afficheScores(){
	
		Hashtable <String, String[]> tab = Player.getJoueur().getScores();
		String s = ("Joueur : "+Player.getJoueur().getNom()+"\n");
		
		for (int i=0; i<Constants.levels.length; i++){
			String[] tmp = tab.get(Constants.levels[i]);
			s+= ("\n"+"Difficulté "+Constants.levels[i]+" :\n");
			for(int j = 0; j<tmp.length; j++){
				
				s+=("     -niveau "+(j+1)+" Score : "+tmp[j]+"\n");
			}
			
		}
		
		scores.setText(s);
	}

	/** Gère les affichages du score et des déplacements déjà effectués
	* lorsque 7 déplacement on été réalisés -> retour à la ligne
	* lorsque la 6ème ligne devrait être affichée-> suppression de la première ligne 
	*/
	public void modifAffGauche(int nbrMove, String deplacement){
		if (nbrMove!=0 && deplacement.length()!=0){
			String [] tab;
			String aff = "Score : "+nbrMove+"\n";
			
			//passe outre le fait que saisies.setWrapStyleWord(true) ne fonctionne pas
			if (saisies.getText()!=null && saisies.getText().length()!=0){
				tab = saisies.getText().split("\n");
			
				int tmp;
				if (tab.length > 5 && tab[5].length()>27) tmp = 2;
				else tmp = 1;
			
				for (int i = tmp; i<tab.length-1; i++){
					aff+=(tab[i]+"\n");
					tmp++;
				}
				String s = tab[tmp];
			
				if (s.length()>27){
					aff+= s.substring(0, 28)+"\n";
					s=s.substring(28);
				}
				aff+=s;
			}
			aff+= deplacement+" ";
			saisies.setText(aff);
		}else saisies.setText("");
	}
}









