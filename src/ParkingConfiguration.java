package rush.hour;

import java.util.ArrayList;

public class ParkingConfiguration {

    private int levelNumber;
    private Parking p;

    /**
     * A constructor for the first level parking configuration
     * @param p 
     */
    public ParkingConfiguration (Parking p) {
        this.levelNumber = 0;
        this.p = p;
    }
    /**
     * A constructor for the parking configuration with a level>0
     * @param p
     * @param move
     * @param lvl 
     */
    public ParkingConfiguration (Parking p, String move, int lvl) {
        this.levelNumber = lvl;
        this.p=p;
        p.move(move);
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
        ArrayList<Vehicule> vehicles = p.getListe_vehicules();
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
        ParkingConfiguration newcfg= new ParkingConfiguration (p, move, levelNumber+1);
        return newcfg;
    }

    /**
     * Method that return a list of all the possible parking configurations possible with the current parking
     * @return the List of all the possibles ParkingConfigurations
     */
    public ArrayList<ParkingConfiguration> generateAllPossibleConfig ()
    {
        ArrayList<ParkingConfiguration> newcfg= new ArrayList();
        ArrayList<String> possibleMoves = getAllVehiclesMoves ();
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
        return p;
    };

}