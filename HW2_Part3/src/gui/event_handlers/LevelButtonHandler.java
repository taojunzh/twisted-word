package gui.event_handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.Model;

public class LevelButtonHandler implements ActionListener {
	private Model m;
	public LevelButtonHandler(Model m) {
		this.m=m;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		m.startNewGame();
		m.notifyObservers();
	}

}
