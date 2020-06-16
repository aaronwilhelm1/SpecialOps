package ai;

import astar.AStarPathFinder;
import astar.Path;
import astar.PathFinder;
import gameobjects.Block;
import gameobjects.World;
import gameobjects.Players.Player;
import geometry.Coordinate;
import utility.Utility;

public class Individual {
	
	private World world;
	private Player player;
	
	/** The map on which the units will move */
	private ObstacleMap map;
	/** The path finder we'll use to search our map */
	private PathFinder finder;
	/** The last path found for the current unit */
	private Path path;
	
	//provides a counter for what step of the path the player is on
	private int counter;
	//the next place the player is going to in its path in pixels
	private Coordinate nextPlace;
	//the goal manager for the individual ai
	private GoalManager gm;
	//the destination of the individual in tile coordinates
	private Coordinate destination;
	
	public Individual(World w, Player p, Block[][] m, GoalManager g) {
		world = w;
		player = p;
		gm = g;
		map = new ObstacleMap(world, m);
		finder = new AStarPathFinder(map, 5000, true);
		nextPlace = player.getPoint();
		/*Coordinate temp = gm.nextPoint();
		goTo((int)temp.getX(), (int)temp.getY());*/ //CHANGED
		refreshPath();
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	//updates where the player is trying to go next
	public void updatePathStatus(){
		if(player.getPoint().isClose(nextPlace)){//at the next place
			//System.out.println("it looked for a new place");
			if(counter < path.getLength()){//still have more of the path to follow
				
				nextPlace = ObstacleMap.tileToPixels(new Coordinate(path.getStep(counter).getX(), path.getStep(counter).getY()));
				counter++;
			} else {//no more path, get a new location
				Coordinate temp = gm.nextPoint();
				if(temp != null){//gm has somewhere new for the individual to go
					//goTo((int)temp.getX(), (int)temp.getY());//CHANGED
					//System.out.println("destination changed");
					destination = temp; 
					counter = counter -1;
					refreshPath();
					//System.out.println(destination);
				}
			}
		}
		
		//System.out.println(new Coordinate(path.getStep(counter).getX(), path.getStep(counter).getY()));
		//System.out.println(nextPlace);
	}
	
	public void updateMovementCommands(){
		updateMovement();
		updateRotation();
		updateSpecial();
	}
	
	private void updateMovement(){
		//checks to see if its current angle is within a reasonable distance from the angle it needs to face
		updatePathStatus();
		

		if (Utility.areAnglesClose(player.getRotation(), player.getPoint().getAngleBetween(nextPlace), Math.PI / 10)){
			world.checkPlayerCollisions(player);
		}

		//TODO: Set movement backwards when necessary
	}
	
	private void updateRotation(){
		updatePathStatus();
		if(!player.getPoint().isClose(nextPlace)){
		if (Utility.areAnglesClose(player.getRotation(), player.getPoint().getAngleBetween(nextPlace), Math.PI / 16)){
			//doesn't need to rotate
		}
		else {

			double rotation = player.getRotation();
			double direcToFace = player.getPoint().getAngleBetween(nextPlace);
			
			double BehindMe = rotation - Math.PI;
			if (BehindMe < 0){
			    BehindMe += Math.PI * 2;
		}
			
			if (direcToFace == BehindMe)
		        player.rotateRight(); // or randomly choose
		    else if ((direcToFace > BehindMe && direcToFace < rotation) ||
		             (rotation < Math.PI && (direcToFace > BehindMe ||
		                                      direcToFace < rotation))){
		    	player.rotateRight();
		    }
		    else if ((direcToFace < BehindMe && direcToFace > rotation) ||
		             (rotation >= Math.PI && (direcToFace < BehindMe ||
		                                      direcToFace > rotation))){
		    	player.rotateLeft();
		    }
		}
		}
	}
		
	
	private void updateSpecial(){
		//TODO: Add special when necessary
	}
	
	//called by the GoalManager to indicate a new position to go to
	//x and y are tile coordinates
	public void goTo(int x, int y){
		synchronized(EnvironmentAnalyzer.influenceMap){
			Coordinate playerLocation = ObstacleMap.nearestTile(player.getPoint());
			/*do{
				path = finder.findPath(player,(int) (playerLocation.getX()),(int) (playerLocation.getY()), x, y);
			}while(path != null);*/
			path = finder.findPath(player,(int) (playerLocation.getX()),(int) (playerLocation.getY()), x, y);
			if(path == null){
				//System.out.println(x + " " + y);
				System.out.println("ERROR: Individual computer couldn't find a path to (" + x + ", " + y + ")");
				System.exit(1);
			}
			counter = 2; //could possibly make this one
			updatePathStatus();
		}
		
	}
	
	public ObstacleMap getObstacleMap(){
		return map;
	}
	
	public synchronized void refreshPath(){
		//System.out.println("Hi");
		if(path == null){
			nextPlace = player.getPoint();
			destination = gm.nextPoint();
			goTo((int)destination.getX(), (int)destination.getY());
		}
		if(counter <= path.getLength() -1){ //that means that it's not at the last step
			//if it was at last step, pathfinder just returns null (no path between start point and end point)
			/*Step destination = path.getStep(path.getLength() - 1);
			nextPlace = player.getPoint();
			//nextPlace = ObstacleMap.tileToPixels(new Coordinate(path.getStep(counter).getX(), path.getStep(counter).getY()));
			//updatePathStatus();
			goTo(destination.getX(), destination.getY());*/ //CHANGED
			nextPlace = player.getPoint();
			goTo((int)destination.getX(), (int)destination.getY());
		}
	}
	
	public void playerDied(){
		nextPlace = player.getPoint();
		/*Coordinate temp = gm.nextPoint();
		goTo((int)temp.getX(), (int)temp.getY());*/
		destination = gm.nextPoint();
	}
	
	public Coordinate getNextPlace(){
		return nextPlace;
	}
	
	public void newPlayer(Player p){
		player = p;
	}
	
	public Path getPath(){
		return path;
	}
	
}
