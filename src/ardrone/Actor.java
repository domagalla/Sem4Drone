package ardrone;


import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Oliver
 */
public interface Actor {
    
    public double[] getPosition();
    public double[] getRotation();
    public void setRotation(double x, double y, double z);
    public void setPosition(double x, double y, double z);
    public void createAttribute(String name, Object value);
    public Object getAttribute(String name);
    public void deleteAttribute(String name);
    public HashMap<String, Object> getAllAttributes();
}
