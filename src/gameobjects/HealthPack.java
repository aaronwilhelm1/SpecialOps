package gameobjects;

import java.awt.Image;

import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import geometry.Coordinate;
import geometry.Rectangle;
import visuals.ImageLoader;



public class HealthPack extends GameObject{
	
	private Image image;
	public static final int WIDTH = (int) (.75 * Block.BLOCK_SIZE);
	public static final int HEIGHT = (int) (.75 * Block.BLOCK_SIZE);
	private Rectangle rect;
	private long isVisibleTimer;
	private Coordinate point;
	
	public HealthPack(Coordinate p, Rectangle r, World w){
		super(p, r , w);
		point = p;
		rect = r;
		setImages();
	}
	


	@Override
	public void setImages() {
		image = ImageLoader.getHealthImage();
		
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public Coordinate getCoordinates() {
		return rect.getCoords()[0];
	}

	@Override
	public double getRotation() {
		return 0;
	}

	@Override
	public boolean needsToFlip() {
		return false;
	}
	
	public void haveInteraction(Player p) {
		if (super.getIsVisible() == true) {
			if (p.getClassName().equals("scout")) {
				if (p.getHealth() < PlayerStats.SCOUT_HEALTH - 1) {
					p.loseHealth(- PlayerStats.SCOUT_HEALTH / 2,null,new Player(0, 0, world, "assault"),true);
					super.setIsVisible(false);
					isVisibleTimer = System.currentTimeMillis();	
					if (p.getHealth() > PlayerStats.getPlayerMaxHealth(p)) {//checks to make sure not being overhealed
						p.health = PlayerStats.getPlayerMaxHealth(p);
					}
				}
			}
			else if (p.getClassName().equals("heavy")) {
				if (p.getHealth() < PlayerStats.HEAVY_HEALTH - 1) {
					p.loseHealth(- PlayerStats.HEAVY_HEALTH / 2,null,new Player(0, 0, world, "assault"),true);
					isVisibleTimer = System.currentTimeMillis();
					super.setIsVisible(false);	
					if (p.getHealth() > PlayerStats.getPlayerMaxHealth(p)) {//checks to make sure not being overhealed
						p.health = PlayerStats.getPlayerMaxHealth(p);
					}
				}
				
			}
			else if (p.getClassName().equals("assault")) {
				if (p.getHealth() < PlayerStats.ASSAULT_HEALTH - 1) {
					p.loseHealth(- PlayerStats.ASSAULT_HEALTH / 2,null,new Player(0, 0, world, "assault"),true);
					super.setIsVisible(false);
					isVisibleTimer = System.currentTimeMillis();	
					if (p.getHealth() > PlayerStats.getPlayerMaxHealth(p)) {//checks to make sure not being overhealed
						p.health = PlayerStats.getPlayerMaxHealth(p);
					}
				}
			}
			else if (p.getClassName().equals("sniper")) {
				if (p.getHealth() < PlayerStats.SNIPER_HEALTH - 1) {
					p.loseHealth(- PlayerStats.SNIPER_HEALTH / 2,null,new Player(0, 0, world, "assault"),true);
					super.setIsVisible(false);
					isVisibleTimer = System.currentTimeMillis();	
					if (p.getHealth() > PlayerStats.getPlayerMaxHealth(p)) {//checks to make sure not being overhealed
						p.health = PlayerStats.getPlayerMaxHealth(p);
					}
				}
			}
			else if (p.getClassName().equals("shield")) {
				if (p.getHealth() < PlayerStats.SHIELD_HEALTH - 1) {
					p.loseHealth(- PlayerStats.SHIELD_HEALTH / 2,null,new Player(0, 0, world, "assault"),true);
					super.setIsVisible(false);
					isVisibleTimer = System.currentTimeMillis();	
					if (p.getHealth() > PlayerStats.getPlayerMaxHealth(p)) {//checks to make sure not being overhealed
						p.health = PlayerStats.getPlayerMaxHealth(p);
					}
				}
			}
			else if (p.getClassName().equals("explosives")) {
				if (p.getHealth() < PlayerStats.EXPLOSIVES_HEALTH - 1) {
					p.loseHealth(- PlayerStats.EXPLOSIVES_HEALTH / 2,null,new Player(0, 0, world, "assault"),true);
					super.setIsVisible(false);
					isVisibleTimer = System.currentTimeMillis();	
					if (p.getHealth() > PlayerStats.getPlayerMaxHealth(p)) {//checks to make sure not being overhealed
						p.health = PlayerStats.getPlayerMaxHealth(p);
					}
				}
			}
			else if (p.getClassName().equals("medic")) {
				if (p.getHealth() < PlayerStats.MEDIC_HEALTH - 1) {
					p.loseHealth(- PlayerStats.MEDIC_HEALTH / 2,null,new Player(0, 0, world, "assault"),true);
					super.setIsVisible(false);
					isVisibleTimer = System.currentTimeMillis();	
					if (p.getHealth() > PlayerStats.getPlayerMaxHealth(p)) {//checks to make sure not being overhealed
						p.health = PlayerStats.getPlayerMaxHealth(p);
					}
				}
			}
			else if (p.getClassName().equals("spy")) {
				if (p.getHealth() < PlayerStats.SPY_HEALTH - 1) {
					p.loseHealth(- PlayerStats.SPY_HEALTH / 2,null,new Player(0, 0, world, "assault"),true);
					super.setIsVisible(false);
					isVisibleTimer = System.currentTimeMillis();	
					if (p.getHealth() > PlayerStats.getPlayerMaxHealth(p)) {//checks to make sure not being overhealed
						p.health = PlayerStats.getPlayerMaxHealth(p);
					}
				}
			}
			else if (p.getClassName().equals("engineer")) {
				if (p.getHealth() < PlayerStats.ENGINEER_HEALTH - 1) {
					p.loseHealth(- PlayerStats.ENGINEER_HEALTH / 2,null,new Player(0, 0, world, "assault"),true);
					super.setIsVisible(false);
					isVisibleTimer = System.currentTimeMillis();	
					if (p.getHealth() > PlayerStats.getPlayerMaxHealth(p)) {//checks to make sure not being overhealed
						p.health = PlayerStats.getPlayerMaxHealth(p);
					}
				}
			}

		}

	}
	
	public void checkHealthPackTimer() {
		if (System.currentTimeMillis() - isVisibleTimer > 10000) {
			super.setIsVisible(true);
		}
	}

	
}
