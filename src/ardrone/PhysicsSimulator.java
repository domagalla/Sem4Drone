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
    
    double forceX;
    double forceY;
    double forceZ;
    
    public PhysicsSimulator(World newWorld){
        myWorld = newWorld;
        forceZ = forceZ-gravity;
        setWind(0,0,0);
    }
    
    public void simulate(){
        drone = (ARDroneActor) myWorld.getActor(0);
        position = drone.getPosition();
        
        //drone.setPosition(position[0], position[1], position[2]-gravity);
        drone.setPosition(position[0]+forceX, position[1]+forceY, position[2]+forceZ);
        
        
    }
    
    public void setWind(double x, double y, double z){
        forceX = forceX + x;
        forceY = forceY + y;
        forceZ = forceZ + z;
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
