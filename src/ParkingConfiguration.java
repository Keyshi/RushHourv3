package rush.hour;

import java.util.ArrayList;

public class ParkingConfiguration {

   private int levelNumber;
   private Parking p;

   public ParkingConfiguration (Parking p) {
      this.levelNumber = 0;
      this.p = p;
   }
   
   public ParkingConfiguration (Parking p, String move, int lvl) {
      this.levelNumber = lvl;
      this.p=p;
      p.move(move);
   }

   /**
    * Gives all the possible moves of a vehicule.
    * @param Vehicule v the vehicule to check.
    * @return the list of possible move.
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
    * @return the list of all vehicles possible moves
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


   public Parking getParking()
   {
       return p;
   };

}