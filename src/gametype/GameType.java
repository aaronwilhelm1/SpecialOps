package gametype;

import java.util.ArrayList;

import gameobjects.GameObject;
import gameobjects.Players.Player;
import geometry.Coordinate;

public interface GameType {
	
	
	public double getScoreBlue();
	
	public double getScoreGreen();
	
	public ArrayList<GameObject> getExtras();

	public void checkGameTypeMechanics(ArrayList<Player> playerList);
	
	public void playerDied(Player p,Player killer);
	
	//returns the coordinates of that team's objectives
	public ArrayList<Coordinate> getObjectives(int team);
}
