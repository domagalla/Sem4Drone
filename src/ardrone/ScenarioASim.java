/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Oliver Kaup
 */
public class ScenarioASim {
    
        public static void main(String[] arg){
            ScenarioASim mySim = new ScenarioASim();
            mySim.start();
        }
        
        
        public void start(){
            //Erstelle Welt und füge Observer hinzu
            World myWorld = new World();
            myWorld.register(new DroneActorPositionAdapter());
            myWorld.register(new TextVisualizer());

            myWorld.createActor(); //Actor der Drohne auf Index 0
            myWorld.getActor(0).createAttribute("Name", "ARDrone");
            myWorld.getActor(0).createAttribute("Accel", (double)0.0);
            myWorld.getActor(0).createAttribute("Accel_UP", (double)0.0);
            myWorld.getActor(0).createAttribute("Speed", (double)0.0);
            myWorld.getActor(0).createAttribute("Speed_UP", (double)0.0);
            myWorld.getActor(0).createAttribute("Finnished", false); //Wird true wenn Scenario beendet
            myWorld.getActor(0).setPosition(0, 0, 0);
            myWorld.giveDroneObject(); //Actor der Drohne an SimpleARDroneModel übergeben
            
            PhysicsSimulator engine = new PhysicsSimulator(myWorld);
            
            //Timer um die DrohnenPosition regelmäßig anzeigen zu lassen
            Timer timer = new Timer();
            // Start in einer halben Sekunde dann Ablauf jede Sekunde
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                 
                    engine.collision();
                    myWorld.aktualisiere();
                    //engine.simulate();
                    
                    
                    if((boolean)myWorld.getActor(0).getAttribute("Finnished")){
                        if(this.cancel()){
                            System.out.println("Scenario erfolgreich beendet.");
                        }
                    }
                }
            }, 1, 100);
       
            /*
            //Raumbegrenzungen
            myWorld.createActor(); //Wand auf Y=0 auf Index 1
            myWorld.getActor(1).createAttribute("Name", "WallAtBot");
            
            myWorld.createActor(); //Wand auf X=Raumlänge auf Index 2
            myWorld.getActor(2).createAttribute("Name", "WallAtRight");
            myWorld.getActor(2).setPos(500, 0, 0);
            
            myWorld.createActor(); //Wand auf Y=Raumbreite auf Index 3
            myWorld.getActor(3).createAttribute("Name", "WallAtTop");
            myWorld.getActor(3).setPos(0, 500, 0);
            
            myWorld.createActor(); //Wand auf X=0 auf Index 4
            myWorld.getActor(4).createAttribute("Name", "WallAtLeft");
            
            myWorld.createActor(); //Decke auf Index 5
            myWorld.getActor(5).createAttribute("Name", "Ceiling");
            myWorld.getActor(2).setPos(0, 0, 300);
            
            myWorld.giveRoomBoundaries(); //Raumbegrenzung an die GUI übergeben
            */
            
            
        }
	
}
