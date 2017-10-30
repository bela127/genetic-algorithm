package simulation;

import java.awt.Point;
import java.util.ArrayList;

public class Bomb{
	Point position;
	int countdown = 10;
	int radius = 500;
	int damage = 50;
	boolean isExploded = false;
	
	Bomb(Point p){
		position = (Point) p.clone();
	}
	
	void tick() {
		countdown--;
	}
	
	void explode(Player player, ArrayList<Zombie> zombies) {
		isExploded = true;
		if(inRadius(player.position)) {
			player.damage(damage);
		}
		
		for(Zombie z : zombies) {
			if(inRadius(z.position)) {
				z.kill();
			}
		}
	}
	
	boolean inRadius(Point position) {
		return (this.position.distance(position) < radius);
	}
	
	@Override
	public String toString() {
		return position.x + " " + position.y + " " + countdown + "\n";
	}
}
