/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.HashMap;

/**
 *
 * @author Oliver
 */
public class Actor {
    
    
        private HashMap<String, Object> attributeList;
	private double PosX, PosY, PosZ, RotX, RotY, RotZ;
	
	public double getPosX(){
		return PosX;
	}
	public double getPosY(){
		return PosY;
	}
	public double getPosZ(){
		return PosZ;
	}
	public double getRotX(){
		return RotX;
	}
	public double getRotY(){
		return RotY;
	}
	public double getRotZ(){
		return RotZ;
	}
	public void setRot(double x, double y, double z){
		RotX = x;
		RotY = y;
		RotZ = z;
	}
	public void setPos(double x, double y, double z){
		PosX = x;
		PosY = y;
		PosZ = z;
	}	
	public void createAttribut(String name, Object value){
		attributeList.put(name, value);
	}
	public void setAttribute(String name, Object value){
		attributeList.replace(name, value);
	}
	public Object getAttribut(String name){
		return attributeList.get(name);
	}
	public void deleteAttribut(String name){
		attributeList.remove(name);
	}
	public HashMap<String, Object> getAllAttributes(){
		return attributeList;
	}
        public void setAllAttributes(HashMap<String, Object> loadFile){
                attributeList = loadFile;
        }
}
