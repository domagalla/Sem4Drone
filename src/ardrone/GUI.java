/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;


/**
 *
 * @author Niklas Domagalla
 */
public class GUI extends JFrame implements Runnable {

    double x;
    double y;
    double z;
    double refX;
    double refY;
    double refZ;
    double rot;
    /**
     * Creates new form NewJFrame1
     */
    public GUI() {
  
    }

 public void createFrame(){
     
     this.setTitle("ARDone Stats");
     this.setSize(1024,768);
     this.setVisible(true);
     this.setDefaultCloseOperation(EXIT_ON_CLOSE);
     
     }
    
    @Override
    public void run() {
        
         createFrame();
            }
    
    @Override
    public void paint(Graphics g){
        //System.out.println("GUI Ticker läuft im: "+Thread.currentThread());
        g.clearRect(0, 0, getWidth(), getHeight());//clearRect legt ein Rechteck über das Frame um vorhandene Elemente zu "entfernen"
        g.fillOval((int)Math.round(x)+100,(int)Math.round(y)+100, 10, 10); //Oval DronenPosition
        
        g.fillOval(0+100,200+100, 5, 5); // refPoint
        g.fillOval(200+100,300+100, 5, 5); // refPoint
        g.fillOval(350+100,200+100, 5, 5); // refPoint
        g.fillOval(500+100,500+100, 5, 5); // refPoint
        
        g.drawRect(275, 175, 60, 60);
        g.drawString("ARDrone", (int)Math.round(x)+115,(int)Math.round(y)+110); // String für DronenPunkt
        g.setColor(Color.black); 
        g.drawString("X:        "+Double.toString(Math.round(x))+"        Y:        "+Double.toString(Math.round(y))+"        Z:        "+Double.toString(Math.round(z)), 600, 700); 
        //Darstellung der Positionswerte

        
    }

    void zeichneDrone(double po, double po0, double po1) {
         x = po;
         y = po0;
         z = po1;
           
        repaint();
     }
    
    void zeichneRefPunkte(double prefX, double prefY, double prefZ){
        
        refX = prefX;
        refY = prefY;
        refZ = prefZ;
        
        
    }
            
        
}


