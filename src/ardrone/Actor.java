/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ardrone;

/**
 *
 * @author Oliver
 */
public class Actor {
 
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
		
	}
	public void setAttribute(String name, Object value){
		
	}
	public Object getAttribut(String name){
		
	}
	public void deleteAttribut(String name){
		
	}
	public HashMap<String, Object> getAllAttributes(){
		
	}
}
