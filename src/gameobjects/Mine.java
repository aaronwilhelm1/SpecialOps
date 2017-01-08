package gameobjects;

import java.awt.Image;
import java.awt.geom.Line2D;

import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import geometry.Coordinate;
import geometry.Shape;
import visuals.Animation;
import visuals.AnimationLoader;
import visuals.ImageLoader;
import visuals.Sound;

public class Mine extends Trap{

	private Animation plantAnimation;
	private Animation detonateAnimation;
	
	
	public Mine(Coordinate p, Shape s, World w, int t, Player pl) {
		
		super(p, s, w, t,pl);
		super.setIsVisible(true);
		
		super.timerLength = PlayerStats.MINE_TIMER;
		super.fuseLength = PlayerStats.MINE_FUSE;
		
		setImages();
	}
	
	public void haveInteraction(Player p) {//checks all players in surrounding area
		world.addExplosion(getPoint(), "mine", self);
		Sound.explosion.play();
		
		
		for (Player player: super.world.playerList) {
			if (player.getTeam() != team && player.getPoint().getDistanceBetween(super.getPoint()) < PlayerStats.MINE_EXPLOSION_RADIUS + (PlayerStats.MINE_EXPLOSION_RADIUS * self.score / 10)) {
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
					player.loseHealth(PlayerStats.MINE_DAMAGE, null,self,true);
				}

			}
		}
		
		//super.world.removeTrap(this);
		

	}

	@Override
	public void setImages() {
		plantAnimation = AnimationLoader.getMinePlant();
		//detonateAnimation = AnimationLoader.getMineDetonate();
	}

	@Override
	public Image getImage() {
		if(isTriggered){
			if(detonateAnimation == null){//like this so mine detonation animation doesn't begin early
				detonateAnimation = AnimationLoader.getMineDetonate();
			}
			return detonateAnimation.getCurrentImage();
		} else {
			return plantAnimation.getCurrentImage();
		}
	}

	@Override
	public Coordinate getCoordinates() {
		return new Coordinate(super.getPoint().getX() - PlayerStats.MINE_RADIUS, super.getPoint().getY() - PlayerStats.MINE_RADIUS);
	}

	@Override
	public double getRotation() {
		return 0;
	}

	@Override
	public boolean needsToFlip() {
		return false;
	}
	
	public boolean checkTrapTimer() {
		if (isTriggered && System.currentTimeMillis() - super.timeTriggered > PlayerStats.MINE_FUSE) {
			//a dummy player to make sure haveINteraction(player) is called
			haveInteraction(null);
			return true;
		}  
		else if (System.currentTimeMillis() - super.timeSet > super.timerLength && isTriggered == false) {
			super.setIsVisible(false);
		}
		return false;
	}
	
	@Override
	public void setTimeTriggered(){
		super.setTimeTriggered();
		Sound.beeping.play();
	}

}
