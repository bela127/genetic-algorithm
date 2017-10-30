package genetik;

public class BasicIndividuum implements Individuum{
	Dna dna;
	double score;
	
	public BasicIndividuum(Dna dna) {
		this.dna = dna;
	}
	
	@Override
	public void setFitness(double score) {
		this.score = score;
	}
	
	@Override
	public double getFitness() {
		return score;
	}

	@Override
	public Dna getDna() {
		return dna;
	}
}
