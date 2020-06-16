package gameobjects.Players;

import java.util.ArrayList;

import gameobjects.World;
import gameobjects.projectiles.Bullet;
import gameobjects.projectiles.Tracer;
import geometry.Circle;
import geometry.CollisionDetector;
import geometry.Coordinate;
import utility.Utility;
import visuals.Sound;

public class Heavy extends Player{

	public Heavy(int num, int teamColor, World w) {
		super(num, teamColor, w,"heavy");
		
		PlayerStats.setPlayerStats(className, this);
	}
	
	public void fire(ArrayList<Player> firingAt) {
		if (!CollisionDetector.areColliding(body, world.blueSpawn)) {//checks to see if the shooter is in top left spawn zone
			if (!CollisionDetector.areColliding(body, world.greenSpawn)) {//checks to see if the shooter is in bottom right spawn zone
				if (firingAt != null){
					for (Player p: firingAt) {
						if (p != null) {
							if (System.currentTimeMillis() - lastFire > fireRate - (fireRate * super.score / (15 + super.score))) {
								if (!CollisionDetector.areColliding(p.getCircle(), world.blueSpawn)) {//checks if in top left spawn zone
									if (!CollisionDetector.areColliding(p.getCircle(), world.greenSpawn)) {//checks if in bottom right spawn zone
										super.world.addBullet(new Bullet(this.point.getX(), this.point.getY(), Math.PI*2 * Math.random(),
										Math.random()*Math.PI*2, (Math.random()*(Bullet.MAX_LINSPEED - Bullet.MIN_LINSPEED)) + Bullet.MIN_LINSPEED, Math.random()*Bullet.MAX_ANGSPEED));
										
										//	p.loseHealth(damage);
											super.world.addProjectile(new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy - (accuracy * super.score / (15 + super.score))) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
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
	
	
	
	public boolean changeSpecial(boolean b, boolean override) {
		boolean previousState = super.inSpecial;
		if (super.changeSpecial(b,override)) {
			if (previousState == true) {//was in special, so change back to normal stats
				super.accuracy = PlayerStats.HEAVY_ACCURACY;
				super.range = PlayerStats.HEAVY_RANGE;
			}
			else { //wasn't in special
				super.accuracy = PlayerStats.HEAVY_S_ACCURACY;
				super.range = PlayerStats.HEAVY_S_RANGE;
			}
			return false;
		}
		return true;
	}
	
	public void moveX(){
		if(!super.inSpecial){
			super.moveX();
		}
	}
	
	public void moveY(){
		if(!super.inSpecial){
			super.moveY();
		}
	}
	
}
