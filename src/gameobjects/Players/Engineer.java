package gameobjects.Players;

import java.util.ArrayList;

import gameobjects.Block;
import gameobjects.Mine;
import gameobjects.World;
import gameobjects.projectiles.Bullet;
import gameobjects.projectiles.Tracer;
import geometry.Circle;
import geometry.CollisionDetector;
import geometry.Coordinate;
import utility.Utility;
import visuals.Sound;

public class Engineer extends Player{

	public Sentry sentry;
	private boolean isRepairing;
	private double repairRate;
	private long lastRepair;

	public Engineer(int num, int teamColor, World w) {
		super(num, teamColor, w, "engineer");

		PlayerStats.setPlayerStats(className, this);
		sentry = null;
		isRepairing = false;
		lastRepair = 0;
		repairRate = PlayerStats.SENTRY_REPAIR_RATE;
	}

	public void fire(ArrayList<Player> firingAt) {
		checkRepairing();

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
		//super.world.addProjectile(new Tracer(new Coordinate(point.getX() + Block.BLOCK_SIZE / 2 * Math.cos(rotation) , point.getY()- Block.BLOCK_SIZE / 2 * Math.sin(rotation)), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
		Sound.assaultBurst.play();
	}

	public void singleFire(Player p) {
		super.world.addProjectile(new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world));
	}


	public boolean changeSpecial(boolean b, boolean override) {

		if (super.changeSpecial(true, false)) {

			synchronized(world.playerList){
				super.inSpecial = false;
				/*Coordinate c = new Coordinate(super.point.getX(), super.point.getY());

				Sentry s = null;

				if (team == 0) {
					s = new Sentry(100,super.team,world,this);
				}
				else if (team == 1) {
					s = new Sentry(101,super.team,world,this);

				}

				if (sentry != null) {
					world.playerList.remove(sentry);//removes old one
				}

				sentry = s;
				world.playerList.add(sentry);
				sentry.point = c;
				sentry.rotation = super.getRotation();

				sentry.originalRotation = super.getRotation();
				sentry.body = new Circle(sentry.point,Player.PLAYER_RADIUS);*/

				return true;
			}
			
		}
		return false;
	}

	public boolean isRepairing(){
		return isRepairing;
	}

	public void checkRepairing() {
		isRepairing = false;
		if (sentry != null && super.getPoint().getDistanceBetween(sentry.getPoint()) < PlayerStats.SENTRY_REPAIR_RANGE
				&& sentry.getHealth() < PlayerStats.SENTRY_HEALTH) {
			isRepairing = true;
			if (System.currentTimeMillis() - lastRepair > repairRate) {
				lastRepair = System.currentTimeMillis();
				isRepairing = true;
				sentry.loseHealth(PlayerStats.SENTRY_REPAIR_AMOUNT * -1, null,this,true);
				Sound.engineerRepair.play();
			}
		}
		else {
			for (Player p: world.playerList) {
				if (p instanceof Sentry && p.getTeam() == team) {
					if (super.getPoint().getDistanceBetween(p.getPoint()) < PlayerStats.SENTRY_REPAIR_RANGE
							&& p.getHealth() < PlayerStats.SENTRY_HEALTH) {
						isRepairing = true;
						if (System.currentTimeMillis() - lastRepair > repairRate) {
							lastRepair = System.currentTimeMillis();
							p.loseHealth(PlayerStats.SENTRY_REPAIR_AMOUNT * -1, null,this,true);
							break;
						}
					}
				}
			}
		}
		/*else {
				isRepairing = false;
			}
		}
		else {
			isRepairing = false;
		}*/
	}

}
