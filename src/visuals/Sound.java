package visuals;

import javax.sound.sampled.*;

public class Sound {
	
	private Clip clip;
	
	// Change file name to match yours, of course
	//NOTE:NEED TO ADD ANY NEW SOUNDS TO STOP ALL SOUNDS METHOD BELOW
	public static Sound menu = new Sound("/sound/menu.wav");
	public static int stoppingPoint = 0;
	public static Sound assaultAutomatic = new Sound("/sound/Assault Automatic.wav");
	public static Sound assaultBurst = new Sound("/sound/Assault Burst.wav");
	public static Sound beeping = new Sound("/sound/Beeping.wav");
	public static Sound captureFlag = new Sound("/sound/Capture Flag.wav");
	public static Sound engineerRepair = new Sound("/sound/Engineer Repair.wav");
	public static Sound explosion = new Sound("/sound/Explosion.wav");
	public static Sound grenadeLauncher = new Sound("/sound/Grenade Launcher.wav");
	public static Sound healingAura = new Sound("/sound/Healing Aura.wav");
	public static Sound medicGun = new Sound("/sound/Medic Gun.wav");
	public static Sound pistol = new Sound("/sound/Pistol(Spy).wav");
	public static Sound sentryMoving = new Sound("/sound/Sentry Moving.wav");
	public static Sound shieldHit = new Sound("/sound/Shield Hit.wav");
	public static Sound shotgun = new Sound("/sound/Shotgun.wav");
	public static Sound sniper = new Sound("/sound/Sniper.wav");
	public static Sound spyCloak = new Sound("/sound/Spy Cloak.wav");
	public static Sound submachine = new Sound("/sound/Submachine Gun.wav");
	
	public static boolean isMute = false;
	
	public Sound (String fileName) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			if (clip != null && Sound.isMute == false) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resume() {
		try {
			if (clip != null && Sound.isMute == false) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		if(clip == null) return;
		clip.stop();
	}
	
	public void loop() {
		try {
			if (clip != null && Sound.isMute == false) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.loop(Clip.LOOP_CONTINUOUSLY);
						}
					}
				}.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isActive(){
		return clip.isActive();
	}
	
	public static void stopAllSounds() {
		Sound.menu.stop();
		Sound.sentryMoving.stop();
		Sound.medicGun.stop();
		Sound.assaultAutomatic.stop();
		Sound.assaultBurst.stop();
		Sound.beeping.stop();
		Sound.captureFlag.stop();
		Sound.engineerRepair.stop();
		Sound.explosion.stop();
		Sound.grenadeLauncher.stop();
		Sound.healingAura.stop();
		Sound.medicGun.stop();
		Sound.pistol.stop();
		Sound.sentryMoving.stop();
		Sound.shieldHit.stop();
		Sound.shotgun.stop();
		Sound.sniper.stop();
		Sound.spyCloak.stop();
		Sound.submachine.stop();
	}
	
	public static void setIsMute(boolean newState){
		Sound.isMute = newState;
		if(newState == true){
			Sound.stopAllSounds();
		}
	}
}