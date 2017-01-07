package gameobjects;

import gameobjects.Players.Player;
import geometry.Coordinate;
import geometry.Shape;

public abstract class Trap extends GameObject{

	protected long timerLength;
	protected long timeSet;
	protected long timeTriggered;
	protected long fuseLength;
	protected int team;
	protected boolean isTriggered;
	protected Player self;
	
	public Trap(Coordinate p, Shape s, World w, int t, Player pl) {
		super(p, s, w);
		timeSet = System.currentTimeMillis();
		team = t;
		isTriggered = false;
		timeTriggered = Long.MAX_VALUE;
		self = pl;
	}
	
	@Override
	public void haveInteraction(Player p) {
		super.haveInteraction(p);
	}
	
	public boolean checkTrapTimer() {
		return false;
	}
	
	public int getTeam() {
		return team;
	}
	
	public boolean isTriggered(){
		return isTriggered;
	}
	
	public void setTimeTriggered() {
		timeTriggered = System.currentTimeMillis();
		isTriggered = true;
		super.setIsVisible(true);
	}
}
