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
        double[] dronePos = (double[]) arg;
        notifyDroneControl(dronePos[0], dronePos[1], dronePos[2]);
    }
    
    public void notifyDroneControl(double x, double y, double z){
        Object[] pos = new Object[3];
        pos[0] = x;
        pos[1] = y;
        pos[2] = z;
        droneCtrl.sendCommand("UpdatePos", pos);
    }
    
    
}
