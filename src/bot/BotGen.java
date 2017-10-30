package bot;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import simulation.Simulation;
import simulation.SimulationState;
import simulation.Simulation.GameOver;

public class BotGen {
	static Random rand;
	public int x;
	public int y;
	public boolean bomb = false;
	
	public BotGen(int x, int y, int seed) {
		this.x = x;
		this.y = y;
		rand = new Random(seed);
	}
	
	
	ArrayList<State> bestIndividuum = null;
	public void update(SimulationState simState) {
		ArrayList<ArrayList<State>> population = new ArrayList<ArrayList<State>>();
		
		int steps = 0;
		while(steps < 10000) {
			Simulation sim = Simulation.fromSimState(simState);
			BotRandom bot = new BotRandom(2500, 2500,rand.nextInt());
			ArrayList<State> individuum = runSim(sim,bot);
			population.add(individuum);
			steps++;
		}
		
		for(ArrayList<State> i : population) {
			if(i.size() == 0)continue;
			State state = i.get(i.size()-1);
			if(bestIndividuum == null || bestIndividuum.size() == 0 || state.score > bestIndividuum.get(bestIndividuum.size()-1).score) {
				bestIndividuum = i;
			}
		}
		
		if(bestIndividuum.size() > 0) {
			State state = bestIndividuum.get(0);
			this.x = state.x;
			this.y = state.y;
			this.bomb = state.bomb;
			bestIndividuum.remove(0);
		}

	}
	
	
	
	
	State loop(Simulation sim, BotRandom bot) throws GameOver {
		SimulationState simState = sim.getSimulationState();
		bot.update(simState);
		
		if(bot.bomb) {
			sim.update(true);
		}else {
			int x = bot.x;
			int y = bot.y;
			sim.update(new Point(x,y));
		}
		
		return new State(bot.bomb, bot.x, bot.y, sim.getScore());
	}
	
	class State{
		boolean bomb;
		int x;
		int y;
		int score;
		
		State(boolean bomb, int x, int y, int score){
			this.bomb = bomb;
			this.x = x;
			this.y = y;
			this.score = score;
		}
	}
	
	ArrayList<State> runSim(Simulation sim, BotRandom bot) {
		ArrayList<State> playerMoves = new ArrayList<State>();
		
		int steps = 0;
		while(steps < 60) {	
			try {
				playerMoves.add(loop(sim, bot));
			} catch (Simulation.GameOver e) {
				break;
			}
			steps++;
		}
		
		return playerMoves;
	}

}
