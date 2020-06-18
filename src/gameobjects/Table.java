package gameobjects;

import java.awt.Image;

import gameobjects.Players.Player;
import gametype.CTF;
import geometry.Coordinate;
import geometry.Rectangle;
import visuals.ImageLoader;
import visuals.Sound;

public class Table extends GameObject{

	private Rectangle rect;
	private boolean hasFlag;
	private Image[] images;
	private CTF ctf;
	private int team;
	
	public Table(Coordinate p, Rectangle r, int teamColor, CTF c, World w) {
		super(p,r, w);
		rect = r;
		hasFlag = true;
		team = teamColor;
		super.setIsVisible(true);
		ctf = c;
		setImages();
	}
	
	public void haveInteraction(Player p) {
		if (p.getTeam() != team) {//touched by different team
			hasFlag = false;
		}
		else if (p.getTeam() == team) {//touched by same team
			//check to see if that player has other flag
			if (p.getHasFlag() == true) {//checks if this table has a flag
				if (hasFlag) {
					p.setHasFlag(false);
					//System.out.println("same team, that's all");
					p.getFlag().returnFlag();
					//set the other table has flag to true
					ctf.getTable(p.getFlag().getTeam()).setHasFlag(true);
					ctf.addToScore(p.getTeam(), 1);
					p.score++;
					p.totalScore++;
					Sound.captureFlag.play();
					//p.setFlag(null);
				}

			}
		}
		

	}

	@Override
	public void setImages() {
		images = ImageLoader.getBriefcaseTableImages(team);
		
	}

	@Override
	public Image getImage() {
		if(this.hasFlag){
			return images[0];
		} else {
			return images[1];
		}
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
	
	public void setHasFlag(boolean b) {
		hasFlag = b;
	}
	
	public boolean getHasFlag(){
		return hasFlag;
	}
}
