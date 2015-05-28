/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

/**
 *
 * @author Niklas Domagalla
 */
public class PhysicsSimulator {
    
    World myWorld;
    ARDroneActor drone;
    double[] position;
    double droneAccel;
    
    double gravity = 980;
    double Wind = 50;
    
    public PhysicsSimulator(World newWorld){
        myWorld = newWorld;
    }
    
    public void simulate(){
        drone = (ARDroneActor) myWorld.getActor(0);
        position = drone.getPosition();
        
        //drone.setPosition(position[0], position[1], position[2]-gravity);
        droneAccel = (double)drone.getAttribute("Accel");
        drone.setAttribute("Accel", droneAccel-gravity);
        
        
    }
    
    public void collision(){
        drone = (ARDroneActor) myWorld.getActor(0);
        position = drone.getPosition();
        
        if(position[2]<0){
            drone.setPosition(position[0], position[1], 0);
            System.out.println("Kollision mit Boden");
        }
    }
}
