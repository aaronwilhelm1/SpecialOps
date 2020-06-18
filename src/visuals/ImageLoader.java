package visuals;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

import gameobjects.Block;
import gameobjects.Flag;
import gameobjects.HealthPack;
import gameobjects.LevelIndex;
import gameobjects.MapSelection;
import gameobjects.Players.Player;
import gameobjects.Players.PlayerStats;
import gameobjects.projectiles.Grenade;
import gameobjects.projectiles.Syringe;
import gameobjects.projectiles.Tracer;
import gui.GUI;

public class ImageLoader {

	protected static Class loader = GUI.class;

	protected static Image[] gsoldierImages;
	protected static Image[] bsoldierImages;
	protected static Image[] gscoutImages;
	protected static Image[] bscoutImages;
	protected static Image[] gheavyImages;
	protected static Image[] bheavyImages;
	protected static Image[] gsniperImages;
	protected static Image[] bsniperImages;
	protected static Image[] gshieldImages;
	protected static Image[] bshieldImages;
	protected static Image[] gexplosivesImages;
	protected static Image[] bexplosivesImages;
	protected static Image[] gmedicImages;
	protected static Image[] bmedicImages;
	protected static Image[] gspyImages;
	protected static Image[] bspyImages;
	protected static Image[] gengineerImages;
	protected static Image[] bengineerImages;

	protected static Image gsentryBaseImage;
	protected static Image bsentryBaseImage;
	protected static Image[] sentryGunImages;


	protected static Image smallBoxImage;
	protected static Image medBoxImage;
	protected static Image largeBoxImage;
	protected static Image fourBoxImage;
	protected static Image smallGreenBoxImage;
	protected static Image medGreenBoxImage;
	protected static Image largeGreenBoxImage;
	protected static Image fourGreenBoxImage;

	protected static Image gspawnImage;
	protected static Image bspawnImage;
	protected static Image gdeathMarkImage;
	protected static Image bdeathMarkImage;

	protected static Image warehouseBackgroundImage;
	protected static Image polarisBackgroundImage;
	protected static Image fortsBackgroundImage;

	protected static Image[] gbriefcaseTableImages;
	protected static Image[] bbriefcaseTableImages;
	protected static Image gbriefcaseImage;
	protected static Image bbriefcaseImage;
	protected static Image ghaloImage;
	protected static Image bhaloImage;
	protected static Image healingHaloImage;
	protected static Image repairingHaloImage;
	protected static Image bspyShadowImage;
	protected static Image gspyShadowImage;

	protected static Image bulletImage;
	protected static Image tracerImage;
	protected static Image grenadeImage;
	protected static Image syringeImage;
	protected static Image[] mineImages;
	protected static Image critTracerImage;
	protected static Image[] bloodImages;
	protected static Image healthImage;

	protected static Image[] mineExplosionImages;
	protected static Image[] grenadeExplosionImages;


	/**
	 * Loads the images for the game
	 * 
	 */
	public static void loadImages(){
		loadbSoldierImages();
		loadgSoldierImages();
		loadbScoutImages();
		loadgScoutImages();
		loadbHeavyImages();
		loadgHeavyImages();
		loadbSniperImages();
		loadgSniperImages();
		loadbShieldImages();
		loadgShieldImages();
		loadbExplosivesImages();
		loadgExplosivesImages();
		loadbMedicImages();
		loadgMedicImages();
		loadbSpyImages();
		loadgSpyImages();
		loadbEngineerImages();
		loadgEngineerImages();
		loadbSentryBaseImage();
		loadgSentryBaseImage();
		loadSentryGunImages();
		loadSmallBoxImage();
		loadMedBoxImage();
		loadLargeBoxImage();
		loadFourBoxImage();
		loadSmallGreenBoxImage();
		loadMedGreenBoxImage();
		loadLargeGreenBoxImage();
		loadFourGreenBoxImage();
		loadbSpawnImage();
		loadgSpawnImage();
		loadbDeathMarkImage();
		loadgDeathMarkImage();
		loadWarehouseBackgroundImage();
		loadPolarisBackgroundImage();
		loadbBriefcaseTableImages();
		loadgBriefcaseTableImages();
		loadbBriefcaseImage();
		loadgBriefcaseImage();
		loadbHaloImage();
		loadgHaloImage();
		loadHealingHaloImage();
		loadRepairingHaloImage();
		loadbSpyShadowImage();
		loadgSpyShadowImage();
		loadBulletImage();
		loadTracerImage();
		loadGrenadeImage();
		loadSyringeImage();
		loadMineImages();
		loadCritTracerImage();
		loadBloodImages();
		loadHealthImage();
		loadMineExplosionImages();
		loadGrenadeExplosionImages();
	}


	/**
	 * Gets the images for the requested player
	 * 
	 * @param cName The class of the player
	 * @return The images of the player
	 */
	public static Image[] getPlayerImage(String cName, int team) {
		if(team == 0){
			if (cName.equals("assault")) {
				if (bsoldierImages == null) {
					loadbSoldierImages();
				}
				return bsoldierImages;
			} else if(cName.equals("scout")){
				if (bscoutImages == null) {
					loadbScoutImages();
				}
				return bscoutImages;
			} else if(cName.equals("heavy")){
				if (bheavyImages == null) {
					loadbHeavyImages();
				}
				return bheavyImages;
			} else if(cName.equals("sniper")){
				if (bsniperImages == null) {
					loadbSniperImages();
				}
				return bsniperImages;
			} else if(cName.equals("shield")){
				if (bshieldImages == null) {
					loadbShieldImages();
				}
				return bshieldImages;
			} else if(cName.equals("explosives")){
				if (bexplosivesImages == null) {
					loadbExplosivesImages();
				}
				return bexplosivesImages;
			} else if(cName.equals("medic")){
				if (bmedicImages == null) {
					loadbMedicImages();
				}
				return bmedicImages;
			}else if(cName.equals("spy")){
				if (bspyImages == null) {
					loadbSpyImages();
				}
				return bspyImages;
			}else if(cName.equals("engineer")){
				if (bengineerImages == null) {
					loadbEngineerImages();
				}
				return bengineerImages;
			}else {
				System.out.println("Error loading player with the name " + cName);
				if (bsoldierImages == null) {
					loadbSoldierImages();
				}
				return bsoldierImages;
			}
		}else if(team == 1){
			if (cName.equals("assault")) {
				if (gsoldierImages == null) {
					loadgSoldierImages();
				}
				return gsoldierImages;
			} else if(cName.equals("scout")){
				if (gscoutImages == null) {
					loadgScoutImages();
				}
				return gscoutImages;
			} else if(cName.equals("heavy")){
				if (gheavyImages == null) {
					loadgHeavyImages();
				}
				return gheavyImages;
			} else if(cName.equals("sniper")){
				if (gsniperImages == null) {
					loadgSniperImages();
				}
				return gsniperImages;
			} else if(cName.equals("shield")){
				if (gshieldImages == null) {
					loadgShieldImages();
				}
				return gshieldImages;
			} else if(cName.equals("explosives")){
				if (gexplosivesImages == null) {
					loadgExplosivesImages();
				}
				return gexplosivesImages;
			} else if(cName.equals("medic")){
				if (gmedicImages == null) {
					loadgMedicImages();
				}
				return gmedicImages;
			}else if(cName.equals("spy")){
				if (gspyImages == null) {
					loadgSpyImages();
				}
				return gspyImages;
			} else if(cName.equals("engineer")){
				if (gengineerImages == null) {
					loadgEngineerImages();
				}
				return gengineerImages;
			}else {
				System.out.println("Error loading player with the name " + cName);
				if (gsoldierImages == null) {
					loadgSoldierImages();
				}
				return gsoldierImages;
			}
		} else{
			System.out.println("Error loading player with team number " + team);
			return gsoldierImages;
		}
	}

	public static Image getSentryBaseImage(int team){
		if(team == 0){
			if(bsentryBaseImage == null){
				loadbSentryBaseImage();
			}
			return bsentryBaseImage;
		} else if(team == 1){
			if(gsentryBaseImage == null){
				loadgSentryBaseImage();
			}
			return gsentryBaseImage;
		} else {
			System.out.println("Can't get sentry base for team with number " + team);
			if(bsentryBaseImage == null){
				loadbSentryBaseImage();
			}
			return bsentryBaseImage;
		}
	}

	public static Image[] getSentryGunImages(){
		if(sentryGunImages == null){
			loadSentryGunImages();
		}
		return sentryGunImages;
	}

	/**
	 * Gets the images for the box
	 * 
	 * @param cName The class of the player
	 * @return The images of the player
	 */
	public static Image getBoxImage(int size) {
		if(MapSelection.getMapString(MapSelection.pointer0).equals("polaris")){
			if(size == 1){
				if(smallGreenBoxImage == null){
					loadSmallGreenBoxImage();
				}
				return smallGreenBoxImage;
			} else if(size == 2){
				if(medGreenBoxImage == null){
					loadMedGreenBoxImage();
				}
				return medGreenBoxImage;
			} else if(size == 3){
				if(largeGreenBoxImage == null){
					loadLargeGreenBoxImage();
				}
				return largeGreenBoxImage;
			} else {
				if(fourGreenBoxImage == null){
					loadFourGreenBoxImage();
				}
				return fourGreenBoxImage;
			}

		} else {
			if(size == 1){
				if(smallBoxImage == null){
					loadSmallBoxImage();
				}
				return smallBoxImage;
			} else if(size == 2){
				if(medBoxImage == null){
					loadMedBoxImage();
				}
				return medBoxImage;
			} else if(size == 3){
				if(largeBoxImage == null){
					loadLargeBoxImage();
				}
				return largeBoxImage;
			} else {
				if(fourBoxImage == null){
					loadFourBoxImage();
				}
				return fourBoxImage;
			}

		}

	}


	
	/**
	 * Gets the images for the two versions of the briefcase table
	 * Note: returns with briefcase, then without briefcase
	 * 
	 * @return The images of the briefcase and table
	 */
	public static Image[] getBriefcaseTableImages(int team) {

		if (team == 0) {
			if (bbriefcaseTableImages == null) {
				loadbBriefcaseTableImages();
				return bbriefcaseTableImages;
			}
		} else if(team == 1) {
			if (gbriefcaseTableImages == null) {
				loadgBriefcaseTableImages();
				return gbriefcaseTableImages;
			}
		} else {
			System.out.println("Error loading briefcase table images with team number " + team);
			return bbriefcaseTableImages;
		}
		
		return bbriefcaseTableImages;
	}

	/**
	 * Gets the image for the briefcase
	 * 
	 * @return The image of the briefcase
	 */
	public static Image getBriefcaseImage(int team) {

		if (team == 0) {
			if (bbriefcaseImage == null) {
				loadbBriefcaseImage();
				return bbriefcaseImage;
			}
		} else if(team == 1) {
			if (gbriefcaseImage == null) {
				loadgBriefcaseImage();
				return gbriefcaseImage;
			}
		} else {
			System.out.println("Error loading briefcase image with team number " + team);
			return bbriefcaseImage;
		}
		
		return bbriefcaseImage;
	}

	/**
	 * Gets the image the health
	 * 
	 * @return The image of the health
	 */
	public static Image getHealthImage() {

		if (healthImage == null) {
			loadHealthImage();
		}
		return healthImage;
	}


	/**
	 * Gets the image of the bullet
	 * 
	 * @return The image of the bullet
	 */
	public static Image getBulletImage() {

		if (bulletImage == null) {
			loadBulletImage();
		}
		return bulletImage;
	}

	/**
	 * Gets the image of the tracer
	 * 
	 * @return The image of the tracer
	 */
	public static Image getTracerImage() {

		if (tracerImage == null) {
			loadTracerImage();
		}
		return tracerImage;
	}

	/**
	 * Gets the image of the grenade
	 * 
	 * @return The image of the grenade
	 */
	public static Image getGrenadeImage() {

		if (grenadeImage == null) {
			loadGrenadeImage();
		}
		return grenadeImage;
	}

	/**
	 * Gets the image of the syringe
	 * 
	 * @return The image of the syringe
	 */
	public static Image getSyringeImage() {

		if (syringeImage == null) {
			loadSyringeImage();
		}
		return syringeImage;
	}

	/**
	 * Gets the images of the mine
	 * 
	 * @return The images of the mine
	 */
	public static Image[] getMineImages() {

		if (mineImages == null) {
			loadMineImages();
		}
		return mineImages;
	}

	/**
	 * Gets the image of the critTracer
	 * 
	 * @return The image of the critTracer
	 */
	public static Image getCritTracerImage() {

		if (critTracerImage == null) {
			loadCritTracerImage();
		}
		return critTracerImage;
	}

	/**
	 * Gets the images of the blood
	 * 
	 * @return The images of the blood
	 */
	public static Image[] getBloodImages() {

		if (bloodImages == null) {
			loadBloodImages();
		}
		return bloodImages;
	}

	/**
	 * Gets the image for green spawn
	 * 
	 * @return The image of the green spawn
	 */
	public static Image getgSpawn() {

		if (gspawnImage == null) {
			loadgSpawnImage();
		}
		return gspawnImage;
	}

	/**
	 * Gets the image for blue spawn
	 * 
	 * @return The image of the blue spawn
	 */
	public static Image getbSpawn() {

		if (bspawnImage == null) {
			loadbSpawnImage();
		}
		return bspawnImage;
	}

	/**
	 * Gets the image for green death Mark
	 * 
	 * @return The image of the green death Mark
	 */
	public static Image getgDeathMark() {

		if (gdeathMarkImage == null) {
			loadgDeathMarkImage();
		}
		return gdeathMarkImage;
	}

	/**
	 * Gets the image for blue death Mark
	 * 
	 * @return The image of the blue death Mark
	 */
	public static Image getbDeathMark() {

		if (bdeathMarkImage == null) {
			loadbDeathMarkImage();
		}
		return bdeathMarkImage;
	}

	/**
	 * Gets the image for the background
	 * 
	 * @return The image of the background
	 */
	public static Image getBackground(String mapName) {

		if(mapName.equals("warehouse")){
			if (warehouseBackgroundImage == null) {
				loadWarehouseBackgroundImage();
			}
			return warehouseBackgroundImage;
		} else if(mapName.equals("polaris")){
			if (polarisBackgroundImage == null) {
				loadPolarisBackgroundImage();
			}
			return polarisBackgroundImage;
		} else if(mapName.equals("forts")){
			if (fortsBackgroundImage == null) {
				loadFortsBackgroundImage();
			}
			return fortsBackgroundImage;
		} else{
			System.out.println("No map with name " + mapName);
			if (warehouseBackgroundImage == null) {
				loadWarehouseBackgroundImage();
			}
			return warehouseBackgroundImage;
		}
	}

	/**
	 * Gets the image for halo
	 * 
	 * @return The image of the halo
	 */
	public static Image getHalo(int team) {
		if(team == 0){
			if (bhaloImage == null) {
				loadbHaloImage();
			}
			return bhaloImage;
		} else if(team == 1){
			if (ghaloImage == null) {
				loadgHaloImage();
			}
			return ghaloImage;
		} else{
			System.out.println("No Team with Number ( halo)" + team);
			if (bhaloImage == null) {
				loadbHaloImage();
			}
			return bhaloImage;
		}
	}

	/**
	 * Gets the image for halo
	 * 
	 * @return The image of the halo
	 */
	public static Image getHealingHalo() {

		if (healingHaloImage == null) {
			loadHealingHaloImage();
		}
		return healingHaloImage;

	}

	/**
	 * Gets the image for halo
	 * 
	 * @return The image of the halo
	 */
	public static Image getRepairingHalo() {

		if (repairingHaloImage == null) {
			loadRepairingHaloImage();
		}
		return repairingHaloImage;

	}

	/**
	 * Gets the image for bspyShadow
	 * 
	 * @return The image of the bspyShadow
	 */
	public static Image getbSpyShadow() {

		if (bspyShadowImage == null) {
			loadbSpyShadowImage();
		}
		return bspyShadowImage;

	}

	/**
	 * Gets the image for gspyShadow
	 * 
	 * @return The image of the gspyShadow
	 */
	public static Image getgSpyShadow() {

		if (gspyShadowImage == null) {
			loadgSpyShadowImage();
		}
		return gspyShadowImage;

	}

	/**
	 * Gets the images for the mine explosions
	 * 
	 * @return The images of the mine explosion
	 */
	public static Image[] getMineExplosionImages() {
		if(mineExplosionImages == null){
			loadMineExplosionImages();
		}
		return mineExplosionImages;
	}

	/**
	 * Gets the images for the grenade explosions
	 * 
	 * @return The images of the grenade explosion
	 */
	public static Image[] getGrenadeExplosionImages() {
		if(grenadeExplosionImages == null){
			loadGrenadeExplosionImages();
		}
		return grenadeExplosionImages;
	}

	/* Loads the image for the green soldier
	 */
	private static void loadgSoldierImages() {
		gsoldierImages = new Image[5];
		for(int j = 0; j < 5; j++)
			try {

				gsoldierImages[j] = ImageIO.read(loader.getResource("/images/gsoldier" + j + ".png"));

				gsoldierImages[j] = gsoldierImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException f) {
				try{

					// PrintStream methods need to be in a try-catch block

					PrintStream p = new PrintStream(new FileOutputStream(new File("trace.txt")));

					p.println("got here");

					f.printStackTrace(p);

				} 

				catch(Exception g)

				{ 

				} 

			}
	}

	/* Loads the image for the blue soldier
	 */
	private static void loadbSoldierImages() {
		bsoldierImages = new Image[5];
		for(int j = 0; j < 5; j++)
			try {

				bsoldierImages[j] = ImageIO.read(loader.getResource("/images/bsoldier" + j + ".png"));

				bsoldierImages[j] = bsoldierImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the green scout
	 */
	private static void loadgScoutImages() {
		gscoutImages = new Image[5];
		for(int j = 0; j < 5; j++)
			try {

				gscoutImages[j] = ImageIO.read(loader.getResource("/images/gscout" + j + ".png"));

				gscoutImages[j] = gscoutImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the blue scout
	 */
	private static void loadbScoutImages() {
		bscoutImages = new Image[5];
		for(int j = 0; j < 5; j++)
			try {

				bscoutImages[j] = ImageIO.read(loader.getResource("/images/bscout" + j + ".png"));

				bscoutImages[j] = bscoutImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the green heavy
	 */
	private static void loadgHeavyImages() {
		gheavyImages = new Image[5];
		for(int j = 0; j < 5; j++)
			try {

				gheavyImages[j] = ImageIO.read(loader.getResource("/images/gheavy" + j + ".png"));

				gheavyImages[j] = gheavyImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the blue heavy
	 */
	private static void loadbHeavyImages() {
		bheavyImages = new Image[5];
		for(int j = 0; j < 5; j++)
			try {

				bheavyImages[j] = ImageIO.read(loader.getResource("/images/bheavy" + j + ".png"));

				bheavyImages[j] = bheavyImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the green sniper
	 */
	private static void loadgSniperImages() {
		gsniperImages = new Image[5];
		for(int j = 0; j < 5; j++)
			try {

				gsniperImages[j] = ImageIO.read(loader.getResource("/images/gsniper" + j + ".png"));

				gsniperImages[j] = gsniperImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.67910447761194), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the blue sniper
	 */
	private static void loadbSniperImages() {
		bsniperImages = new Image[5];
		for(int j = 0; j < 5; j++)
			try {

				bsniperImages[j] = ImageIO.read(loader.getResource("/images/bsniper" + j + ".png"));

				bsniperImages[j] = bsniperImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.67910447761194), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the green shield
	 */
	private static void loadgShieldImages() {
		gshieldImages = new Image[10];
		for(int j = 0; j < 5; j++)
			try {

				gshieldImages[j] = ImageIO.read(loader.getResource("/images/gshieldg" + j + ".png"));

				gshieldImages[j] = gshieldImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
		for(int j = 5; j < 10; j++)
			try {

				gshieldImages[j] = ImageIO.read(loader.getResource("/images/gshields" + (j-5) + ".png"));

				gshieldImages[j] = gshieldImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.24477611), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the blue shield
	 */
	private static void loadbShieldImages() {
		bshieldImages = new Image[10];
		for(int j = 0; j < 5; j++)
			try {

				bshieldImages[j] = ImageIO.read(loader.getResource("/images/bshieldg" + j + ".png"));

				bshieldImages[j] = bshieldImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
		for(int j = 5; j < 10; j++)
			try {

				bshieldImages[j] = ImageIO.read(loader.getResource("/images/bshields" + (j-5) + ".png"));

				bshieldImages[j] = bshieldImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.24477611), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the green explosives
	 */
	private static void loadgExplosivesImages() {
		gexplosivesImages = new Image[10];
		for(int j = 0; j < 5; j++)
			try {

				gexplosivesImages[j] = ImageIO.read(loader.getResource("/images/gexplosives" + j + ".png"));

				gexplosivesImages[j] = gexplosivesImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the blue explosives
	 */
	private static void loadbExplosivesImages() {
		bexplosivesImages = new Image[10];
		for(int j = 0; j < 5; j++)
			try {

				bexplosivesImages[j] = ImageIO.read(loader.getResource("/images/bexplosives" + j + ".png"));

				bexplosivesImages[j] = bexplosivesImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the green medic
	 */
	private static void loadgMedicImages() {
		gmedicImages = new Image[10];
		for(int j = 0; j < 5; j++)
			try {

				gmedicImages[j] = ImageIO.read(loader.getResource("/images/gmedic" + j + ".png"));

				gmedicImages[j] = gmedicImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the blue medic
	 */
	private static void loadbMedicImages() {
		bmedicImages = new Image[10];
		for(int j = 0; j < 5; j++)
			try {

				bmedicImages[j] = ImageIO.read(loader.getResource("/images/bmedic" + j + ".png"));

				bmedicImages[j] = bmedicImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.5), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the green spy
	 */
	private static void loadgSpyImages() {
		gspyImages = new Image[10];
		for(int j = 0; j < 5; j++)
			try {

				gspyImages[j] = ImageIO.read(loader.getResource("/images/gspy" + j + ".png"));

				gspyImages[j] = gspyImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.28125), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the blue spy
	 */
	private static void loadbSpyImages() {
		bspyImages = new Image[10];
		for(int j = 0; j < 5; j++)
			try {

				bspyImages[j] = ImageIO.read(loader.getResource("/images/bspy" + j + ".png"));

				bspyImages[j] = bspyImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.28125), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the green engineer
	 */
	private static void loadgEngineerImages() {
		gengineerImages = new Image[10];
		for(int j = 0; j < 5; j++)
			try {

				gengineerImages[j] = ImageIO.read(loader.getResource("/images/gengineer" + j + ".png"));

				gengineerImages[j] = gengineerImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.28125), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the blue engineer
	 */
	private static void loadbEngineerImages() {
		bengineerImages = new Image[10];
		for(int j = 0; j < 5; j++)
			try {

				bengineerImages[j] = ImageIO.read(loader.getResource("/images/bengineer" + j + ".png"));

				bengineerImages[j] = bengineerImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.28125), Player.PLAYER_HEIGHT, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* Loads the image for the green sentry gun base
	 */
	private static void loadgSentryBaseImage() {
		try {

			gsentryBaseImage = ImageIO.read(loader.getResource("/images/gsentrybase" + ".png"));

			gsentryBaseImage = gsentryBaseImage.getScaledInstance((int)(Player.PLAYER_WIDTH), Player.PLAYER_HEIGHT, 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the blue sentry gun base
	 */
	private static void loadbSentryBaseImage() {
		try {

			bsentryBaseImage = ImageIO.read(loader.getResource("/images/bsentrybase" + ".png"));

			bsentryBaseImage = bsentryBaseImage.getScaledInstance((int)(Player.PLAYER_WIDTH), Player.PLAYER_HEIGHT, 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the images for the sentry gun
	 */
	private static void loadSentryGunImages() {
		sentryGunImages = new Image[10];
		for(int j = 0; j < 3; j++)
			try {

				sentryGunImages[j] = ImageIO.read(loader.getResource("/images/sentrygun" + j + ".png"));

				sentryGunImages[j] = sentryGunImages[j].getScaledInstance((int)(Player.PLAYER_WIDTH * 1.15151), (int)(Player.PLAYER_HEIGHT * 0.742424242), 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}


	/* Loads the image for the small box
	 * 
	 */
	private static void loadSmallGreenBoxImage() {

		try {
			//was GUI.class
			smallGreenBoxImage = ImageIO.read(loader.getResource("/images/greenbox" + ".png"));

			smallGreenBoxImage = smallGreenBoxImage.getScaledInstance(Block.BLOCK_SIZE, Block.BLOCK_SIZE, 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the medium GreenBox
	 * 
	 */
	private static void loadMedGreenBoxImage() {

		try {

			medGreenBoxImage = ImageIO.read(loader.getResource("/images/greenbox" + ".png"));

			medGreenBoxImage = medGreenBoxImage.getScaledInstance(Block.BLOCK_SIZE * 2, Block.BLOCK_SIZE * 2, 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the medium GreenBox
	 * 
	 */
	private static void loadLargeGreenBoxImage() {

		try {

			largeGreenBoxImage = ImageIO.read(loader.getResource("/images/greenbox" + ".png"));

			largeGreenBoxImage = largeGreenBoxImage.getScaledInstance(Block.BLOCK_SIZE * 3, Block.BLOCK_SIZE * 3, 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the four by four GreenBox
	 * 
	 */
	private static void loadFourGreenBoxImage() {

		try {

			fourGreenBoxImage = ImageIO.read(loader.getResource("/images/greenbox" + ".png"));

			fourGreenBoxImage = fourGreenBoxImage.getScaledInstance(Block.BLOCK_SIZE * 4, Block.BLOCK_SIZE * 4, 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the small box
	 * 
	 */
	private static void loadSmallBoxImage() {

		try {
			//was GUI.class
			smallBoxImage = ImageIO.read(loader.getResource("/images/box" + ".png"));

			smallBoxImage = smallBoxImage.getScaledInstance(Block.BLOCK_SIZE, Block.BLOCK_SIZE, 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the medium box
	 * 
	 */
	private static void loadMedBoxImage() {

		try {

			medBoxImage = ImageIO.read(loader.getResource("/images/box" + ".png"));

			medBoxImage = medBoxImage.getScaledInstance(Block.BLOCK_SIZE * 2, Block.BLOCK_SIZE * 2, 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the medium box
	 * 
	 */
	private static void loadLargeBoxImage() {

		try {

			largeBoxImage = ImageIO.read(loader.getResource("/images/box" + ".png"));

			largeBoxImage = largeBoxImage.getScaledInstance(Block.BLOCK_SIZE * 3, Block.BLOCK_SIZE * 3, 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the four by four box
	 * 
	 */
	private static void loadFourBoxImage() {

		try {

			fourBoxImage = ImageIO.read(loader.getResource("/images/box" + ".png"));

			fourBoxImage = fourBoxImage.getScaledInstance(Block.BLOCK_SIZE * 4, Block.BLOCK_SIZE * 4, 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/* Loads the images for the green briefcase table
	 * 
	 */
	private static void loadgBriefcaseTableImages() {

		gbriefcaseTableImages = new Image[2];
		for(int j = 0; j < 2; j++)
			try {
				if(j == 0){
					gbriefcaseTableImages[j] = ImageIO.read(loader.getResource("/images/gbriefcaseandtable" + ".png"));
				} else{
					gbriefcaseTableImages[j] = ImageIO.read(loader.getResource("/images/table" + ".png"));
				}


				gbriefcaseTableImages[j] = gbriefcaseTableImages[j].getScaledInstance(Block.BLOCK_SIZE, Block.BLOCK_SIZE, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/* Loads the images for the blue briefcase table
	 * 
	 */
	private static void loadbBriefcaseTableImages() {

		bbriefcaseTableImages = new Image[2];
		for(int j = 0; j < 2; j++)
			try {
				if(j == 0){
					bbriefcaseTableImages[j] = ImageIO.read(loader.getResource("/images/bbriefcaseandtable" + ".png"));
				} else{
					bbriefcaseTableImages[j] = ImageIO.read(loader.getResource("/images/table" + ".png"));
				}


				bbriefcaseTableImages[j] = bbriefcaseTableImages[j].getScaledInstance(Block.BLOCK_SIZE, Block.BLOCK_SIZE, 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	

	/* Loads the image for the green briefcase
	 * 
	 */
	private static void loadgBriefcaseImage() {

		try {
			gbriefcaseImage = ImageIO.read(loader.getResource("/images/gbriefcase" + ".png"));
			gbriefcaseImage = gbriefcaseImage.getScaledInstance((int)(Flag.WIDTH), (int)(Flag.HEIGHT), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	/* Loads the image for the blue briefcase
	 * 
	 */
	private static void loadbBriefcaseImage() {

		try {
			bbriefcaseImage = ImageIO.read(loader.getResource("/images/bbriefcase" + ".png"));
			bbriefcaseImage = bbriefcaseImage.getScaledInstance((int)(Flag.WIDTH), (int)(Flag.HEIGHT), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* Loads the image for the health
	 * 
	 */
	private static void loadHealthImage() {

		try {
			healthImage = ImageIO.read(loader.getResource("/images/health" + ".png"));
			healthImage = healthImage.getScaledInstance((int)(HealthPack.WIDTH), (int)(HealthPack.HEIGHT), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* Loads the images for the blood
	 * 
	 */
	private static void loadBloodImages() {
		bloodImages = new Image[3];
		try {
			for(int j = 0; j < 3; j++){
				bloodImages[j] = ImageIO.read(loader.getResource("/images/blood" + j +".png"));
				bloodImages[j] = bloodImages[j].getScaledInstance((int)(.24 *Block.BLOCK_SIZE), (int)(.24 *Block.BLOCK_SIZE), 10);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the bullet
	 * 
	 */
	private static void loadBulletImage() {

		try {
			bulletImage = ImageIO.read(loader.getResource("/images/bullet" + ".png"));
			bulletImage = bulletImage.getScaledInstance((int)(.08 *Block.BLOCK_SIZE), (int)(.24 *Block.BLOCK_SIZE), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the tracer
	 * 
	 */
	private static void loadTracerImage() {

		try {
			tracerImage = ImageIO.read(loader.getResource("/images/tracer" + ".png"));
			tracerImage = tracerImage.getScaledInstance((int)(Tracer.TRACER_RADIUS * 2), (int)(Tracer.TRACER_RADIUS * 2), 10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the grenade
	 * 
	 */
	private static void loadGrenadeImage() {

		try {
			grenadeImage = ImageIO.read(loader.getResource("/images/grenade" + ".png"));
			grenadeImage = grenadeImage.getScaledInstance((int)(Grenade.GRENADE_RADIUS * 2), (int)(Grenade.GRENADE_RADIUS * 2), 10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the syringe
	 * 
	 */
	private static void loadSyringeImage() {

		try {
			syringeImage = ImageIO.read(loader.getResource("/images/syringe" + ".png"));
			syringeImage = syringeImage.getScaledInstance((int)(Syringe.SYRINGE_RADIUS * 2), (int)(Syringe.SYRINGE_RADIUS * 2), 10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the images for the mine
	 * 
	 */
	private static void loadMineImages() {
		mineImages = new Image[3];
		for(int j = 0; j < 3; j++){
			try {
				mineImages[j] = ImageIO.read(loader.getResource("/images/mine" + j + ".png"));
				mineImages[j] = mineImages[j].getScaledInstance((int)(PlayerStats.MINE_RADIUS * 2), (int)(PlayerStats.MINE_RADIUS * 2), 10);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/* Loads the image for the critTracer
	 * 
	 */
	private static void loadCritTracerImage() {

		try {
			critTracerImage = ImageIO.read(loader.getResource("/images/crittracer" + ".png"));
			critTracerImage = critTracerImage.getScaledInstance((int)(Tracer.TRACER_RADIUS * 2), (int)(Tracer.TRACER_RADIUS * 2), 10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the green spawn
	 * 
	 */
	private static void loadgSpawnImage() {

		try {
			gspawnImage = ImageIO.read(loader.getResource("/images/gspawn" + ".png"));
			gspawnImage = gspawnImage.getScaledInstance((int)(3 *Block.BLOCK_SIZE), (int)(3 *Block.BLOCK_SIZE), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the blue spawn
	 * 
	 */
	private static void loadbSpawnImage() {

		try {
			bspawnImage = ImageIO.read(loader.getResource("/images/bspawn" + ".png"));
			bspawnImage = bspawnImage.getScaledInstance((int)(3 *Block.BLOCK_SIZE), (int)(3 *Block.BLOCK_SIZE), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the green deathMark
	 * 
	 */
	private static void loadgDeathMarkImage() {

		try {
			gdeathMarkImage = ImageIO.read(loader.getResource("/images/gdeathmark" + ".png"));
			gdeathMarkImage = gdeathMarkImage.getScaledInstance((int)(Player.PLAYER_WIDTH), (int)(Player.PLAYER_HEIGHT), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the blue deathMark
	 * 
	 */
	private static void loadbDeathMarkImage() {

		try {
			bdeathMarkImage = ImageIO.read(loader.getResource("/images/bdeathmark" + ".png"));
			bdeathMarkImage = bdeathMarkImage.getScaledInstance((int)(Player.PLAYER_WIDTH), (int)(Player.PLAYER_HEIGHT), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/* Loads the image for the background for the Warehouse
	 * 
	 */
	private static void loadWarehouseBackgroundImage() {

		try {
			warehouseBackgroundImage = ImageIO.read(loader.getResource("/images/warehousebackground" + ".png"));
			warehouseBackgroundImage = warehouseBackgroundImage.getScaledInstance((int)(Block.BLOCK_SIZE * LevelIndex.LEVEL_WIDTH), (int)(LevelIndex.LEVEL_HEIGHT *Block.BLOCK_SIZE), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the background for Polaris
	 * 
	 */
	private static void loadPolarisBackgroundImage() {

		try {
			polarisBackgroundImage = ImageIO.read(loader.getResource("/images/polarisbackground" + ".png"));
			polarisBackgroundImage = polarisBackgroundImage.getScaledInstance((int)(Block.BLOCK_SIZE * LevelIndex.LEVEL_WIDTH), (int)(LevelIndex.LEVEL_HEIGHT *Block.BLOCK_SIZE), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* Loads the image for the background for Forts
	 * 
	 */
	private static void loadFortsBackgroundImage() {

		try {
			fortsBackgroundImage = ImageIO.read(loader.getResource("/images/warehousebackground" + ".png"));
			fortsBackgroundImage = fortsBackgroundImage.getScaledInstance((int)(Block.BLOCK_SIZE * LevelIndex.LEVEL_WIDTH), (int)(LevelIndex.LEVEL_HEIGHT *Block.BLOCK_SIZE), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the green halo
	 * 
	 */
	private static void loadgHaloImage() {

		try {
			ghaloImage = ImageIO.read(loader.getResource("/images/ghalo" + ".png"));
			ghaloImage = ghaloImage.getScaledInstance((int)(2 *Player.PLAYER_WIDTH), (int)(2 *Player.PLAYER_HEIGHT), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the blue halo
	 * 
	 */
	private static void loadbHaloImage() {

		try {
			bhaloImage = ImageIO.read(loader.getResource("/images/bhalo" + ".png"));
			bhaloImage = bhaloImage.getScaledInstance((int)(2 *Player.PLAYER_WIDTH), (int)(2 *Player.PLAYER_HEIGHT), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the healing halo
	 * 
	 */
	private static void loadHealingHaloImage() {

		try {
			healingHaloImage = ImageIO.read(loader.getResource("/images/healinghalo" + ".png"));
			healingHaloImage = healingHaloImage.getScaledInstance((int)(2 * PlayerStats.MEDIC_S_RANGE), (int)(2 * PlayerStats.MEDIC_S_RANGE), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the repairing halo
	 * 
	 */
	private static void loadRepairingHaloImage() {

		try {
			repairingHaloImage = ImageIO.read(loader.getResource("/images/repairinghalo" + ".png"));
			repairingHaloImage = repairingHaloImage.getScaledInstance((int)(2 * Player.PLAYER_WIDTH), (int)(2 * Player.PLAYER_HEIGHT), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the image for the blue spy shadow
	 * 
	 */
	private static void loadbSpyShadowImage() {

		try {
			bspyShadowImage = ImageIO.read(loader.getResource("/images/bspyshadow" + ".png"));
			bspyShadowImage = bspyShadowImage.getScaledInstance((int)(Player.PLAYER_WIDTH), (int)(Player.PLAYER_WIDTH), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/* Loads the image for the green spy shadow
	 * 
	 */
	private static void loadgSpyShadowImage() {

		try {
			gspyShadowImage = ImageIO.read(loader.getResource("/images/gspyshadow" + ".png"));
			gspyShadowImage = gspyShadowImage.getScaledInstance((int)(Player.PLAYER_WIDTH), (int)(Player.PLAYER_WIDTH), 10);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* Loads the images for the mine explosion
	 * 
	 */
	private static void loadMineExplosionImages() {
		mineExplosionImages = new Image[7];
		for(int j = 0; j < 7; j++){
			try {
				mineExplosionImages[j] = ImageIO.read(loader.getResource("/images/explosion" + j + ".png"));
				mineExplosionImages[j] = mineExplosionImages[j].getScaledInstance((int)(PlayerStats.MINE_EXPLOSION_RADIUS * 2), (int)(PlayerStats.MINE_EXPLOSION_RADIUS * 2), 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/* Loads the images for the grenade explosion
	 * 
	 */
	private static void loadGrenadeExplosionImages() {
		grenadeExplosionImages = new Image[7];
		for(int j = 0; j < 7; j++){
			try {
				grenadeExplosionImages[j] = ImageIO.read(loader.getResource("/images/explosion" + j + ".png"));
				grenadeExplosionImages[j] = grenadeExplosionImages[j].getScaledInstance((int)(PlayerStats.GRENADE_EXPLOSION_RADIUS * 2), (int)(PlayerStats.GRENADE_EXPLOSION_RADIUS * 2), 10);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
