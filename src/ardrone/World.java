/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Oliver Kaup, Izabela Wojcicki, Marvin Voß, Niklas Domagalla
 */
public class World extends Observable implements WorldControl, WorldEvents{
        
        //Liste mit Actors, wird in ScenarioASim gefüllt
        ArrayList <Actor> actorList = new ArrayList<Actor>();
        ArrayList <Observer> observers = new ArrayList<Observer>();
	
	public Actor getActor(int index){
		return actorList.get(index);
     
	}
	public void createActor(){
		ARDroneActor a = new ARDroneActor();
                actorList.add(a);
	}
        
	public void removeActor(ARDroneActor a){
		if(actorList.remove(a)){
                    System.out.println("Actor wurde entfernt");
                } else {
                    System.out.println("Actor nicht vorhanden");
                }
	}
        
	public int getActorsCount(){
		return actorList.size();
	}
        /*
        public void giveRoomBoundaries(){
            double[] bound = new double[]{
                1337,
                actorList.get(1).getPosY(),
                actorList.get(2).getPosX(),
                actorList.get(3).getPosY(),
                actorList.get(4).getPosX(),
            };
            this.hasChanged();
            this.notifyObservers(bound);
        }
        */
        
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

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update(getActor(0).getPosition(), getActor(0).getRotation());
        }
    }

}
