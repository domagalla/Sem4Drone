/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Izabela Wojcicki
 */
public class ScenarioADroneControl implements DronePosition, Runnable{
    
    SimpleARDroneModel model = new SimpleARDroneModel();
    
    boolean firstLoop = true;
    boolean refPointReached = false;
    
    ArrayList<Double> refX = new ArrayList();
    ArrayList<Double> refY = new ArrayList();
    ArrayList<Double> refZ = new ArrayList();
    
    double posX;
    double posY;
    double posZ;
    double rotX;
    double rotY;
    double rotZ;
    
    int i = 0;
    
    public ScenarioADroneControl(){
        //REFERENZPUNKTE SETZEN
        refX.add(100.0);
        refY.add(100.0);
        refZ.add(100.0);
        
        refX.add(300.0);
        refY.add(100.0);
        refZ.add(150.0);
        
        refX.add(350.0);
        refY.add(200.0);
        refZ.add(150.0);
        
        refX.add(250.0);
        refY.add(300.0);
        refZ.add(100.0);
         
    }
    
    @Override
    public void setPosition(double x, double y, double z) {
        model.tickPerDeciSecond();
        posX = x;
        posY = y;
        posZ = z;
    }

    @Override
    public void setRotation(double x, double y, double z) {
        rotX = x;
        rotY = y;
        rotZ = z;
    }
    

    @Override
    public void run() {
        Timer timer = new Timer();
        // Start in einer halben Sekunde dann Ablauf jede Sekunde
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //Wenn die Drohne nicht gestartet ist, dann starte
                if(!model.getStarted()){
                    model.start();
                }
               
                if(SimpleARDroneModel.getCheckpointReached()){
                    if(i>=refX.size()-1){
                        model.stop();
                    } else {
                        SimpleARDroneModel.resetCheckpointReached();
                        i++;
                    }
                }
                model.setAktRef(refX.get(i), refY.get(i), refZ.get(i));
            }
        }, 1, 1000);
    }

}
