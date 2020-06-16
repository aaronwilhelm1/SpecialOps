package gameobjects.Players;

import java.awt.Image;
import java.util.ArrayList;

import gameobjects.Blood;
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
import visuals.Animation;
import visuals.AnimationLoader;
import visuals.ImageLoader;
import visuals.Sound;

public class Shield extends Player {
	
	private Animation gWalkAnimation;
	private Animation sWalkAnimation;

	public Shield(int num, int teamColor, World w) {
		super(num, teamColor, w, "shield");
		PlayerStats.setPlayerStats(className, this);
	}
	
	@Override
	public void setImages() {
		super.images = ImageLoader.getPlayerImage(className, team);
		gWalkAnimation = AnimationLoader.getShieldGWalking(team);
		sWalkAnimation = AnimationLoader.getShieldSWalking(team);
	}
	
	@Override
	public Image getImage() {
		if(!super.isStanding){
			if(!super.inSpecial){
				return gWalkAnimation.getCurrentImage();
			} else {

				return sWalkAnimation.getCurrentImage();
			}
		} else {
			if(!super.inSpecial){
				return super.images[0];
			} else {

				return super.images[5];
			}
		}
		
		
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
										
										if (super.inSpecial == true) {
											if (p instanceof Sentry) {
												p.loseHealth(damage * 4/5, null,this,true);
												Sound.shieldHit.play();
											}
											else {
												Sound.shieldHit.play();
												p.loseHealth(damage,null,this,true/*new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), 0), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world)*/);
											}
										}
										else {
											
											super.world.addBullet(new Bullet(this.point.getX(), this.point.getY(), Math.PI*2 * Math.random(),
											Math.random()*Math.PI*2, (Math.random()*(Bullet.MAX_LINSPEED - Bullet.MIN_LINSPEED)) + Bullet.MIN_LINSPEED, Math.random()*Bullet.MAX_ANGSPEED));
											
											//	p.loseHealth(damage);
											
											for(int j = 0; j < 4; j++) {
												super.world.addProjectile(new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
											}
											Sound.shotgun.play();
										}
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
	
	public void loseHealth(double damage, Projectile projectile, Player p,boolean blood) {//need to pass in bullet rotation
		if (super.inSpecial == true && projectile != null) {//null means a projectile isn't really being launched at the player or penetrates shield i.e. a health pack, opposing shield, or explosive
			double oppositeAngle = projectile.rotation - Math.PI;
			if (oppositeAngle < 0) {
				oppositeAngle = projectile.rotation + Math.PI;
			}

			double angleDifference = Math.abs(rotation - (oppositeAngle));

			if (angleDifference < lateralRange || (2 * Math.PI) - angleDifference < lateralRange)
			{
				//does nothing
			}
			else {//shield does not block
				//System.out.println("takes damage with shield out");
				//System.out.println("opposite angle" + oppositeAngle);
				//System.out.println(angleDifference);
				takeDamage(damage,p,blood);
			}
			
		}
		else {//not in shield or going over healthpack-like item
			//System.out.println("takes damage with no shield");
			takeDamage(damage,p,blood);
		}
		
	}
	
	public void takeDamage(double d, Player p,boolean blood) {
		if (p instanceof Explosives && inSpecial == true) {
			d = d / 2;
		}
		
		health = health - d;
		if (d < 0) {
			p.damageDone -= d;
		}
		else {
			p.damageDone += d;
		}
		if (d > 0 && blood == true) {
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
			
			if (className == "shield") {
				health = PlayerStats.SHIELD_HEALTH;
			}
		}

	}

	public boolean changeSpecial(boolean b, boolean override) {
		boolean previousState = super.inSpecial;
		if (		super.changeSpecial(b,override)) {
			if (previousState == true) {//was in special, so change back to normal stats
				super.damage = PlayerStats.SHIELD_DAMAGE;
				super.fireRate = PlayerStats.SHIELD_FIRE_RATE;
				super.range = PlayerStats.SHIELD_RANGE;
				super.accuracy = PlayerStats.SHIELD_ACCURACY;
				super.lateralRange = PlayerStats.SHIELD_LATERAL_RANGE;
				super.speed = PlayerStats.SHIELD_SPEED;
				super.rotateAmount = PlayerStats.SHIELD_ROTATE_AMOUNT;
			}
			else { //wasn't in special
				super.damage = PlayerStats.SHIELD_S_DAMAGE;
				super.fireRate = PlayerStats.SHIELD_S_FIRE_RATE;
				super.range = PlayerStats.SHIELD_S_RANGE;
				super.accuracy = PlayerStats.SHIELD_S_ACCURACY;
				super.lateralRange = PlayerStats.SHIELD_S_LATERAL_RANGE;
				super.speed = PlayerStats.SHIELD_S_SPEED;
				super.rotateAmount = PlayerStats.SHIELD_S_ROTATE_AMOUNT;
	
			}
			return true;
		}
		return false;
	}
	
	public void moveX() {
		if (isBackwards) {
			point.setX(-Math.cos(rotation)* (speed + (super.score * speed / 30)) * backwardsSpeedRatio+point.getX());
		}
		else {
			point.setX(Math.cos(rotation)  * ((super.score * speed / 30) + speed)+point.getX());
		}
	}
	
	public void moveY() {
		if (isBackwards) {
			point.setY(Math.sin(rotation)* (speed + ((super.score * speed / 30))) * backwardsSpeedRatio+point.getY());
		}
		else point.setY(-Math.sin(rotation) * ((super.score * speed / 30) + speed) + point.getY());
	}
	
	public void rotateLeft() {
		rotation = rotation + rotateAmount + (rotateAmount * super.score / 30);
		if (rotation > Math.PI * 2) {
			rotation = rotation - Math.PI * 2;
		}
	}
	
	public void rotateRight() {
		rotation = rotation - rotateAmount - (rotateAmount * super.score / 30);
		if (rotation < 0) {
			rotation = rotation + Math.PI*2;
		}
	}
	
}
