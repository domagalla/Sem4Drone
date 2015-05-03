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
public class SimpleARDroneModel extends World implements DroneControl {
    
    boolean started = false;
    Actor droneActor;
    
    public void move(double x, double y, double z){
       //Position der Drohne wird in Abhängigkeit von der aktuellen Rotation der Drohne verändert
        droneActor.setPos(droneActor.getPosX()+x*Math.cos(Math.toRadians(droneActor.getRotZ())),
                          droneActor.getPosY()+x*Math.sin(Math.toRadians(droneActor.getRotZ())),
                          droneActor.getPosZ()+z);
       // System.out.println("Drohne hat sich bewegt!");
       //  System.out.println(droneActor.getPosX() +" "+ droneActor.getPosY());
    }
    public void rotate(double x, double y, double z){
        //Die Drohne rotiert ohne ihre Position zu verändern
        //Die Drohne rotiert gegen den Uhrzeigersinn (negative Rotation = im Uhrzeigersinn)
         droneActor.setRot(0, 0, droneActor.getRotZ()+z);
    }
    public void stop(){
        droneActor.setPos(droneActor.getPosX(), droneActor.getPosX(), 0);
        System.out.println("Drohne ist gelandet");
        
        droneActor.setAttribute("Finnished", true);
       
        //started = false; //würde Endlosschleife verursachen?
        
        
    }
    public void start(){
        //System.out.println("Drohne wurde gestartet!");
        started = true;
        droneActor.setPos(250, 50, 100);
        
    }
    public void interpretCommand(String cmd, Object[] parameters){
        //Wenn das Drohnen Objekt übergeben wurde speicher es dir unter droneActor
        if(cmd.equals("droneActor")){
            droneActor = (Actor)parameters[0];
        }
    }
    
    public boolean getStarted(){
        return started;
    }
    
            
}
