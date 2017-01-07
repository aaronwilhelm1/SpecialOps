package gametype;

import java.util.ArrayList;

import ai.Individual;
import ai.VIPIndividual;
import gameobjects.Block;
import gameobjects.GameObject;
import gameobjects.HealthPack;
import gameobjects.LevelIndex;
import gameobjects.World;
import gameobjects.Players.Assault;
import gameobjects.Players.Explosives;
import gameobjects.Players.Heavy;
import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import gameobjects.Players.Scout;
import gameobjects.Players.Shield;
import gameobjects.Players.Sniper;
import geometry.CollisionDetector;
import geometry.Coordinate;
import geometry.Rectangle;

public class VIP implements GameType{

	private ArrayList<GameObject> extras;
	public VIPIndividual blueVIP;
	public VIPIndividual greenVIP;
	private World world;
	private double blueScore;
	private double greenScore;
	private long endGameTime;
	private long endGameLength;
	private String winningTeam;
	private HealthPack healthPack1;
	private HealthPack healthPack2;
	public VIP(World w) {
		
		world = w;
		Player gp = PlayerStats.createRandomPlayer(21,1,w);
		Player bp = null;
		if (gp instanceof Scout) {
			bp = new Scout(20,0,world);
		}
		else if (gp instanceof Assault) {
			bp =new Assault(20,0,world);
		}
		else if (gp instanceof Heavy) {
			bp = new Heavy(20,0,world);
		}
		else if (gp instanceof Sniper) {
			bp = new Sniper(20,0,world);
		}
		else if (gp instanceof Shield) {
			bp = new Shield(20,0,world);
		}
		else if (gp instanceof Explosives) {
			bp = new Explosives(20,0,world);
		}
		
		endGameLength  = 3000;
		
		world.playerList.add(gp);
		world.playerList.add(bp);
		
		blueVIP = new VIPIndividual(w, bp, LevelIndex.getTileMap(world.map));
		greenVIP = new VIPIndividual(w, gp, LevelIndex.getTileMap(world.map));
		
		extras = new ArrayList<GameObject>();
		
		Coordinate[] hp1 = new Coordinate[4]; 
		
		Coordinate topLeft0 = LevelIndex.getHealthPacks(world.map).get(0);

		
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
	
	@Override
	public double getScoreBlue() {
		return blueScore;
	}

	@Override
	public double getScoreGreen() {
		return greenScore;
	}

	@Override
	public ArrayList<GameObject> getExtras() {
		return extras;
	}

	@Override
	public void checkGameTypeMechanics(ArrayList<Player> playerList) {
		blueVIP.updateMovementCommands();
		greenVIP.updateMovementCommands();
		
		for (Player p: playerList) {
			for (GameObject x: extras) {
				if (CollisionDetector.areColliding(x.getShape(), p.getCircle())) {
					x.haveInteraction(p);
				//	System.out.println("having interaction");
				}
			}
		}
		
		if (checkEndgameTimer()) {//if the game is in the process of being over
			world.setWinningTeam(winningTeam);
		}
		
		healthPack1.checkHealthPackTimer();
		healthPack2.checkHealthPackTimer();
	}
	
	public void playerDied(Player p, Player killer) {
		if(blueVIP.getPlayer().equals(p)){
			blueVIP.playerDied();
			addToScore(1, 1);
			killer.totalScore++;
			killer.score++;
		} else if(greenVIP.getPlayer().equals(p)){
			greenVIP.playerDied();
			addToScore(0, 1);
			killer.score++;
			killer.totalScore++;
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
	
	public void addToScore(int team, double pointsToBeAdded) {
		if (team == 0) {
			blueScore = blueScore + pointsToBeAdded;
			if (blueScore == 3.0) {
				if (endGameTime == 0) {
					endGameTime = System.currentTimeMillis();
					winningTeam = "Blue Team";
				}
			}
		}
		else if (team == 1) {
			greenScore = greenScore + pointsToBeAdded;
			if (greenScore == 3.0) {
				if (endGameTime == 0) {
					endGameTime = System.currentTimeMillis();
					winningTeam = "Green Team";
				}
			}
		}
	}

	@Override
	public ArrayList<Coordinate> getObjectives(int team) {
		ArrayList<Coordinate> toReturn = new ArrayList<Coordinate>();
		if(team == 0){
			toReturn.add(blueVIP.getPlayer().getPoint());
		} else {

			toReturn.add(greenVIP.getPlayer().getPoint());
		}
		return toReturn;
	}
	
}
