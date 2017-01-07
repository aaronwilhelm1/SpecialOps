package gameobjects.Players;

import java.util.ArrayList;

import gameobjects.World;
import gameobjects.projectiles.Bullet;
import gameobjects.projectiles.Grenade;
import gameobjects.projectiles.Projectile;
import gameobjects.projectiles.Syringe;
import gameobjects.projectiles.Tracer;
import geometry.Circle;
import geometry.CollisionDetector;
import geometry.Coordinate;
import utility.Utility;
import visuals.Sound;



public class Medic extends Player{

	private long lastHeal;//time of last self heal
	private double healRate;//rate of self heal in milliseconds
	
	public Medic(int num, int teamColor, World w) {
		super(num, teamColor, w, "medic");
		PlayerStats.setPlayerStats(className, this);
		
		lastHeal = System.currentTimeMillis();
		healRate = PlayerStats.MEDIC_SELF_HEAL_RATE;
		
	}

	
	public void fire(ArrayList<Player> firingAt) {
		if (super.inSpecial == true) {
			areaHeal(firingAt);
			if(Sound.healingAura.isActive() == false){
				Sound.healingAura.loop();
			}
		}
		else {
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
										super.world.addProjectile(new Syringe(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
											lastFire = System.currentTimeMillis();
											Sound.medicGun.play();
										
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
	
	public ArrayList<Player> lateralAimAndInRange(ArrayList<Player> pList) {//returns null if no players able to be shot; otherwise returns player to be shot
		ArrayList<Player> playersInLateralRange = new ArrayList<Player>();
		for (Player p:pList) {//First determine players who are in lateralRange
			if (!p.equals(this)){
				double angleDifference = Math.abs(rotation - point.getAngleBetween(p.getPoint()));
				if (angleDifference < lateralRange || (2 * Math.PI) - angleDifference < lateralRange) {
					if (p.getPoint().getDistanceBetween(point) < range) {
						if ((p.getTeam() == team && p.getHealth() < (PlayerStats.getPlayerMaxHealth(p)) * PlayerStats.OVERHEAL_RATIO) && p instanceof Sentry == false || p.getTeam() != team ){//is player on opposing team OR is player on same team and doesn't have full health
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
	
	public void areaHeal(ArrayList<Player> firingAt) {
		//System.out.println("area heal " + firingAt.size());

		if (!CollisionDetector.areColliding(body, world.blueSpawn)) {//checks to see if the shooter is in top left spawn zone
			if (!CollisionDetector.areColliding(body, world.greenSpawn)) {//checks to see if the shooter is in bottom right spawn zone
				if (firingAt != null){
					int numHealing = 0;
					ArrayList<Player> healing = new ArrayList<Player>();
					for (Player p: firingAt) {
						if (p != null) {
							if (System.currentTimeMillis() - lastFire > fireRate) {
								if (!CollisionDetector.areColliding(p.getCircle(), world.blueSpawn)) {//checks if in top left spawn zone
									if (!CollisionDetector.areColliding(p.getCircle(), world.greenSpawn)) {//checks if in bottom right spawn zone
										/*super.world.addBullet(new Bullet(this.point.getX(), this.point.getY(), Math.PI*2 * Math.random(),
										Math.random()*Math.PI*2, (Math.random()*(Bullet.MAX_LINSPEED - Bullet.MIN_LINSPEED)) + Bullet.MIN_LINSPEED, Math.random()*Bullet.MAX_ANGSPEED));
										super.world.addProjectile(new Syringe(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
										lastFire = System.currentTimeMillis();*/
										if (p.getTeam() == team && p.getHealth() < PlayerStats.getPlayerMaxHealth(p) * PlayerStats.OVERHEAL_RATIO) {
											numHealing++;
											healing.add(p);
										}
										
									}
		
								}
								
							}
						}
					}
					if (healing.size() != 0){
						//System.out.println("healing " + healing.size());
						double amountHealed = (damage + (damage * super.score / 7)) / numHealing;
						for (Player p: healing) {
							p.loseHealth(amountHealed * -1, null,this,true);
						}
						lastFire = System.currentTimeMillis();
					}
				}
			}
		}
	}
	
	public boolean changeSpecial(boolean b, boolean override) {
		boolean previousState = super.inSpecial;
		if (super.changeSpecial(b,override)) {
		if (previousState == true) {//was in special, so change back to normal stats
			Sound.healingAura.stop();
			super.damage = PlayerStats.MEDIC_DAMAGE;
			super.fireRate = PlayerStats.MEDIC_FIRE_RATE;
			super.range = PlayerStats.MEDIC_RANGE;
			super.accuracy = PlayerStats.MEDIC_ACCURACY;
			super.lateralRange = PlayerStats.MEDIC_LATERAL_RANGE;
			super.speed = PlayerStats.MEDIC_SPEED;
			super.rotateAmount = PlayerStats.MEDIC_ROTATE_AMOUNT;
		}
		else { //wasn't in special
			Sound.healingAura.loop();
			super.damage = PlayerStats.MEDIC_S_DAMAGE;
			super.fireRate = PlayerStats.MEDIC_S_FIRE_RATE;
			super.range = PlayerStats.MEDIC_S_RANGE;
			super.accuracy = PlayerStats.MEDIC_S_ACCURACY;
			super.lateralRange = PlayerStats.MEDIC_S_LATERAL_RANGE;
			super.speed = PlayerStats.MEDIC_S_SPEED;
			super.rotateAmount = PlayerStats.MEDIC_S_ROTATE_AMOUNT;

		}
		return true;
		}
		return false;
	}
	
	public void selfHeal() {
		if (inSpecial == true) {
			if (System.currentTimeMillis() - lastHeal > healRate) {
				loseHealth(PlayerStats.MEDIC_SELF_HEAL_AMOUNT * -1,null,this,true);
				lastHeal = System.currentTimeMillis();
			}
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
	
	public void loseHealth(double damage, Projectile projectile, Player p,boolean blood) {
		super.loseHealth(damage, projectile,p,blood);
		if (health <= 0) {
			Sound.healingAura.stop();
		}
	}
}
