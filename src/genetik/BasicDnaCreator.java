package genetik;

public class BasicDnaCreator implements DnaCreator {
	GenCreator genCreator;
	int dnaSize;
	
	public BasicDnaCreator(GenCreator genCreator, int dnaSize){
		this.genCreator = genCreator;
		this.dnaSize = dnaSize;
	}
	
	@Override
	public Dna create() {
		Dna dna = new Dna();
		for(int i = 0; i < dnaSize; i++) {
			dna.gens.add(genCreator.create());
		}
		return dna;
	}

}
