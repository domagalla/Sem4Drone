/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.ArrayList;
import java.util.Observer;

/**
 *
 * @author Oliver
 */
public interface World extends Observer{
    
	
	public Actor getActor(int index);
	public void createActor(int index);
	public void removeActor(Actor a);
	public int getActorsCount();
	public void setChanged();
	public void notifyObservers(Object arg);
}
