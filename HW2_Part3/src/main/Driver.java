package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import code.Model;
import gui.GUI;

public class Driver implements Runnable {
	
	private Model _model;
	private JFrame _window;
	private JPanel _mainPanel;
	
	public Driver(Model m) {
		_model = m;
	}
	
	public static void main(String[] args) {
		Model m = new Model("dictionaries/large.txt", "dictionaries/HW2.config" );
		SwingUtilities.invokeLater(new Driver(m));
	}

	@Override
	public void run() {
		_window = new JFrame("TwistyWord");
		_mainPanel = new JPanel();
		_window.getContentPane().add(_mainPanel);

		new GUI(_model, _mainPanel, this);
		
		_window.setVisible(true);
		_window.pack();
		_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	public void updateJFrame() {
		_window.pack();
		_window.repaint();
	}
}
