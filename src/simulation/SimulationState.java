package simulation;

import java.util.ArrayList;
import java.util.Scanner;

public class SimulationState {
	public int width;
	public int height;
	
	public int[] player;
	public ArrayList<int[]> zombies = new ArrayList<int[]>();
	public ArrayList<int[]> bombs= new ArrayList<int[]>();;
	public int score = 0;
	
	SimulationState(Map map,int score) {
		this.width = map.width;
		this.height = map.height;
		
		this.player = new int[3];
		player[0] = map.player.position.x;
		player[1] = map.player.position.y;
		player[2] = map.player.health;
		
		for(Zombie z : map.zombies) {
			int[] zombie = new int[2];
			zombie[0] = z.position.x;
			zombie[1] = z.position.y;
			zombies.add(zombie);
		}
		
		for(Bomb b : map.bombs) {
			int[] bomb = new int[3];
			bomb[0] = b.position.x;
			bomb[1] = b.position.y;
			bomb[2] = b.countdown;
			bombs.add(bomb);
		}
		
		this.score = score;
	}
	
	public SimulationState(String state,int width, int height) {
		this.width = width;
		this.height = height;
		
		Scanner scanner = new Scanner(state);
		String line = "";
		
		line = scanner.nextLine();
		int[] player = stringToInt(line.split(" "));
		this.player = player;
		
		line = scanner.nextLine();
		int[] zombieCount = stringToInt(line.split(" "));
		for(int i = 0; i < zombieCount[0]; i++) {
			line = scanner.nextLine();
			int[] zombie = stringToInt(line.split(" "));
			zombies.add(zombie);
		}
		
		line = scanner.nextLine();
		int[] bombCount = stringToInt(line.split(" "));
		for(int i = 0; i < bombCount[0]; i++) {
			line = scanner.nextLine();
			int[] bomb = stringToInt(line.split(" "));
			bombs.add(bomb);
		}
		
		line = scanner.nextLine();
		int[] score = stringToInt(line.split(" "));
		this.score = score[0];
		
		scanner.close();
	}
	
	public static int[] stringToInt(String[] s) {
		int[] converted = new int[s.length];
		
		for(int i = 0; i < s.length; i++) {
			converted[i] = Integer.parseInt(s[i]);
		}
		
		return converted;
	}
}
