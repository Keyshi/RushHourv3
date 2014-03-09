
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
        ArrayList<ParkingConfiguration> newConfs= new ArrayList(pc.generateAllPossibleConfig());
        for (int i=0; i<newConfs.size();i++)
        {
            chemintemp.clear();
            chemintemp.add(i);
            if (newConfs.get(i).getParking().get("X").getPosX()>=6*Constants.SQUARE)
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
        while (!victory)
        {
            // Length of the paths of the previous level of config
            int longueurNiveauInf=chemins.get(chemins.size()-1).size();
            // Counts the number of paths from the previous level of config
            int nombreNiveauInf=0;
            for (int i=0; i<chemins.size();i++)
            {
                if (chemins.get(i).size()==longueurNiveauInf)
                    nombreNiveauInf++;
            }
            // Calculates the configurations of the next level 
            for (int i=chemins.size()-nombreNiveauInf; i<chemins.size(); i++)
            {
                chemintemp.clear();
                chemintemp.addAll(chemins.get(i));
                newConfs.clear();
                newConfs.addAll(conf.get(i).generateAllPossibleConfig());
                for (int j=0; j<newConfs.size();j++)
                {
                    chemintemp.add(chemins.size());
                    if (newConfs.get(j).getParking().get("X").getPosX()>=6*Constants.SQUARE)
                    {
                        victory=true;
                        for (int k=0; k<longueurNiveauInf; k++)
                        {
                            if (k!=0)
                            {
                                int a=(int)chemintemp.get(k-1);
                                int b=(int)chemintemp.get(k);
                                sol.add(conf.get(a).getAllVehiclesMoves().get(b));
                            }
                            else
                            {
                                int b=(int)chemintemp.get(k);
                                sol.add(pc.getAllVehiclesMoves().get(b));
                            }
                        }
                    }
                    else
                    {
                        if (!conf.contains(newConfs.get(j)))
                        {
                            conf.add(newConfs.get(j));
                            chemins.add(chemintemp);
                        }
                    }
                }
            }
        }
        return sol;
    }
    
    /**
     * Method that plays the solution
     */
    public void playSolution ()
    {
        ArrayList<String> sol= new ArrayList(solution ());
        for (int i=0; i<sol.size();i++)
        {
            p.move(sol.get(i));
        }
    }
}
