import java.util.*;

public class PlateauSimulation extends Plateau {

  Plateau origine;

  public PlateauSimulation(Plateau p){
    origine=p;
  }

  public void initialisation(){

    for(int i=0; i<this.cases.length; i++){
      for(int j=0; j<this.cases[0].length; j++){
        this.cases[i][j] = origine.cases[i][j];
      }
    }
  }

}
