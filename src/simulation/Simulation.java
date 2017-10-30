package simulation;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Point;



public class Simulation {
	Map map;
	int placedBombs = 0;
	int killScore = 0;
	boolean gameover = false;
	
	public Simulation(int width, int height) {
		map = new Map(width, height);
	}
	
	public void addZombies(ArrayList<Point> zombies){
		for(Point p : zombies) {
			map.addZombie(new Zombie(p));
		}
	}
	
	public void addPlayer(Point player){
			map.addPlayer(new Player(player));
	}
	
	
	public void update(boolean placeBomb) throws GameOver {
		map.addBomb(new Bomb(map.player.position));
		placedBombs++;
		update();
	}
	
	public void update(Point nextPosition) throws GameOver {
		updatePlayer(nextPosition);
		update();
	}
	
	void update() throws GameOver {
		isGameOver();
		if(gameover)throw new GameOver();
		updateBomb();
		updateZombie();
	}
	
	void isGameOver(){
		if(map.player.isDead) gameover = true;
		if(map.zombies.size() == 0) gameover = true;
	}
	
	void updatePlayer(Point nextPosition) {
	    if (map.isInMap(nextPosition)) map.player.move(nextPosition);
        else throw new RuntimeException("tried moving outside the Map");
		
	}
	
	void updateBomb() {
		Iterator<Bomb> iterator = map.bombs.iterator();
		while(iterator.hasNext()) {
			Bomb b = iterator.next();
			if(b.isExploded) iterator.remove();
			else {
				b.tick();
				if(b.countdown <= 0)b.explode(map.player, map.zombies);
			}
		}
	}
	
	void updateZombie() {
		Iterator<Zombie> iterator = map.zombies.iterator();
		int kills = 0;
		while(iterator.hasNext()) {
			Zombie z = iterator.next();
			if(z.isDead) {
				iterator.remove();
				kills++;
			}
			else {
				z.move(map.player.position);
				if(z.position.equals(map.player.position))z.hit(map.player);
			}
		}
		int zombieScore = kills * 100;
		killScore += kills * zombieScore;
	}
	
	public class GameOver extends Exception{
		public GameOver() {
	        super("Game Over");
	    }
	}
	
	//-------------for simulation only-------------
	
	public static Simulation fromSimState(SimulationState state) {
		Simulation sim = new Simulation(state.width,state.height);
		
		Point player = new Point(state.player[0],state.player[1]);
		sim.addPlayer(player,state.player[2]);
		
		ArrayList<Point> zombies = new ArrayList<Point>();
        for(int[] zombie : state.zombies) {
            zombies.add(new Point(zombie[0],zombie[1]));
        }
		sim.addZombies(zombies);
		
		for(int[] bomb : state.bombs) {
            sim.addBomb(bomb[0], bomb[1], bomb[2]);
        }
		
        return sim;
}
	
	public void addBomb(int x, int y, int countdown){
		Bomb b = new Bomb(new Point(x,y));
		b.countdown = countdown;
		map.addBomb(b);
	}
	
	public void addPlayer(Point player, int health){
		Player p = new Player(player);
		p.health = health;
		map.addPlayer(p);
	}
	
	public int getScore() {
		int score = map.player.health * 10 - placedBombs * 20 + killScore;
		return score;
	}
	
	public SimulationState getSimulationState() {
	   return new SimulationState(map,getScore());
	}
	
	public String getSimulationStateString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(map.player.toString());
		
		sb.append(map.zombies.size() + "\n");
		for(Zombie z : map.zombies) {
			sb.append(z.toString());
		}
		
		sb.append(map.bombs.size() + "\n");
		for(Bomb b : map.bombs) {
			sb.append(b.toString());
		}
		
		sb.append(getScore() + "\n");
		
		return sb.toString();
	}

}
