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
public interface DronePosition {
    
    public void setPosition(double x, double y, double z);
    public void setRotation(double x, double y, double z);
    
}
