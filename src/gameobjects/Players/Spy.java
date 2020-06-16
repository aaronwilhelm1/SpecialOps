package gameobjects.Players;

import java.awt.Image;
import java.util.ArrayList;

import gameobjects.World;
import gameobjects.projectiles.Bullet;
import gameobjects.projectiles.Tracer;
import geometry.Circle;
import geometry.CollisionDetector;
import geometry.Coordinate;
import utility.Utility;
import visuals.AnimationLoader;
import visuals.ImageLoader;
import visuals.Sound;

public class Spy extends Player{
	public boolean isDisguised;
	String disguise;
	
	public Spy(int num, int teamColor, World w) {
		super(num, teamColor, w, "spy");
		PlayerStats.setPlayerStats(className, this);
		isDisguised = false;
	}
	
	@Override
	public void setImages() {
		super.images = ImageLoader.getPlayerImage(className, team);
		walkAnimation = AnimationLoader.getWalking(className, team);
		/*gWalkAnimation = AnimationLoader.getShieldGWalking(team);
		sWalkAnimation = AnimationLoader.getShieldSWalking(team);*/
	}
	
	@Override
	public Image getImage() {
		/*if(!super.isStanding){
			if(!super.inSpecial){
				return gWalkAnimation.getCurrentImage();
			} else {

				return sWalkAnimation.getCurrentImage();
			}
		}*/
		if(!super.inSpecial){

			return super.images[0];
		} else {
			if(isDisguised){
				return super.images[0];
			} else {
				return ImageLoader.getbSpyShadow();
			}
			
			//return super.images[5];
		}
	}
	
	public void fire(ArrayList<Player> firingAt) {
		if (inSpecial == false) {
		if (!CollisionDetector.areColliding(body, world.blueSpawn)) {//checks to see if the shooter is in top left spawn zone
			if (!CollisionDetector.areColliding(body, world.greenSpawn)) {//checks to see if the shooter is in bottom right spawn zone
				if (firingAt != null){
					for (Player p: firingAt) {
						if (p != null) {
							if (System.currentTimeMillis() - lastFire > fireRate) {
								if (!CollisionDetector.areColliding(p.getCircle(), world.blueSpawn)) {//checks if in top left spawn zone
									if (!CollisionDetector.areColliding(p.getCircle(), world.greenSpawn)) {//checks if in bottom right spawn zone
										
										if (super.inSpecial == true) {
											p.loseHealth(damage,null,this,true/*new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), 0), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world)*/);
										}
										else {
											
											super.world.addBullet(new Bullet(this.point.getX(), this.point.getY(), Math.PI*2 * Math.random(),
											Math.random()*Math.PI*2, (Math.random()*(Bullet.MAX_LINSPEED - Bullet.MIN_LINSPEED)) + Bullet.MIN_LINSPEED, Math.random()*Bullet.MAX_ANGSPEED));
											
											//	p.loseHealth(damage);
											
											Tracer t = new Tracer(new Coordinate(point.getX(), point.getY()), new Circle(point, (int)Tracer.TRACER_RADIUS), Utility.getRandomInRange(point.getAngleBetween(p.getPoint()), (1 - accuracy) * lateralRange), super.className, super.team, this, new Coordinate(point.getX(), point.getY()), world);
											t.damage = determineDamage(p);
											super.world.addProjectile(t);
											
										}
										lastFire = System.currentTimeMillis();
										Sound.pistol.play();
											
										
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
	
	public double determineDamage(Player p) {//need to pass in bullet rotation
	
			double oppositeAngle = p.rotation;/*projectile.rotation - Math.PI;/*
			if (oppositeAngle < 0) {
				oppositeAngle = projectile.rotation + Math.PI;
			}*/
		

			double angleDifference = Math.abs(super.point.getAngleBetween(p.getPoint()) - (oppositeAngle));

			if (angleDifference < Math.PI / 4 || (2 * Math.PI) - angleDifference < Math.PI / 4)//from behind!!!
			{
				if (p.getPoint().getDistanceBetween(super.point) < PlayerStats.SPY_STAB_RANGE) {
					return p.getHealth();
				}
				else {
					return 3 * PlayerStats.SPY_DAMAGE + (PlayerStats.SPY_DAMAGE * super.score / 10);
				}
			}
			else if (angleDifference < 3 * Math.PI / 4 || (2 * Math.PI) - angleDifference < 3 * Math.PI / 4){//from the side
				return  2 * PlayerStats.SPY_DAMAGE + (PlayerStats.SPY_DAMAGE * super.score / 10);
			}
			else {
				return PlayerStats.SPY_DAMAGE+ (PlayerStats.SPY_DAMAGE * super.score / 10);
			}
				
		
	}
	
	/*public void takeDamage(double d, Player p) {
		
		System.out.println("spy take damage method called");
	
		health = health - d;
		if (damage < 0) {
			p.damageDone -= damage;
		}else {
			p.damageDone += damage;
		}
		
		if (d > 0) {
		world.addBlood(new Blood(getPoint().getX(), getPoint().getY(), Math.PI*2 * Math.random(),
				Math.random()*Math.PI*2, (Math.random()*(Blood.MAX_LINSPEED - Blood.MIN_LINSPEED)) + Blood.MIN_LINSPEED, 0));
		}
		//System.out.println("Player: " + playerNum + " Health: " + health);
		if (health <= 0) {
			double difference = health * -1;
			p.damageDone -= difference;//makes sure not receiving extra points above damage
			
			deathTime = System.currentTimeMillis();
			isDead = true;
			world.addDeathMark(new Coordinate(point.getX() - (Player.PLAYER_WIDTH/2), point.getY() - (Player.PLAYER_WIDTH/2)), team);

			
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
			world.gameType.playerDied(this);

			//System.out.println("Flag's point: " + flag.getPoint() + " Player's point: " + point);
			
			if (className == "shield") {
				health = PlayerStats.SHIELD_HEALTH;
			}
		}

		if (className == "shield" && health > PlayerStats.SHIELD_HEALTH) {
			health = PlayerStats.SHIELD_HEALTH;
		}
	}*/

	public boolean changeSpecial(boolean b, boolean override) {
		boolean previousState = super.inSpecial;
		boolean canChange = false;
		boolean inTime = false;
		if (hasFlag == false) {
			if (System.currentTimeMillis() - lastSpecialTime > specialLength || override) {
				lastSpecialTime = System.currentTimeMillis();
				//inSpecial = !inSpecial;
				if (override) {
					if (b == true) {//overrides to give spy a disguise
						//TODO: Code does not specify disguise, must do in other method if want specific disguise
						setDisguise("");
						inSpecial = true;
						isDisguised = true;
						return true;
					}
					else if (b == false) {//overrides to take spy out of special				
						removeDisguise();
						inSpecial = false;
						isDisguised = false;
						return true;
					}
				}
				
				if (inSpecial == true) {//was already in special and is calling to change to special again

					if (isDisguised == false) {
						inSpecial = true;
						isDisguised = true;
						setDisguise("");
						canChange = true;
						return true;
					}
					else if (isDisguised == true) {
						removeDisguise();
						inSpecial = false;
						isDisguised = false;
						return true;
					}
				}
				else if (inSpecial == false){//wasn't in special, so trying to get into special
					inSpecial = true;
					removeDisguise();
					isDisguised = false;
					canChange = true;
					Sound.spyCloak.play();
					return true;
				}
			}
			/*
			if (inSpecial != b) {//not going to reset unless actually changes
				if (System.currentTimeMillis() - lastSpecialTime > specialLength || override) {
					//System.out.println("activating special");
					lastSpecialTime = System.currentTimeMillis();
					inSpecial = !inSpecial;
					return true;
				}
			}
			return false;*/
			
		}
		return false;
	}
	
	public void setInSpecial(boolean b) {
		inSpecial = b;
	}
	
	//put in class name for that disguise
	//"" will set a disguise to a random enemy player's class
	public void setDisguise(String newDisguise){
		int opposingTeam = 0;
		if(getTeam() == 0){
			opposingTeam = 1;
		}
		ArrayList<Player> enemies = new ArrayList<Player>();
		for(Player p : world.playerList){
			if(p.getTeam()== opposingTeam){
				enemies.add(p);
			}
		}
		//disguise = "";
		disguise = newDisguise;
		while(disguise.equals("sentry") || disguise.equals("")){
			disguise = enemies.get((int)(Math.random()*enemies.size())).getClassName();
		}
		
		images = ImageLoader.getPlayerImage(disguise, opposingTeam);
		if(disguise.equals("shield")){//do this since the shield is special
			walkAnimation = AnimationLoader.getShieldGWalking(opposingTeam);
		} else {
			walkAnimation = AnimationLoader.getWalking(disguise, opposingTeam);
		}
		
	}
	
	public void removeDisguise(){
		images = ImageLoader.getPlayerImage(getClassName(), team);
		walkAnimation = AnimationLoader.getWalking(getClassName(), team);
	}
	

}
