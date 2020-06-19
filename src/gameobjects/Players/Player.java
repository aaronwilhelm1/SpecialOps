package gameobjects.Players;
import java.awt.Image;
import java.util.ArrayList;

import astar.Mover;
import gameobjects.Block;
import gameobjects.Blood;
import gameobjects.Flag;
import gameobjects.World;
import gameobjects.projectiles.Bullet;
import gameobjects.projectiles.Projectile;
import gameobjects.projectiles.Tracer;
import gametype.CTF;
import geometry.Circle;
import geometry.CollisionDetector;
import geometry.Coordinate;
import geometry.Rectangle;
import utility.Utility;
import visuals.Animation;
import visuals.AnimationLoader;
import visuals.Drawable;
import visuals.ImageLoader;

public class Player implements Drawable, Mover{
	//these values may be concatenated based upon what Block.BLOCK_SIZE
	public static final int PLAYER_RADIUS = (int) ((0.8 * Block.BLOCK_SIZE) / 2);
	public static final int PLAYER_WIDTH = Player.PLAYER_RADIUS * 2;
	public static final int PLAYER_HEIGHT = Player.PLAYER_RADIUS * 2;
	public static final long PLAYER_DOUBLE_TAP_DELAY = 75;

	double speed;
	double backwardsSpeedRatio;
	double accuracy;
	public double damage;
	public double health;
	double fireRate;//time, in milliseconds, to fire again
	long lastFire;//time, in milliseconds, since last fire
	public Coordinate point;
	int playerNum;
	boolean isPressedRight;
	boolean isPressedLeft;
	protected boolean isStanding;
	boolean waitingDTRight;//true if waiting on a double tap right
	boolean waitingDTLeft;//true if waiting on a double tap left
	boolean isStrafingRight;
	boolean isStrafingLeft;
	public boolean isBackwards;
	public double rotation;
	double range;
	long timeStrafing;//time, in milliseconds, of beginning the strafe
	boolean canSetTimeOfTap;//true if time of tap can be set i.e. not being held down 
	long timeOfTap;//Time, in milliseconds, of the first press of a button
	public double rotateAmount;//Amount, in radians, that the player can rotate in one tick
	double lateralRange;//is only half i.e. if equal to pi/4, then could see 180 degrees
	public Circle body;
	protected Coordinate spawnPoint;
	protected boolean hasFlag;
	protected Flag flag;
	protected World world;
	
	public double score;//# of points recieved in life so far
	public double damageDone;
	
	protected boolean isDead;
	
	public long spawnTime;//duration, in milliseconds, of spawn
	public long deathTime;// time, in milliseconds of death
	
	protected String className;
	
	protected int team;//0 = blue, 1 = green
	
	protected Image[] images;
	protected Animation walkAnimation; //current animation of player
	
	public int bulletMoves; //number of times bullet moves per frame
	
	public boolean inSpecial;
	
	public long lastSpecialTime;//time when special was changed last
	public double specialLength;//time until can be tapped again
	
	public double criticalChance;
	
	public long lastUnheal;
	public double unhealRate;
	
	public int kills;
	public int deaths;
	public int totalScore;
	
	public boolean switchingClasses;
	
	
	public Player(int num, int teamColor, World w, String cName) {

		className = cName;
		world = w;
		team = teamColor;

		unhealRate = PlayerStats.UNHEAL_RATE;
		
		if (teamColor == 0) {
			rotation = 0;
		}
		else if (teamColor ==1) {
					rotation = Math.PI;
		}
		
		
		setRandomSpawnPoint();
		score = 0;
		damageDone = 0;
		playerNum = num;
		body = new Circle(point,Player.PLAYER_RADIUS);
		lastFire = 0;
		timeStrafing = 0;
		isStanding = true;
		hasFlag = false;
		flag = null;

		backwardsSpeedRatio = .75;
		inSpecial = false;
		specialLength = PlayerStats.getSpecialRate(this);
		lastSpecialTime = 0;

		spawnTime = CTF.SPAWN_TIME;
		isDead = false;
		setImages();

	}
	
	public ArrayList<Player> lateralAimAndInRange(ArrayList<Player> pList) {//returns null if no players able to be shot; otherwise returns player to be shot
		ArrayList<Player> playersInLateralRange = new ArrayList<Player>();
		for (Player p:pList) {//First determine players who are in lateralRange
			if (!p.equals(this) && p.getTeam() != team){
				double angleDifference = Math.abs(rotation - point.getAngleBetween(p.getPoint()));
				if (angleDifference < lateralRange || (2 * Math.PI) - angleDifference < lateralRange) {
					if (p.getPoint().getDistanceBetween(point) < range) {
						if (p instanceof Spy == true && p.inSpecial == true) {
							//don't add to list
						}
						else {
							playersInLateralRange.add(p);
						}
					}
				}
			}
		}
		
		return playersInLateralRange;
	}
	

		
	public Player getClosestPlayer(ArrayList<Player> clearShotPlayers) {
		
		if (clearShotPlayers != null){
		Player closest = null;
		double distance = Double.MAX_VALUE;
		for (Player p: clearShotPlayers) {//Second find the closest player - pyro class may skip this
			double newDistance = p.getPoint().getDistanceBetween(point);
			if (newDistance < distance) {
				distance = newDistance;
				closest = p;
			}
		}
		
		if (distance < range) {
			return closest;
		}
		}
		return null;
	}
	
	public void fire(ArrayList<Player> firingAt) {
		if (!CollisionDetector.areColliding(body, world.blueSpawn)) {//checks to see if the shooter is in top left spawn zone
			if (!CollisionDetector.areColliding(body, world.greenSpawn)) {//checks to see if the shooter is in bottom right spawn zone
				if (firingAt != null){
					for (Player p: firingAt) {
						if (p != null) {
							if (System.currentTimeMillis() - lastFire > fireRate) {
								if (!CollisionDetector.areColliding(p.getCircle(), world.blueSpawn)) {//checks if in top left spawn zone
									if (!CollisionDetector.areColliding(p.getCircle(), world.greenSpawn)) {//checks if in bottom right spawn zone
										world.addBullet(new Bullet(this.point.getX(), this.point.getY(), Math.PI*2 * Math.random(),
										Math.random()*Math.PI*2, (Math.random()*(Bullet.MAX_LINSPEED - Bullet.MIN_LINSPEED)) + Bullet.MIN_LINSPEED, Math.random()*Bullet.MAX_ANGSPEED));
										
										//	p.loseHealth(damage);
											world.addProjectile(new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(rotation, (1 - accuracy) * lateralRange), className, team, this, new Coordinate(point.getX(), point.getY()), world));
											lastFire = System.currentTimeMillis();
											
										
									}
		
								}
								
							}
						}
					}
				}
			}
		}
	}
	
	public void loseHealth(double damage, Projectile projectile, Player p,boolean blood) {
		health = health - damage;
		if (damage < 0) {
			p.damageDone -= damage;
		}else {
			p.damageDone += damage;
		}
		if (damage > 0 && blood == true) {
			world.addBlood(new Blood(getPoint().getX(), getPoint().getY(), Math.PI*2 * Math.random(),
					Math.random()*Math.PI*2, (Math.random()*(Blood.MAX_LINSPEED - Blood.MIN_LINSPEED)) + Blood.MIN_LINSPEED, 0));
		}
		//System.out.println("Player: " + playerNum + " Health: " + health);
		if (health <= 0) {
			if (p instanceof Spy) {//gets a backstab
				Spy sp = (Spy) p;
				sp.changeSpecial(true,true);//overrides to give special
				sp.setDisguise(className);
			}
			
			deaths++;
			p.kills++;
			p.totalScore++;
			double difference = health * -1;
			p.damageDone -= difference;//makes sure not receiving extra points above damage
			
			deathTime = System.currentTimeMillis();
			isDead = true;
			world.addDeathMark(new Coordinate(point.getX() - (Player.PLAYER_WIDTH/2), point.getY() - (Player.PLAYER_WIDTH/2)), team);
			
			world.gameType.playerDied(this,p);
			
			p.score++;
			damageDone = 0;
			score = 0;
			
			if (team == 0) {
				rotation = 0;
			}
			else if (team == 1) {
				rotation = Math.PI;
			}
			
			
			point.setX(spawnPoint.getX());
			point.setY(spawnPoint.getY());
			inSpecial = false;
			PlayerStats.setPlayerStats(className, this);
			//System.out.println("Flag's point: " + flag.getPoint() + " Player's point: " + point);
			
			if (className == "scout") {
				health = PlayerStats.SCOUT_HEALTH;
			}
			else if (className == "heavy"){
				health = PlayerStats.HEAVY_HEALTH;
			}
			else if (className == "assault") {
				health = PlayerStats.ASSAULT_HEALTH;
			}
			else if (className == "sniper") {
				health = PlayerStats.SNIPER_HEALTH;
			}
			else if (className == "shield") {
				health = PlayerStats.SHIELD_HEALTH;
			}
			else if (className == "medic") {
				health = PlayerStats.MEDIC_HEALTH;
			}
			else if (className == "explosives") {
				health = PlayerStats.EXPLOSIVES_HEALTH;
			}
			else if (className == "spy") {
				health = PlayerStats.SPY_HEALTH;
			}
			else if (className == "engineer") {
				health = PlayerStats.ENGINEER_HEALTH;
			}
			else if (className == "sentry") {
				health = PlayerStats.SENTRY_HEALTH;
			}
		}/*
		if (className == "scout" && health > PlayerStats.SCOUT_HEALTH) {
			health = PlayerStats.SCOUT_HEALTH;
		}
		else if (className == "heavy" && health > PlayerStats.HEAVY_HEALTH){
			health = PlayerStats.HEAVY_HEALTH;
		}
		else if (className == "assault" && health > PlayerStats.ASSAULT_HEALTH) {
			health = PlayerStats.ASSAULT_HEALTH;
		}
		else if (className == "sniper" && health > PlayerStats.SNIPER_HEALTH) {
			health = PlayerStats.SNIPER_HEALTH;
		}
		else if (className == "shield" && health > PlayerStats.SHIELD_HEALTH) {
			health = PlayerStats.SHIELD_HEALTH;
		}
		else if (className == "medic" && health > PlayerStats.MEDIC_HEALTH) {
			health = PlayerStats.MEDIC_HEALTH;
		}
		else if (className == "spy" && health > PlayerStats.SPY_HEALTH) {
			health = PlayerStats.SPY_HEALTH;
		}
		else if (className == "engineer" && health > PlayerStats.ENGINEER_HEALTH) {
			health = PlayerStats.ENGINEER_HEALTH;
		}*/
		else if (className == "sentry" && health > PlayerStats.SENTRY_HEALTH) {
			health = PlayerStats.SENTRY_HEALTH;
		}
		
		
	}
	
	public Coordinate getPoint() {
		return point;
	}
	
	public int getPlayerNum() {
		return playerNum;
	}

	
	public void rotateLeft() {
		rotation = rotation + rotateAmount;
		if (rotation > Math.PI * 2) {
			rotation = rotation - Math.PI * 2;
		}
	}
	
	public void rotateRight() {
		rotation = rotation - rotateAmount;
		if (rotation < 0) {
			rotation = rotation + Math.PI*2;
		}
	}
	
	public void moveX() {
		if (isBackwards) {
			point.setX(-Math.cos(rotation)* speed * backwardsSpeedRatio+point.getX());
		}
		else {
			point.setX(Math.cos(rotation)*speed+point.getX());
		}
	}
	
	public void moveY() {
		if (isBackwards) {
			point.setY(Math.sin(rotation)* speed * backwardsSpeedRatio+point.getY());
		}
		else point.setY(-Math.sin(rotation)*speed+point.getY());
	}
	
	public void strafeRight() {
		point.setY(Math.cos(rotation)*speed+point.getY());
		point.setX(Math.sin(rotation)*speed+point.getX());
	}
	
	public void strafeLeft() {
		point.setY(-Math.cos(rotation)*speed+point.getY());
		point.setX(-Math.sin(rotation)*speed+point.getX());
	}
	
	public void setIsPressedRight(boolean b) {
		isPressedRight = b;
	}
	
	public boolean getIsPressedRight() {
		return isPressedRight;
	}
	
	public void setIsPressedLeft(boolean b) {
		isPressedLeft = b;
	}
	
	public boolean getIsPressedLeft() {
		return isPressedLeft;
	}
	
	public void setIsStanding(boolean b) {
		isStanding = b;
	}

	
	public boolean getIsStanding() {
		return isStanding;
	}
	

	@Override
	public void setImages() {
		images = ImageLoader.getPlayerImage(className, team);
		walkAnimation = AnimationLoader.getWalking(className, team);
	}

	@Override
	public Image getImage() {
		if(!isStanding){
			return walkAnimation.getCurrentImage();
		}
		return images[0];
	}

	@Override
	public Coordinate getCoordinates() {
		//TRANSLATE TO ORIGIN
		double x1 = -Player.PLAYER_RADIUS;
		double y1 = -Player.PLAYER_RADIUS;

		//APPLY ROTATION
		double temp_x1 = x1 * Math.cos(-rotation) - y1 * Math.sin(-rotation);
		double temp_y1 = x1 * Math.sin(-rotation) + y1 * Math.cos(-rotation);

		//TRANSLATE BACK
		return new Coordinate(temp_x1 + body.getCenter().getX(), temp_y1 + body.getCenter().getY());
		
	}
	
	public void setTimeOfTap() {
		timeOfTap = System.currentTimeMillis();
	}
	
	public long getTimeOfTap() {
		return timeOfTap;
	}
	
	public boolean getCanSetTimeOfTap() {
		return canSetTimeOfTap;
	}
	
	public void setCanSetTimeOfTap(boolean b) {
		canSetTimeOfTap = b;
	}
	
	public boolean getWaitingDTRight() {
		return waitingDTRight;
	}
	
	public void setWaitingDTRight(boolean b) {
		waitingDTRight = b;
	}
	
	public boolean getWaitingDTLeft() {
		return waitingDTLeft;
	}
	
	public void setWaitingDTLeft(boolean b) {
		waitingDTLeft = b;
	}
	
	public boolean getIsStrafingRight() {
		return isStrafingRight;
	}
	
	public void setIsStrafingRight(boolean b) {
		isStrafingRight = b;
	}
	
	public boolean getIsStrafingLeft() {
		return isStrafingLeft;
	}
	
	public void setIsStrafingLeft(boolean b) {
		isStrafingLeft = b;
	}
	
	public void setTimeStrafing() {
		timeStrafing = System.currentTimeMillis();
	}
	
	public boolean canStopStrafing() {
		return (System.currentTimeMillis() - timeStrafing > 75);//tested for optimal time for starting when asked to and not ending too early
	}
	
	public boolean equals(Player p) {
		return (playerNum == p.getPlayerNum());
	}
	
	public Coordinate getNextMove() {
		if (isBackwards) {
			return new Coordinate(-Math.cos(rotation)*speed+point.getX(),Math.sin(rotation)*speed+point.getY());
		}
		return new Coordinate(Math.cos(rotation)*speed+point.getX(),-Math.sin(rotation)*speed+point.getY());
	}
	
	public Circle getCircle() {
		return body;
	}
	
	public boolean getHasFlag() {
		return hasFlag;
	}
	
	public void setHasFlag(boolean b) {
		hasFlag = b;
	}
	
	public int getTeam() {
		return team;
	}
	
	public Flag getFlag() {
		return flag;
	}
	
	public void setFlag(Flag f) {
		flag = f;
	}

	@Override
	public double getRotation() {
		return rotation;
	}

	@Override
	public boolean needsToFlip() {
		return false;
	}
	
	public double getHealth() {
		return health;
	}
	
	public boolean getIsDead() {
		return isDead;
	}
	
	public void setIsDead(boolean b) {
		isDead = b;
	}
	
	public long getDeathTime() {
		return deathTime;
	}
	
	public void setDeathTime() {
		deathTime = System.currentTimeMillis();
	}
	
	public long getSpawnTime() {
		return spawnTime;
	}
	
	public String getClassName() {
		return className;
	}

	public double getAccuaracy() {
		return accuracy;
	}
	
	public boolean changeSpecial(boolean b, boolean override) {//returns true if it does change;override will still change value, even if timing is wrong
		if (inSpecial != b) {//not going to reset unless actually changes
			if (System.currentTimeMillis() - lastSpecialTime > specialLength || override) {
				//System.out.println("activating special");
				lastSpecialTime = System.currentTimeMillis();
				inSpecial = !inSpecial;
				return true;
			}
		}
		return false;

	}
	
	public void setRandomSpawnPoint() {
		double randomX;
		double randomY;
		
		if (team == 0) {

			Coordinate topLeft = world.blueSpawn.getCoords()[0];
					
			randomX = Math.random() * (world.blueSpawn.getWidth() - Player.PLAYER_RADIUS * 2.2) ;
			randomY = Math.random() * (world.blueSpawn.getHeight() - Player.PLAYER_RADIUS * 2.2);
			
			point = new Coordinate(randomX + topLeft.getX() + Player.PLAYER_RADIUS * 1.1, randomY + Player.PLAYER_RADIUS * 1.1 + topLeft.getY());
			spawnPoint = new Coordinate(randomX + topLeft.getX() + Player.PLAYER_RADIUS * 1.1, randomY + topLeft.getY() + Player.PLAYER_RADIUS * 1.1);
		}
		else if (team == 1) {
			
			Coordinate topLeft = world.greenSpawn.getCoords()[0];
					
			randomX = Math.random() * (world.blueSpawn.getWidth() - Player.PLAYER_RADIUS * 2.2);
			randomY = Math.random() * (world.blueSpawn.getHeight() - Player.PLAYER_RADIUS * 2.2);
			
			point = new Coordinate(randomX + topLeft.getX() + Player.PLAYER_RADIUS * 1.1, randomY + topLeft.getY() + Player.PLAYER_RADIUS * 1.1);

			spawnPoint = new Coordinate(randomX + topLeft.getX() + Player.PLAYER_RADIUS * 1.1, randomY + topLeft.getY() + Player.PLAYER_RADIUS * 1.1);
		}
		

	}

	public void checkDamageDone() {
		int toAdd = ((int)damageDone) / 200;
		if ( toAdd > 0) {
			score += toAdd;
			totalScore += toAdd;
			damageDone -= toAdd * 200;
		}
	}

	
}
