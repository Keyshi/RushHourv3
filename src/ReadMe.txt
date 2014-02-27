/********************************************************/
/******************* Auteurs du code ********************/
/********************************************************/
CLENET Nicolas
HAUTIER Ludovic
MAHIEUX Denis
RAHAMEFY Landry



/********************************************************/
/**************** Modifications de code *****************/
/********************************************************/

Classe Vehicule :
	-Ajout de la méthode getDir pour obtenir la direction du véhicule et utiliser cette dernière dans la class ParkingController.
	-Modification de la méthode move -> abandon du switch (String) qui ne passe que sur les dernières versions de java -> remplacement par des if(equals()).
	-Modification de la méthode get qui passe de private à public.

Classe Rectangle:
	-Ajout des méthodes getPosX et getPosY pour obtenir la position du rectangle et utiliser cette dernière dans la class ParkingController.
	
Classe Constants:
	-Retour aux tailles de base pour plus de fluidité -> SQUARE = 30 BORDER = 4.
	
Classe Parking:
	-Ajout d'une variable de controle (ParkingController pc).
	-Ajout d'une variable permettant de savoir si le jeu est gagné ou pas (boolean victoire).
	-Ajout au constructeur de classe, l'initialisation de pc et victoire.
	-Ajout à la méthode add, la mise en évidence dans le controller des cases occupées par le véhicule ajouté au parking (tableau de boolean à deux dimensions).
	-Modification de la méthode move qui ne génère plus d'exception non géré.
	-Ajout de la méthode victory qui modifie la variable victoire.
	
Classe Canvas:
	-Afin de mettre en place une interface graphique, quelques modifications ont été faites dans le constructeur:
		-Création de la fenêtre via la classe IHM.
		-Centrer la fenêtre à l'écran
		-Empêcher le redimentionnement de la fenêtre (évite certains bugs d'ordre graphique)


	
/********************************************************/
/********************* Nouveau code *********************/
/********************************************************/

classe BouttonsCtrl :
	-Utilisation d'un thread -> évite l'effet de blocage du bouton sur le thread principal et donc sur le déplacement dynamique des véhicules.
	
Classe ParkingFactory :
	-La méthode move est synchronized, ce qui évite les bugs du jeu lors d'un multi-clic sur le bouton "valider"

Warning à la compilation:
	-Le cast dans la méthode chargerJoueur de la classe Player est considéré comme "comportant des risques". Lors de l'exécution, chargerJoueur n'est pas bloquant.



/********************************************************/
/********************** Programme ***********************/
/********************************************************/	

Le jeu se lance et s'exécute sans erreurs.


Problème notable: 
lors d'une saisie dans le JFormatedTextField, une mémoire semble s'activer. Même en modifiant le contenu de cet objet (setText("")), il semble garder son ancienne valeur sans pour autant l'afficher.
Ce qui a pour conséquences: 	-on entre une valeur (PU3), on valide, le véhicule se déplace.
				-on entre aucune valeur, on valide à nouveau, la mémoire s'active et relance la valeur précédente (PU3) et le véhicule tente un déplacement.
Ce problème n'a pas grande influence sur le déroulement de la partie, mais peut fausser le score en fonction des actions de l'utilisateur. 


Lancer le programme:
	-se placer dans le répertoire ww
	-configurer son classpath à l'aide de la commande : export CLASSPATH=../class
	-compiler le programme à l'aide de la commande : javac -d ../class ../src/*.java
	-lancer le jeu à l'aide de la commande : java RushHour2


Pour créer un joueur, charger un joueur, lancer une nouvelle partie ou sauvegarder un score, utiliser le menu "Jeu" en haut à gauche de la fenêtre.
Pour déplacer un véhicule, utiliser le champs texte puis cliquer sur "Valider"
