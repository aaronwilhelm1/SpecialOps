package gameobjects.Players;

import java.awt.Image;
import java.util.ArrayList;

import gameobjects.Flag;
import gameobjects.World;
import gameobjects.projectiles.Bullet;
import gameobjects.projectiles.Projectile;
import gameobjects.projectiles.Tracer;
import geometry.Circle;
import geometry.CollisionDetector;
import geometry.Coordinate;
import geometry.Rectangle;
import utility.Utility;
import visuals.ImageLoader;
import visuals.Sound;

public class Sentry extends Player{
	
	public double originalRotation;
	public boolean isFiring;
	public boolean isConstructing;
	public static final double CON_TIME = 3000;
	public long timeConstructed;
	Player engineer;
	boolean targeting;
	
	//the displacement values were tested: I can't find out why it needs them though
	public static final double WIDTH_DISPLACEMENT = 0.128787879 * Player.PLAYER_RADIUS / 10;
	public static final double HEIGHT_DISPLACEMENT = 0.128787879 * Player.PLAYER_RADIUS / 10;
	public static final double GUN_RADIUS = 0.742424242 * Player.PLAYER_RADIUS;
	
	public Sentry(int num, int teamColor, World w, Player eng) {
		super(num, teamColor, w,"sentry");
		
		engineer = eng;
		isFiring = false;
		isConstructing = true;
		timeConstructed = System.currentTimeMillis();
		targeting = false;
		
		PlayerStats.setPlayerStats(className, this);
	}
	
	@Override
	public void setImages() {
		images = ImageLoader.getSentryGunImages();
	}

	@Override
	public Image getImage() {
		if(this.isFiring){
			return images[2];
		} else if(this.isConstructing){
			return images[1];
		}
		return images[0];
	}

	@Override
	public Coordinate getCoordinates() {
		//TRANSLATE TO ORIGIN
		double x1 = -GUN_RADIUS;// + WIDTH_DISPLACEMENT;
		double y1 = -GUN_RADIUS;// + HEIGHT_DISPLACEMENT;

		//APPLY ROTATION
		double temp_x1 = x1 * Math.cos(-rotation) - y1 * Math.sin(-rotation);
		double temp_y1 = x1 * Math.sin(-rotation) + y1 * Math.cos(-rotation);

		//TRANSLATE BACK
		return new Coordinate(temp_x1 + getPoint().getX(), temp_y1 + getPoint().getY());
		
	}
	
	public void fire(ArrayList<Player> firingAt) {
		if (isConstructing == true) {
			checkConstruction();
		}
		else {
		if (firingAt.get(0) != null) {//default seems to be 1
			if (targeting == false) {
				targeting = true;
				Sound.beeping.play();
			}
			
			isFiring = true;
			if (!Utility.areAnglesClose(super.getRotation(), super.getPoint().getAngleBetween(firingAt.get(0).point), PlayerStats.SENTRY_SMALL_LATERAL_RANGE)){
				//needs to rotate to face player
				updateRotation(firingAt.get(0).getPoint());
			}
			else if (!CollisionDetector.areColliding(body, world.blueSpawn)) {//checks to see if the shooter is in top left spawn zone
				if (!CollisionDetector.areColliding(body, world.greenSpawn)) {//checks to see if the shooter is in bottom right spawn zone
					if (firingAt != null){
						for (Player p: firingAt) {
							if (p != null) {
								if (System.currentTimeMillis() - lastFire > fireRate - (fireRate * engineer.score / (9 + engineer.score))) {
									if (!CollisionDetector.areColliding(p.getCircle(), world.blueSpawn)) {//checks if in top left spawn zone
										if (!CollisionDetector.areColliding(p.getCircle(), world.greenSpawn)) {//checks if in bottom right spawn zone
											super.world.addBullet(new Bullet(this.point.getX(), this.point.getY(), Math.PI*2 * Math.random(),
											Math.random()*Math.PI*2, (Math.random()*(Bullet.MAX_LINSPEED - Bullet.MIN_LINSPEED)) + Bullet.MIN_LINSPEED, Math.random()*Bullet.MAX_ANGSPEED));
											
											//	p.loseHealth(damage);
												super.world.addProjectile(new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * PlayerStats.SENTRY_SMALL_LATERAL_RANGE), super.className, super.team, engineer, new Coordinate(point.getX(), point.getY()), world));
												lastFire = System.currentTimeMillis();
												Sound.assaultAutomatic.play();
											
										}
			
									}
									
								}
							}
						}
					}
				}
			}
		}
			else {//size is zero, so not shooting at anyone
				//
				returnRotation();
				if (targeting == true) {
					targeting = false;
					Sound.beeping.play();
				}
				isFiring = false;
				
			}
		}
	}
	
	
	
	public boolean changeSpecial(boolean b, boolean override) {
		return false;
	}
	
	public void moveX(){
		
	}
	
	public void moveY(){
		
	}
	
	public void rotateLeft() {
		rotation = rotation + rotateAmount + (rotateAmount * engineer.score / 20);
		if (rotation > Math.PI * 2) {
			rotation = rotation - Math.PI * 2;
		}
		if (Sound.sentryMoving.isActive() == false) {
			Sound.sentryMoving.loop();
		}
	}
	
	public void rotateRight() {
		rotation = rotation - rotateAmount - (rotateAmount * engineer.score / 20);
		if (rotation < 0) {
			rotation = rotation + Math.PI*2;
		}
		if (Sound.sentryMoving.isActive() == false) {
			Sound.sentryMoving.loop();
		}
	}
	
	private void updateRotation(Coordinate p){
		
			double rotation = super.getRotation();
			double direcToFace = super.getPoint().getAngleBetween(p);
			
			double BehindMe = rotation - Math.PI;
			if (BehindMe < 0){
			    BehindMe += Math.PI * 2;
		}
			
			if (direcToFace == BehindMe)
		        rotateRight(); // or randomly choose
		    else if ((direcToFace > BehindMe && direcToFace < rotation) ||
		             (rotation < Math.PI && (direcToFace > BehindMe ||
		                                      direcToFace < rotation))){
		    	rotateRight();
		    }
		    else if ((direcToFace < BehindMe && direcToFace > rotation) ||
		             (rotation >= Math.PI && (direcToFace < BehindMe ||
		                                      direcToFace > rotation))){
		    	rotateLeft();
		    }
		}
			
	private void returnRotation(){
		
		if (!Utility.areAnglesClose(super.rotation, originalRotation, Math.PI / 18)) {
		
		double rotation = super.getRotation();
		double direcToFace = originalRotation;
		
		double BehindMe = rotation - Math.PI;
		
		
		if (BehindMe < 0){
		    BehindMe += Math.PI * 2;
		}
		
		if (direcToFace == BehindMe)
	        rotateRight(); // or randomly choose
	    else if ((direcToFace > BehindMe && direcToFace < rotation) ||
	             (rotation < Math.PI && (direcToFace > BehindMe ||
	                                      direcToFace < rotation))){
	    	rotateRight();
	    }
	    else if ((direcToFace < BehindMe && direcToFace > rotation) ||
	             (rotation >= Math.PI && (direcToFace < BehindMe ||
	                                      direcToFace > rotation))){
	    	rotateLeft();
	    }
		}
		else {
			Sound.sentryMoving.stop();
		}
	}
	
	public void loseHealth(double damage, Projectile projectile, Player p,boolean blood) {
		health = health - damage;
		if (damage < 0) {
			p.damageDone -= damage;
		}else {
			p.damageDone += damage;
		}
		
		if (health <= 0) {
			if (p instanceof Spy) {//gets a backstab
				Spy sp = (Spy) p;
				sp.changeSpecial(true,true);//overrides to give special
				sp.setDisguise("engineer");
			}
			
			Sound.sentryMoving.stop();
			
			double difference = health * -1;
			p.damageDone -= difference;//makes sure not receiving extra points above damage
			
			deathTime = System.currentTimeMillis();
			isDead = true;
			world.addDeathMark(new Coordinate(point.getX() - (Player.PLAYER_WIDTH/2), point.getY() - (Player.PLAYER_WIDTH/2)), team);
			
			p.totalScore++;
			p.score++;
			score = 0;
			damageDone = 0;
			
			if (team == 0) {
				rotation = 0;
			}
			else if (team == 1) {
				rotation = Math.PI;
			}
			
			if (hasFlag == true) {
				hasFlag = false;
				flag.setIsBeingHeld(false);
				
				flag.setPoint(new Coordinate(point.getX(),point.getY()));
				
				Coordinate[] b = new Coordinate[4];
				b[0] = new Coordinate(point.getX(),point.getY());
				b[1] = new Coordinate(point.getX() + Flag.WIDTH, point.getY());
				b[2] = new Coordinate(point.getX() + Flag.WIDTH, point.getY() + Flag.HEIGHT);
				b[3] = new Coordinate (point.getX(), point.getY() + Flag.HEIGHT);
				
				flag.setRect(new Rectangle(b, Flag.WIDTH, Flag.HEIGHT));
				flag.setIsVisible(true);
				
			}
			point.setX(spawnPoint.getX());
			point.setY(spawnPoint.getY());
			inSpecial = false;
			PlayerStats.setPlayerStats(className, this);
			world.gameType.playerDied(this,p);
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
		}
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
		}
		else if (className == "sentry" && health > PlayerStats.SENTRY_HEALTH) {
			health = PlayerStats.SENTRY_HEALTH;
		}
		
		
		
	}
	
	public void checkConstruction() {
		if (System.currentTimeMillis() - timeConstructed > CON_TIME) {
			isConstructing = false;
		}
	}

	
}
