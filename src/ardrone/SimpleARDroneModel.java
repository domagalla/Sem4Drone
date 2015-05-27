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
    
    static boolean started = false;
    static boolean startComplete = false;
    ARDroneActor droneActor;
    
    static double aktSpeed;
    static double desSpeed;
    static double aktAccel = 0;
    static double startHeight;
    static double[] position;
    static double[] rotation;
    
    public void tickPerSecond(){
        System.out.println("tickperSecond "+ started + " " + startComplete);
        if(droneActor==null){
            droneActor = World.getDrone();
        }
        position = droneActor.getPosition();
        rotation = droneActor.getRotation();
        if(started && !startComplete){
            System.out.println("Pos2 "+ position[2]+ "startHeight " + startHeight);
            if(position[2]<startHeight){
                rotation(0,90,0);
                aktAccel = aktAccel+1;
                aktSpeed = aktSpeed+aktAccel;
                droneActor.setPosition(position[0]+aktSpeed*Math.cos(Math.toRadians(rotation[2]))+aktSpeed*Math.cos(Math.toRadians(rotation[1])),
                          position[1]+aktSpeed*Math.sin(Math.toRadians(rotation[2])),
                          position[2]+aktSpeed*Math.sin(Math.toRadians(rotation[1])));
                System.out.println("Z-Position: " +position[2]);
            }
        } else if (started&&startComplete){
            
        }
    }
    
    public void speed(double speed){
        desSpeed = speed;
        position = droneActor.getPosition();
        rotation = droneActor.getRotation();
        //Position der Drohne wird in Abhängigkeit von der aktuellen Rotation der Drohne verändert
        /*
        droneActor.setPosition(position[0]+speed*Math.cos(Math.toRadians(rotation[2])),
                          position[1]+speed*Math.sin(Math.toRadians(rotation[2])),
                          position[2]);
                */
       // System.out.println("Drohne hat sich bewegt!");
       //  System.out.println(droneActor.getPosX() +" "+ droneActor.getPosY());
    }
    public void rotation(double x, double y, double z){
        //Die Drohne rotiert ohne ihre Position zu verändern
        //Die Drohne rotiert gegen den Uhrzeigersinn (negative Rotation = im Uhrzeigersinn)
         droneActor.setRotation(x, y, z);
    }
    public void stop(){
        droneActor.setPosition(droneActor.getPosition()[0], droneActor.getPosition()[1], 0);
        System.out.println("Drohne ist gelandet");
        
        droneActor.setAttribute("Finnished", true);
    }
    public void start(){
        System.out.println("Drohne wurde gestartet!");
        droneActor = World.getDrone();
        started = true;
        startHeight = 100;
        droneActor.setPosition(250, 50, 0);
    }
    
    public boolean getStarted(){
        return started;
    }
    
            
}
