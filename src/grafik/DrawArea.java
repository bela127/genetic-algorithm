package grafik;

import java.awt.*;

import javax.swing.JPanel;

import simulation.SimulationState;

public class DrawArea extends JPanel{
	int width;
	int height;
	SimulationState state;
	
	public DrawArea(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		
		if(state != null) {
			g.setColor(Color.blue);
			g.fillOval(state.player[0]*width/state.width-10,state.player[1]*height/state.height -10, 20, 20);
			g.setColor(Color.red);
			g.drawString("HP: " + state.player[2], state.player[0]*width/state.width-8, state.player[1]*height/state.height+4);
			
			for(int[] bomb : state.bombs) {
				if(bomb[2] == 0) {
					g.setColor(Color.orange);
					g.fillOval(bomb[0]*width/state.width-500*height/state.height,bomb[1]*height/state.height -500*height/state.height, 1000*height/state.height, 1000*height/state.height);
				}
				g.setColor(Color.black);
				g.fillOval(bomb[0]*width/state.width-10,bomb[1]*height/state.height -10, 20, 20);
				g.setColor(Color.orange);
				g.drawString("" + bomb[2], bomb[0]*width/state.width-3, bomb[1]*height/state.height+4);
			}
			
			for(int[] zombie : state.zombies) {
				g.setColor(Color.green);
				g.fillOval(zombie[0]*width/state.width-10,zombie[1]*height/state.height -10, 20, 20);
			}
			
			g.setColor(Color.black);
			g.drawString("Score: " + state.score, 50, 50);
			
			g.setColor(Color.red);
			g.drawString("HP: " + state.player[2], 500, 50);
			
		}
	}
	
	public void updateSimulationState(SimulationState state) {
		this.state = state;
		repaint();
	}
	
}
