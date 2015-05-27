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
 * @author Izabela Wojcicki
 */
public class ScenarioADroneControl implements DronePosition, Runnable{
    
    SimpleARDroneModel model = new SimpleARDroneModel();
    
    boolean firstLoop = true;
    
    double posX;
    double posY;
    double posZ;
    double rotX;
    double rotY;
    double rotZ;
    
    
    @Override
    public void setPosition(double x, double y, double z) {
        model.tickPerSecond();
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
    
    
    public void flyScenario(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                Timer timer = new Timer();
                // Start in einer halben Sekunde dann Ablauf jede Sekunde
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        
                        System.out.println("scenario wird geflogen");
                        //Wenn die Drohne nicht gestartet ist, dann starte
                        if(!model.getStarted()){
                            model.start();
                        }
                        //Wenn die Drohne das Ziel(Einen Kreis fliegen) erreicht hat, dann stoppe die Drohne
                        if(rotZ==360){
                            model.stop();
                        } else {
                            //Sonst fliege das Scenario weiter
                            model.speed(10); //fliege im Kreis in dem du dich nach jeder Vorwärtsbewegung um 5 Grad drehst
                            model.rotation(0, 0, 5);
                            System.out.println("bewege");
                        }
                    }
                }, 500, 100);
            }
        });
        
    }
    
    public void fly8Scenario(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                Timer timer = new Timer();
                // Start in einer halben Sekunde dann Ablauf jede Sekunde
                timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    
                if(!model.getStarted()){
                    model.start();
                    model.rotation(0, 0, 90);
                }
                if(rotZ == 450){
                    firstLoop = false;
                }
                if(rotZ == 90 && !firstLoop){
                    model.stop();
                }
                if(firstLoop){
                    model.speed(10);
                    model.rotation(0, 0, 5);
                } else {
                    model.speed(10);
                    model.rotation(0, 0, -5);
                }
                }
            }, 500, 100);
            }
        });
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        // Start in einer halben Sekunde dann Ablauf jede Sekunde
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                  
                //System.out.println("scenario wird geflogen");
                //Wenn die Drohne nicht gestartet ist, dann starte
                //System.out.println("Control Ticker läuft im: "+Thread.currentThread());
                if(!model.getStarted()){
                    model.start();
                }
                //Wenn die Drohne das Ziel(Einen Kreis fliegen) erreicht hat, dann stoppe die Drohne
                if(rotZ==360){
                    model.stop();
                } else {
                    //Sonst fliege das Scenario weiter
                    model.speed(10); //fliege im Kreis in dem du dich nach jeder Vorwärtsbewegung um 5 Grad drehst
                    model.rotation(0, 0, 5);
                    //System.out.println("bewege");
                }
            }
        }, 500, 100);
    }

}
