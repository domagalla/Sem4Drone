/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;

/**
 *
 * @author Oliver
 */
public class TextVisualizer implements Observer{
    
    GUI guiInstance = new GUI();
    
    @Override
    public void update(Observable o, Object arg) {
        //nur ausführen wenn nicht nur die Drohne für das Model übergeben wird
        if(!arg.getClass().getName().equals("ardrone.Actor")){
            visualize((double[])arg);
           
        }
    }
    
    public void visualize(double[] pos){
        //pos beinhaltet Position in x, y und z und die Rotation um die Z-Achse
	System.out.println("Drone bewegt sich auf " + pos[0] +", "+ pos[1] +", "+ pos[2]);
        System.out.println("Drohne ist um "+ pos[3] +" Grad rotiert");
     //  GUI guiInstance = new GUI();
        guiInstance.launch();
        
    }
	
}
