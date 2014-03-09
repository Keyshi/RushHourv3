
package rush.hour;
import java.util.ArrayList;
import java.util.HashSet;
/**
 * ParkingResolver finds ad play a solution to the level in its current state
 * @author Xavier Leblond, Kevin Meyer, Raphaël Voirin, Meryem Fourkane.
 * @version 2014.03.09
 */
public class ParkingResolver {
    
    private Parking p;
    private HashSet Conf;       
    
    /**
     * Constructor of the class
     * @param p 
     */
    public ParkingResolver (Parking p)
    {
        this.p=p;
        Conf=new HashSet ();
    }
    
    /**
     * Method that calculates a solution to the level in its current state
     * @return a solution as an ArrayList of moves
     */
    public ArrayList<String> solution ()
    {
        ArrayList<String> sol= new ArrayList();
        
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
