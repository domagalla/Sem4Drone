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
public interface WorldControl {
    
    public Actor getActor(int index);
    public void createActor();
    public void removeActor(ARDroneActor a);
    public int getActorsCount();
        
}
