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
public class DroneActorPositionAdapter implements Observer{

    ScenarioADroneControl droneCtrl = new ScenarioADroneControl();
    
    @Override
    public void update(Observable o, Object arg) {
        //Falls die Drone übergeben wird, reiche sie weiter an ScenarioADroneControl
        if(arg.getClass().getName().equals("ardrone.Actor")){
            Object[] actor = new Object[1];
            actor[0] = arg;
            droneCtrl.sendCommand("droneActor", actor);
        } else {
            //Ansonsten benachrichtige die DroneControl über die aktuelle Position
            double[] dronePos = (double[]) arg;
            notifyDroneControl(dronePos[0], dronePos[1], dronePos[2], dronePos[3]);
        }
    }
    
    //Stecke die Position in ein Object Array und schick es an ScenarioADroneControl
    public void notifyDroneControl(double x, double y, double z, double rotZ){
        Object[] pos = new Object[4];
        pos[0] = x;
        pos[1] = y;
        pos[2] = z;
        pos[3] = rotZ;
        droneCtrl.sendCommand("UpdatePos", pos);
    }
    
    
}
