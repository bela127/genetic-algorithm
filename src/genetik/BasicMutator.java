package genetik;

import java.util.ArrayList;
import java.util.Random;

public class BasicMutator implements Mutator{
	Random rand;
	DnaCreator dnaCreator;
	int mutationCount;
	
	public BasicMutator(int seed, DnaCreator dnaCreator, int mutationCount) {
		rand = new Random(seed);
		this.dnaCreator = dnaCreator;
		this.mutationCount = mutationCount;
	}

	@Override
	public void mutate(ArrayList<Dna> populationsDna) {
		int populationCount = populationsDna.size();
		
		for(int i = 0; i < mutationCount; i++) {
			Dna dna = dnaCreator.create();
			int index = rand.nextInt(populationCount);
			populationsDna.set(index, dna);
		}
		
	}

}
