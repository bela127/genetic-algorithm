package genetik;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasicRecombinator implements Recombinator{
	Random rand;
	int recombinationCount;
	int populationSize;
	
	public BasicRecombinator(int seed, int recombinationCount){
		rand = new Random(seed);
		this.recombinationCount = recombinationCount;
	}
	
	@Override
	public void recombinate(ArrayList<Dna> populationsDna) {
		populationSize = populationsDna.size();
		
		for(int i = 0; i < recombinationCount; i++) {

			while(true) {
				
			int index1= rand.nextInt(populationSize);
			int index2= rand.nextInt(populationSize);
			
			while(index1 == index2) {
				index2 = rand.nextInt(populationSize-1);
			}
			
			Dna dna1 = populationsDna.get(index1);
			Dna dna2 = populationsDna.get(index2);
			
			int dnaSize = dna1.gens.size();
			int dna2Size = dna2.gens.size();
			
			if(dnaSize <= 0) {
				populationsDna.remove(index1);
				populationSize--;
				continue;
			}
			
			if(dna2Size <= 0) {
				populationsDna.remove(index2);
				populationSize--;
				continue;
			}
			
			if(dnaSize > dna2Size) dnaSize = dna2Size;
			
			
			int splitIndex = rand.nextInt(dnaSize);
			
			List<Gen> gens1start = dna1.gens.subList(0,splitIndex);
			List<Gen> gens2start = dna2.gens.subList(0,splitIndex);
			List<Gen> gens1end = dna1.gens.subList(splitIndex, dnaSize);
			List<Gen> gens2end = dna2.gens.subList(splitIndex, dnaSize);
			
			ArrayList<Gen> gens1 = new ArrayList<Gen>();
			ArrayList<Gen> gens2 = new ArrayList<Gen>();
			
			
			gens1.addAll(gens1start);
			gens1.addAll(gens2end);	
			
			gens2.addAll(gens2start);
			gens2.addAll(gens1end);
			
			dna1.gens = gens1;
			dna2.gens = gens2;
			break;
			}
		}
	}

}
