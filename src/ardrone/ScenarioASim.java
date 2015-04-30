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
 * @author Oliver
 */
public class ScenarioASim implements World{
        
        public static void main(String[] arg){
            ScenarioASim mySim = new ScenarioASim();
            mySim.start();
        }
        
        public void start(){
            
            createActor(); //Actor der Drone auf Index 0
            getActor(0).createAttribute("Name", "ARDrone");
            getActor(0).setPos(10, 10, 1);
            
            createActor(); //Wand auf Y=0 auf Index 1
            getActor(1).createAttribute("Name", "WallAtBot");
            
            
            createActor(); //Wand auf X=Raumlänge auf Index 2
            getActor(2).createAttribute("Name", "WallAtRight");
            getActor(2).setPos(500, 0, 0);
            
            createActor(); //Wand auf Y=Raumbreite auf Index 3
            getActor(3).createAttribute("Name", "WallAtTop");
            getActor(3).setPos(0, 500, 0);
            
            createActor(); //Wand auf X=0 auf Index 4
            getActor(4).createAttribute("Name", "WallAtLeft");
            
            createActor(); //Decke auf Index 5
            getActor(5).createAttribute("Name", "Ceiling");
            getActor(2).setPos(0, 0, 300);
            

            System.out.println(getActor(0));
        }
	
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
	public void setChanged(){
		
	}
        
	public void notifyObservers(Object arg){
		
	}

        @Override
        public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
