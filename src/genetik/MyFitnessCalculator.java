package genetik;

import java.awt.Point;
import java.util.Iterator;

import simulation.Simulation;
import simulation.SimulationState;

public class MyFitnessCalculator implements FitnessCalculator{	
	SimulationState simState;
	int simulationSteps;
	
	public MyFitnessCalculator(int simulationSteps) {
		this.simulationSteps = simulationSteps;
	}
	
	@Override
	public double fitnessOf(Individuum individuum) {
		Simulation sim = Simulation.fromSimState(simState);
		runSim(sim,(BasicIndividuum) individuum);
		return sim.getScore();
	}
	
	void runSim(Simulation sim, BasicIndividuum individuum) {
		int x = simState.player[0];
		int y = simState.player[1];
		boolean bomb = false;
		
		Iterator<Gen> iterator = individuum.dna.gens.iterator();
		for(int i = 0; i < simulationSteps && iterator.hasNext(); i++) {	
			try {
				MyGen gen = (MyGen)iterator.next();
				bomb = gen.bomb;
				
				if(x == simState.player[0] && y == simState.player[1]) {
					x = gen.x;
					y = gen.y;
				}
				
				if(bomb) {
					sim.update(true);
				}else {
					sim.update(new Point(x,y));
				}

			} catch (Simulation.GameOver e) {
				break;
			}
		}
	}

	public void updateSimulationState(SimulationState simState) {
		this.simState = simState;
	}
}
