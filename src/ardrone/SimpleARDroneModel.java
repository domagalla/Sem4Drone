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
    
    static boolean checkpointReached = false;
    static boolean started = false;
    static boolean startComplete = false;
    static boolean steigen = true;
    ARDroneActor droneActor;
    
    static double tolerance = 10;
    static double aktSpeed = 0;
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
    
    static double[] richtungsVec = new double[]{rX,rY,rZ};
    static double[] referenceVec = new double[3];
    static double[] tempVec = new double[3];
  
    
    static double[] position;
    static double[] rotation;
    
    static double gravity;
    
    static double maxV = 10;
    
    static double dist;
    double angle;
    
    public void tickPerDeciSecond(){
       
        
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
        
      
        if(started && !startComplete){
            System.out.println("Pos2 "+ position[2]+ "startHeight " + startHeight);
            //Solange die Starthöhe noch nicht erreicht ist
            if(position[2]<startHeight){
                //Fliege auf die Starthöhe
                
   
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
            if((boolean)droneActor.getAttribute("Finnished")){
                
            } else {
                if(position[0]<refX+tolerance && position[0]>refX-tolerance && position[1]<refY+tolerance && position[1]>refY-tolerance && position[2]<refZ+tolerance && position[2]>refZ-tolerance){
                    
                    System.out.println("Pos: "+ position[0]+", "+position[1]+", "+position[2]);
                    System.out.println("Ref: "+ refX+", "+refY+", "+refZ);
                    System.out.println("Wegpunkt erreicht!");
                    checkpointReached = true;
                     
                }
                  
                 
                //Fliege das Scenario
               //  System.out.println("X-Richtung: " +richtungsVec[0]+" Y-Richtung: " +richtungsVec[1]+ " Z-Richtung: " +richtungsVec[2]);
                referenceVec[0] = refX - position[0];
                referenceVec[1] = refY - position[1];
                referenceVec[2] = refZ - position[2];
                
                richtungsVec = getRichtung(richtungsVec, referenceVec);
                
                
                //Bewegungsgeschwindigkeit und Bremswegberechnung
                
                
                
              aktSpeed = getCalculatedSpeed(berechneStreckeDronePunkt(position,referenceVec));
               
               
                move();
            }
        }
    }
    
    public void move(){
         
        droneActor.setPosition(position[0]+aktSpeed*richtungsVec[0],
                               position[1]+aktSpeed*richtungsVec[1],
                               position[2]+aktSpeed*richtungsVec[2]);
    }
    
    public void rotToVec(){
        richtungsVec = getRichtung(richtungsVec, referenceVec);
    }
    
    public double[] getRichtung(double[] vec1, double[] vec2){
        
        double[] richtung = new double[3];
        
        double[] n = vecProdukt(vec1,vec2);
        n = vereinheitliche(n);
        double angle = checkAngle(vec1,vec2)/2;
        
        richtung[0]=    vec1[0]*(Math.pow(n[0], 2)*(1-Math.cos(Math.toRadians(angle)))+Math.cos(Math.toRadians(angle))) + 
                        vec1[1]*(n[0]*n[1]*(1-Math.cos(Math.toRadians(angle)))-n[2]*Math.sin(Math.toRadians(angle))) + 
                        vec1[2]*(n[0]*n[2]*(1-Math.cos(Math.toRadians(angle)))+n[1]*Math.sin(Math.toRadians(angle)));
        
        richtung[1]=    vec1[0]*(n[1]*n[0]*(1-Math.cos(Math.toRadians(angle)))+n[2]*Math.sin(Math.toRadians(angle))) + 
                        vec1[1]*(Math.pow(n[1], 2)*(1-Math.cos(Math.toRadians(angle)))+Math.cos(Math.toRadians(angle))) + 
                        vec1[2]*(n[1]*n[2]*(1-Math.cos(Math.toRadians(angle)))-n[0]*Math.sin(Math.toRadians(angle)));
        
        richtung[2]=    vec1[0]*(n[2]*n[0]*(1-Math.cos(Math.toRadians(angle)))-n[1]*Math.sin(Math.toRadians(angle))) + 
                        vec1[1]*(n[2]*n[1]*(1-Math.cos(Math.toRadians(angle)))+n[0]*Math.sin(Math.toRadians(angle))) + 
                        vec1[2]*(Math.pow(n[2], 2)*(1-Math.cos(Math.toRadians(angle)))+Math.cos(Math.toRadians(angle)));
        
        return richtung;
    }
    
    public void setAktRef(double x, double y, double z){
        refX = x;
        refY = y;
        refZ = z;
    }
    
    public void speed(double speed){
        desSpeed = speed;
    }
    
    @Override
    public void rotation(double x, double y, double z){
        //Die Drohne rotiert ohne ihre Position zu verändern
        //Die Drohne rotiert gegen den Uhrzeigersinn (negative Rotation = im Uhrzeigersinn)
         droneActor.setRotation(x, y, z);
         rotation = droneActor.getRotation();
    }
    @Override
    public void stop(){
        droneActor.setPosition(droneActor.getPosition()[0], droneActor.getPosition()[1], 0);
        System.out.println("Pos: "+ position[0]+", "+position[1]+", "+position[2]);
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
       
        double scalar;
        double length;
        
        scalar = skalarProdukt(vec1, vec2);
        length = betrag(vec1)*betrag(vec2);
        
        if(!Double.isNaN(Math.toDegrees(Math.acos(scalar/length)))){
            angle = Math.toDegrees(Math.acos(scalar/length));
            //System.out.println("ANGLE: " +angle);
            return angle;
            
        } else {
            //System.out.println("ANGLE: " +angle);
            return angle;
        }
        

        
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
        
        vecReturn[0] = vecA[1]*vecB[2]-vecA[2]*vecB[1];
        vecReturn[1] = vecA[2]*vecB[0]-vecA[0]*vecB[2];
        vecReturn[2] = vecA[0]*vecB[1]-vecA[1]*vecB[0];
        
        return vecReturn;
    
    }
    private double getBremsdist(double v0, double a){
        double bremsdistanz = 0.5 *(v0*v0/a);
        return bremsdistanz;
    }

    public static boolean getCheckpointReached(){
        return checkpointReached;
    }
    
    public static void resetCheckpointReached(){
       checkpointReached = false;
    }

    private double berechneStreckeDronePunkt(double [] drone, double[] ref) {
        double[] strecke = new double[3];
       strecke[0] = ref[0] - drone[0];
         strecke[1] = ref[1] - drone[1];
          strecke[2] = ref[0] - drone[2];
         
          return betrag(strecke);
    }

    private double getCalculatedSpeed(double strecke) {
       double speed = 0;
       double halbeStrecke = strecke/2;
       
       if(){
           
       }
       
       
       
        return speed;
    }
    
}
