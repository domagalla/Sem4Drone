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
    
        //Hashmap zur Identifizierung des Actors
        private HashMap<String, Object> attributeList = new HashMap<String, Object>();
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
        //Neuse Attribut anlegen
	public void createAttribute(String name, Object value){
		attributeList.put(name, value);
	}
        //Vorhandenes Attribut ändern
	public void setAttribute(String name, Object value){
		attributeList.replace(name, value);
	}
        //Gibt den Wert des gesuchten Attributs zurück
	public Object getAttribute(String name){
		return attributeList.get(name);
	}
        //Löscht das gesuchte Attribut
	public void deleteAttribut(String name){
		attributeList.remove(name);
	}
        //Gibt die Hashmap mit allen Attributen zurück
	public HashMap<String, Object> getAllAttributes(){
		return attributeList;
	}
        //Setzt alle Attribute durch mitgabe einer HashMap (Bsp. zum Laden aus einer Datei)
        public void setAllAttributes(HashMap<String, Object> loadFile){
                attributeList = loadFile;
        }
}
