/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.Timer;
import java.util.TimerTask;



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
            myWorld.getActor(0).createAttribute("Finnished", false); //Wird true wenn Scenario beendet
            myWorld.getActor(0).setPosition(0, 0, 0);
            double[] dronePos = new double[5];
            myWorld.giveDroneObject(); //Actor der Drohne an SimpleARDroneModel übergeben
            
            //Timer um die DrohnenPosition regelmäßig anzeigen zu lassen
            Timer timer = new Timer();
            // Start in einer halben Sekunde dann Ablauf jede Sekunde
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    double[] position = myWorld.getActor(0).getPosition();
                    double[] rotation = myWorld.getActor(0).getRotation();

                    dronePos[0] = position[0];
                    dronePos[1] = position[1];
                    dronePos[2] = position[2];
                    dronePos[3] = rotation[1];
                    dronePos[4] = rotation[2];
                    myWorld.aktualisiere(dronePos);
                    
                    if((boolean)myWorld.getActor(0).getAttribute("Finnished")){
                        if(this.cancel()){
                            System.out.println("Scenario erfolgreich beendet.");
                        }
                    }
                }
            }, 500, 100);
       
            /*
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
