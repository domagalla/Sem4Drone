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
public interface DronePosition {
    
    public void sendCommand(String cmd, Object[] parameters);
    public void flyScenario();
    
}