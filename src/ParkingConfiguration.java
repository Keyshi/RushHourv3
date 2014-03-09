public class ParkingConfiguration {

   private int levelNumber;
   private Parking p;

   public ParkingConfiguration (Parking p) {
      this.levelNumber = 0;
      this.p = p;
   }

   /**
    * Gives all the possible moves of a vehicule.
    * @param Vehicule v the vehicule to check.
    * @return the list of possible move.
    */
   public ArrayList<String> getAllMoves(Vehicule v)
   {
      ArrayList<String> possibleMove;
      String direction = v.getDir();
      String name = v.getNom();
      int distance = 0;
      String[] direction = {"L","R","U","D"};
      for (int i = 0 ; i < direction.length ; i++) {
         do {
            distance = 1;
            // TODO : Instancier ParkingController
            isMovePossible = verifDeplacement(v, direction[i], distance);
            if (isMovePossible == true) {
               possibleMove.add(name + direction[i] + distance);
            }
            distance++;
         } while (isMovePossible == true);
      }
      return possibleMove;
   }


   public void getParking()
   {};

}
