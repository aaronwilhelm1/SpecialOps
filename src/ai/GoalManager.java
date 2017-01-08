package ai;

import java.util.ArrayList;

import gameobjects.Block;
import gameobjects.LevelIndex;
import gameobjects.World;
import gameobjects.Players.Player;

import geometry.Coordinate;



public class GoalManager {

	private Individual ind;
	//the points in (x,y) in the tile origin
	private ArrayList<Coordinate> futurePoints;
	private Player player;
	private Block[][] map;
	private World world;
	private boolean onOffense;

	public GoalManager(World w, Player p, Block[][] m){
		futurePoints = new ArrayList<Coordinate>();
		player = p;
		world = w;
		map = m;
		captureFlag();
		/*if(Math.random() >= .5){
			onOffense = true;
			captureFlag();//TODO: Replace this with a call to goal decider
		}else{
			onOffense = false;
			defendObjective();
		}*/
		ind = new Individual(w, p, m, this);
	}

	public void captureFlag(){
		ArrayList<Coordinate> flags = LevelIndex.getFlags(world.map);
		boolean validPlace = false;
		int y;
		do {
			y = (int) (Math.random() * ObstacleMap.HEIGHT);
			if(map[27][y] == null) {
				validPlace = true;
			}
		} while(validPlace == false);
		futurePoints.add(new Coordinate(27, (double)y));
		if(player.getTeam() == 0){
			futurePoints.add(flags.get(1));
		} else {
			futurePoints.add(flags.get(0));
		}
		validPlace = false;
		do {
			y = (int) (Math.random() * ObstacleMap.HEIGHT);
			if(map[27][y] == null) {
				validPlace = true;
			}
		} while(validPlace == false);
		futurePoints.add(new Coordinate(27, (double)y));
		if(player.getTeam() == 0){
			futurePoints.add(flags.get(0));
		} else {
			futurePoints.add(flags.get(1));
		}
	}

	public void defendObjective(){
		Coordinate friendlyObjective = ObstacleMap.nearestTile(world.gameType.getObjectives(player.getTeam()).get(0));
		friendlyObjective = new Coordinate(friendlyObjective.getX(), friendlyObjective.getY());
		if(map[(int)friendlyObjective.getX()][(int)friendlyObjective.getY()] != null){
			System.out.println("it's occupied");
		} else{
			futurePoints.add(friendlyObjective);
		}
	}

	//returns null when no next point
	//NOTE: Calling this removes the point from the GM's list.
	//calling this twice in a row will result in different results
	public Coordinate nextPoint(){
		if(futurePoints.size() == 0){//the player has finished the goal
			/*if(player.getHasFlag() == false){
				if(onOffense){
					captureFlag();
				} else {
					if(player.getPoint().isClose(world.gameType.getObjectives(player.getTeam()).get(0))){
						return null;
					} else {
						defendObjective();
					}
				}
			} else {
				return null;
			}*/
			if(player.getHasFlag() == false){
				captureFlag();
			} else {
				return null;
			}

		} 
		Coordinate toReturn = futurePoints.get(0);
		futurePoints.remove(0);
		return toReturn;

	}

	public Individual getIndividual() {
		return ind;
	}
	
	public void newPlayer(Player p){
		player = p;
		ind.newPlayer(p);
	}

	public void playerDied(){
		futurePoints = new ArrayList<Coordinate>();
		/*if(Math.random() >= .5){
			onOffense = true;
			captureFlag();//TODO: Replace this with a call to goal decider
		}else{
			onOffense = false;
			defendObjective();
		}*/
		ind.playerDied();
	}

}
