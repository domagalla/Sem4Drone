/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Oliver
 */
public class TextVisualizer implements Observer{
    
    
    @Override
    public void update(Observable o, Object arg) {
        if(!arg.getClass().getName().equals("ardrone.Actor")){
            visualize((double[])arg);
        }
    }
    
    public void visualize(double[] pos){
	System.out.println("Drone bewegt sich auf " + pos[0] +", "+ pos[1] +", "+ pos[2]);
        System.out.println("Drohne ist um "+ pos[3] +" Grad rotiert");
    }

    
	
	
	
}
