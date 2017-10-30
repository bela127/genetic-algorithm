package genetik;

import java.util.ArrayList;

public interface DnaSelector {
	
	ArrayList<Dna> select(ArrayList<Individuum> population, double populationFitness);
}
