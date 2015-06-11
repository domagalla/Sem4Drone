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
    
    double gravity = 98;
    
    double forceX;
    double forceY;
    double forceZ;
    
    public PhysicsSimulator(World newWorld){
        myWorld = newWorld;
        drone = (ARDroneActor) myWorld.getActor(0);
        forceZ = forceZ-gravity;
        setWind(1,-2,-1);
        drone.createAttribute("gravity", gravity);
    }
    
    public void simulate(){
        
        position = drone.getPosition();
        
        //drone.setPosition(position[0], position[1], position[2]-gravity);
        drone.setPosition(position[0]+forceX, position[1]+forceY, position[2]+forceZ);
        
        
    }
    
    public void setWind(double x, double y, double z){
        forceX = x;
        forceY = y;
        forceZ = z;
    }
    
    public void collision(){
        position = drone.getPosition();
        
        if(position[2]<0){
            drone.setPosition(position[0], position[1], 0);
            System.out.println("Kollision mit Boden");
        }
    }
}
