package gameobjects.projectiles;

import java.awt.Image;
import java.awt.geom.Line2D;

import gameobjects.Block;
import gameobjects.World;
import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import geometry.Circle;
import geometry.Coordinate;
import visuals.ImageLoader;
import visuals.Sound;

public class Grenade extends Projectile{

	boolean isCritical;
	private Image image;
	public static final double GRENADE_RADIUS = Block.BLOCK_SIZE * .1;
	
	public Grenade(Coordinate p, Circle s, double r, String player, int t, Player sel, Coordinate startingPoint,
			World w) {
		super(p, s, r, player, t, sel, startingPoint, w);
		
		if (Math.random() < self.criticalChance) {
			isCritical = true;
		}
		if (isCritical) {
			super.damage = super.damage * 2;
		}
		
		setImages();
	}
	
	public void haveCollision(Block b) {
		explode();
	}
	
	public void haveCollision(Player p) {
		explode();
	}
	
	public void explode() {
		world.addExplosion(getPoint(), "grenade", self);
		Sound.explosion.play();
		
		for (Player player: super.world.playerList) {
			if (player.getTeam() != team && player.getPoint().getDistanceBetween(super.getPoint()) < PlayerStats.GRENADE_EXPLOSION_RADIUS + (self.score * PlayerStats.GRENADE_EXPLOSION_RADIUS / 10)) {
				boolean canAddPlayertoList = true;

				for (Block b: super.world.blockList) {//check to see if going through block

					java.awt.geom.Rectangle2D rect = b.getJavaRectangle();
					java.awt.geom.Line2D line = new Line2D.Double(player.getPoint().getX(),player.getPoint().getY(),super.getPoint().getX(),super.getPoint().getY());
					if (line.intersects(rect)) {
						canAddPlayertoList = false;
						break;
					}

				}

				if (canAddPlayertoList == true) {//if player can be hit at directly
					double ratio = (PlayerStats.GRENADE_EXPLOSION_RADIUS - player.getPoint().getDistanceBetween(super.point)) / PlayerStats.GRENADE_EXPLOSION_RADIUS;
					double damage = PlayerStats.EXPLOSIVES_DAMAGE * ratio;
					if (damage < PlayerStats.EXPLOSIVES_MIN_DAMAGE) {
						damage = PlayerStats.EXPLOSIVES_MIN_DAMAGE;
					}
					
					player.loseHealth(damage, null,self,true);
				}

			}
		}
		
		
	}

	@Override
	public void setImages() {
		if(isCritical){
			image = ImageLoader.getCritTracerImage();
		} else{

			image = ImageLoader.getGrenadeImage();
		}
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public Coordinate getCoordinates() {
		/*//TRANSLATE TO ORIGIN
				double x1 = -Player.PLAYER_RADIUS;
				double y1 = -Player.PLAYER_RADIUS;

				//APPLY ROTATION
				double temp_x1 = x1 * Math.cos(-rotation) - y1 * Math.sin(-rotation);
				double temp_y1 = x1 * Math.sin(-rotation) + y1 * Math.cos(-rotation);

				//TRANSLATE BACK
				return new Coordinate(temp_x1 + getCircle().getCenter().getX(), temp_y1 + getCircle().getCenter().getY());*/
		Circle circle = getCircle();
		return new Coordinate(circle.getCenter().getX() - circle.getRadius(), circle.getCenter().getY() - circle.getRadius());
	}

	@Override
	public double getRotation() {
		return 0;
	}

	@Override
	public boolean needsToFlip() {
		return false;
	}

}
