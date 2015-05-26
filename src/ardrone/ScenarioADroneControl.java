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
    double rotX;
    double rotY;
    double rotZ;
    
    
    @Override
    public void setPosition(double x, double y, double z) {
        posX = x;
        posY = y;
        posZ = z;
    }

    @Override
    public void setRotation(double x, double y, double z) {
        rotX = x;
        rotY = y;
        rotZ = z;
    }
    
    
    public void flyScenario(){
        new Thread(new Runnable(){
            @Override
            public void run() {
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
                    model.rotation(0, 0, 5);
                }
            }
        });
        
    }
    
    public void fly8Scenario(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                if(!model.getStarted()){
                    model.start();
                    model.rotation(0, 0, 90);
                }
                if(rotZ == 450){
                    firstLoop = false;
                }
                if(rotZ == 90 && !firstLoop){
                    model.stop();
                }
                if(firstLoop){
                    model.speed(10);
                    model.rotation(0, 0, 5);
                } else {
                    model.speed(10);
                    model.rotation(0, 0, -5);
                }
            }
        });
    }

}
