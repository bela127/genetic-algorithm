package simulation;
import java.util.ArrayList;
import java.awt.Point;

public class Map {
	Player player;
	ArrayList<Zombie> zombies;
	ArrayList<Bomb> bombs;
	
	int width;
	int height;
	
	Map(int width, int height) {
		this.width = width;
		this.height = height;
		
		zombies = new ArrayList<Zombie>();
		bombs = new ArrayList<Bomb>();
	}
	
	void addPlayer(Player p){
		if (isInMap(p.position)) player = p;
		else throw new RuntimeException("Player not in map or map occupied " + p.position);
	}
	
	void addZombie(Zombie z){
		if (isInMap(z.position)) zombies.add(z);
		else throw new RuntimeException("Zombie not in map or map occupied " + z.position);
	}
	
	void addBomb(Bomb b){
		if (isInMap(b.position)) bombs.add(b);
		else throw new RuntimeException("Bomb not in Map");
	}
	
	boolean isInMap(Point p) {
		return isInMap(p.x, p.y);
	}
	
	boolean isInMap(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height);
	}
	
}
