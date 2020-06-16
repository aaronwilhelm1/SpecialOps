package ai;

import astar.AStarPathFinder;
import astar.Path;
import astar.PathFinder;
import gameobjects.Block;
import gameobjects.World;
import gameobjects.Players.Player;
import geometry.Coordinate;
import utility.Utility;

public class VIPIndividual {

	private World world;
	public Player player;

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


	public VIPIndividual(World w, Player p, Block[][] m){
		world = w;
		player = p;
		map = new ObstacleMap(world, m);
		finder = new AStarPathFinder(map, 500, true);
		nextPlace = ObstacleMap.tileToPixels(map.nearestTile(player.getPoint()));
		Coordinate temp = nextPoint();
		goTo((int)temp.getX(), (int)temp.getY());
	}

	//return next point in (x,y) in the tile origin
	private Coordinate nextPoint(){

		boolean validPlace = false;
		int x, y;
		do {
			x = (int) (Math.random() * ObstacleMap.WIDTH);
			y = (int) (Math.random() * ObstacleMap.HEIGHT);
			if(!map.blocked(player, x, y)) {
				validPlace = true;
			}
		} while(validPlace == false);
		return new Coordinate((double)x, (double) y);
	}

	//updates where the player is trying to go next
	public void updatePathStatus(){
		try{
			if(player.getPoint().isClose(nextPlace)){//at the next place
				//System.out.println("it looked for a new place");
				if(counter < path.getLength()){//still have more of the path to follow

					nextPlace = ObstacleMap.tileToPixels(new Coordinate(path.getStep(counter).getX(), path.getStep(counter).getY()));
					counter++;
				} else {//no more path, get a new location
					Coordinate temp = nextPoint();
					if(temp != null){// has somewhere new for the individual to go
						goTo((int)temp.getX(), (int)temp.getY());
					}
				}
			}
		} catch(NullPointerException e){
			System.out.println("Player Num: " + player.getPlayerNum() + "Team: " + player.getTeam() + " Class: " + player.getClassName());
			System.out.println(nextPlace + " And the path is " + path);
			e.printStackTrace();
		}
		//System.out.println(new Coordinate(path.getStep(counter).getX(), path.getStep(counter).getY()));
		//System.out.println(nextPlace);
	}

	


public void updateMovementCommands(){
	if (player.getIsDead() == false) {
		if(player.getTeam() == 0){
			//System.out.println(nextPlace);
		}
		updateMovement();
		updateRotation();
		updateSpecial();
	}
}

private void updateMovement(){
	//checks to see if its current angle is within a reasonable distance from the angle it needs to face
	updatePathStatus();


	if (Utility.areAnglesClose(player.getRotation(), player.getPoint().getAngleBetween(nextPlace), Math.PI / 12)){
		world.checkPlayerCollisions(player);
	}

	//TODO: Set movement backwards when necessary
}

private void updateRotation(){
	updatePathStatus();
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


private void updateSpecial(){
	//TODO: Add special when necessary
}


//x and y are tile coordinates
public void goTo(int x, int y){
	Coordinate playerLocation = map.nearestTile(player.getPoint());
	path = finder.findPath(player,(int) (playerLocation.getX()),(int) (playerLocation.getY()), x, y);
	counter = 1;
	updatePathStatus();
}

public Player getPlayer() {
	return player;
}

public void playerDied(){
	nextPlace = player.getPoint();
	Coordinate temp = nextPoint();
	//System.out.println(temp);
	goTo((int)temp.getX(), (int)temp.getY());
}

}
