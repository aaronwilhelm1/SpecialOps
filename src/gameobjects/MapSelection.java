package gameobjects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gui.GUI;
import gui.OurPanel;
import gui.GUI.State;

public class MapSelection {
	OurPanel panel;
	GUI gui;
	World world;
	public static int pointer0;
	public static int pointerGT;

	
	public MapSelection(GUI g, OurPanel p, World w) {
		gui = g;
		panel = p;
		world = w;
		
		pointer0 = 0;
		pointerGT = 0;

	}
	
	public void updateMenu() {
		
	}
	
	public void paintOffScreen(Graphics g){
		Font font1 = new Font("Verdana", Font.ITALIC, 48);
		g.setFont(font1);
		g.drawString("Game Type/Map Selection", ((LevelIndex.LEVEL_WIDTH - 6) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE);
		
		Font font3 = new Font("Verdana", Font.ITALIC, 36);
		g.setFont(font3);
		g.drawString("Map: " + getMapString(pointer0),((LevelIndex.LEVEL_WIDTH - 5) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 10);

		//g.drawString("Blue Team",((LevelIndex.LEVEL_WIDTH - 4) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 3);
		//g.drawString("Green Team", ((LevelIndex.LEVEL_WIDTH - 4) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 10);
		
		g.drawString("Game Type: " + getGTString(pointerGT),((LevelIndex.LEVEL_WIDTH - 5) / 2)* Block.BLOCK_SIZE, Block.BLOCK_SIZE * 4);
		
		Font font2 = new Font("Verdana", Font.BOLD, 24);
		g.setFont(font2);

		


	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_B) {
			pointerAdd(pointer0,0);
		}
		if (e.getKeyCode() == KeyEvent.VK_V) {
			pointerSubtract(pointer0,0);
		}
		if (e.getKeyCode() == KeyEvent.VK_C) {
			pointerGTAdd(pointerGT,1);
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
	//		world.buildWorld();
			gui.state = State.PLAYER_SELECTION;
			
		}
	}
	public void pointerGTAdd(int p, int pointerNumber) {
		p++;
		if (p > 1) {
			p = 0;
		}
		
		if (pointerNumber == 0) {
			pointer0 = p;
		}
		if (pointerNumber == 1) {
			pointerGT = p;
		}
	}
	
	public void pointerAdd(int p, int pointerNumber) {
		p++;
		if (p > 2) {
			p = 0;
		}
		
		if (pointerNumber == 0) {
			pointer0 = p;
		}
		if (pointerNumber == 1) {
			pointerGT = p;
		}
	}
	

	
	public void pointerSubtract(int p, int pointerNumber) {
		p--;
		if (p < 0) {
			p = 2;
		}
		
		
		if (pointerNumber == 0) {
			pointer0 = p;
		}
		
	}
	
	
	public static String getMapString(int i) {
		if (i == 0) {
			return "warehouse";
		}
		if (i == 1) {
			return "polaris";
		}
		if (i == 2) {
			return "forts";
		}
		return "assault";
	//	return "Whoops! There is an error!";
	}
	

	
	public static int getPointer(int i) {
		if (i == 0) {
			return pointer0;
		}
		if (i == 1) {
			return pointerGT;
		}
		return 3;//returns 3 for assault
	}
	
	public static String getGTString(int i) {
		if (i == 0) {
			return "CTF";
		}
		if (i == 1) {
			return "VIP";
		}
		return "assault";
	//	return "Whoops! There is an error!";
	}
	
}
