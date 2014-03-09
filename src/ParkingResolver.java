
package rush.hour;
import java.util.ArrayList;
/**
 * ParkingResolver finds ad play a solution to the level in its current state
 * @author Xavier Leblond, Kevin Meyer, Raphaël Voirin, Meryem Fourkane.
 * @version 2014.03.09
 */
public class ParkingResolver {
    
    private Parking p;
    private ArrayList<ParkingConfiguration> conf;       
    
    /**
     * Constructor of the class
     * @param p 
     */
    public ParkingResolver (Parking p)
    {
        this.p=p;
        conf=new ArrayList ();
    }
    
    /**
     * Method that calculates a solution to the level in its current state
     * @return a solution as an ArrayList of moves
     */
    public ArrayList<String> solution ()
    {
        // True if the solution was found
        boolean victory=false;
        // Solution
        ArrayList<String> sol= new ArrayList();
        // Path from the given parking to the solution
        ArrayList<ArrayList> chemins = new ArrayList();
        // Temporary path used in the iterations
        ArrayList chemintemp = new ArrayList();
        // First iteration
        ParkingConfiguration pc = new ParkingConfiguration (p);
        ArrayList<ParkingConfiguration> newConfs= pc.generateAllPossibleConfig();
        for (int i=0; i<newConfs.size();i++)
        {
            chemintemp.clear();
            chemintemp.add(i);
            if (newConfs.get(i).getParking().get("X").getPosX()==6*Constants.SQUARE)
            {
                sol.add(pc.getAllVehiclesMoves().get(i));
                victory=true;
            }
            else
            {
                if (!conf.contains(newConfs.get(i)))
                {
                    conf.add(newConfs.get(i));
                    chemins.add(chemintemp);
                }
            }
        }
        //Next iterations
        while (victory)
        {
            int longueurNiveauInf=chemins.get(chemins.size()-1).size();
            
        }
        return sol;
    }
    
    /**
     * Method that plays the solution
     */
    public void playSolution ()
    {
        ArrayList<String> sol= solution ();
        for (int i=0; i<sol.size();i++)
        {
            p.move(sol.get(i));
        }
    }
}
