package demo;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import grafik.DisplayWindow;
import simulation.Simulation;
import simulation.SimulationState;

public class Anzeige {

	public static void main(String[] args) {

		Path initPath = Paths.get(args[0], args[1]);
		Path inPath = Paths.get(args[0], args[2]);

		Simulation sim = initSim(initPath.toString());

		String simString = runSim(sim, inPath.toString());

		int score = sim.getScore();

		System.out.println(simString);

		displaySim(simString);

		System.out.println(score);
		
	}
	
	static void display(String[] simStates, DisplayWindow window) {
		int index = simStates.length - 1;
		String simState = simStates[index];
		window.drawSimulationState(simState);
		System.out.println("r for replay, n for next Step, p for previews Step, any Number for Step.");
		Scanner in = new Scanner(System.in);
		while(true) {
			if(in.hasNextInt()) {
				int i = in.nextInt();
				if(i >= 0 && i < simStates.length) {
					index = i;
				}
			}else if(in.hasNext()) {
				String i = in.next();
				switch(i) {
				case "n":
					if(index + 1 < simStates.length) {
						index++;
					}
					break;
				case "p":
					if(index - 1 >= 0) {
						index--;
					}
					break;
				case "r":
					for(int j = 0; j < simStates.length; j++) {
						System.out.println(j);
						simState = simStates[j];
						System.out.println(simState);
						window.drawSimulationState(simState);
						try {
							Thread.sleep(120);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					break;
				case "q":
					window.dispose();
					return;
				default:
					System.out.println("Unknown Comand");
					break;
				}
			}
			System.out.println(index);
			simState = simStates[index];
			System.out.println(simState);
			window.drawSimulationState(simState);
			while(!in.hasNext());
		}
	}

	static void displaySim(String simString) {
		ArrayList<String> simStates = new ArrayList<String>();
		
		Scanner scanner = new Scanner(simString);
		String line = "";

		line = scanner.nextLine();
		int[] size = SimulationState.stringToInt(line.split(" "));
		DisplayWindow window = new DisplayWindow(size[0], size[1]);

		StringBuilder simState = new StringBuilder();
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if (!line.equals("#")) {
				simState.append(line + "\n");
			} else {
				simStates.add(simState.toString());
				window.drawSimulationState(simState.toString());
				simState = new StringBuilder();

				try {
					Thread.sleep(60);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		scanner.close();
		String[] simArray = simStates.toArray(new String[simStates.size()]);
		display(simArray, window);
	}

	static String runSim(Simulation sim, String playerInputPath) {
		File file = new File(playerInputPath);
		System.out.println(file.getAbsolutePath());

		Scanner input;
		try {
			input = new Scanner(file);
			String line = "";

			StringBuilder simSB = new StringBuilder();
			simSB.append(sim.getSimulationState().width + " " + sim.getSimulationState().height + "\n");

			System.out.println("Loop");
			int steps = 0;
			while (steps < 200 && input.hasNextLine()) {
				String state = sim.getSimulationStateString();
				simSB.append(state);
				simSB.append("#\n");

				try {
					line = input.nextLine();
					if (line.equals("true"))
						sim.update(true);
					else {
						int[] playerPos = SimulationState.stringToInt(line.split(" "));
						sim.update(new Point(playerPos[0], playerPos[1]));
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

	static Simulation initSim(String simFilePath) {
		System.out.println("Init");

		File file = new File(simFilePath);
		System.out.println(file.getAbsolutePath());

		Scanner input;
		try {
			input = new Scanner(file);

			String line = "";

			line = input.nextLine();
			int[] size = SimulationState.stringToInt(line.split(" "));
			Simulation sim = new Simulation(size[0], size[1]);

			line = input.nextLine();
			int[] playerPos = SimulationState.stringToInt(line.split(" "));
			Point player = new Point(playerPos[0], playerPos[1]);
			sim.addPlayer(player);

			ArrayList<Point> zombies = new ArrayList<Point>();
			while (input.hasNextLine()) {
				line = input.nextLine();
				int[] zombiePos = SimulationState.stringToInt(line.split(" "));
				zombies.add(new Point(zombiePos[0], zombiePos[1]));
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
