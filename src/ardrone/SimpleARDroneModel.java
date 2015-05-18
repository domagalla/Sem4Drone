/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

/**
 *
 * @author Oliver Kaup
 */
public class SimpleARDroneModel implements DroneControl {
    
    boolean started = false;
    ARDroneActor droneActor;
    
    public void speed(double speed){
       double[] position = droneActor.getPosition();
       double[] rotation = droneActor.getRotation();
       //Position der Drohne wird in Abhängigkeit von der aktuellen Rotation der Drohne verändert
        droneActor.setPosition(position[0]+speed*Math.cos(Math.toRadians(rotation[2])),
                          position[1]+speed*Math.sin(Math.toRadians(rotation[2])),
                          position[2]);
       // System.out.println("Drohne hat sich bewegt!");
       //  System.out.println(droneActor.getPosX() +" "+ droneActor.getPosY());
    }
    public void rotate(double x, double y, double z){
        //Die Drohne rotiert ohne ihre Position zu verändern
        //Die Drohne rotiert gegen den Uhrzeigersinn (negative Rotation = im Uhrzeigersinn)
         droneActor.setRotation(0, 0, droneActor.getRotation()[2]+z);
    }
    public void stop(){
        droneActor.setPosition(droneActor.getPosition()[0], droneActor.getPosition()[1], 0);
        System.out.println("Drohne ist gelandet");
        
        droneActor.setAttribute("Finnished", true);
    }
    public void start(){
        //System.out.println("Drohne wurde gestartet!");
        started = true;
        droneActor.setPosition(250, 50, 100);
        
    }
    public void interpretCommand(String cmd, Object[] parameters){
        //Wenn das Drohnen Objekt übergeben wurde speicher es dir unter droneActor
        if(cmd.equals("droneActor")){
            droneActor = (ARDroneActor)parameters[0];
        }
    }
    
    public boolean getStarted(){
        return started;
    }
    
            
}
