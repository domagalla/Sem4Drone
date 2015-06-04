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
    ARDroneActor droneActor;
    
    static double aktSpeed;
    static double desSpeed;
    static double aktAccel;
    static double desAccel;
    static double startHeight;
    
    double refX;
    double refY;
    double refZ;
    
    double rX=1;
    double rY=0;
    double rZ=0;
    
    static double[] richtungsVec = new double[3];
    static double[] referenceVec = new double[3];
    static double[] tempVec = new double[3];
  
    
    static double[] position;
    static double[] rotation;
    
    public void tickPerSecond(){
        
        System.out.println("tickperSecond "+ started + " " + startComplete);
        if(droneActor==null){
            droneActor = World.getDrone();
        }
        position = droneActor.getPosition();
        rotation = droneActor.getRotation();
        aktAccel = (double)droneActor.getAttribute("Accel");
        
        if(started && !startComplete){
            System.out.println("Pos2 "+ position[2]+ "startHeight " + startHeight);
            //Solange die Starthöhe noch nicht erreicht ist
            if(position[2]<startHeight){
                rotation(0,-90,0);
                
                droneActor.setAttribute("Accel", aktAccel);
                aktSpeed = aktSpeed+0.2;
                droneActor.setAttribute("Speed", aktSpeed);
                
                rotToVec();
                
                //Auf 6 Nachkommastellen runden (gibt im Moment noch NaN aus)
                /*
                if(richtungsVec[0]%0.000001>0){
                    richtungsVec[0]=Math.round(richtungsVec[0]*1000000)/1000000;
                }
                if(richtungsVec[1]%0.000001>0){
                    richtungsVec[1]=Math.round(richtungsVec[1]*1000000)/1000000;
                }
                if(richtungsVec[2]%0.000001>0){
                    richtungsVec[2]=Math.round(richtungsVec[2]*1000000)/1000000;
                }
                */
                
                //Vektor auf Länge 1 bringen
                richtungsVec = vereinheitliche(richtungsVec);
                
                move();
                
                System.out.println("Speed: "+ aktSpeed);
                System.out.println("X-Richtung: " +richtungsVec[0]+" Y-Richtung: " +richtungsVec[1]+ " Z-Richtung: " +richtungsVec[2]);
            
            } else {
                startComplete = true;
            }
        } else if (started&&startComplete){
            //Falls Höhe überschritten wurde
            if(position[2]>startHeight){
                aktSpeed = -0.2;
                move();
            } else {
                //Fliege das Scenario
                referenceVec[0] = refX - position[0];
                referenceVec[1] = refY - position[1];
                referenceVec[2] = refZ - position[2];
                
                tempVec[0] = referenceVec[0];
                tempVec[1] = richtungsVec[1];   
                tempVec[2] = referenceVec[2];
                
                rotation(0, checkAngle(richtungsVec, tempVec), checkAngle(tempVec, referenceVec));
              
                rotToVec();
                
                aktSpeed = 5;//SPEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEED!!!!!!!! DANN LÜÜPTS!
                
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
        
        richtungsVec[0]=Math.cos(Math.toRadians(rotation[2]))*rX-Math.sin(Math.toRadians(rotation[2]))*rY;
        richtungsVec[1]=Math.sin(Math.toRadians(rotation[2]))*rX+Math.cos(Math.toRadians(rotation[2]))*rY;
        richtungsVec[2]=rZ;
                
        double tempX = richtungsVec[0];
                
        //Rotation um die Y-Achse
        richtungsVec[0]=Math.cos(Math.toRadians(rotation[1]))*richtungsVec[0]+Math.sin(Math.toRadians(rotation[1]))*richtungsVec[2];
        //Y-Richtung bleibt gleich
        richtungsVec[2]=-Math.sin(Math.toRadians(rotation[1]))*tempX+Math.cos(Math.toRadians(rotation[1]))*richtungsVec[2];
    }
    
    public void setAktRef(double x, double y, double z){
        refX = x;
        refY = y;
        refZ = z;
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
        
        angle = scalar/length;
        
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
            
}
