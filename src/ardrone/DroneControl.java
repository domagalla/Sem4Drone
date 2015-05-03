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
public interface DroneControl {
    
    public void move(double x, double y, double z);
    public void rotate(double x, double y, double z);
    public void stop();
    public void start();
    public void interpretCommand(String cmd, Object[] parameters);
}
