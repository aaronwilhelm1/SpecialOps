package gameobjects.Players;

import java.util.ArrayList;

import gameobjects.World;
import gameobjects.projectiles.Bullet;
import gameobjects.projectiles.Projectile;
import gameobjects.projectiles.Tracer;
import geometry.Circle;
import geometry.CollisionDetector;
import geometry.Coordinate;
import utility.Utility;
import visuals.Sound;

public class Sniper extends Player{

	public Sniper(int num, int teamColor, World w) {
		super(num, teamColor, w, "sniper");
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
										world.addBullet(new Bullet(this.point.getX(), this.point.getY(), Math.PI*2 * Math.random(),
										Math.random()*Math.PI*2, (Math.random()*(Bullet.MAX_LINSPEED - Bullet.MIN_LINSPEED)) + Bullet.MIN_LINSPEED, Math.random()*Bullet.MAX_ANGSPEED));
										
										//	p.loseHealth(damage);

										Projectile proj = new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), className, team, this, new Coordinate(point.getX(), point.getY()), world);
										
										
											world.addProjectile(proj);
											lastFire = System.currentTimeMillis();
											
											if (super.inSpecial != true){
												world.addTrailProjectile(proj);
												Sound.sniper.play();
											}
											else {
												Sound.submachine.play();
											}
											
										
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
		boolean previousState = super.inSpecial;
		if (super.changeSpecial(b,override)){
		if (previousState == true) {//was in special, so change back to normal stats
			super.damage = PlayerStats.SNIPER_DAMAGE;
			super.fireRate = PlayerStats.SNIPER_FIRE_RATE;
			super.range = PlayerStats.SNIPER_RANGE;
			super.accuracy = PlayerStats.SNIPER_ACCURACY;
			super.lateralRange = PlayerStats.SNIPER_LATERAL_RANGE;
			super.speed = PlayerStats.SNIPER_SPEED;
			super.rotateAmount = PlayerStats.SNIPER_ROTATE_AMOUNT;
		}
		else { //wasn't in special
			super.damage = PlayerStats.SNIPER_S_DAMAGE;
			super.fireRate = PlayerStats.SNIPER_S_FIRE_RATE;
			super.range = PlayerStats.SNIPER_S_RANGE;
			super.accuracy = PlayerStats.SNIPER_S_ACCURACY;
			super.lateralRange = PlayerStats.SNIPER_S_LATERAL_RANGE;
			super.speed = PlayerStats.SNIPER_S_SPEED;
			super.rotateAmount = PlayerStats.SNIPER_S_ROTATE_AMOUNT;

		}return true;
		}
		return false;
	}
	
}
