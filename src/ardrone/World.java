/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

/**
 *
 * @author Oliver
 */
public class World extends Observable implements WorldControl {
        
        //Liste mit Actors, wird in ScenarioASim gefüllt
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
        //Nur einmal aufgerufen um eine Referenz auf das droneActor Objekt zum Model zu bekommen
        public void giveDroneObject(){
            this.setChanged();
            this.notifyObservers(actorList.get(0));
        }
        
        //stetig aufgerufen um die aktuelle Position der Drone an die Observer zu geben
        public void aktualisiere(Object arg){
            this.setChanged();
            this.notifyObservers(arg);
        }
        

}
