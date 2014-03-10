
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
        ArrayList <String> sol=new ArrayList();
        // True if the solution was found
        boolean victory=false;
        // First iteration
        ParkingConfiguration pc = new ParkingConfiguration (p);
        ArrayList<ParkingConfiguration> newConfs= new ArrayList(pc.generateAllPossibleConfig());
        for (int i=0; i<newConfs.size();i++)
        {
            if (newConfs.get(i).getParking().get("X").getPosX()>=6*Constants.SQUARE)
            {
                victory=true;
                String temp []= newConfs.get(i).getConfig()[1].split(" ");
                for (int j=0; j< temp.length; j++)
                {
                    sol.add(temp[j]);
                }
            }
            else
            {
                if (!conf.contains(newConfs.get(i)))
                {
                    conf.add(newConfs.get(i));
                }
            }
        }
        //Next iterations
        while (!victory)
        {
            // Counts the number of paths from the previous level of config
            int nombreNiveauInf=0;
            for (int i=0; i<conf.size();i++)
            {
                if (conf.get(i).getlevel()==conf.get(conf.size()).getlevel())
                    nombreNiveauInf++;
            }
            // Calculates the configurations of the next level 
            for (int i=conf.size()-nombreNiveauInf; i<conf.size(); i++)
            {
                newConfs.clear();
                newConfs.addAll(conf.get(i).generateAllPossibleConfig());
                for (int j=0; j<newConfs.size();j++)
                {
                    if (newConfs.get(j).getParking().get("X").getPosX()>=6*Constants.SQUARE)
                    {
                        victory=true;
                        String temp []= newConfs.get(j).getConfig()[1].split(" ");
                            for (int k=0; k< temp.length; k++)
                            {
                                sol.add(temp[k]);
                            }
                    }
                    else
                    {
                        if (!conf.contains(newConfs.get(j)))
                        {
                            conf.add(newConfs.get(j));
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
