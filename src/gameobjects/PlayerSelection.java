package gameobjects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import gui.GUI;
import gui.GUI.State;
import gui.OurPanel;
import visuals.Sound;

public class PlayerSelection {

	OurPanel panel;
	GUI gui;
	World world;
	public static int pointer1;
	public static int pointer2;
	public static int pointer3;
	public static int pointer4;
	public static int pointer5;
	public static int pointer0;
	
	public static int teamPointer1;
	public static int teamPointer2;
	public static int teamPointer3;
	public static int teamPointer4;
	public static int teamPointer5;
	public static int teamPointer0;
	
	public static int playersPerTeam;
	
	public PlayerSelection(GUI g, OurPanel p, World w) {
		gui = g;
		panel = p;
		world = w;
		pointer0 = 1;
		pointer1 = 1;
		pointer2 = 1;
		pointer3 = 1;
		pointer4 = 1;
		pointer5 = 1;
		
		teamPointer0 = 2;
		teamPointer1 = 2;
		teamPointer2 = 2;
		teamPointer3 = 2;
		teamPointer4 = 2;
		teamPointer5 = 2;
		
		playersPerTeam = 1;
	}
	
	public void updateMenu() {
		
	}
	
	public void paintOffScreen(Graphics g){
		Font font1 = new Font("Verdana", Font.ITALIC, 48);
		g.setFont(font1);
		g.drawString("Player Selection", ((LevelIndex.LEVEL_WIDTH - 6) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE);
		
		Font font3 = new Font("Verdana", Font.ITALIC, 36);
		g.setFont(font3);
		g.drawString("Players Per Team: " + playersPerTeam,((LevelIndex.LEVEL_WIDTH - 5) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 4);

		//g.drawString("Blue Team",((LevelIndex.LEVEL_WIDTH - 4) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 3);
		//g.drawString("Green Team", ((LevelIndex.LEVEL_WIDTH - 4) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 10);
		
		String p1 = getClassString(pointer0);
		String p2 = getClassString(pointer1);
		String p3 = getClassString(pointer2);
		String p4 = getClassString(pointer3);
		String p5 = getClassString(pointer4);
		String p6 = getClassString(pointer5);
		
		Font font2 = new Font("Verdana", Font.BOLD, 24);
		g.setFont(font2);

		g.drawString("Player 1 Selection(Keyboard 1, WSX): " + p1,((LevelIndex.LEVEL_WIDTH - 15) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 5);
		g.drawString("P1 Team: " + getTeamString(teamPointer0),((LevelIndex.LEVEL_WIDTH - 10)) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 5);

		
		g.drawString("Player 2 Selection(Keyboard 2, ,./): " + p2, ((LevelIndex.LEVEL_WIDTH - 15) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 6);
		g.drawString("P2 Team: " + getTeamString(teamPointer1),((LevelIndex.LEVEL_WIDTH - 10)) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 6);

		if (playersPerTeam > 1) {
		g.drawString("Player 3 Selection(Keyboard 1, NumberPad 123): " + p3, ((LevelIndex.LEVEL_WIDTH - 20) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 7);
		g.drawString("P3 Team: " + getTeamString(teamPointer2),((LevelIndex.LEVEL_WIDTH - 10)) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 7);
		
		
		g.drawString("Player 4 Selection(Keyboard 3, LeftDownRight): " + p4, ((LevelIndex.LEVEL_WIDTH - 20) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 8);
		g.drawString("P4 Team: " + getTeamString(teamPointer3),((LevelIndex.LEVEL_WIDTH - 10)) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 8);
		}
		
		if (playersPerTeam > 2) {
		g.drawString("Player 5 Selection(Keyboard 2, AddEnterSubtract): " + p5, ((LevelIndex.LEVEL_WIDTH - 20) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 9);
		g.drawString("P5 Team: " + getTeamString(teamPointer4),((LevelIndex.LEVEL_WIDTH - 10)) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 9);

		
		g.drawString("Player 6 Selection(Keyboard 3, UIO): " + p6, ((LevelIndex.LEVEL_WIDTH - 15) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 10);
		g.drawString("P6 Team: " + getTeamString(teamPointer5),((LevelIndex.LEVEL_WIDTH - 10)) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 10);
		}
		
		if (playersPerTeam > 3) {
		g.drawString("# of Additional Computers: " + (playersPerTeam * 2 - 6), ((LevelIndex.LEVEL_WIDTH - 15) / 2) * Block.BLOCK_SIZE, Block.BLOCK_SIZE * 13);
		}


	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_S) {
			teamPointerAdd(teamPointer0,0);
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			pointerSubtract(pointer0,0);
		}
		if (e.getKeyCode() == KeyEvent.VK_X) {
			pointerAdd(pointer0,0);
		}
		if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
			teamPointerAdd(teamPointer1,1);
		}
		if (e.getKeyCode() == KeyEvent.VK_COMMA) {
			pointerSubtract(pointer1,1);
		}
		if (e.getKeyCode() == KeyEvent.VK_SLASH) {
			pointerAdd(pointer1,1);
		}
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
			teamPointerAdd(teamPointer2,2);
		}
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
			pointerSubtract(pointer2,2);
		}
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
			pointerAdd(pointer2,2);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			teamPointerAdd(teamPointer3,3);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			pointerSubtract(pointer3,3);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			pointerAdd(pointer3,3);
		}
		if (e.getKeyCode() == KeyEvent.VK_ADD) {
			teamPointerAdd(teamPointer4,4);
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			pointerSubtract(pointer4,4);
		}
		if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
			pointerAdd(pointer4,4);
		}
		if (e.getKeyCode() == KeyEvent.VK_I) {
			teamPointerAdd(teamPointer5,5);
		}
		if (e.getKeyCode() == KeyEvent.VK_U) {
			pointerSubtract(pointer5,5);
		}
		if (e.getKeyCode() == KeyEvent.VK_O) {
			pointerAdd(pointer5,5);
		}
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			playersPerTeamAdd(playersPerTeam);
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			world.buildWorld();
			gui.state = State.GAME;
			Sound.menu.stop();
		}
	}
	
	public static void pointerAdd(int p, int pointerNumber) {
		p++;
		if (p > 9) {
			p = 1;
		}
		
		if (pointerNumber == 0) {
			pointer0 = p;
		}
		else if (pointerNumber == 1) {
			pointer1 = p;
		}
		else if (pointerNumber == 2) {
			pointer2 = p;
		}
		else if (pointerNumber == 3) {
			pointer3 = p;
		}
		else if (pointerNumber == 4) {
			pointer4 = p;
		}
		else if (pointerNumber == 5) {
			pointer5 = p;
		}
	}
	
	public void teamPointerAdd(int p, int pointerNumber) {
		p++;
		if (p > 2) {
			p = 0;
		}
		
		if (pointerNumber == 0) {
			teamPointer0 = p;
		}
		else if (pointerNumber == 1) {
			teamPointer1 = p;
		}
		else if (pointerNumber == 2) {
			teamPointer2 = p;
		}
		else if (pointerNumber == 3) {
			teamPointer3 = p;
		}
		else if (pointerNumber == 4) {
			teamPointer4 = p;
		}
		else if (pointerNumber == 5) {
			teamPointer5 = p;
		}
	}
	
	public void playersPerTeamAdd(int p) {
		p++;
		if (p > 8) {
			p = 1;
		}
		playersPerTeam = p;
	}
	
	public static void pointerSubtract(int p, int pointerNumber) {
		p--;
		if (p < 1) {
			p = 9;
		}
		
		
		if (pointerNumber == 0) {
			pointer0 = p;
		}
		else if (pointerNumber == 1) {
			pointer1 = p;
		}
		else if (pointerNumber == 2) {
			pointer2 = p;
		}
		else if (pointerNumber == 3) {
			pointer3 = p;
		}
		else if (pointerNumber == 4) {
			pointer4 = p;
		}
		else if (pointerNumber == 5) {
			pointer5 = p;
		}
	}
	
	public void teamPointerSubtract(int p, int pointerNumber) {
		p--;
		if (p < 0) {
			p = 2;
		}
		
		if (pointerNumber == 0) {
			teamPointer0 = p;
		}
		else if (pointerNumber == 1) {
			teamPointer1 = p;
		}
		else if (pointerNumber == 2) {
			teamPointer2 = p;
		}

	}
	
	public static String getClassString(int i) {
		if (i == 1) {
			return "scout";
		}
		else if(i == 2) {
			return "heavy";
		}
		else if (i == 3) {
			return "assault";
		}
		else if (i == 4) {
			return "sniper";
		}
		else if (i == 5) {
			return "shield";
		}
		else if (i == 6) {
			return "explosives";
		}
		else if (i == 7) {
			return "medic";
		}
		else if (i == 8) {
			return "spy";
		}
		else if (i == 9) {
			return "engineer";
		}
		return "assault";
	//	return "Whoops! There is an error!";
	}
	
	public static String getTeamString(int i) {
		if (i == 0) {
			return "blue";
		}
		else if(i == 1) {
			return "green";
		}
		else if (i == 2) {
			return "computer";
		}
		return "computer";
		//return "Whoops! There is an error with team!";
	}
	
	public static int getPointer(int i) {
		if (i == 0) {
			return pointer0;
		}
		else if(i == 1) {
			return pointer1;
		}
		else if (i == 2) {
			return pointer2;
		}
		else if(i == 3) {
			return pointer3;
		}
		else if (i == 4) {
			return pointer4;
		}
		else if (i == 5) {
			return pointer5;
		}
		return 3;//returns 3 for assault
	}
	
	public static int getTeamPointer(int i) {
		if (i == 0) {
			return teamPointer0;
		}
		else if(i == 1) {
			return teamPointer1;
		}
		else if (i == 2) {
			return teamPointer2;
		}
		else if(i == 3) {
			return teamPointer3;
		}
		else if (i == 4) {
			return teamPointer4;
		}
		else if (i == 5) {
			return teamPointer5;
		}
		return 2;//returns 2 for computer
	}
	
}
