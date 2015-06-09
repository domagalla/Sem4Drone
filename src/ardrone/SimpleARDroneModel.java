/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

/**
 *
 * @author Oliver Kaup
 */
public class SimpleARDroneModel implements DroneControl {
    
    static boolean started = false;
    static boolean startComplete = false;
    static boolean steigen = true;
    ARDroneActor droneActor;
    
    static double tolerance = 10;
    static double aktSpeed;
    static double desSpeed;
    static double aktAccel;
    static double aktAccel_UP = 0;
    static double aktSpeed_UP;
    static double startHeight;
    
    static double refX;
    static double refY;
    static double refZ;
    
    static double rX=1;
    static double rY=0;
    static double rZ=0;
    
    static double[] richtungsVec = new double[3];
    static double[] referenceVec = new double[3];
    static double[] tempVec = new double[3];
  
    
    static double[] position;
    static double[] rotation;
    
    static double gravity;
    
    static double maxV = 10;
    
    static double dist;
    
    
    public void tickPerDeciSecond(){
        
        System.out.println("RichtungsVec: " + richtungsVec[0]+", "+richtungsVec[1]+", "+richtungsVec[2]);
        System.out.println("tickperSecond "+ started + " " + startComplete);

        
        
        if(droneActor==null){
            droneActor = World.getDrone();
        }
        position = droneActor.getPosition();
        rotation = droneActor.getRotation();
        aktAccel = (double)droneActor.getAttribute("Accel");
        aktAccel_UP = (double)droneActor.getAttribute("Accel_UP");
        aktSpeed_UP = (double)droneActor.getAttribute("Speed_UP"); 
        gravity = (double)droneActor.getAttribute("gravity");
       
       
        dist = startHeight-position[2];
        
        /*if(aktSpeed_UP <= maxV){
            
        }
        if(dist >= bremsdist){
            
        }
        if(aktSpeed >= 0){
            
        }*/
        if(started && !startComplete){
            System.out.println("Pos2 "+ position[2]+ "startHeight " + startHeight);
            //Solange die Starthöhe noch nicht erreicht ist
            if(position[2]<startHeight){
                //Fliege auf die Starthöhe
                
                
                /*
                droneActor.setAttribute("Accel", aktAccel);
                aktSpeed = aktSpeed+0.2;
                */
                
                //droneActor.setAttribute("Accel_UP", 99.0);
                
                if(aktSpeed_UP <= maxV && steigen){
                    droneActor.setAttribute("Accel_UP", 99.0);
                    aktSpeed_UP = (double) droneActor.getAttribute("Accel_UP")-gravity +aktSpeed_UP;
                   
                }
                
                if(dist>=getBremsdist(aktSpeed_UP,(double) droneActor.getAttribute("Accel_UP")) ){
                 
                }
                
                else{
                steigen = false;
                } 
                
                 if ((double) droneActor.getAttribute("Accel_UP")>=0 && !steigen){
                                                         droneActor.setAttribute("Accel_UP", 97.0);  
                                                           aktSpeed_UP = (double) droneActor.getAttribute("Accel_UP")-gravity +aktSpeed_UP;
                                                          } 
                
                
                droneActor.setAttribute("Speed_UP", aktSpeed_UP);
                
                droneActor.setPosition(position[0],position[1], position[2]+aktSpeed_UP);
                
              //  System.out.println("Speed: "+ aktSpeed_UP);
              //  System.out.println("X-Richtung: " +richtungsVec[0]+" Y-Richtung: " +richtungsVec[1]+ " Z-Richtung: " +richtungsVec[2]);
            
            } else {
                startComplete = true; 
                      
            }
            
        } else if (started&&startComplete){
               
               
                 if(position[0]<refX+tolerance && position[0]>refX-tolerance && position[1]<refY+tolerance && position[1]>refY-tolerance && position[2]<refZ+tolerance && position[2]>refZ-tolerance){
                    System.out.println("Wegpunkt erreicht!");
                    
                    stop();
                }
                  
                 
                //Fliege das Scenario
               
                System.out.println("fliege zum Punkt");
               //  System.out.println("X-Richtung: " +richtungsVec[0]+" Y-Richtung: " +richtungsVec[1]+ " Z-Richtung: " +richtungsVec[2]);
                referenceVec[0] = refX - position[0];
                referenceVec[1] = refY - position[1];
                referenceVec[2] = refZ - position[2];
                
                
                
                tempVec[0] = referenceVec[0];
                tempVec[1] = richtungsVec[1];   
                tempVec[2] = referenceVec[2];
                
                rotation(0, checkAngle(richtungsVec, tempVec), checkAngle(tempVec, referenceVec));
              
                System.out.println("TempVec " + richtungsVec[0]+ ","+ richtungsVec[2]);
                
                rotToVec();
                
               
                
              //  move();
     
        }
    }
    
    public void move(){
         
        droneActor.setPosition(position[0]+aktSpeed*richtungsVec[0],
                               position[1]+aktSpeed*richtungsVec[1],
                               position[2]+aktSpeed*richtungsVec[2]);
    }
    
    public void rotToVec(){
        
        richtungsVec[0]=Math.cos(Math.toRadians(rotation[2]))*rX-Math.sin(Math.toRadians(rotation[2]))*rY;
        richtungsVec[1]=Math.sin(Math.toRadians(rotation[2]))*rX+Math.cos(Math.toRadians(rotation[2]))*rY;
        richtungsVec[2]=rZ;
                
        double tempX = richtungsVec[0];
        double tempX2 = richtungsVec[2];        
       
        //Rotation um die Y-Achse
        richtungsVec[0]=Math.cos(Math.toRadians(rotation[1]))*richtungsVec[0]+Math.sin(Math.toRadians(rotation[1]))*richtungsVec[2];
        
        //Y-Richtung bleibt gleich
        richtungsVec[2]=-Math.sin(Math.toRadians(rotation[1]))*tempX+Math.cos(Math.toRadians(rotation[1]))*richtungsVec[2];
    }
    
    public void setAktRef(double x, double y, double z){
        refX = x;
        refY = y;
        refZ = z;
        
        
       //System.out.println(+  refX +":x  " +  refY +":y  "+  refZ +":z  ");
    }
    
    public void speed(double speed){
        desSpeed = speed;
        //position = droneActor.getPosition();
        //rotation = droneActor.getRotation();
        //Position der Drohne wird in Abhängigkeit von der aktuellen Rotation der Drohne verändert
        /*
        droneActor.setPosition(position[0]+speed*Math.cos(Math.toRadians(rotation[2])),
                          position[1]+speed*Math.sin(Math.toRadians(rotation[2])),
                          position[2]);
                */
       // System.out.println("Drohne hat sich bewegt!");
       //  System.out.println(droneActor.getPosX() +" "+ droneActor.getPosY());
    }
    public void rotation(double x, double y, double z){
        //Die Drohne rotiert ohne ihre Position zu verändern
        //Die Drohne rotiert gegen den Uhrzeigersinn (negative Rotation = im Uhrzeigersinn)
         droneActor.setRotation(x, y, z);
         rotation = droneActor.getRotation();
    }
    public void stop(){
        droneActor.setPosition(droneActor.getPosition()[0], droneActor.getPosition()[1], 0);
        System.out.println("Drohne ist gelandet");
        
        droneActor.setAttribute("Finnished", true);
    }
    public void start(){
        System.out.println("Drohne wurde gestartet!");
        droneActor = World.getDrone();
        started = true;
        droneActor.setAttribute("Accel_UP", 10.0);
        droneActor.setAttribute("Speed_UP", 0.0);
        startHeight = 100;
        droneActor.setPosition(250, 50, 0);
    }
    
    public boolean getStarted(){
        return started;
    }
    
    private double checkAngle(double[] vec1, double[] vec2){
        double angle;
        double scalar;
        double length;
        
        scalar = skalarProdukt(vec1, vec2);
        length = betrag(vec1)*betrag(vec2);
        
        angle = Math.toDegrees(Math.acos(scalar/length));
        System.out.println("ANGLE: " +angle);
        return angle;
    }
    
    private double skalarProdukt(double[] vec1, double[] vec2){
        double ergebnis = vec1[0]*vec2[0]+vec1[1]*vec2[1]+vec1[2]*vec2[2];
        return ergebnis;
    }
    
    private double betrag(double[] vec1){
        double ergebnis = Math.sqrt(vec1[0]*vec1[0]+vec1[1]*vec1[1]+vec1[2]*vec1[2]);
        return ergebnis;
    }
    
    private double[] vereinheitliche(double[] vec){
        double[] newVec = new double[3];
        double length = betrag(vec);
        newVec[0]=vec[0]/length;
        newVec[1]=vec[1]/length;
        newVec[2]=vec[2]/length;
        return newVec;
    }
    private double[] vecProdukt(double[] vecA, double[] vecB){
        
        double[] vecReturn = new double[3];
        
        vecReturn[1] = vecA[2]*vecB[3]-vecA[3]*vecB[2];
        vecReturn[2] = vecA[3]*vecB[1]-vecA[1]*vecB[3];
        vecReturn[3] = vecA[1]*vecB[2]-vecA[2]*vecB[1];
        
        return vecReturn;
    
    }
    private double getBremsdist(double v0, double a){
        double bremsdistanz = 0.5 *(v0*v0/a);
        return bremsdistanz;
    }

    
}
