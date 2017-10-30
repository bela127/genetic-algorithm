package simulation;
import java.awt.Point;

public class Zombie{
	Point position;
	int stepSize = 50;
	int damage = 30;
	boolean isDead = false;
	
	
	Zombie(Point p){
		position = (Point) p.clone();
	}
	
	void move(Point position) {
		int dx = position.x - this.position.x;
		int dy = position.y - this.position.y;
		double dist = this.position.distance(position);
		double moveFactor = stepSize / dist;
		if(moveFactor >= 1) this.position.setLocation(position);
		else {
			Point newPosition = new Point((int) (this.position.x + dx * moveFactor), (int) (this.position.y + dy * moveFactor));
			this.position.setLocation(newPosition);
		}
	}
	
	void kill() {
		isDead = true;
	}
	
	void hit(Player p) {
		p.damage(damage);
	}
	
	@Override
	public String toString() {
		return position.x + " " + position.y + "\n";
	}
	
	
}
