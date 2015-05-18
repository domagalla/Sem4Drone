/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

import java.util.HashMap;

/**
 *
 * @author Marvin Voß
 */
public class ARDroneActor implements Actor{
    
        //Hashmap zur Identifizierung des Actors
        private HashMap<String, Object> attributeList = new HashMap<String, Object>();
        //Positions- und Rotations-Variablen
	private double[] position = new double[3];
        private double[] rotation = new double[3];
	
        @Override
	public double[] getPosition(){
            return position;
        }
        @Override
	public double[] getRotation(){
            return rotation;
        }
        @Override
	public void setRotation(double x, double y, double z){
		rotation[0] = x;
		rotation[1] = y;
		rotation[2] = z;
	}
        @Override
	public void setPosition(double x, double y, double z){
		position[0] = x;
		position[1] = y;
		position[2] = z;
	}
        //Neuse Attribut anlegen
        @Override
	public void createAttribute(String name, Object value){
		attributeList.put(name, value);
	}
        //Vorhandenes Attribut ändern
	public void setAttribute(String name, Object value){
		attributeList.replace(name, value);
	}
        //Gibt den Wert des gesuchten Attributs zurück
        @Override
	public Object getAttribute(String name){
		return attributeList.get(name);
	}
        //Löscht das gesuchte Attribut
        @Override
	public void deleteAttribute(String name){
		attributeList.remove(name);
	}
        //Gibt die Hashmap mit allen Attributen zurück
        @Override
	public HashMap<String, Object> getAllAttributes(){
		return attributeList;
	}
        //Setzt alle Attribute durch mitgabe einer HashMap (Bsp. zum Laden aus einer Datei)
        public void setAllAttributes(HashMap<String, Object> loadFile){
                attributeList = loadFile;
        }
}
