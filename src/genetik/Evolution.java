package genetik;

import java.util.ArrayList;

public class Evolution {
	DnaCreator dnaCreator;
	IndividuumCreator individuumCreator;
	FitnessCalculator fitnessCalculater;
	DnaSelector dnaSelector;
	Recombinator recombinator;
	Mutator mutator;
	int populationSize;
	int generations;
	
	ArrayList<Individuum> population;
	double populationFitness;
	ArrayList<Dna> populationsDna;
	Individuum fitestIndividuum;
	
	public Evolution(DnaCreator dnaCreator, IndividuumCreator individuumCreator,FitnessCalculator fitnessCalculater, DnaSelector dnaSelector, Recombinator recombinator, Mutator mutator, int populationSize, int generations) {
		this.dnaCreator = dnaCreator;
		this.individuumCreator = individuumCreator;
		this.fitnessCalculater = fitnessCalculater;
		this.dnaSelector = dnaSelector;
		this.recombinator = recombinator;
		this.mutator = mutator;
		this.populationSize = populationSize;
		this.generations = generations;
		
		population = new ArrayList<Individuum>();
	}
	
	public void newPopulation() {
		populate();
	}
	
	public void evolve(){
		live();
		for(int i = 0; i < generations; i++) {
			selection();
			recombination();
			mutation();
			live();
		}
		
	}
	
	
	void mutation(){
		mutator.mutate(populationsDna);
	}
	
	void recombination() {
		recombinator.recombinate(populationsDna);
	}
	
	void selection() {
		populationsDna = dnaSelector.select(population,populationFitness);
	}
	
	void live() {
		populationFitness = 0;
		for(Individuum individuum : population) {
			double fitness = fitnessCalculater.fitnessOf(individuum);
			populationFitness += fitness;
			individuum.setFitness(fitness);
		}
	}
	
	void populate() {
		for(int i = 0; i < populationSize; i++) {
			Dna dna = dnaCreator.create();
			population.add(individuumCreator.create(dna));
		}
	}

	public Individuum getFitestIndividuum() {
		if(fitestIndividuum == null) {
			fitestIndividuum = population.get(0);
		}
		
		
		for(Individuum individuum : population) {
			if(individuum.getFitness() > fitestIndividuum.getFitness()) {
				fitestIndividuum = individuum;
			}
		}
		return fitestIndividuum;
	}
	
	public ArrayList<Individuum> getPopulation(){
		return population;
	}
}
