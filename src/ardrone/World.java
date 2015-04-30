/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Oliver
 */
public class World extends Observable implements WorldControl {
    
        ArrayList <Actor> actorList = new ArrayList<Actor>();
	
	public Actor getActor(int index){
		return actorList.get(index);
     
	}
	public void createActor(){
		Actor a = new Actor();
                actorList.add(a);
	}
	public void removeActor(Actor a){
		if(actorList.remove(a)){
                    System.out.println("Actor wurde entfernt");
                } else {
                    System.out.println("Actor nicht vorhanden");
                }
	}
	public int getActorsCount(){
		return actorList.size();
	}
        
        public void aktualisiere(Object arg){
            this.setChanged();
            this.notifyObservers(arg);
        }
        

}
