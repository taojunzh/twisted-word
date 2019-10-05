package gui.event_handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.Model;

public class InventoryButtonHandler implements ActionListener {
	private int _i;
	private Model _m;

	public InventoryButtonHandler(Model m, int i) {
		_i = i;
		_m = m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		_m.moveLetterFromInventoryToGuess(_i);
	}
}
