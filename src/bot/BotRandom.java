package bot;

import java.util.Random;

import simulation.SimulationState;

public class BotRandom {
	Random rand;
	public int x;
	public int y;
	public boolean bomb = false;
	
	public BotRandom(int x, int y, int seed) {
		this.x = x;
		this.y = y;
		rand = new Random(seed);
	}
	
	public void update(SimulationState simState) {
		
		if(rand.nextDouble() > 0.9) {
			bomb = true;
		}else {
			bomb = false;
			if(x == simState.player[0] && y == simState.player[1]) {
				x = rand.nextInt(5000);
				y = rand.nextInt(5000);
			}
		}
		
	}

}
