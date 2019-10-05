package gui.event_handlers;
import code.Model;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class textHandler implements ActionListener {
	private Model m;
	private String text;
	public textHandler(Model m, String text) {
		this.m=m;
		this.text=text;
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		m.name=text;
		m.notifyObservers();
		}

}
