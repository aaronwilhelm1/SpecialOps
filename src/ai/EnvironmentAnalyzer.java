package ai;

import java.util.ArrayList;

import gameobjects.Block;
import gameobjects.LevelIndex;
import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import gameobjects.Players.Sentry;
import geometry.Coordinate;
import gui.GUI;
import utility.Utility;
import gameobjects.World;

public class EnvironmentAnalyzer implements Runnable{

	//TODO: Could theoretically set this to zero, would just need to make sure that there is a "momentum" to switching paths/decisions to keep AI from jerking around
	private final int APPROXIMATE_UPDATE_INTERVAL = 200; //too low of a number cause the AI to jerk a little since they constantly change paths

	public static double highestinfluence = 0;

	//[team][x][y] in tile coordinates
	private double[][][] damageMaps;
	//the amount of dps (+ for blue, - for green) that will happen at a point
	//health of the players is taken into account
	public static double[][] influenceMap;
	private Block[][] blockMap;
	private ObstacleMap obstacleMap;
	private World world;
	private int previousInterval;
	private long previousTime;
	private boolean isRunning;
	private ArrayList<Double> timeToLive;
	

	public EnvironmentAnalyzer(World w){
		isRunning = true;
		world = w;
		damageMaps = new double[2][ObstacleMap.WIDTH][ObstacleMap.HEIGHT];
		//positive for blue, negative for green
		influenceMap = new double[ObstacleMap.WIDTH][ObstacleMap.HEIGHT];
		blockMap = LevelIndex.getTileMap(world.map);
		obstacleMap = new ObstacleMap(world, blockMap);
		timeToLive = new ArrayList<Double>();
		previousInterval = -1;//just a temp value so it gets an idea of where to go
		previousTime = System.currentTimeMillis();
	}

	private void updateData(){
		//synchronized(world.playerList){
			//synchronized(influenceMap){
				//synchronized(damageMaps){
					if(previousInterval == -1) {
						previousInterval = APPROXIMATE_UPDATE_INTERVAL;
					} else {
						previousInterval = (int) (System.currentTimeMillis() - previousTime);
					}
					previousTime = System.currentTimeMillis();
					setTTL();
					resetInfluenceMap();
					/*if running into bugs with sentry, the setTTL() could be using a player list with a sentry
					and if a sentry is deleted the updateTeam() may be trying to use that same number 
					of players, resulting in it trying to access players that don't exist*/
					resetDamageMaps();
					for(int j = 0; j < 2; j++){
						updateTeam(j);
					}
				//}
			//}
		//}
		
	}

	private void resetDamageMaps(){
		//System.out.println("Maps cleared");
		synchronized(damageMaps){
			damageMaps = new double[2][ObstacleMap.WIDTH][ObstacleMap.HEIGHT];
		}
		/*synchronized(influenceMap){
			influenceMap = new double[ObstacleMap.WIDTH][ObstacleMap.HEIGHT];
		}*/
	}

	private void resetInfluenceMap(){
		synchronized(influenceMap){
			influenceMap = new double[ObstacleMap.WIDTH][ObstacleMap.HEIGHT];
		}
	}

	private void setTTL(){
		timeToLive = new ArrayList<Double>();
		synchronized(world.playerList){
			for(int j = 0; j < world.playerList.size(); j++){
				Player p = world.playerList.get(j);
				int opposingTeam;
				if(p.getTeam() == 0){
					opposingTeam = 1;
				} else {
					opposingTeam = 0;
				}
				Coordinate loc = ObstacleMap.nearestTile(p.getPoint());
				double dpms = damageMaps[opposingTeam][(int)loc.getX()][(int)loc.getY()] / previousInterval;
				if(dpms == 0){
					timeToLive.add(Double.MAX_VALUE);
				} else {

					timeToLive.add(p.getHealth() / dpms);
				}
			}
		}
	}

	//updates the damage map and the influence map for that team
	private void updateTeam(int team){
		synchronized(world.playerList){
			synchronized(damageMaps){
				synchronized(influenceMap){
					double[][]damageMap = damageMaps[team];
					for(int j = 0; j < world.playerList.size(); j++){
						for(int x = 0; x < damageMap.length; x++){
							for(int y = 0; y < damageMap[0].length; y++){
								//System.out.println(x + " " + y);
								Player p = world.playerList.get(j);

								//System.out.println(damage);
								if(p.getTeam() == team){

									double damage;
									if(p instanceof Sentry && ((Sentry) p).isConstructing){
										damage = 0;
									} else {
										damage =  calculateDamage(x, y, p);
									}
									
									damageMap[x][y] += damage;
									
									// Now update influence map
									//the amount of dps they did without accounting for time to live
									double DPMSOld = damage / previousInterval;
									if(damage == 0){
										continue;//the calculations below won't matter
									}
									//System.out.println(DPMSOld);
									//the amount of dps they do accounting for their time to live
									double DPMSNew;
									if(timeToLive.get(j) > previousInterval){
										DPMSNew = DPMSOld;
									} else {
										DPMSNew = DPMSOld * timeToLive.get(j) / previousInterval;
									}
									//System.out.println(DPMSNew);
									if(team == 0){
										//influenceMap[x][y] += (damage * p.getHealth() / PlayerStats.getPlayerMaxHealth(p));
										influenceMap[x][y] += DPMSNew;
									} else{
										//influenceMap[x][y] -= (damage * p.getHealth() / PlayerStats.getPlayerMaxHealth(p));
										influenceMap[x][y] -= DPMSNew;
									}
									
									
								}

							}
						}

					}

				}
			}

		}
	}

	//calculates the damage the player "p" will do upon (x, y) in tile coordinates
	private double calculateDamage(int x, int y, Player p){
		//System.out.println(x + " " + y);
		if(obstacleMap.blocked(p, x, y)){//there's a block there or the opponent's spawn
			return 0;
		}
		Coordinate target = ObstacleMap.tileToPixels(new Coordinate(x, y));
		double distance = Utility.getDistance(p.getPoint(), target);
		//double angleToTurn = Utility.clamp(target.getAngleBetween(p.getPoint()) - PlayerStats.getPlayerLateralRange(p), 0, Math.PI);

		double angleToTurn = Math.abs(p.getRotation() - p.getPoint().getAngleBetween(target));

		if (angleToTurn > Math.PI) {
			angleToTurn = 2 * Math.PI - angleToTurn;
		}

		double turnSpeed = PlayerStats.getPlayerRotateAmount(p) * ((double)1 / GUI.TIME_PER_TICK);//1 so it's in milliseconds
		double moveSpeed = PlayerStats.getPlayerSpeed(p) * ((double)1 / GUI.TIME_PER_TICK);
		double timeToTurn, timeToMove;
		if(turnSpeed == 0){
			timeToTurn = 100000;//high value, but don't want to add two max values for fear of a negative
		} else {
			timeToTurn = angleToTurn / turnSpeed;
		}
		if(distance < PlayerStats.getPlayerRange(p)){//inside the range, so don't have to move
			timeToMove = 0;
		} else if(moveSpeed == 0){//don't divide by zero
			timeToMove = 100000;//high value, but don't want to add two max values for fear of a negative
		} else {
			timeToMove = (distance - PlayerStats.getPlayerRange(p)) / moveSpeed;
		}
		//out of range and movement/turn area
		if(previousInterval < (timeToTurn + timeToMove)){
			return 0;
		}
		if(!world.hasClearShot(target, p)){//TODO: THIS IS WRONG: Player could move around obstacle to fire
			return 0;						//Assuming previousInterval <<< time per tick it won't make much of a difference
		}
		double firingLatRange = (1 - PlayerStats.getPlayerAccuracy(p)) * PlayerStats.getPlayerLateralRange(p) * 2;
		//System.out.println(firingLatRange);
		double perBulletsThatHit = (2 * Player.PLAYER_RADIUS / (distance * firingLatRange));
		if(perBulletsThatHit > 1){//in some cases distance is so small, the above ratio gives too large of numbers
			perBulletsThatHit = 1;
		}
		double dpms = (PlayerStats.getPlayerDamage(p) * PlayerStats.getPlayerNumOfBullets(p) * perBulletsThatHit * (1 + PlayerStats.getCritChance(p))) / PlayerStats.getPlayerFireRate(p);
		//System.out.println(dpms);
		//System.out.println(dpms * 1000);
		double damageOnTile;
		//if needs to move or aim
		if(distance > PlayerStats.getPlayerRange(p) || target.getAngleBetween(p.getPoint())> PlayerStats.getPlayerLateralRange(p)){
			damageOnTile = (previousInterval - timeToTurn - timeToMove) * dpms;
		} else {
			damageOnTile = previousInterval * dpms;
		}
		//System.out.println(damageOnTile);
		return damageOnTile;
	}

	public double getDamageAt(int team, int x, int y){
		synchronized(damageMaps){
			return damageMaps[team][x][y];
		}
	}

	public double getInfluenceAt(int x, int y){
		//synchronized(influenceMap){
			return influenceMap[x][y];
		//}
	}

	@Override
	public void run() {
		ArrayList <Integer> previousIntervals = new ArrayList<Integer>(2);
		previousIntervals.add(APPROXIMATE_UPDATE_INTERVAL);
		previousIntervals.add(APPROXIMATE_UPDATE_INTERVAL);
		long previousDifference = 0;
		while(isRunning == true){
			updateData(); 
			//in updateData the num is turned to zero
				synchronized(world.computers){
					for(int j = 0; j < world.computers.size(); j++){
						world.computers.get(j).getIndividual().refreshPath();
					}
				}
			

			previousIntervals.set(1, previousIntervals.get(0));
			previousIntervals.set(0, previousInterval);
			int timeSpent = previousInterval;
			long difference = timeSpent - APPROXIMATE_UPDATE_INTERVAL;
			previousDifference += difference;
			long sleepTime = APPROXIMATE_UPDATE_INTERVAL - previousDifference;
			if(timeSpent < 0) {
				timeSpent = 0;
			}
			if(sleepTime < 0) {
				sleepTime = 0;
			}

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void destroy(){
		isRunning = false;
	}
}
