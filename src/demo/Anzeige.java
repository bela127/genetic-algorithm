package demo;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import grafik.DisplayWindow;
import simulation.Simulation;
import simulation.SimulationState;

public class Anzeige {
	

	public static void main(String[] args) {
		
		Simulation sim = initSim("init2.sim");
		
		String simString = runSim(sim,"player.simin");
		
		System.out.println(simString);
		
		displaySim(simString);
	}
	
	static void displaySim(String simString) {
		Scanner scanner = new Scanner(simString);
		String line = "";
		
		line = scanner.nextLine();
		int[] size = SimulationState.stringToInt(line.split(" "));
		DisplayWindow window = new DisplayWindow(size[0],size[1]);
		
		StringBuilder simState = new StringBuilder();
		while(scanner.hasNextLine()) {
			line = scanner.nextLine();
			if(!line.equals("#")) {
				simState.append(line + "\n");
			}else{
				window.drawSimulationState(simState.toString());
				simState = new StringBuilder();
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		scanner.close();
	}
	
	
	static String runSim(Simulation sim, String playerInput) {
		URL url = Vorf�hrungRand.class.getResource(playerInput);
		File file = new File(url.getPath());
        System.out.println(file.getAbsolutePath());
        
        Scanner input;
		try {
			input = new Scanner(file);
			String line = "";
			

		
		StringBuilder simSB = new StringBuilder();
		simSB.append(sim.getSimulationState().width + " " + sim.getSimulationState().height + "\n");
		
		System.out.println("Loop");
		int steps = 0;
		while(steps < 200 && input.hasNextLine()) {
			String state = sim.getSimulationStateString();
			simSB.append(state);
			simSB.append("#\n");
			
			try {
				line = input.nextLine();
					if(line.equals("true"))sim.update(true);
					else {
						int[] playerPos = SimulationState.stringToInt(line.split(" "));
						sim.update(new Point(playerPos[0],playerPos[1]));
					}
			} catch (Simulation.GameOver e) {
				e.printStackTrace();
				break;
			}
			
			steps++;
		}
		
		input.close();
		return simSB.toString();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}
	
	static Simulation initSim(String simFile) {
		System.out.println("Init");
		
		URL url = Anzeige.class.getResource(simFile);
		File file = new File(url.getPath());
        System.out.println(file.getAbsolutePath());

            Scanner input;
			try {
				input = new Scanner(file);
				
				String line = "";
				
				line = input.nextLine();
				int[] size = SimulationState.stringToInt(line.split(" "));
				Simulation sim = new Simulation(size[0],size[1]);
				
				line = input.nextLine();
				int[] playerPos = SimulationState.stringToInt(line.split(" "));
				Point player = new Point(playerPos[0],playerPos[1]);
				sim.addPlayer(player);
				
				ArrayList<Point> zombies = new ArrayList<Point>();
	            while (input.hasNextLine()) {
	                line = input.nextLine();
	                int[] zombiePos = SimulationState.stringToInt(line.split(" "));
	                zombies.add(new Point(zombiePos[0],zombiePos[1]));
	            }
				sim.addZombies(zombies);

	            input.close();
	            return sim;
	            
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		return null;
	}

}