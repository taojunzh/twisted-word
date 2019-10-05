package gui.event_handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.Model;

public class ResignButtonHandler implements ActionListener {
	private Model m;
	public ResignButtonHandler(Model m) {
		this.m=m;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		m.name="";
		m.scor=0;
		
		m.startNewGame();
	}

}
