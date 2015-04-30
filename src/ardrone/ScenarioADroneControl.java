/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

/**
 *
 * @author Oliver
 */
public class ScenarioADroneControl implements DronePosition{
    
    SimpleARDroneModel model = new SimpleARDroneModel();
    
    double posX;
    double posY;
    double posZ;
    
    public void sendCommand(String cmd, Object[] parameters){
        if(cmd.equals("UpdatePos")){
            
            posX = (double)parameters[0];
            posY = (double)parameters[1];
            posZ = (double)parameters[2];
            
            flyScenario();
        } else if(cmd.equals("droneActor")){
            model.interpretCommand("droneActor", parameters);
        }
    }
    
    public void flyScenario(){
        if(!model.getStarted()){
            model.start();
        }
        if(posX==10 && posY==10){
            model.stop();
        } else {
            model.move(1, 0, 0); //fliege im Kreis mit einem Winkel von 5 Grad
            model.rotate(0, 0, 5);
        }
        
    }
}
