package gameobjects.Players;

import java.util.ArrayList;

import gameobjects.Blood;
import gameobjects.Flag;
import gameobjects.Mine;
import gameobjects.World;
import gameobjects.projectiles.Bullet;
import gameobjects.projectiles.Grenade;
import gameobjects.projectiles.Projectile;
import gameobjects.projectiles.Tracer;
import geometry.Circle;
import geometry.CollisionDetector;
import geometry.Coordinate;
import geometry.Rectangle;
import utility.Utility;
import visuals.Sound;

public class Explosives extends Player{
	
	public int minesPlaced;
	public ArrayList<Mine> minesActive;

	public Explosives(int num, int teamColor, World w) {
		super(num, teamColor, w,"explosives");
		
		minesPlaced = 0;
		minesActive = new ArrayList<Mine>();
		PlayerStats.setPlayerStats(className, this);
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
										super.world.addBullet(new Bullet(this.point.getX(), this.point.getY(), Math.PI*2 * Math.random(),
										Math.random()*Math.PI*2, (Math.random()*(Bullet.MAX_LINSPEED - Bullet.MIN_LINSPEED)) + Bullet.MIN_LINSPEED, Math.random()*Bullet.MAX_ANGSPEED));
										super.world.addProjectile(new Grenade(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
											lastFire = System.currentTimeMillis();
											Sound.grenadeLauncher.play();
										
									}
		
								}
								
							}
						}
					}
				}
			}
		}
	}
	
	
	
	public boolean changeSpecial(boolean b, boolean override) {
		//may eventually do this instead of rectangle
//		world.addTrap(new Mine(point, body, world));
		
		if (super.changeSpecial(true, false)) {
		
			super.inSpecial = false;
			Coordinate c = new Coordinate(super.point.getX(), super.point.getY());
			
			Mine m = new Mine(c,new Circle(c,(int)PlayerStats.MINE_RADIUS) , world, super.team,this);
			
			world.addTrap(m);
			minesActive.add(m);
			
			minesPlaced++;
			if (minesPlaced > PlayerStats.MINE_NUM_LIMIT) {
				
				Mine removed = minesActive.remove(0);
				
				world.removeTrap(removed);
				minesPlaced--;
			}
			return true;
		}
		return false;
			/*		boolean previousState = super.inSpecial;
		super.changeSpecial();
		if (previousState == true) {//was in special, so change back to normal stats
			super.accuracy = PlayerStats.EXPLOSIVES_ACCURACY;
			super.range = PlayerStats.EXPLOSIVES_RANGE;
		}
		else { //wasn't in special
			super.accuracy = PlayerStats.EXPLOSIVES_ACCURACY;
			super.range = PlayerStats.EXPLOSIVES_RANGE;
		}*/
	}
	
	public void loseHealth(double damage, Projectile projectile, Player p,boolean blood) {
		health = health - damage;
		if (damage < 0) {
			p.damageDone -= damage;
		}else {
			p.damageDone += damage;
		}
		if (damage > 0 && blood) {
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
			
			double difference = health * -1;
			p.damageDone -= difference;
			
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

			world.gameType.playerDied(this,p);
			PlayerStats.setPlayerStats(className, this);
			//System.out.println("Flag's point: " + flag.getPoint() + " Player's point: " + point);

			health = PlayerStats.EXPLOSIVES_HEALTH;
			minesPlaced = 0;
			
			super.damageDone = 0;
			super.damageDone = 0;
			
			
		}

		
	}
	
}
