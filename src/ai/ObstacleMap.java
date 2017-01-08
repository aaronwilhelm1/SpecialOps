package ai;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import astar.Mover;
import astar.TileBasedMap;
import gameobjects.Block;
import gameobjects.LevelIndex;
import gameobjects.World;
import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import geometry.Coordinate;
import geometry.Rectangle;
import gui.GUI;
import utility.Utility;

public class ObstacleMap implements TileBasedMap{
	
	private World world;
	
	public static final int TILES_PER_SQUARE = 2; //the number of tiles per side
	//the above number has to be one, two, four or greater (with Player radius being .8*blockSize)
	//should however keep it at two as that is the best (and probably the only way it works...)
	public static int WIDTH = LevelIndex.LEVEL_WIDTH * TILES_PER_SQUARE;
	public static int HEIGHT = LevelIndex.LEVEL_HEIGHT * TILES_PER_SQUARE;
	
	public static final double INFLUENCE_MULTIPLIER = 10;
	
	//the amount of influence the time to live has on path deciding
	public static final double TTL_WEIGHT = .01; // was .01
	//the amount of influence team mates have upon path deciding vs enemies
	public static final double FRIENDLY_WEIGHT = .5; //was .5
	
	/** The terrain settings for each tile in the map */
	private Block[][] blocks = new Block[WIDTH][HEIGHT];
	/** The unit in each tile of the map */
	private int[][] units = new int[WIDTH][HEIGHT];
	/** Indicator if a given tile has been visited during the search */
	private boolean[][] visited = new boolean[WIDTH][HEIGHT];
	
	
	private ArrayList<Rectangle> spawns;
	
	public ObstacleMap(World w, Block[][] b){
		world = w;
		blocks = b;
		spawns = LevelIndex.getSpawns(world.map);
	}

	@Override
	public int getWidthInTiles() {
		return WIDTH;
	}

	@Override
	public int getHeightInTiles() {
		return HEIGHT;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}

	@Override
	public boolean blocked(Mover mover, int x, int y) {	
		if(blocks[x][y] != null) {//block is there
			return true;
		}
		Coordinate destination = tileToPixels(new Coordinate(x, y));
		int oppositeTeam;
		Player p = (Player) mover;
		if(p.getTeam() == 0){
			oppositeTeam = 1;
		} else {
			oppositeTeam = 0;
		}
		if(spawns.get(oppositeTeam).pointIsInside(destination)){
			return true;
		}
		return false;
	}

	@Override
	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		/*float dx = tx - sx;
		float dy = ty - sy;
		
		float distance = (float) (Math.sqrt((dx*dx)+(dy*dy)));
		//System.out.println(distance);
		return distance;
		if(world.ea == null){
			return distance;
		}
		double influence = world.ea.getInfluenceAt(tx, ty) - world.ea.getInfluenceAt(sx, sy);
		//System.out.println(world.ea.getInfluenceAt(tx, ty));
		Player p = (Player) mover;
		//System.out.println(influence);
		if(p.getTeam() == 0){ //it's blue team
			return (float) (distance - (influence * INFLUENCE_MULTIPLIER));
		} else {
			return (float) (distance + (influence * INFLUENCE_MULTIPLIER));
		}*/
		//return distance;
		
		float dx = tx - sx;
		float dy = ty - sy;
		
		float distance = (float) (Math.sqrt((dx*dx)+(dy*dy)));
		
		if(world.ea == null){
			return distance;
		}
		double dpmss = world.ea.getInfluenceAt(sx, sy);
		double dpmsf = world.ea.getInfluenceAt(tx, ty);
		Player p = (Player) mover;
		if(p.getTeam() == 0){ //if player is on blue team, multiply it get a pos dpms
			dpmss = dpmss * -1;
			dpmsf = dpmsf * -1;
		}
		double moveSpeed = PlayerStats.getPlayerSpeed(p) * ((double)1 / GUI.TIME_PER_TICK);
		moveSpeed = (moveSpeed / Block.BLOCK_SIZE) / ObstacleMap.TILES_PER_SQUARE;
		//time to move from start to finish
		double movementTime = distance / moveSpeed;
		//System.out.println(movementTime);
		//the amount of dpms that the player takes by going through here
		double dpmsTaken = ((dpmss + dpmsf) / 2);
		//the amount of time the player will have on this route
		//this could be negative if near path goes near team mates
		double timeToLive;
		if(dpmsTaken == 0){
			return (float) movementTime;//no benefit or draw back of going here
		} else {
			timeToLive = p.getHealth() / dpmsTaken;
		}
		//System.out.println(timeToLive);
		if(timeToLive < 0){//it's team mates are nearby
			timeToLive = timeToLive * ObstacleMap.FRIENDLY_WEIGHT;
		}
		
		//System.out.println(timeToLive);
		if(Double.isInfinite(timeToLive)){
			System.out.println(p.getHealth() + " " + dpmsTaken);
		}
		//System.out.println(movementTime + " " + timeToLive);
		double timeLost = movementTime - (TTL_WEIGHT * timeToLive);
		//System.out.println(timeLost);
		//return (float) timeLost;
		if(timeLost < 0){
			return 0;
		}
		return (float) timeLost;
	}

	public void clearVisited() {
		for (int x=0;x<getWidthInTiles();x++) {
			for (int y=0;y<getHeightInTiles();y++) {
				visited[x][y] = false;
			}
		}
	}
	
	public boolean visited(int x, int y) {
		return visited[x][y];
	}
	
	public static Coordinate nearestTile(Coordinate point){
		double x = point.getX();
		double y = point.getY();
		
		double tempx = ((x / Block.BLOCK_SIZE) * TILES_PER_SQUARE);
		double tempy = ((y / Block.BLOCK_SIZE) * TILES_PER_SQUARE);
		int lowx = (int) tempx;
		int lowy = (int) tempy;
		
		return new Coordinate(Utility.round((double)lowx, tempx, (double)lowx+1), Utility.round((double)lowy, tempy, (double)lowy+1));
	}
	
	public static Coordinate tileToPixels(Coordinate c){
		double pixelsPerTile = Block.BLOCK_SIZE / ObstacleMap.TILES_PER_SQUARE;
		double x = c.getX() * pixelsPerTile;
		double y = c.getY() * pixelsPerTile;
		return new Coordinate(x, y);
	}
	
	//for debugging
	public void drawObstacleMap(Graphics g){
		
		double size = Block.BLOCK_SIZE / TILES_PER_SQUARE;
		g.setColor(Color.BLACK);
		for(int y  = 0; y < HEIGHT; y++){
			//draw horizontal line
			g.drawLine(0,(int) (y * size), Block.BLOCK_SIZE * LevelIndex.LEVEL_WIDTH, (int)(y * size));
		}
		for(int x = 0; x < WIDTH; x++){
			//draw vertical line
			g.drawLine((int)(x * size), 0, (int)(x * size),Block.BLOCK_SIZE * LevelIndex.LEVEL_HEIGHT);
		}
		g.setColor(Color.GREEN);
		for(int y  = 0; y < HEIGHT; y++){
			for(int x = 0; x < WIDTH; x++){
				if(blocks[x][y] == null){
					g.drawRect((int)((x * size) - size/4), (int)((y * size) - size/4), (int)size/2, (int)size/2);
				}
			}
		}
		
	}
	
}
