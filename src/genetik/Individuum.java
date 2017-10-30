package genetik;

public interface Individuum {
	
	Dna getDna();

	void setFitness(double score);
	
	double getFitness();
}
