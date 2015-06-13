/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.ArrayList;
import com.shigeodayo.ardrone.ARDrone;
import com.shigeodayo.ardrone.navdata.AttitudeListener;
import com.shigeodayo.ardrone.navdata.BatteryListener;
import com.shigeodayo.ardrone.navdata.DroneState;
import com.shigeodayo.ardrone.navdata.StateListener;
import com.shigeodayo.ardrone.navdata.VelocityListener;
import com.shigeodayo.ardrone.video.ImageListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 *
 * @author Oliver Kaup
 */
public class SimpleARDroneModel implements DroneControl {
    
    
    private static ARDrone ardrone;
            
    static boolean checkpointReached = false;
    static boolean started = false;
    static boolean startComplete = false;
    static boolean steigen = true;
    static boolean evade = false;
    
    ARDroneActor droneActor;
    ArrayList<Actor> actorList;
    
    static double weitblick = 40; //Wie weit die Drohne vor sich guckt, um ausweichen zu können
    static double tolerance = 10; //Wie nah die Drohne an die Referenzpunkte fliegen muss
    
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
    static double[] evadeVec = new double[3];
    
    static double evadePuffer;
    
    static double[] position;
    static double[] rotation;
    
    static double gravity;
    
    static double maxV = 10;
    
    static double dist;
    double angle;
    
    static double streckeZuRefpunkt;
    public SimpleARDroneModel(){
        initialize();
    }
    
    
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
                
                //Falls ein Wegpunkt erreicht wurde
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
                
                if(evade||evadePuffer>0){
                    //Ausweichen statt auf Referenzpunkt zuzufliegen
                    System.out.println("Kollision");
                    
                    evadeVec = new double[3];
                    int alpha= -10;
                    evadeVec[0] = richtungsVec[0]*Math.cos(Math.toRadians(alpha))-richtungsVec[1]*Math.sin(Math.toRadians(alpha));
                    evadeVec[1] = richtungsVec[0]*Math.sin(Math.toRadians(alpha))+richtungsVec[1]*Math.cos(Math.toRadians(alpha));
                    evadeVec[2] = richtungsVec[2];
                    richtungsVec = getRichtung(richtungsVec, evadeVec, 1);
                    evadePuffer--;
                            
                    move();
                    
                } else {
                
                    System.out.println("Keine Kollision");
                    richtungsVec = getRichtung(richtungsVec, referenceVec, 2);
                
                
                    //Bewegungsgeschwindigkeit und Bremswegberechnung
                
                
               
                    aktSpeed = getCalculatedSpeed(betrag(referenceVec), aktSpeed);
    
               
                    move();
                }
            }
        }
    }
    
    public void move(){
        
        checkCollision();
          
        droneActor.setPosition(position[0]+aktSpeed*richtungsVec[0],
                               position[1]+aktSpeed*richtungsVec[1],
                               position[2]+aktSpeed*richtungsVec[2]);

    }
    
    public void checkCollision(){
        if(actorList==null){
            actorList = World.getActors();
        }
        int actors = actorList.size();
        for(int i = 1; i<=actors-1; i++){
            Actor tempActor = actorList.get(i);
            double[] colPos = tempActor.getPosition();
            double colX = (double)tempActor.getAttribute("x");
            double colY = (double)tempActor.getAttribute("y");
            double colZ = (double)tempActor.getAttribute("z");
            
            for(double j=0;j<=weitblick;j+=0.2){
                if(position[0]+weitblick*richtungsVec[0]>=colPos[0]&&position[0]+weitblick*richtungsVec[0]<=colPos[0]+colX){
                    if(position[1]+weitblick*richtungsVec[1]>=colPos[1]&&position[1]+weitblick*richtungsVec[1]<=colPos[1]+colY){
                        if(position[2]+weitblick*richtungsVec[2]>=colPos[2]&&position[2]+weitblick*richtungsVec[2]<=colPos[2]+colZ){
                            evade = true;
                            evadePuffer = 10;
                            System.out.println("Pos: "+ position[0]+", "+position[1]+", "+position[2]);
                            System.out.println("Richtung: "+ richtungsVec[0]+", "+richtungsVec[1]+", "+richtungsVec[2]);
                            System.out.println("Vorraussichtliche Kollision");
                            break;
                        } else {evade = false;}
                    } else {evade = false;}
                } else {evade = false;}
            }
        }
    }
    
    public double[] getRichtung(double[] vec1, double[] vec2, double curve){
        
        double[] richtung = new double[3];
        
        double[] n = vecProdukt(vec1,vec2);
        n = vereinheitliche(n);
        double angle = checkAngle(vec1,vec2)/curve;
        
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

    private double getCalculatedSpeed(double strecke, double speed) {
       double tempSpeed = speed;
       
       
           if(strecke-2<=tempSpeed){
              tempSpeed = tempSpeed-3;
           } else 
                if(speed<= maxV){
           tempSpeed+= .5;
           }
           
        return tempSpeed;
    }
    //DRONE
    	private void initialize() {
		ardrone = new ARDrone("192.168.1.1");
		System.out.println("connect drone controller");
		ardrone.connect();
		System.out.println("connect drone navdata");
		ardrone.connectNav();
		System.out.println("connect drone video");
		ardrone.connectVideo();
		System.out.println("start drone");
		ardrone.start();

		

		ardrone.addAttitudeUpdateListener(new AttitudeListener() {
			@Override
			public void attitudeUpdated(float pitch, float roll, float yaw,
					int altitude) {
				System.out.println("pitch: " + pitch + ", roll: " + roll
				 + ", yaw: " + yaw + ", altitude: " + altitude);
			}
		});

		ardrone.addBatteryUpdateListener(new BatteryListener() {
			@Override
			public void batteryLevelChanged(int percentage) {
				System.out.println("battery: " + percentage + " %");
			}
		});

		ardrone.addStateUpdateListener(new StateListener() {
			@Override
			public void stateChanged(DroneState state) {
				 //System.out.println("state: " + state);
			}
		});

		ardrone.addVelocityUpdateListener(new VelocityListener() {
			@Override
			public void velocityChanged(float vx, float vy, float vz) {
				 System.out.println("vx: " + vx + ", vy: " + vy + ", vz: " +
				 vz);
			}
		});
    

        
		addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				ardrone.stop();
			}

			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				int mod = e.getModifiersEx();

				boolean shiftflag = false;
				if ((mod & InputEvent.SHIFT_DOWN_MASK) != 0) {
					shiftflag = true;
				}

				switch (key) {
				case KeyEvent.VK_ENTER:
					ardrone.takeOff();
					break;
				case KeyEvent.VK_SPACE:
					ardrone.landing();
					break;
				case KeyEvent.VK_S:
					ardrone.stop();
					break;
				case KeyEvent.VK_LEFT:
					if (shiftflag)
						ardrone.spinLeft();
					else 
						ardrone.goLeft();
					break;
				case KeyEvent.VK_RIGHT:
					if (shiftflag)
						ardrone.spinRight();
					else
						ardrone.goRight();
					break;
				case KeyEvent.VK_UP:
					if (shiftflag)
						ardrone.up();
					else
						ardrone.forward();
					break;
				case KeyEvent.VK_DOWN:
					if (shiftflag)
						ardrone.down();
					else
						ardrone.backward();
					break;
				case KeyEvent.VK_1:
					ardrone.setHorizontalCamera();
					// System.out.println("1");
					break;
				case KeyEvent.VK_2:
					ardrone.setHorizontalCameraWithVertical();
					// System.out.println("2");
					break;
				case KeyEvent.VK_3:
					ardrone.setVerticalCamera();
					// System.out.println("3");
					break;
				case KeyEvent.VK_4:
					ardrone.setVerticalCameraWithHorizontal();
					// System.out.println("4");
					break;
				case KeyEvent.VK_5:
					ardrone.toggleCamera();
					// System.out.println("5");
					break;
				case KeyEvent.VK_R:
					ardrone.spinRight();
					break;
				case KeyEvent.VK_L:
					ardrone.spinLeft();
					break;
				case KeyEvent.VK_U:
					ardrone.up();
					break;
				case KeyEvent.VK_D:
					ardrone.down();
					break;
				case KeyEvent.VK_E:
					ardrone.reset();
					break;
				}
			}
		});

        }

}
