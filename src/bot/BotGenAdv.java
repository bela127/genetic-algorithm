package bot;

import java.util.Random;

import genetik.*;
import simulation.SimulationState;

public class BotGenAdv {
	Random rand;
	public int x;
	public int y;
	public boolean bomb = false;
	
	Evolution ev;
	DnaCreator dc;
	GenCreator gc;
	IndividuumCreator ic;
	MyFitnessCalculator fc;
	DnaSelector ds;
	Recombinator rc;
	Mutator mt;
	
	public BotGenAdv(int x, int y, int seed) {
		rand = new Random(seed);
		this.x = x;
		this.y = y;
		
		int populationSize = 5000;
		gc = new MyGenCreator(rand.nextInt(), 5000, 5000, 0.9);
		dc = new BasicDnaCreator(gc, 20);
		ic = new BasicIndividuumCreator();
		fc = new MyFitnessCalculator(150);
		ds = new BasicDnaSelector(rand.nextInt());
		rc = new BasicRecombinator(rand.nextInt(), (int)(populationSize * 0.2));
		mt = new BasicMutator(rand.nextInt(),dc, (int)(populationSize * 0.2));
		ev = new Evolution(dc, ic, fc, ds, rc, mt, populationSize, 15);
		
		ev.newPopulation();
	}
	
	
	public void update(SimulationState simState) {
		x = simState.player[0];
		y = simState.player[1];
		bomb = false;
		
		fc.updateSimulationState(simState);
		ev.evolve();
		Individuum individuum = ev.getFitestIndividuum();
		Dna dna = individuum.getDna();
		MyGen gen = (MyGen)dna.getGen(0);
		if(gen == null)return;
		bomb = gen.bomb;
		
		if(x == simState.player[0] && y == simState.player[1]) {;
			x = gen.x;
			y = gen.y;
			//dna.removeGen(0);
		}

	}
	

}
