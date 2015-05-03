/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class GUI extends JFrame implements Runnable {

    double x;
    double y;
    double z;
    double rot;
    /**
     * Creates new form NewJFrame1
     */
    public GUI() {
  
    }

 public void createFrame(){
 
     this.setTitle("ARDon Stats");
     this.setSize(1024,768);
 
            this.setVisible(true);
   
     }
    
    @Override
    public void run() {
    
         createFrame();
            }
    
    @Override
    public void paint(Graphics g){
    g.clearRect(0, 0, getWidth(), getHeight());
        g.fillOval((int)Math.round(x)+100,(int)Math.round(y)+100, 10, 10);
        g.drawString("ARDrone", (int)Math.round(x)+115,(int)Math.round(y)+110);
        g.setColor(Color.black);
        g.drawString("X:        "+Double.toString(x)+"        Y:        "+Double.toString(y), 400, 400);
        try {
            this.wait(500);
        } catch (InterruptedException ex) {
            
        }
        
    }

    void zeichneDrone(double po, double po0, double po1) {
         x = po;
         y = po0;
         z = po1;
           
        repaint();
     }
        
}


