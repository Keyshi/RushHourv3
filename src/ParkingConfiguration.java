package rush.hour;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * ParkingConfiguration represents the configuaration of a parking
 * @author Xavier Leblond, Kevin Meyer, Raphaël Voirin, Meryem Fourkane.
 * @version 2014.03.09
 */
public class ParkingConfiguration {
   
   // The level number of the parking configuration.
   private int levelNumber;
   // The array that includes the configuration of the parking an the moves done
   private String [] config;
   // The parking associated with the configuration
   private Parking p;

    /**
     * A constructor for the first level parking configuration
     * @param p a parking object
     */
    public ParkingConfiguration (Parking p) {
        this.p=p;
        this.levelNumber = 0;
        config=new String[2];
        config[0]=config[1]="";
        for (int i=0; i<p.getListe_vehicules().size(); i++)
        {
            String vehicule=new String();
            Vehicule vehicle=p.getListe_vehicules().get(i);
            vehicule += vehicle.getNom() + vehicle.getDir() + (vehicle.getPosX()-Constants.BORDER)/Constants.SQUARE + "" + (vehicle.getPosY()-Constants.BORDER)/Constants.SQUARE + "";
            config [0] += vehicule + " ";
        }
    }
    /**
     * A constructor for the parking configuration with a level>0
     * @param pc a parking configuration object
     * @param move the movement 
     */
    public ParkingConfiguration (ParkingConfiguration pc, String move) {
        this.levelNumber = pc.levelNumber+1;
        this.config=pc.config;
        this.config[1] += move + " ";
        int index=0;
        do{
            String temp=config[0].substring(index);
            index=config[0].indexOf(move.substring(0,1));
        }while(index%4!=0);
        int newX=(int)(config[0].charAt(index+2));
        int newY=(int)(config[0].charAt(index+3));
        switch (move.charAt(1))
        {
            case 'D':
                newY += (int)move.charAt(2);
                break;
            case 'U':
                newY -= (int)move.charAt(2);
                break;
            case 'R':
                newX += (int)move.charAt(2);
                break;
            case 'L':
                newX -= (int)move.charAt(2);
                break;
        }
        config[0].replace(config[0].substring(index+2,index+4), newX+""+newY+"");
        this.p=getParking();
    }

    /**
     * Gives all the possible moves for a vehicle.
     * @param Vehicule v the vehicule to check.
     * @return the list of the possible moves for the Vehicule.
     */
    public ArrayList<String> getAllMoves(Vehicule v)
    {
        ArrayList<String> possibleMove = new ArrayList();
        String name = v.getNom();
        int distance;
        boolean isMovePossible;
        String[] sens = {"L","R","U","D"};
        ParkingController pc=p.getParkingController();
        for (int i = 0 ; i < sens.length ; i++) {
            distance=1;
            do {
                isMovePossible = pc.verifDeplacement(v, sens[i], distance);
                if (isMovePossible) {
                    possibleMove.add(name + sens[i] + distance);
                }
                distance++;
           } while (isMovePossible);
        }
        return possibleMove;
    }


    /**  
     * Gives all the possibles moves on the parking
     * @return the list of all possible moves
     */
    public ArrayList<String> getAllVehiclesMoves ()
    {
        ArrayList<Vehicule> vehicles =  new ArrayList(p.getListe_vehicules());
        ArrayList<String> possibleMove = new ArrayList();
        for (int i=0; i< vehicles.size(); i++)
        {
            possibleMove.addAll(getAllMoves(vehicles.get(i)));
        }
        return possibleMove;
    }

    /**
     * Method that instanciates a possible new parking configuration
     * @param a possible move
     * @return a possible ParkingConfiguration
     */
    public ParkingConfiguration generateConfig (String move)
    {
        ParkingConfiguration newcfg= new ParkingConfiguration (this, move);
        return newcfg;
    }

    /**
     * Method that return a list of all the possible parking configurations possible with the current parking
     * @return the List of all the possibles ParkingConfigurations
     */
    public ArrayList<ParkingConfiguration> generateAllPossibleConfig ()
    {
        ArrayList<ParkingConfiguration> newcfg= new ArrayList();
        ArrayList<String> possibleMoves = new ArrayList(getAllVehiclesMoves ());
        for (int i=0; i< possibleMoves.size(); i++)
        {
            newcfg.add(generateConfig(possibleMoves.get(i)));
        }
        return newcfg;
    }

    /**
     * A method that return the parking of the current parking configuration
     * @return the Parking of the Parkingconfiguration
     */
    public Parking getParking()
    {
        this.p = new Parking ();
        Hashtable <String, String[]>vehicles  = new Hashtable < String, String []> ();
        try {	FileReader reader = new FileReader ("../data/vehicles.dat");
            Scanner in = new Scanner (reader);
            while (in.hasNextLine()){
		String tmp= in.nextLine();
		String [] tab = new String [2];
		tab [0] = tmp.substring(1, 2);
		tab [1] =tmp.substring(2);
                vehicles.put (tmp.substring(0, 1), tab);
            }
        }catch(FileNotFoundException e) {}
	String [] vehicules = config[0].split(" ");
	for(int i = 0; i<vehicules.length; i++){
            String [] tmp = vehicles.get(vehicules[i].substring(0, 1));
            p.add(new Vehicule(	vehicules[i].charAt(0),     //char nom
            Integer.parseInt(tmp[0]),     //int longueur
            vehicules[i].substring(1, 2),     //String direction
            new Slot(Integer.parseInt(vehicules[i].substring(2, 3)),Integer.parseInt(vehicules[i].substring(3))),     // Slot position
            tmp[1]));     //String couleur
	}
        return this.p;
    };
    
    /**
     * Method that gives the level of the config
     * @return level of the config
     */
    public int getlevel ()
    {
        return this.levelNumber;
    }
    
    /**
     * Method that returns the array config
     * @return array config 
     */
    public String [] getConfig()
    {
        return config;
    }
    
    /**
     * Method that tests if two configurations have the same parking
     * @param pc a parking configuration
     * @return true if they have the same parking
     */
    public boolean equals (ParkingConfiguration pc)
    {
        return (this.config==pc.config);
    }

}
