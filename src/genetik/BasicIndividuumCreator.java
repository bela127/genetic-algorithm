package genetik;

public class BasicIndividuumCreator implements IndividuumCreator{
	
	public BasicIndividuumCreator() {
		
	}

	@Override
	public BasicIndividuum create(Dna dna) {
		BasicIndividuum individuum = new BasicIndividuum(dna);
		return individuum;
	}
	

}
