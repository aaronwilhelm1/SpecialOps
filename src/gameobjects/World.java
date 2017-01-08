package gameobjects;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Timer;

import ai.EnvironmentAnalyzer;
import ai.GoalManager;
import ai.Individual;
import ai.ObstacleMap;
import astar.Path;
import astar.Path.Step;
import gameobjects.Players.Assault;
import gameobjects.Players.Engineer;
import gameobjects.Players.Explosives;
import gameobjects.Players.Heavy;
import gameobjects.Players.Medic;
import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import gameobjects.Players.Scout;
import gameobjects.Players.Sentry;
import gameobjects.Players.Shield;
import gameobjects.Players.Sniper;
import gameobjects.Players.Spy;
import gameobjects.projectiles.Bullet;
import gameobjects.projectiles.Grenade;
import gameobjects.projectiles.Projectile;
import gameobjects.projectiles.Syringe;
import gameobjects.projectiles.Tracer;
import gametype.CTF;
import gametype.GameType;
import gametype.VIP;
import geometry.CollisionDetector;
import geometry.Coordinate;
import geometry.Rectangle;
import geometry.Circle;
import geometry.Shape;
import gui.GUI;
import gui.OurPanel;
import utility.Utility;
import visuals.Animation;
import visuals.AnimationLoader;
import visuals.Drawable;
import visuals.Explosion;
import visuals.ImageLoader;
import visuals.Sound;

public class World{

	Timer timer;
	ArrayList<Shape> objectList;
	public ArrayList<Block> blockList;
	public ArrayList<Player> playerList;
	public Block[][] blockLayout;
	boolean upPressed;
	boolean downPressed;
	boolean leftPressed;
	boolean rightPressed;
	boolean upReleased;
	private OurPanel panel;
	final double playerMoveDelay = 50;
	private enum GameMode {CTF,VIP};
	public GameMode mode;
	public GameType gameType;
	private ArrayList<GameObject> gameTypeExtras;
	private ArrayList<Rectangle> boundaries;
	private ArrayList<Bullet> shells;
	private final int maxNumBullets = 1000;
	private ArrayList<Blood> bloods;
	private final int maxNumBloods = 1000;
	private String winningTeam;
	private ArrayList<Projectile> projectileList;
	private ArrayList<Animation> deathMarkList; 
	private ArrayList<Projectile> trailProjectiles;
	public ArrayList<GoalManager> computers;
	private ArrayList<Trap> trapList;
	private ArrayList<Explosion> explosionList;
	public Rectangle blueSpawn;
	public Rectangle greenSpawn;
	
	public Player topPlayer;

	public EnvironmentAnalyzer ea;

	private GUI gui;

	public String map;

	public World(GUI g, OurPanel p) {
		objectList = new ArrayList<Shape>();
		blockList = new ArrayList<Block>();
		playerList = new ArrayList<Player>();
		shells = new ArrayList<Bullet>(maxNumBullets);
		bloods = new ArrayList<Blood>(maxNumBloods);
		timer = new Timer();
		winningTeam = null;
		projectileList = new ArrayList<Projectile>();
		deathMarkList = new ArrayList<Animation>();
		trailProjectiles = new ArrayList<Projectile>();
		computers = new ArrayList<GoalManager>();
		trapList = new ArrayList<Trap>();
		explosionList = new ArrayList<Explosion>();
		topPlayer = null;
		gui = g;
		panel = p;



		/*	boundaries = new ArrayList<Rectangle>();
	Coordinate[] tl = new Coordinate[4];
	boundaries.add(new Rectangle())*/
	}

	public void buildWorld() {
		String gt = MapSelection.getGTString(MapSelection.pointerGT);

		if (gt.equals("VIP")) {

			mode = GameMode.VIP;
		}
		else if (gt.equals("CTF")) {

			mode = GameMode.CTF;
		}



		map = MapSelection.getMapString(MapSelection.pointer0);

		if (map == "warehouse") {
			blueSpawn  = LevelIndex.getWarehouseSpawns().get(0);
			greenSpawn  = LevelIndex.getWarehouseSpawns().get(1);
		}
		else if (map == "polaris") {
			blueSpawn  = LevelIndex.getPolarisSpawns().get(0);
			greenSpawn  = LevelIndex.getPolarisSpawns().get(1);
		}
		else if (map == "forts") {
			blueSpawn  = LevelIndex.getFortsSpawns().get(0);
			greenSpawn  = LevelIndex.getFortsSpawns().get(1);
		}


		if (mode == GameMode.CTF) {
			gameType = new CTF(this);
		}
		else if (mode == GameMode.VIP) {
			gameType = new VIP(this);
		}
		gameTypeExtras = gameType.getExtras();
		winningTeam = null;

		createMap();
		createPlayers();


		//createAI();



	}

	public void destroyWorld() {
		blockList = null;
		playerList = new ArrayList<Player>();
		gameType = null;
		gameTypeExtras = null;
		gui.state = GUI.State.MAP_SELECTION;
		Sound.menu.loop();
		winningTeam = null;
		bloods = new ArrayList<Blood>();
		shells = new ArrayList<Bullet>();
		projectileList = new ArrayList<Projectile>();
		trailProjectiles = new ArrayList<Projectile>();
		computers = new ArrayList<GoalManager>();
		trapList = new ArrayList<Trap>();
		topPlayer = null;
		ea.destroy();
		ea = null;
		Sound.sentryMoving.stopAllSounds();
	}

	public void updateWorld(){
		checkMovement();
		checkFiring();
		checkSpawn();
		checkProjectiles();
		checkDeathMarks();
		checkTraps();
		checkExplosions();
		gameType.checkGameTypeMechanics(playerList);
	}

	public void createMap() {
		if (map == "warehouse"){
			blockList = LevelIndex.getWarehouseList();
		}
		else if (map == "polaris") {
			blockList = LevelIndex.getPolarisList();
		}
		else if (map == "forts") {
			blockList = LevelIndex.getFortsList();
		}
	}

	public void createPlayers() {
		synchronized(computers){
			synchronized(playerList){
				int blueTeamTotal = 0;
				int greenTeamTotal = 0;
				int computerTotal = 0;

				for (int i = 0; i < PlayerSelection.playersPerTeam * 2; i++) {
					if (PlayerSelection.getTeamPointer(i) == 0){//if on blue team
						blueTeamTotal++;
					}
					else if (PlayerSelection.getTeamPointer(i) == 1) {
						greenTeamTotal++;
					}
					else if (PlayerSelection.getTeamPointer(i) == 2){
						computerTotal++;
					}
				}

				if(computerTotal > 0 || gameType instanceof VIP){
					ea = new EnvironmentAnalyzer(this);
					Thread thread = new Thread(ea);
					thread.start();
				}

				for (int i = 0; i < PlayerSelection.playersPerTeam * 2; i++){

					boolean isComp = false;

					String className = PlayerSelection.getClassString(PlayerSelection.getPointer(i));
					int teamNum = PlayerSelection.getTeamPointer(i);
					Player p = null;


					if (teamNum == 2){//it is a computer 
						isComp = true;
						if (greenTeamTotal > blueTeamTotal) {//assign to smaller team
							teamNum = 0;
							blueTeamTotal++;
						}
						else {
							teamNum = 1;
							greenTeamTotal++;
						}

						className = PlayerSelection.getClassString((int)(Math.random() * 7) + 1);

					}


					if (className.equals("assault")) {
						p = (new Assault(i,teamNum,this));
					}
					else if (className.equals("heavy")) {
						p = new Heavy(i,teamNum,this);
					}
					else if (className.equals("scout")) {
						p = (new Scout(i,teamNum,this));
					}
					else if (className.equals("sniper")) {
						p = (new Sniper(i,teamNum,this));
					}
					else if (className.equals("shield")) {
						p = (new Shield(i,teamNum,this));
					}
					else if (className.equals("explosives")) {
						p = (new Explosives(i,teamNum,this));
					}
					else if (className.equals("medic")) {
						p = (new Medic(i,teamNum,this));
					}
					else if (className.equals("spy")) {
						p = (new Spy(i,teamNum,this));
					}
					else if (className.equals("engineer")) {
						p = new Engineer(i,teamNum,this);
					}

					playerList.add(i,p);

					if (isComp == true) {

						if (map == "warehouse") {
							GoalManager g = new GoalManager(this, p, LevelIndex.getWarehouseTileMap());
							computers.add(g);
						}
						if (map == "polaris") {
							GoalManager g = new GoalManager(this, p, LevelIndex.getPolarisTileMap());
							computers.add(g);
						}
						if (map == "forts") {
							GoalManager g = new GoalManager(this, p, LevelIndex.getFortsTileMap());
							computers.add(g);
						}
					}
				}

			}
		}


	}

	public void checkMovement() {
		synchronized(playerList){
			ArrayList<Player> toBeRemoved = new ArrayList<Player>();

			ListIterator<Player> iter = playerList.listIterator();
			
			while (iter.hasNext()) {
				Player player = iter.next();
				boolean isComp = false;
				Individual ind = null;
				GoalManager g = null;
				for (GoalManager gm: computers) {
					if (gm.getIndividual().getPlayer().equals(player)) {
						isComp = true;
						ind = gm.getIndividual();
						g = gm;
					}
				}
				if (isComp == false) {
					if (player.getIsDead() == true) {
						if (player instanceof Sentry) {
							toBeRemoved.add(player);
						}
						
						if (player.getIsStanding() == false /*&& player.getIsPressedRight() == true && player.getIsPressedLeft() == true*/) {
							player.switchingClasses = true;
						}

					}
					else if (player.getIsDead() == false) {
						if (player.getIsStanding() == false && player.getIsPressedRight() == true && player.getIsPressedLeft() == true) {
							if (player instanceof Engineer) {
								if (player.changeSpecial(!player.inSpecial, false)){
									Engineer eng = (Engineer) player;
									Sentry s = null;

									if (eng.getTeam() == 0) {
										s = new Sentry(100,eng.getTeam(),this,eng);
									}
									else if (eng.getTeam() == 1) {
										s = new Sentry(101,eng.getTeam(),this,eng);

									}

									if (eng.sentry != null) {
										toBeRemoved.add(eng.sentry);//removes old one
									}

									Coordinate c = new Coordinate(eng.getPoint().getX(), eng.getPoint().getY());

									
									eng.sentry = s;
									eng.sentry.point = c;
									eng.sentry.rotation = eng.getRotation();

									eng.sentry.originalRotation = eng.getRotation();
									eng.sentry.body = new Circle(eng.sentry.point,Player.PLAYER_RADIUS);
									
									iter.add(s);
								//	playerList.add(sentry);
								}
							}else {
								player.changeSpecial(!player.inSpecial,false);
							}
							

						}

						if (player.getIsStanding() == false) {
							checkPlayerCollisions(player);
						}

						if (player.getIsPressedRight() == true) {
							player.rotateRight();
						}
						else if (player.getIsPressedLeft() == true) {
							player.rotateLeft();
						}
					}
				}
				else {//is a computer
					if (player.getIsDead() == false) {
						ind.updateMovementCommands();
					}
					else {
						g.playerDied();
					}
				}

			}

			for (Player p:toBeRemoved) {
				playerList.remove(p);
			}

		}

	}


	public void keyPressed(KeyEvent e) {
		Player player0 = null;
		Player player1 = null;
		Player player2 = null;
		Player player3 = null;
		Player player4 = null;
		Player player5 = null;
		for (Player p:playerList) {
			if (p.getPlayerNum() == 0) {
				player0 = p;
			}
			else if (p.getPlayerNum() == 2) {
				player2 = p;
			}
			else if (p.getPlayerNum() == 3) {
				player3 = p;
			}
			else if (p.getPlayerNum() == 4) {
				player4 = p;
			}
			else if (p.getPlayerNum() == 5) {
				player5 = p;
			}
			else if (p.getPlayerNum() == 1) {
				player1 = p;
			}
		}
		
		
		if (e.getKeyCode() == KeyEvent.VK_S) {
			if (System.currentTimeMillis() - player0.getTimeOfTap() < Player.PLAYER_DOUBLE_TAP_DELAY) {
				player0.isBackwards = true;
			}
			player0.setIsStanding(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			player0.setIsPressedLeft(true);
			/*  playerList.get(0).setIsStrafingLeft(false);//ends strafing
       playerList.get(0).setIsStrafingRight(false);//ends strafing*/

		}
		if (e.getKeyCode() == KeyEvent.VK_X) {
			player0.setIsPressedRight(true);
			/* playerList.get(0).setIsStrafingRight(false);//ends strafing
       playerList.get(0).setIsStrafingLeft(false);//ends strafing*/
		}
		if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
			if (System.currentTimeMillis() - player1.getTimeOfTap() < Player.PLAYER_DOUBLE_TAP_DELAY) {
				player1.isBackwards = true;
				//System.out.println("is set Backwards");
			}

			player1.setIsStanding(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_COMMA) {
			player1.setIsPressedLeft(true);

		}
		if (e.getKeyCode() == KeyEvent.VK_SLASH) {
			player1.setIsPressedRight(true);
		}
		if (PlayerSelection.playersPerTeam * 2 > 2) {
			if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
				if (System.currentTimeMillis() - player2.getTimeOfTap() < Player.PLAYER_DOUBLE_TAP_DELAY) {
					player2.isBackwards = true;
					//System.out.println("is set Backwards");
				}

				player2.setIsStanding(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
				player2.setIsPressedLeft(true);

			}
			if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
				player2.setIsPressedRight(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (System.currentTimeMillis() - player3.getTimeOfTap() < Player.PLAYER_DOUBLE_TAP_DELAY) {
					player3.isBackwards = true;
					//System.out.println("is set Backwards");
				}

				player3.setIsStanding(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player3.setIsPressedLeft(true);

			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player3.setIsPressedRight(true);
			}
		}
		if (PlayerSelection.playersPerTeam * 2 > 4) {
			if (e.getKeyCode() == KeyEvent.VK_ADD) {
				if (System.currentTimeMillis() - player4.getTimeOfTap() < Player.PLAYER_DOUBLE_TAP_DELAY) {
					player4.isBackwards = true;
					//System.out.println("is set Backwards");
				}

				player4.setIsStanding(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				player4.setIsPressedLeft(true);

			}
			if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
				player4.setIsPressedRight(true);
			}
			if (e.getKeyCode() == KeyEvent.VK_I) {
				if (System.currentTimeMillis() - player5.getTimeOfTap() < Player.PLAYER_DOUBLE_TAP_DELAY) {
					player5.isBackwards = true;
					//System.out.println("is set Backwards");
				}

				player5.setIsStanding(false);
			}
			if (e.getKeyCode() == KeyEvent.VK_U) {
				player5.setIsPressedLeft(true);

			}
			if (e.getKeyCode() == KeyEvent.VK_O) {
				player5.setIsPressedRight(true);
			}
		}
	} 


	public void keyReleased(KeyEvent e) {//TODO:might have problems with having a set index for each player due to removal/additions to playerlist in other areas
										//should eventually iterate through player list and get player values
		Player player0 = null;
		Player player1 = null;
		Player player2 = null;
		Player player3 = null;
		Player player4 = null;
		Player player5 = null;
		for (Player p:playerList) {
			if (p.getPlayerNum() == 0) {
				player0 = p;
			}
			else if (p.getPlayerNum() == 2) {
				player2 = p;
			}
			else if (p.getPlayerNum() == 3) {
				player3 = p;
			}
			else if (p.getPlayerNum() == 4) {
				player4 = p;
			}
			else if (p.getPlayerNum() == 5) {
				player5 = p;
			}
			else if (p.getPlayerNum() == 1) {
				player1 = p;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_M) {
			Sound.setIsMute(!Sound.isMute);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			player0.setIsStanding(true);
			player0.setTimeOfTap();
			player0.setCanSetTimeOfTap(true);
			player0.isBackwards = false;
			Player player = player0;
			if (player.getIsDead() == true) {
				player.switchingClasses = false;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			player0.setIsPressedLeft(false);
			Player player = player0;
			if (player.getIsDead() == true && player.switchingClasses == true) {
				PlayerSelection.pointerSubtract(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_X) {
			player0.setIsPressedRight(false);
			Player player = player0;
			if (player.getIsDead() == true && player.switchingClasses == true) {
				PlayerSelection.pointerAdd(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
			player1.setIsStanding(true);
			player1.setTimeOfTap();
			player1.setCanSetTimeOfTap(true);
			player1.isBackwards = false;
			if (player1.getIsDead() == true) {
				player1.switchingClasses = false;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_COMMA) {
			player1.setIsPressedLeft(false);
			Player player = player1;
			if (player.getIsDead() == true && player.switchingClasses == true) {
				PlayerSelection.pointerSubtract(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SLASH) {
			player1.setIsPressedRight(false);
			Player player = player1;
			if (player.getIsDead() == true && player.switchingClasses == true) {
				PlayerSelection.pointerAdd(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
			}
		}
		if (PlayerSelection.playersPerTeam * 2 > 2) {
			if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
				player2.setIsStanding(true);
				player2.setTimeOfTap();
				player2.setCanSetTimeOfTap(true);
				player2.isBackwards = false;
				Player player = player2;
				if (player.getIsDead() == true) {
					player.switchingClasses = false;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
				player2.setIsPressedLeft(false);
				Player player = player2;
				if (player.getIsDead() == true && player.switchingClasses == true) {
					PlayerSelection.pointerSubtract(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
				player2.setIsPressedRight(false);
				Player player = player2;
				if (player.getIsDead() == true && player.switchingClasses == true) {
					PlayerSelection.pointerAdd(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				player3.setIsStanding(true);
				player3.setTimeOfTap();
				player3.setCanSetTimeOfTap(true);
				player3.isBackwards = false;
				Player player = player3;
				if (player.getIsDead() == true) {
					player.switchingClasses = false;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player3.setIsPressedLeft(false);
				Player player = player3;
				if (player.getIsDead() == true && player.switchingClasses == true) {
					PlayerSelection.pointerSubtract(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player3.setIsPressedRight(false);
				Player player = player3;
				if (player.getIsDead() == true && player.switchingClasses == true) {
					PlayerSelection.pointerAdd(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
				}
			}
		}
		if (PlayerSelection.playersPerTeam * 2 > 4) {
			if (e.getKeyCode() == KeyEvent.VK_ADD) {
				player4.setIsStanding(true);
				player4.setTimeOfTap();
				player4.setCanSetTimeOfTap(true);
				player4.isBackwards = false;
				Player player = player4;
				if (player.getIsDead() == true) {
					player.switchingClasses = false;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				player4.setIsPressedLeft(false);
				Player player = player4;
				if (player.getIsDead() == true && player.switchingClasses == true) {
					PlayerSelection.pointerSubtract(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
				player4.setIsPressedRight(false);
				Player player = player4;
				if (player.getIsDead() == true && player.switchingClasses == true) {
					PlayerSelection.pointerAdd(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_I) {
				player5.setIsStanding(true);
				player5.setTimeOfTap();
				player5.setCanSetTimeOfTap(true);
				player5.isBackwards = false;
				Player player = player5;
				if (player.getIsDead() == true) {
					player.switchingClasses = false;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_U) {
				player5.setIsPressedLeft(false);
				Player player = player5;
				if (player.getIsDead() == true && player.switchingClasses == true) {
					PlayerSelection.pointerSubtract(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_O) {
				player5.setIsPressedRight(false);
				Player player = player5;
				if (player.getIsDead() == true && player.switchingClasses == true) {
					PlayerSelection.pointerAdd(PlayerSelection.getPointer(player.getPlayerNum()), player.getPlayerNum());
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			destroyWorld();
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public Player getPlayer(int num) {
		return playerList.get(num);
	}

	public void paintOffScreen(Graphics g)
	{

		// location of soldier changes each time the timer goes off
		//	g.drawImage(soldier, soldierX, soldierY, this);	// 'soldier' was loaded in constructor

		g.drawImage(ImageLoader.getBackground(map), 0, 0, panel);
		updateWorld();

		//computers.get(0).getIndividual().getObstacleMap().drawObstacleMap(g);
		//g.drawImage(GUI.soldier, getPlayer(0).point.getX(), getPlayer(0).point.getY(), gui);



		g.drawImage(ImageLoader.getbSpawn(),(int) blueSpawn.getCoords()[0].getX(),(int) blueSpawn.getCoords()[0].getY(), panel);
		g.drawImage(ImageLoader.getgSpawn(), (int)greenSpawn.getCoords()[0].getX(),(int) greenSpawn.getCoords()[0].getY() , panel);
		for(Animation a: deathMarkList){
			g.drawImage(a.getCurrentImage(), (int)a.getPoint().getX(), (int)a.getPoint().getY(), panel);
		}

		for (Projectile p: trailProjectiles) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawLine((int)p.point.getX(), (int) p.point.getY(),(int) p.start.getX(),(int) p.start.getY());
		}

		for(Bullet b: shells){
			drawGameObject(g, b);
		}
		for(Blood b: bloods){
			drawGameObject(g, b);
		}
		for (GameObject o: gameTypeExtras) {
			if (o.getIsVisible() == true) {
				drawGameObject(g,o);
			}
		}

		for (Block b: blockList) {
			drawGameObject(g, b);
		}

		//drawAIDebug(g);

		for (Trap t: trapList) {
			if (t.getIsVisible() == true) {
				drawGameObject(g,t);
			}
		}

		for (Projectile p: projectileList) {
			drawGameObject(g,p);
		}

		for (Explosion e: explosionList) {
			drawGameObject(g,e);
		}

		Font font = new Font("Verdana", Font.BOLD,(int) (Block.BLOCK_SIZE / 4.5));
		g.setFont(font);

		for (Player p:playerList){
			if (p.getIsDead() == false) {


				if(p instanceof Medic){
					if(p.inSpecial){
						g.drawImage(ImageLoader.getHealingHalo(), (int) (p.getPoint().getX() - PlayerStats.MEDIC_S_RANGE),  (int) (p.getPoint().getY() - PlayerStats.MEDIC_S_RANGE), panel);
					}
				} else if(p instanceof Sentry){
					g.drawImage(ImageLoader.getSentryBaseImage(p.getTeam()), (int) (p.getPoint().getX() - Player.PLAYER_RADIUS),  (int) (p.getPoint().getY() - Player.PLAYER_RADIUS), panel);
				} else if(p instanceof Engineer){
					if(((Engineer) p).isRepairing()){
						g.drawImage(ImageLoader.getRepairingHalo(), (int) (p.getPoint().getX() - Player.PLAYER_WIDTH), (int) (p.getPoint().getY() - Player.PLAYER_HEIGHT), panel);
					}
				}

				if (mode == GameMode.VIP) {
					if (p.getPlayerNum() == 20 || p.getPlayerNum() == 21){
						g.drawImage(ImageLoader.getHalo(p.getTeam()), (int)(p.getPoint().getX() - (Player.PLAYER_WIDTH)), (int)(p.getPoint().getY() - (Player.PLAYER_HEIGHT)), panel);
					}
				} else if(mode == GameMode.CTF){
					if(p.getHasFlag()){
						g.drawImage(ImageLoader.getHalo(p.getFlag().getTeam()), (int)(p.getPoint().getX() - (Player.PLAYER_WIDTH)), (int)(p.getPoint().getY() - (Player.PLAYER_HEIGHT)), panel);
					}
				}

				drawGameObject(g,p);
				boolean disguised = false;
				if (p.getClassName() == "spy") {
					Spy sp = (Spy) p;
					if (sp.isDisguised == true) {
						disguised = true;
					}
				}
				
				if (p.getClassName() == "spy" && p.inSpecial == true && disguised == false){
				}
				else {
					
					if(p.inSpecial && p instanceof Spy == false || (p instanceof Spy && disguised != true && p.inSpecial == true)){
						g.setColor(new Color(222,184,135));
					} else{
						g.setColor(Color.WHITE);
					}

					if (p.getPlayerNum() == 100 || p.getPlayerNum() == 101) {//is a sentry
						//g.drawString("SNTRY", (int)(p.getPoint().getX() - Block.BLOCK_SIZE * 2 / 3), (int)(p.getPoint().getY() - Block.BLOCK_SIZE * 5 /9));

					}
					else {
						g.fillRect((int) (p.getPoint().getX() - Block.BLOCK_SIZE * 2 / 3), (int)(p.getPoint().getY() - Block.BLOCK_SIZE * 5 /7), Block.BLOCK_SIZE * 3/7,Block.BLOCK_SIZE * 1/4);
					}


					double healthRatio = p.getHealth() / PlayerStats.getPlayerMaxHealth(p);
					if(healthRatio > ((double)1)){
						g.setColor(new Color(100, 112, 219));
					} else if(healthRatio > ((double)2/3)){
						g.setColor(new Color(33, 209, 101));
					} else if(healthRatio > ((double)1/3)){
						g.setColor(new Color(240, 240, 48));
					} else {
						g.setColor(new Color(245, 82, 82));
					}

					g.fillRect((int) (p.getPoint().getX() - Block.BLOCK_SIZE * 2 / 3), (int)(p.getPoint().getY() + Block.BLOCK_SIZE * 3 /7), Block.BLOCK_SIZE * 9/8,Block.BLOCK_SIZE * 1/4);


					g.setColor(Color.BLACK);

					boolean isComp = false;

					for (GoalManager gm: computers) {
						if (gm.getIndividual().getPlayer().equals(p)) {
							isComp = true;
						}
					}


					if (isComp) {
						g.drawString("AI", (int)(p.getPoint().getX() - Block.BLOCK_SIZE * 2 / 3), (int)(p.getPoint().getY() - Block.BLOCK_SIZE * 5 /9));

					}
					else if (p.getPlayerNum() == 100 || p.getPlayerNum() == 101) {//is a sentry
						//g.drawString("SNTRY", (int)(p.getPoint().getX() - Block.BLOCK_SIZE * 2 / 3), (int)(p.getPoint().getY() - Block.BLOCK_SIZE * 5 /9));

					}
					else {
						if (mode == GameMode.VIP) {
							if (p.getPlayerNum() == 20 || p.getPlayerNum() == 21){

								g.drawString("VIP", (int)(p.getPoint().getX() - Block.BLOCK_SIZE * 2 / 3), (int)(p.getPoint().getY() - Block.BLOCK_SIZE * 5 /9));
							}
							else {
								g.drawString("P" + (p.getPlayerNum() + 1 + " S: " + p.score), (int)(p.getPoint().getX() - Block.BLOCK_SIZE * 2 / 3), (int)(p.getPoint().getY() - Block.BLOCK_SIZE * 5 /9));

							}
						}
						else {
							g.drawString("P" + (p.getPlayerNum() + 1 + " S: " + p.score), (int)(p.getPoint().getX() - Block.BLOCK_SIZE * 2 / 3), (int)(p.getPoint().getY() - Block.BLOCK_SIZE * 5 /9));
						}
					}


					double health = p.getHealth();
					String shortHealth;

					DecimalFormat df = new DecimalFormat("###.#");
					shortHealth = df.format(health);

					g.drawString("H: " + shortHealth,(int) (p.getPoint().getX() - Block.BLOCK_SIZE * 2 / 3), (int)(p.getPoint().getY() + Block.BLOCK_SIZE * 2 /3));	
				}
			}
		}


		Font font2 = new Font("Verdana", Font.BOLD, Block.BLOCK_SIZE * 2 / 5);
		g.setFont(font2);

		g.drawString("Blue Score: " + gameType.getScoreBlue() + "  Green Score " + gameType.getScoreGreen(), Block.BLOCK_SIZE * 3, 25);

		Font font3 = new Font("Verdana", Font.BOLD, Block.BLOCK_SIZE);
		g.setFont(font3);

		if (winningTeam != null) { 
			if (topPlayer == null) {
				determineTopPlayer();
			}
			
			
			g.drawString(winningTeam + " Wins!", ((LevelIndex.LEVEL_WIDTH / 2) - 4) * Block.BLOCK_SIZE, LevelIndex.LEVEL_HEIGHT / 2 * Block.BLOCK_SIZE);
			g.drawString("Top Player: " + (topPlayer.getPlayerNum() + 1) + "  Score: " + topPlayer.totalScore, ((LevelIndex.LEVEL_WIDTH / 2) - 4) * Block.BLOCK_SIZE, (LevelIndex.LEVEL_HEIGHT + 1) / 2 * Block.BLOCK_SIZE);

		}
		
		drawHUD(g);

	}

	/**
	 * Draws the game object
	 * 
	 * @param g
	 *            The graphics image
	 * @param gameObject
	 *            The object to be drawn
	 * @param box
	 *            The box that holds the size of the image
	 */
	protected void drawGameObject(Graphics g, Drawable gameObject) { // TODO

		// Vector2f v1 = gameObject.getCoordinates();
		AffineTransform backup = ((Graphics2D) g).getTransform();
		AffineTransform trans = new AffineTransform();
		trans.rotate(-gameObject.getRotation(), gameObject.getCoordinates()
				.getX(), gameObject.getCoordinates().getY()); // the points to
		// rotate around

		((Graphics2D) g).transform(trans);
		Image image = gameObject.getImage();
		if (gameObject.needsToFlip()) {
			g.drawImage(image,(int) gameObject.getCoordinates().getX()
					+ image.getWidth(panel), (int) gameObject
					.getCoordinates().getY(), -(image.getWidth(panel)),
					image.getHeight(panel), panel);
		} else {
			g.drawImage(image, (int) gameObject.getCoordinates().getX(),
					(int) gameObject.getCoordinates().getY(), panel); // the
			// actual
			// location
			// of
			// the
			// object
		}

		((Graphics2D) g).setTransform(backup); // restore previous transform
	}

	public void checkFiring() {
		for (Player p:playerList) {
			if (p instanceof Medic) {
				Medic m = (Medic) p;
				m.selfHeal();
			}

			ArrayList<Player> firingAt = p.lateralAimAndInRange(playerList);
			ArrayList<Player> clearShot = findClearShotPlayers(firingAt, p);
			ArrayList<Player> inRange = new ArrayList<Player>();
			if (p instanceof Medic == false) {
				inRange.add(p.getClosestPlayer(clearShot));
			}
			else {
				inRange = clearShot;
			}
			if (inRange != null) {
				p.fire(inRange);
			}

			p.checkDamageDone();
		}

		for (Player p: playerList) {
			if (p.getHealth() > PlayerStats.getPlayerMaxHealth(p) && System.currentTimeMillis() - p.lastUnheal > PlayerStats.UNHEAL_RATE) {
				double difference = (PlayerStats.OVERHEAL_RATIO - 1) * PlayerStats.getPlayerMaxHealth(p) / 10;
				if (p.getHealth() - difference < PlayerStats.getPlayerMaxHealth(p)) {
					difference = p.getHealth() - PlayerStats.getPlayerMaxHealth(p);
				}
				p.loseHealth(difference, null, new Assault(-1, 0, this),false);//third value is dummy player to avoid null pointer
				p.lastUnheal = System.currentTimeMillis();
			}
			if (p.getHealth() > PlayerStats.getPlayerMaxHealth(p) * PlayerStats.OVERHEAL_RATIO) {
				p.health = PlayerStats.getPlayerMaxHealth(p) * PlayerStats.OVERHEAL_RATIO;
			}
		}
	}

	public ArrayList<Player> findClearShotPlayers(ArrayList<Player> playersInLateralRange, Player shooter) {
		ArrayList<Player> clearShotPlayers = new ArrayList<Player>();
		if (playersInLateralRange != null) {
			for (Player p: playersInLateralRange) {
				boolean canAddPlayertoList = true;


				for (Block b: blockList) {

					java.awt.geom.Rectangle2D rect = b.getJavaRectangle();
					java.awt.geom.Line2D line = new Line2D.Double(shooter.getPoint().getX(),shooter.getPoint().getY(),p.getPoint().getX(),p.getPoint().getY());
					if (line.intersects(rect)) {
						canAddPlayertoList = false;
						break;
					}

				}

				if (canAddPlayertoList == true) {//if player can be shot at directly
					clearShotPlayers.add(p);
				}
			}

		}

		return clearShotPlayers;
	}

	//only checks if it has a clear shot through BLOCKS, not other Players
	public boolean hasClearShot(Coordinate target, Player shooter) {
		for (Block b: blockList) {
			java.awt.geom.Rectangle2D rect = b.getJavaRectangle();
			java.awt.geom.Line2D line = new Line2D.Double(shooter.getPoint().getX(),shooter.getPoint().getY(),target.getX(),target.getY());
			if (line.intersects(rect)) {
				return false;
			}
		}
		//if it gets here there have been no intersections
		return true;
	}

	public void addBullet(Bullet bullet){
		//TODO
		if(shells.size() >= maxNumBullets){
			shells.remove(0);
		}
		shells.add(bullet);
	}

	public void addBlood(Blood blood){
		if(bloods.size() >= maxNumBloods){
			bloods.remove(0);
		}
		bloods.add(blood);
	}

	public void checkPlayerCollisions(Player player) {
		boolean canMoveX = true;
		boolean canMoveY = true;

		for (Player p: playerList) {
			/*if (!player.equals(p) && p.getTeam() != player.getTeam()) {
				Circle c = new Circle(player.getNextMove(),Player.PLAYER_RADIUS);
				if (CollisionDetector.areColliding(c,p.getCircle())) {
					canMoveX = false;
					canMoveY = false;
				}
			}*/
			if (player.getTeam() != p.getTeam() && !player.equals(p)) {
				Circle c = new Circle(player.getNextMove(),Player.PLAYER_RADIUS);
				if (CollisionDetector.areColliding(c,p.getCircle())) {//is hitting a player
					double rotation = player.getPoint().getAngleBetween(p.getPoint());
					if (p instanceof Spy) {
						Spy sp = (Spy) p;
						sp.changeSpecial(false,true);
					}
					if (player instanceof Spy) {
						Spy sp = (Spy) player;
						sp.changeSpecial(false,true);
					}


					if (0 < rotation && rotation < Math.PI / 4) {//player is to the left
						canMoveX = false;
					}
					if (Math.PI / 4 < rotation && rotation < Math.PI * 3/4) {//player is above
						canMoveY = false;
					}
					if (Math.PI * 3/4 < rotation && rotation < Math.PI * 5/4) {//player to the right
						canMoveX = false;
					}
					if (Math.PI * 5/4 < rotation && rotation < Math.PI * 7/4) {//player is below
						canMoveY = false;
					}
					if (Math.PI * 7/4 < rotation) {
						canMoveX = false;
					}

				}
			}

		}

		for (Block b: blockList) {
			Circle c = new Circle(player.getNextMove(),Player.PLAYER_RADIUS);
			if (CollisionDetector.areColliding(c,b.getRectangle())) {//is hitting a wall
				double rotation = player.getPoint().getAngleBetween(b.getMiddle());

				if (0 < rotation && rotation < Math.PI / 4) {//player is to the left
					canMoveX = false;
				}
				if (Math.PI / 4 < rotation && rotation < Math.PI * 3/4) {//player is above
					canMoveY = false;
				}
				if (Math.PI * 3/4 < rotation && rotation < Math.PI * 5/4) {//player to the right
					canMoveX = false;
				}
				if (Math.PI * 5/4 < rotation && rotation < Math.PI * 7/4) {//player is below
					canMoveY = false;
				}
				if (Math.PI * 7/4 < rotation) {
					canMoveX = false;
				}

			}
		}

		java.awt.geom.Ellipse2D circle = new Ellipse2D.Double(player.getNextMove().getX(),player.getNextMove().getY(),Player.PLAYER_RADIUS,Player.PLAYER_RADIUS);

		java.awt.geom.Rectangle2D lineLeft = new Rectangle2D.Double(0,0,1, LevelIndex.LEVEL_HEIGHT * Block.BLOCK_SIZE);
		if (circle.intersects(lineLeft)) {
			canMoveX = false;
		}

		java.awt.geom.Rectangle2D lineRight = new Rectangle2D.Double(LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE,0,1, LevelIndex.LEVEL_HEIGHT * Block.BLOCK_SIZE);
		if (circle.intersects(lineRight)) {
			canMoveX = false;
		}

		java.awt.geom.Rectangle2D lineTop = new Rectangle2D.Double(0,0,LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE,1);
		if (circle.intersects(lineTop)) {
			canMoveY = false;
		}

		java.awt.geom.Rectangle2D lineBot = new Rectangle2D.Double(0,LevelIndex.LEVEL_HEIGHT * Block.BLOCK_SIZE,LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE,1);
		if (circle.intersects(lineBot)) {
			canMoveY = false;
		}

		//checks to make sure not moving into spawn zone
		Circle c = new Circle(player.getNextMove(),Player.PLAYER_RADIUS);




		if ((CollisionDetector.areColliding(c, blueSpawn) && player.getTeam() != 0) || (player.getHasFlag() == true && player.getTeam() == 0 && (CollisionDetector.areColliding(c, blueSpawn)))) {//player will collide with spawn and is not on blue team
			if (player.getPoint().getY() < 	blueSpawn.getCoords()[3].getY() && player.getPoint().getY() > 	blueSpawn.getCoords()[0].getY()) {//y is not preventing it, so must be on the right side
				canMoveX = false;
			}
			if (player.getPoint().getX() > blueSpawn.getCoords()[0].getX() && player.getPoint().getX() < blueSpawn.getCoords()[1].getX()) {//X is not a factor, must be underneath the spawn trying to move up
				canMoveY = false;

			}
		}

		if ((CollisionDetector.areColliding(c, greenSpawn) && player.getTeam() != 1)|| (player.getHasFlag() == true && player.getTeam() == 1 && (CollisionDetector.areColliding(c, greenSpawn)))) {//player will collide with spawn and is not on blue team
			if (player.getPoint().getY() < 	greenSpawn.getCoords()[3].getY() && player.getPoint().getY() > 	greenSpawn.getCoords()[0].getY()) {//y is not preventing it, so must be on the right side
				canMoveX = false;
			}
			if (player.getPoint().getX() > greenSpawn.getCoords()[0].getX() && player.getPoint().getX() < greenSpawn.getCoords()[1].getX()) {//X is not a factor, must be underneath the spawn trying to move up
				canMoveY = false;

			}
		}

		/*if (CollisionDetector.areColliding(c, greenSpawn) && player.getTeam() !=1) {
			if (player.getPoint().getY() > Block.BLOCK_SIZE * (LevelIndex.LEVEL_HEIGHT - 3)) {
				canMoveX = false;
			}
			if (player.getPoint().getX() > Block.BLOCK_SIZE * (LevelIndex.LEVEL_WIDTH - 3)) {
				canMoveY = false;
			}
		}*/


		if (canMoveX == true) {
			player.moveX();
			//doesn't have a collision
		}
		if (canMoveY == true) {
			player.moveY();
			//doesn't have a collision
		}
	}

	public int sign(double num) {//return -1 if number is negative
		if (num < 0) {
			return -1;
		}
		return 1;
	}

	public int getOctant(Coordinate origin, Coordinate point) {
		double angle = origin.getAngleBetween(point);
		if (angle > 0 && angle < Math.PI / 4) {
			return 0;
		}
		else if (angle > Math.PI / 4 && angle < Math.PI / 2) {
			return 1;
		}
		else if (angle > Math.PI / 2 && angle < Math.PI * 3 / 4) {
			return 2;
		}
		else if (angle > Math.PI *3 / 4 && angle < Math.PI) {
			return 3;
		}
		else if (angle > Math.PI && angle < Math.PI *5 / 4) {
			return 4;
		}
		else if (angle > Math.PI *5 / 4 && angle < Math.PI * 6 / 4) {
			return 5;
		}
		else if (angle > Math.PI *6 / 4 && angle < Math.PI * 7 / 4) {
			return 6;
		}
		else if (angle > Math.PI *7 / 4 && angle < Math.PI * 8 / 4) {
			return 7;
		}

		return 0;
	}

	public Coordinate switchToOctantZeroFrom(int octant, Coordinate point) {
		switch (octant) {
		case 0: return new Coordinate(point.getX(),point.getY());
		case 1: return new Coordinate(point.getY(),point.getX());
		case 2: return new Coordinate(point.getY(),-point.getX());
		case 3: return new Coordinate(-point.getX(),point.getY());
		case 4: return new Coordinate(-point.getX(),-point.getY());
		case 5: return new Coordinate(-point.getY(),-point.getX());
		case 6: return new Coordinate(-point.getY(),point.getX());
		case 7: return new Coordinate(point.getX(),-point.getY());
		}
		return null;
	}

	public Coordinate switchFromOctantZeroTo(int octant, Coordinate point) {
		switch (octant) {
		case 0: return new Coordinate(point.getX(),point.getY());
		case 1: return new Coordinate(point.getY(),point.getX());
		case 2: return new Coordinate(-point.getY(),point.getX());
		case 3: return new Coordinate(-point.getX(),point.getY());
		case 4: return new Coordinate(-point.getX(),-point.getY());
		case 5: return new Coordinate(-point.getY(),-point.getX());
		case 6: return new Coordinate(point.getY(),-point.getX());
		case 7: return new Coordinate(point.getX(),-point.getY());
		}
		return null;
	}

	public void checkSpawn() {
		ListIterator<Player> iter = playerList.listIterator();
		while(iter.hasNext()) {
			Player p = iter.next();
			if (p.getIsDead() == true) {
				if (System.currentTimeMillis() - p.getDeathTime() > p.getSpawnTime() && p.switchingClasses == false) {
					//p.setIsDead(false);
					
					//will need to remove p from game to exchange with new player
					boolean isComp = false;
					GoalManager goalManager = null;
					for (GoalManager gm: computers) {
						if (gm.getIndividual().getPlayer().equals(p)) {
							isComp = true;
							goalManager = gm;
							//gm.getIndividual().newPlayer(player);
						}
					}
					
					Player player = null;
					
					String cName;
					if (p.getPlayerNum() == 20 || p.getPlayerNum() == 21) {
						cName = p.getClassName();
					}
					else if (isComp == false){
						cName = PlayerSelection.getClassString(PlayerSelection.getPointer(p.getPlayerNum()));
					}
					else {
						cName = p.getClassName();
					}
					

					
					if (cName.equals("assault")) {
						player = (new Assault(p.getPlayerNum(),p.getTeam(),this));
					}
					else if (cName.equals("heavy")) {
						player = new Heavy(p.getPlayerNum(),p.getTeam(),this);
					}
					else if (cName.equals("scout")) {
						player = (new Scout(p.getPlayerNum(),p.getTeam(),this));
					}
					else if (cName.equals("sniper")) {
						player = (new Sniper(p.getPlayerNum(),p.getTeam(),this));
					}
					else if (cName.equals("shield")) {
						player = (new Shield(p.getPlayerNum(),p.getTeam(),this));
					}
					else if (cName.equals("explosives")) {
						player = (new Explosives(p.getPlayerNum(),p.getTeam(),this));
					}
					else if (cName.equals("medic")) {
						player = (new Medic(p.getPlayerNum(),p.getTeam(),this));
					}
					else if (cName.equals("spy")) {
						player = (new Spy(p.getPlayerNum(),p.getTeam(),this));
					}
					else if (cName.equals("engineer")) {
						player = new Engineer(p.getPlayerNum(),p.getTeam(),this);
					}
					
					player.kills = p.kills;
					player.deaths = p.deaths;
					player.totalScore = p.totalScore;
					
					iter.set(player);
					
						if (isComp) {
							goalManager.getIndividual().newPlayer(player);
						}
						
						if (p.getPlayerNum() == 20 || p.getPlayerNum() == 21) {
							VIP g = (VIP)gameType;
							if (p.getTeam() == 0) {
								System.out.println("running");
								g.blueVIP.player = player;
							}
							else if (p.getTeam() == 1){
								System.out.println("running1");

								g.greenVIP.player = player;
							}
						}
						
					if (p instanceof Engineer && player instanceof Engineer == false) {//doesn't remain engineer
						((Engineer) p).sentry.loseHealth(((Engineer) p).sentry.health, null, new Assault(-1, 1, this), false);
					}
					else if (p instanceof Engineer && player instanceof Engineer == true) {//doesn't remain engineer
						((Engineer) player).sentry = ((Engineer)p).sentry;
					}
					else if (p instanceof Explosives && player instanceof Explosives == false) {//doesn't remain explosives
						for (Mine m: ((Explosives) p).minesActive) {
							trapList.remove(m);
						}
					}
					else if (p instanceof Explosives && player instanceof Explosives == true) {//doesn't remain explosives
						((Explosives)player).minesActive = ((Explosives)p).minesActive;
						((Explosives)player).minesPlaced = ((Explosives)p).minesPlaced;
					}
				}
			}
		}
	}

	public void setWinningTeam(String s) {
		winningTeam = s;
	}

	public Projectile checkProjectileCollisions(Projectile projectile) {
		//Shape next = projectile.getShape();
		for (int i = 0; i < projectile.self.bulletMoves;i++) {

			boolean canMove = true;

			for (Player p: playerList) {
				if (CollisionDetector.areColliding(p.getCircle(),projectile.getShape())) {

					if (!projectile.self.equals(p) && (p.getTeam() != projectile.team || projectile instanceof Syringe && p instanceof Sentry == false)) {
						projectile.haveCollision(p);
						canMove = false;
					}
				}
			}

			for (Block b: blockList) {
				if (CollisionDetector.areColliding(b.getRectangle(),projectile.getShape())) {
					projectile.haveCollision(b);
					canMove = false;
				}
			}

			if (canMove) {
				projectile.move();
			}
			else {//it hit something
				return projectile;
			}

			if (projectile instanceof Grenade) {
				java.awt.geom.Ellipse2D circle = new Ellipse2D.Double(projectile.getPoint().getX(),projectile.getPoint().getY(),Tracer.TRACER_RADIUS,Tracer.TRACER_RADIUS);
				java.awt.geom.Rectangle2D lineLeft = new Rectangle2D.Double(0,0,10, LevelIndex.LEVEL_HEIGHT * Block.BLOCK_SIZE);
				if (circle.intersects(lineLeft)) {
					Grenade g = (Grenade) projectile;
					g.explode();
					return projectile;
				}

				java.awt.geom.Rectangle2D lineRight = new Rectangle2D.Double(LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE,0,10, LevelIndex.LEVEL_HEIGHT * Block.BLOCK_SIZE);
				if (circle.intersects(lineRight)) {
					Grenade g = (Grenade) projectile;

					g.explode();
					return projectile;
				}

				java.awt.geom.Rectangle2D lineTop = new Rectangle2D.Double(0,0,LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE,10);
				if (circle.intersects(lineTop)) {
					Grenade g = (Grenade) projectile;
					g.explode();
					return projectile;
				}

				java.awt.geom.Rectangle2D lineBot = new Rectangle2D.Double(0,LevelIndex.LEVEL_HEIGHT * Block.BLOCK_SIZE,LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE,10);
				if (circle.intersects(lineBot)) {
					Grenade g = (Grenade) projectile;
					g.explode();
					return projectile;
				}

			}

		}
		return null;
	}

	public void addProjectile(Projectile p) {
		projectileList.add(p);
	}

	public void checkProjectiles() {
		ArrayList<Projectile> removal = new ArrayList<Projectile>();

		for (Projectile p: projectileList) {
			Projectile toBeRemoved = checkProjectileCollisions(p);
			if ( toBeRemoved != null) {
				removal.add(toBeRemoved);
			} else if(p.isOutOfRange()){
				if (p instanceof Grenade) {
					Grenade g = (Grenade) p;
					g.explode();
				}
				removal.add(p);
			}
		}

		for (Projectile p: removal) {
			projectileList.remove(p);
			if (trailProjectiles.contains(p)) {
				trailProjectiles.remove(p);
			}
		}
	}

	public void addDeathMark(Coordinate c, int team){
		deathMarkList.add(AnimationLoader.getDeathMark(team, c));
	}

	private void checkDeathMarks(){
		ArrayList<Animation> temp = new ArrayList<Animation>();
		for(Animation a: deathMarkList){
			if(!a.isValid()){
				temp.add(a);
			}
		}
		for(Animation a: temp){
			deathMarkList.remove(a);
		}
	}

	private void checkExplosions(){
		ArrayList<Explosion> temp = new ArrayList<Explosion>();
		for(Explosion e: explosionList){
			if(!e.isValid()){
				temp.add(e);
			}
		}
		for(Explosion e: temp){
			explosionList.remove(e);
		}
	}

	public void addTrailProjectile(Projectile p) {
		trailProjectiles.add(p);
	}



	public void addTrap(Trap t) {
		trapList.add(t);
	}

	public void removeTrap(Trap t) {
		trapList.remove(t);
	}

	public void checkTraps() {
		ArrayList<Trap> toBeRemoved = new ArrayList<Trap>();

		for (Trap t: trapList) {//check timers
			if (t.checkTrapTimer()) {
				toBeRemoved.add(t);
			}
		}

		for (Trap t: trapList) {//checks if activated(in collision)
			if (toBeRemoved.contains(t) == false) {
				for (Player p: playerList) {
					if (p.getTeam() != t.getTeam()) {
						if (CollisionDetector.areColliding(t.getShape(), p.getCircle())) {
							//t.haveInteraction(p);
							if (t.isTriggered == false) {
								t.setTimeTriggered();
							}
						}
					}
				}
			}
		}

		//removes trap after being touched
		if (toBeRemoved.size() != 0) {
			for (Trap t: toBeRemoved) {
				trapList.remove(t);
			}
		}
	}

	//adds explosion of type "type" at the specified point
	public void addExplosion(Coordinate center, String type, Player p){
		explosionList.add(new Explosion(center, type, p));
	}

	public void drawAIDebug(Graphics g){
		if (computers.size() != 0) {
			computers.get(0).getIndividual().getObstacleMap().drawObstacleMap(g);
			for(GoalManager gm : computers){
				Coordinate temp = gm.getIndividual().getNextPlace();
				double pPerT = Block.BLOCK_SIZE / ObstacleMap.TILES_PER_SQUARE;
				temp = new Coordinate(temp.getX(), temp.getY());
				g.setColor(Color.RED);
				g.drawOval((int)(temp.getX() - (pPerT / 2)),(int)( temp.getY() - (pPerT / 2)),(int) pPerT,(int) pPerT);
			}
			for(int x = 0; x < ObstacleMap.WIDTH; x++){
				for(int y = 0; y < ObstacleMap.HEIGHT; y++){
					g.setColor(Color.WHITE);
					double influence = ea.getInfluenceAt(x, y);
					/*if(EnvironmentAnalyzer.highestinfluence < influence){
					EnvironmentAnalyzer.highestinfluence = influence;
					System.out.println(influence);
				}*/

					DecimalFormat df = new DecimalFormat("##.##");
					g.drawString(df.format(influence), (int)(x * Block.BLOCK_SIZE / 2), (int)(y * Block.BLOCK_SIZE / 2));
				}
			}
			
			Path firstPath = computers.get(0).getIndividual().getPath();
			double pPerT = Block.BLOCK_SIZE / ObstacleMap.TILES_PER_SQUARE;
			g.setColor(Color.BLUE);
			for(int j = 0; j < firstPath.getLength(); j++){
				Step step = firstPath.getStep(j);
				Coordinate c = ObstacleMap.tileToPixels(new Coordinate(step.getX(), step.getY()));
				g.drawOval((int)(c.getX() - (pPerT / 2)),(int)( c.getY() - (pPerT / 2)),(int) pPerT,(int) pPerT);
			}
		}
	}
	
	public void drawHUD(Graphics g){
		Font font = new Font("Verdana", Font.BOLD, Block.BLOCK_SIZE * 2 / 5);
		g.setFont(font);

		g.setColor(new Color(157,162,163));

		
		g.fillRect(LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE, 0, Block.BLOCK_SIZE * 5,Block.BLOCK_SIZE * LevelIndex.LEVEL_HEIGHT);

		g.setColor(Color.BLACK);
		
		int count = 0;
		for (Player p:playerList) {
			boolean isComp = false;
			for (GoalManager gm: computers) {
				if (gm.getIndividual().getPlayer().equals(p)) {
					isComp = true;
				}
			}
					if (isComp == false && p instanceof Sentry == false && p.getPlayerNum() != 20 && p.getPlayerNum() != 21) {
						count++;
						int baseY = (int)(Block.BLOCK_SIZE * 2.4 * (count)) - Block.BLOCK_SIZE * 2;//TODO: change it so i can see if class is updated when pushed i.e. playerselection.getPointer.getString
					
						if (p.getTeam() == 0) {
							g.setColor(new Color(33, 82, 143));
						}
						else if (p.getTeam() == 1) {
							g.setColor(new Color(63, 134, 0));
						}
						
						g.drawString("Player: " + (p.getPlayerNum() + 1),LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE, baseY);
						
						if (p.switchingClasses == true) {
							g.setColor(Color.RED);
						}
						else {
							g.setColor(Color.BLACK);
						}
						g.drawString("Class: " + PlayerSelection.getClassString(PlayerSelection.getPointer(p.getPlayerNum())),LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE, baseY + Block.BLOCK_SIZE * 2 / 5);
						
						g.setColor(Color.BLACK);
						
						g.drawString("Score: " + (int) p.score + "/" + p.totalScore,LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE, baseY + Block.BLOCK_SIZE * 4 / 5);
						g.drawString("Kills: " + p.kills,LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE, baseY + Block.BLOCK_SIZE * 6 / 5);
						g.drawString("Death: " + p.deaths,LevelIndex.LEVEL_WIDTH * Block.BLOCK_SIZE, baseY + Block.BLOCK_SIZE * 8 / 5);

			}
		}
	}

	public void determineTopPlayer() {
		int max = 0;
		for (Player p: playerList) {
			if (p.totalScore > max) {
				max = p.totalScore;
				topPlayer = p;
			}
		}
	}

}
