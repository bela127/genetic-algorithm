package genetik;

import java.util.Random;

public class MyGenCreator implements GenCreator {
	Random rand;
	int width;
	int height;
	
	double bombLikelihood = 0.9;
	
	int x;
	int y;
	
	public MyGenCreator(int seed,int width,int height,double bombLikelihood){
		rand = new Random(seed);
		this.width = width;
		this.height = height;
		this.bombLikelihood = bombLikelihood;
	}
	
	@Override
	public MyGen create() {
		boolean bomb = false;
		
		if(rand.nextDouble() > bombLikelihood) {
			bomb = true;
		}else{
			x = rand.nextInt(width);
			y = rand.nextInt(height);
		}
 
		return new MyGen(bomb,x,y);
	}

}
