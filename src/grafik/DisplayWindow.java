package grafik;

import java.awt.HeadlessException;
import javax.swing.JFrame;

import simulation.SimulationState;

public class DisplayWindow extends JFrame{
	int width;
	int height;
	DrawArea drawArea;
	
	public DisplayWindow(int width, int height) throws HeadlessException{
		super("Zombie Shooter");
		
		this.width = width;
		this.height = height;
		
		setSize(600,600);
		drawArea = new DrawArea(600,600);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(drawArea);
		setVisible(true);
	}
	
	public void drawSimulationState(String state) {
		SimulationState s = new SimulationState(state,width,height);
		drawArea.updateSimulationState(s);
	}
	
}
