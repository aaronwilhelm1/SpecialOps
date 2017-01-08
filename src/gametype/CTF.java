package gametype;

import java.util.ArrayList;

import gameobjects.Block;
import gameobjects.Flag;
import gameobjects.GameObject;
import gameobjects.HealthPack;
import gameobjects.LevelIndex;
import gameobjects.Table;
import gameobjects.World;
import gameobjects.Players.Player;
import geometry.CollisionDetector;
import geometry.Coordinate;
import geometry.Rectangle;

public class CTF implements GameType{

	private double blueScore;
	private double greenScore;
	private ArrayList<GameObject> extras;
	private Flag blueFlag;
	private Table blueTable;
	private Flag greenFlag;
	private Table greenTable;
	private HealthPack healthPack1;
	private HealthPack healthPack2;
	private World world;
	private long endGameTime;
	private long endGameLength;
	private String winningTeam;
	
	public final static int SPAWN_TIME = 7000;
	
	public CTF(World w) {
		world = w;
		
		endGameTime = 0;
		endGameLength = 3000;
		
		blueScore = 0;
		greenScore = 0;
		extras = new ArrayList<GameObject>();
		
		Coordinate tlb = null;
		Coordinate tlg = null;
		ArrayList<Coordinate> flags = new ArrayList<Coordinate>();
		
		if (world.map == "warehouse") {
			//flags = LevelIndex.getPolarisFlags();
			tlb = new Coordinate(Block.BLOCK_SIZE * 2, Block.BLOCK_SIZE * 8);
			tlg = new Coordinate((LevelIndex.LEVEL_WIDTH - 3) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 8);

		}
		else if (world.map == "polaris") {
			tlb = new Coordinate(Block.BLOCK_SIZE * 1, Block.BLOCK_SIZE * 1);
			tlg = new Coordinate((LevelIndex.LEVEL_WIDTH - 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 15);
		}
		else if (world.map == "forts") {
			tlb = new Coordinate(Block.BLOCK_SIZE * 5, Block.BLOCK_SIZE * 8);
			tlg = new Coordinate(22 * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 8);
		}
		
	//	Coordinate tlb = new Coordinate(Block.BLOCK_SIZE * 2, Block.BLOCK_SIZE * 8);
		Coordinate[] b = new Coordinate[4];
		b[0] = tlb;
		b[1] = new Coordinate(tlb.getX() + Flag.WIDTH, tlb.getY());
		b[2] = new Coordinate(tlb.getX() + Flag.WIDTH, tlb.getY() + Flag.HEIGHT);
		b[3] = new Coordinate (tlb.getX(), tlb.getY() + Flag.HEIGHT);
		
//		Coordinate tlg = new Coordinate((LevelIndex.LEVEL_WIDTH - 3) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 8);
		Coordinate[] g = new Coordinate[4];
		g[0] = tlg;
		g[1] = new Coordinate(tlg.getX() + Flag.WIDTH, tlg.getY());
		g[2] = new Coordinate(tlg.getX() + Flag.WIDTH, tlg.getY() + Flag.HEIGHT);
		g[3] = new Coordinate (tlg.getX(), tlg.getY() + Flag.HEIGHT);
		//TODO: if null pointer on line below, change so the flag is the only thing to have a reference
		blueTable = new Table (tlb, new Rectangle(b, Block.BLOCK_SIZE, Block.BLOCK_SIZE),0, this, w);
		greenTable = new Table(tlg, new Rectangle(g, Block.BLOCK_SIZE, Block.BLOCK_SIZE),1, this, w);
		blueFlag = new Flag(tlb, new Rectangle(b, Flag.WIDTH, Flag.HEIGHT), 0, blueTable, w);
		greenFlag = new Flag(tlg, new Rectangle(g, Flag.WIDTH, Flag.HEIGHT), 1, greenTable, w);

		
		extras.add(blueTable);
		extras.add(greenTable);
		extras.add(greenFlag);
		extras.add(blueFlag);

		
		Coordinate topLeft0 = LevelIndex.getHealthPacks(world.map).get(0);
		
		
		Coordinate[] hp1 = new Coordinate[4]; 
		
		hp1[0] = topLeft0;
		hp1[1] = new Coordinate(topLeft0.getX() + Block.BLOCK_SIZE * 1 - HealthPack.WIDTH / 6, topLeft0.getY() + HealthPack.HEIGHT / 6);
		hp1[2] = new Coordinate(topLeft0.getX() + Block.BLOCK_SIZE * 1 - HealthPack.WIDTH / 6, topLeft0.getY() + Block.BLOCK_SIZE * 1 - HealthPack.HEIGHT / 6);
		hp1[3] = new Coordinate(topLeft0.getX() + HealthPack.WIDTH / 6, topLeft0.getY() + Block.BLOCK_SIZE * 1 - HealthPack.HEIGHT / 6);
		
		
		healthPack1 = new HealthPack(hp1[0], new Rectangle(hp1, HealthPack.WIDTH, HealthPack.HEIGHT), w);
		extras.add(healthPack1);
		
		Coordinate topLeft1 = LevelIndex.getHealthPacks(world.map).get(1);
		
		
		Coordinate[] hp2 = new Coordinate[4]; 
		hp2[0] = topLeft1;
		hp2[1] = new Coordinate(topLeft1.getX() + Block.BLOCK_SIZE * 1 - HealthPack.WIDTH / 6, topLeft1.getY() + HealthPack.HEIGHT / 6);
		hp2[2] = new Coordinate(topLeft1.getX() +Block.BLOCK_SIZE * 1 - HealthPack.WIDTH / 6, topLeft1.getY() + Block.BLOCK_SIZE * 1 - HealthPack.HEIGHT / 6);
		hp2[3] = new Coordinate(topLeft1.getX() + HealthPack.WIDTH / 6, topLeft1.getY() + Block.BLOCK_SIZE * 1 - HealthPack.HEIGHT / 6);
		
		healthPack2 = new HealthPack(hp2[0], new Rectangle(hp2, HealthPack.WIDTH, HealthPack.HEIGHT), w);
		extras.add(healthPack2);	
	}
	
	public Flag getFlag(int team) {
		if (team == 0) {
			return blueFlag;
			
		}
		else if (team == 1) {
			return greenFlag;
		}
		else return null;
	}
	
	@Override
	public double getScoreBlue() {
		return blueScore;
	}

	@Override
	public double getScoreGreen() {
		return greenScore;
	}
	
	public void addToScore(int team, double pointsToBeAdded) {
		if (team == 0) {
			blueScore = blueScore + pointsToBeAdded;
			if (blueScore == 3.0) {
				endGameTime = System.currentTimeMillis();
				winningTeam = "Blue Team";
			}
		}
		else if (team == 1) {
			greenScore = greenScore + pointsToBeAdded;
			if (greenScore == 3.0) {
				endGameTime = System.currentTimeMillis();
				winningTeam = "Green Team";
			}
		}
	}


	@Override
	public ArrayList<GameObject> getExtras() {
		return extras;
	}

	@Override
	public void checkGameTypeMechanics(ArrayList<Player> playerList) {
		for (Player p: playerList) {
			for (GameObject x: extras) {
				if (CollisionDetector.areColliding(x.getShape(), p.getCircle())) {
					x.haveInteraction(p);
				//	System.out.println("having interaction");
				}
			}
		}
		
		healthPack1.checkHealthPackTimer();
		healthPack2.checkHealthPackTimer();
		if (checkEndgameTimer()) {//if the game is in the process of being over
			world.setWinningTeam(winningTeam);
		}
	}
	
	private boolean checkEndgameTimer() {//returns true if the game is in the process of ending
		if (endGameTime != 0) {
			
			if (System.currentTimeMillis() - endGameTime > endGameLength) {
				world.destroyWorld();
			}
			return true;
		}
		return false;
	}

	public Table getTable(int team) {
		if (team == 0) {
			return blueTable;
		}
		else if (team == 1) {
			return greenTable;
		}
		return null;
	}

	public void playerDied(Player p,Player killer) {//TODO: need to add code for if has flag
		
	}

	@Override
	public ArrayList<Coordinate> getObjectives(int team) {
		ArrayList<Coordinate> toReturn = new ArrayList<Coordinate>();
		if(team == 0){
			toReturn.add(blueFlag.getPoint());
		} else {
			toReturn.add(greenFlag.getPoint());
		}
		return toReturn;
	}
}
