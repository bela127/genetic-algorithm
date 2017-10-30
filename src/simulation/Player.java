package simulation;

import java.awt.Point;

public class Player{
	Point position;
	int stepSize = 100;
	int health = 100;
	boolean isDead = false;
	
	Player(Point p){
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
	
	void damage(int damage) {
		health -= damage;
		if(health <= 0) {
			health = 0;
			isDead = true;
		}
	}
	
	@Override
	public String toString() {
		return position.x + " " + position.y + " " + health + "\n";
	}
}
