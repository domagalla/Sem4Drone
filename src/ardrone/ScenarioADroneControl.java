/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

/**
 *
 * @author Izabela Wojcicki
 */
public class ScenarioADroneControl implements DronePosition{
    
    SimpleARDroneModel model = new SimpleARDroneModel();
    
    boolean firstLoop = true;
    double posX;
    double posY;
    double posZ;
    double rotZ;
    
    
    public void sendCommand(String cmd, Object[] parameters){
        //Wenn dir die Drohnen Position übergeben wird, lege sie auf deine eigenen Variablen und fliege das Scenario weiter
        if(cmd.equals("UpdatePos")){
            
            posX = (double)parameters[0];
            posY = (double)parameters[1];
            posZ = (double)parameters[2];
            rotZ = (double)parameters[3];
            
            fly8Scenario();
            
        } else if(cmd.equals("droneActor")){
           //Falls das Drohnen Object übergeben wird, schicke es weiter an das model
            model.interpretCommand("droneActor", parameters);
            
        }
    }
    
    public void flyScenario(){
        //Wenn die Drohne nicht gestartet ist, dann starte
        if(!model.getStarted()){
            model.start();
        }
        //Wenn die Drohne das Ziel(Einen Kreis fliegen) erreicht hat, dann stoppe die Drohne
        if(rotZ==360){
            model.stop();
        } else {
        //Sonst fliege das Scenario weiter
        model.speed(10); //fliege im Kreis in dem du dich nach jeder Vorwärtsbewegung um 5 Grad drehst
        model.rotate(0, 0, 5);
        
        }
        
    }
    
    public void fly8Scenario(){
        if(!model.getStarted()){
            model.start();
            model.rotate(0, 0, 90);
        }
        if(rotZ == 450){
            firstLoop = false;
        }
        if(rotZ == 90 && !firstLoop){
            model.stop();
        }
        if(firstLoop){
            model.speed(10);
            model.rotate(0, 0, 5);
        } else {
            model.speed(10);
            model.rotate(0, 0, -5);
        }
        
    }
}
