package gameobjects.Players;

import java.util.ArrayList;

import gameobjects.Block;
import gameobjects.World;
import gameobjects.projectiles.Bullet;
import gameobjects.projectiles.Tracer;
import geometry.Circle;
import geometry.CollisionDetector;
import geometry.Coordinate;
import utility.Utility;
import visuals.Sound;

public class Assault extends Player{

	
	public Assault(int num, int teamColor, World w) {
		super(num, teamColor, w, "assault");
		
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
										
										//	p.loseHealth(damage);
											if(!super.inSpecial) {
												tripleFire(p);
												//System.out.println("firing three");
											}
											else {
												singleFire(p);
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
	
	public void tripleFire(Player p) {
		super.world.addProjectile(new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
		super.world.addProjectile(new Tracer(new Coordinate(point.getX() - Block.BLOCK_SIZE / 2 * Math.cos(rotation), point.getY()+ Block.BLOCK_SIZE / 2 * Math.sin(rotation)), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
		super.world.addProjectile(new Tracer(new Coordinate(point.getX() + Block.BLOCK_SIZE / 2 * Math.cos(rotation) , point.getY()- Block.BLOCK_SIZE / 2 * Math.sin(rotation)), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
		Sound.assaultBurst.play();
	}
	
	public void singleFire(Player p) {
		super.world.addProjectile(new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
		Sound.assaultAutomatic.play();
	}
	
	public boolean changeSpecial(boolean b, boolean override) {
		boolean previousState = super.inSpecial;
		if (super.changeSpecial(b,override)) {
			if (previousState == true) {//was in special, so change back to normal stats
				super.damage = PlayerStats.ASSAULT_DAMAGE;
				super.fireRate = PlayerStats.ASSAULT_FIRE_RATE;
				super.range = PlayerStats.ASSAULT_RANGE;
				super.accuracy = PlayerStats.ASSAULT_ACCURACY;
				super.lateralRange = PlayerStats.ASSAULT_LATERAL_RANGE;
			}
			else { //wasn't in special
				super.damage = PlayerStats.ASSAULT_S_DAMAGE;
				super.fireRate = PlayerStats.ASSAULT_S_FIRE_RATE;
				super.range = PlayerStats.ASSAULT_S_RANGE;
				super.accuracy = PlayerStats.ASSAULT_S_ACCURACY;
				super.lateralRange = PlayerStats.ASSAULT_S_LATERAL_RANGE;
			}
			return true;
		}
		return false;
	}

}
