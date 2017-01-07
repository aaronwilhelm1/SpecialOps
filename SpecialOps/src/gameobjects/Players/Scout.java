package gameobjects.Players;

import java.util.ArrayList;

import gameobjects.Block;
import gameobjects.Blood;
import gameobjects.Flag;
import gameobjects.LevelIndex;
import gameobjects.World;
import gameobjects.projectiles.Bullet;
import gameobjects.projectiles.Projectile;
import gameobjects.projectiles.Tracer;
import geometry.Circle;
import geometry.CollisionDetector;
import geometry.Coordinate;
import geometry.Rectangle;
import utility.Utility;
import visuals.Sound;

public class Scout extends Player{

	public Scout(int num, int teamColor, World w) {
		super(num, teamColor, w,"scout");
		PlayerStats.setPlayerStats(className, this);
	}
	
	public void fire(ArrayList<Player> firingAt) {
		if (!CollisionDetector.areColliding(body, world.blueSpawn)) {//checks to see if the shooter is in top left spawn zone
			if (!CollisionDetector.areColliding(body, world.greenSpawn)) {//checks to see if the shooter is in bottom right spawn zone
				if (firingAt != null){
					for (Player p: firingAt) {
						if (p != null) {
							if (System.currentTimeMillis() - lastFire > fireRate) {
								if (!CollisionDetector.areColliding(p.getCircle(), world.greenSpawn)) {//checks if in top left spawn zone
									if (!CollisionDetector.areColliding(p.getCircle(), world.blueSpawn)) {//checks if in bottom right spawn zone
										super.world.addBullet(new Bullet(this.point.getX(), this.point.getY(), Math.PI*2 * Math.random(),
										Math.random()*Math.PI*2, (Math.random()*(Bullet.MAX_LINSPEED - Bullet.MIN_LINSPEED)) + Bullet.MIN_LINSPEED, Math.random()*Bullet.MAX_ANGSPEED));
										
										//	p.loseHealth(damage);
										
										for(int j = 0; j < 5; j++) {
											super.world.addProjectile(new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
											
										}
										Sound.shotgun.play();
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
	
	public void loseHealth(double damage, Projectile p, Player play,boolean blood) {
		if (super.inSpecial == true && damage > 0) {
			damage = damage * 2;
		}
		
		health = health - damage;
		if (damage < 0) {
			play.damageDone -= damage;
		}else {
			play.damageDone += damage;
		}	
		
		if (damage > 0 && blood) {
		world.addBlood(new Blood(getPoint().getX(), getPoint().getY(), Math.PI*2 * Math.random(),
				Math.random()*Math.PI*2, (Math.random()*(Blood.MAX_LINSPEED - Blood.MIN_LINSPEED)) + Blood.MIN_LINSPEED, 0));
		}
		
		if (health <= 0) {
			if (play instanceof Spy) {//gets a backstab
				Spy sp = (Spy) play;
				sp.changeSpecial(true,true);//overrides to give special
				sp.setDisguise(className);
			}
			deaths++;
			play.kills++;
			
			double difference = health * -1;
			play.damageDone -= difference;//makes sure not receiving extra points above damage
			
			super.deathTime = System.currentTimeMillis();
			super.isDead = true;
			world.addDeathMark(new Coordinate(point.getX() - (Player.PLAYER_WIDTH/2), point.getY() - (Player.PLAYER_WIDTH/2)), team);

			play.totalScore++;
			play.score++;
			score = 0;
			damageDone = 0;
			
			if (team == 0) {
				rotation = 0;
			}
			else if (team == 1) {
				rotation = Math.PI;
			}
			
			if (super.hasFlag == true) {
				super.hasFlag = false;
				super.flag.setIsBeingHeld(false);
				
				super.flag.setPoint(new Coordinate(point.getX(),point.getY()));
				
				Coordinate[] b = new Coordinate[4];
				b[0] = new Coordinate(point.getX(),point.getY());
				b[1] = new Coordinate(point.getX() + Flag.WIDTH, point.getY());
				b[2] = new Coordinate(point.getX() + Flag.WIDTH, point.getY() + Flag.HEIGHT);
				b[3] = new Coordinate (point.getX(), point.getY() + Flag.HEIGHT);
				
				super.flag.setRect(new Rectangle(b, Flag.WIDTH, Flag.HEIGHT));
				super.flag.setIsVisible(true);
				
			}
			point.setX(super.spawnPoint.getX());
			point.setY(super.spawnPoint.getY());
			inSpecial = false;
			PlayerStats.setPlayerStats(className, this);
			world.gameType.playerDied(this,play);

			//System.out.println("Flag's point: " + flag.getPoint() + " Player's point: " + point);
			
			if (className == "scout") {
				health = PlayerStats.SCOUT_HEALTH;
			}
		}
		
	}

	public boolean changeSpecial(boolean b, boolean override) {
		boolean previousState = super.inSpecial;
		if (super.changeSpecial(b,override)) {
		if (previousState == true) {//was in special, so change back to normal stats
			super.criticalChance = PlayerStats.SCOUT_CRITICAL_CHANCE;
		}
		else { //wasn't in special
			super.criticalChance = PlayerStats.SCOUT_S_CRITICAL_CHANCE;
		}return true;
		}
		return false;
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
}
