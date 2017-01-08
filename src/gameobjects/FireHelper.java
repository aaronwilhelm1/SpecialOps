package gameobjects;

import gameobjects.Players.Player;
import geometry.Circle;
import geometry.Coordinate;
import utility.Utility;

public class FireHelper {
	
	World world;
	
	public FireHelper(World w){
		world = w;
	}
	
	public void makePlayerFire(Player p){
		if(p.getClassName().equals("assault")){
			assaultFire(p);
		} else if(p.getClassName().equals("heavy")){
			heavyFire(p);
		} else if(p.getClassName().equals("scout")){
			scoutFire(p);
		}
	}
	
	private void assaultFire(Player p){
		
	}
	
	private void heavyFire(Player p){

	}
	
	private void scoutFire(Player p){
		
	}
	
}
