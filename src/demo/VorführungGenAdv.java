package demo;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import bot.BotGenAdv;
import grafik.DisplayWindow;
import simulation.Simulation;
import simulation.Simulation.GameOver;
import simulation.SimulationState;

public class VorführungGenAdv {

	public static void main(String[] args) {

		Path initPath = Paths.get(args[0], args[1]);
		Path inPath = Paths.get(args[0], args[2]);
		Path outPath = Paths.get(args[0], args[3]);

		Simulation sim = initSim(initPath.toString());

		String simString = runSim(sim, inPath.toString());

		try {
			File file = new File(outPath.toString());
			PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
			System.out.println(file.getAbsolutePath());
			writer.println(simString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(simString);

		displaySim(simString);
	}

	static void displaySim(String simString) {
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

	static String runSim(Simulation sim, String playerInputPath) {
		try {
			File file = new File(playerInputPath);
			PrintWriter writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
			System.out.println(file.getAbsolutePath());

			StringBuilder simSB = new StringBuilder();
			simSB.append(sim.getSimulationState().width + " " + sim.getSimulationState().height + "\n");

			System.out.println("Loop");
			int steps = 0;
			while (steps < 200) {
				String state = sim.getSimulationStateString();
				simSB.append(state);
				simSB.append("#\n");

				try {
					loop(sim, writer);
				} catch (Simulation.GameOver e) {
					e.printStackTrace();
					break;
				}

				steps++;
			}

			writer.close();
			return simSB.toString();

		} catch (IOException e) {
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
			return null;
		}
	}

	static BotGenAdv bot = new BotGenAdv(2500, 2500, 42);

	static void loop(Simulation sim, PrintWriter out) throws GameOver {
		SimulationState simState = sim.getSimulationState();
		bot.update(simState);

		if (bot.bomb) {
			System.out.println("true");
			out.println("true");
			sim.update(true);
		} else {
			int x = bot.x;
			int y = bot.y;

			System.out.println(x + " " + y);
			out.println(x + " " + y);
			sim.update(new Point(x, y));
		}

	}

}
