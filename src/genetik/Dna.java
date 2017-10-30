package genetik;

import java.util.ArrayList;

public class Dna {
	ArrayList<Gen> gens;
	
	public Dna() {
		gens = new ArrayList<Gen>();
	}
	
	public Gen getGen(int index) {
		if(gens.size() == 0) return null;
		return gens.get(index);
	}
	
	public Gen removeGen(int index) {
		return gens.remove(index);
	}
}
