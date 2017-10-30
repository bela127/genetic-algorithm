package genetik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BasicDnaSelector implements DnaSelector{
	Random rand;
	
	int populationSize;
	ArrayList<Dna> populationsDna;
	
	ArrayList<RouletteEntry> roulette;
	
	public BasicDnaSelector(int seed){
		rand = new Random(seed);
	}
	
	@Override
	public ArrayList<Dna> select(ArrayList<Individuum> population, double populationFitness) {
		populationSize = population.size();
		populationsDna = new ArrayList<Dna>();
		
		//------- Build roulette ---------
		roulette = new ArrayList<RouletteEntry>();
		double entryPosition = 0;
		for(Individuum individuum : population) {
			double entrySize = individuum.getFitness() / populationFitness;
			roulette.add(new RouletteEntry(entryPosition, entrySize, individuum.getDna()));
			entryPosition += entrySize;
		}
		
		//------- Spin roulette ---------
		for(int i = 0; i < populationSize; i++) {
			double position = rand.nextDouble();
			
			int index = Collections.binarySearch(roulette, position);
			
			if(index < 0) index =-1 *  index - 2;
			
			populationsDna.add(roulette.get(index).dna);
		}
		
		return populationsDna;
	}
	
	
	
	class RouletteEntry implements Comparable<Double>{
		Dna dna;
		double entryPosition;
		double entrySize;
		
		RouletteEntry(double entryPosition, double entrySize, Dna dna){
			this.entryPosition = entryPosition;
			this.entrySize = entrySize;
			this.dna = dna;
		}

		@Override
		public int compareTo(Double entryPosition) {
			if(this.entryPosition > entryPosition) return 1;
		    else if(this.entryPosition < entryPosition && this.entryPosition + entrySize > entryPosition) return 0;
		    else return -1;
		}
	}

}
