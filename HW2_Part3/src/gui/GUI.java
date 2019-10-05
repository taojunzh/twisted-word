package gui;
import gui.event_handlers.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.Timer;

import code.Model;
import code.Observer;
import main.Driver;

public class GUI implements Observer {

	private JPanel _inventoryPanel;
	private JPanel _guessPanel;
	private Model _model;
	private Driver _windowHolder;
	private JPanel words;
	private JPanel message;
	private JPanel control;
	private JPanel score;
	private JTextField text;
	private int level=1;

	
	public GUI(Model m, JPanel mp, Driver driver) {
		_windowHolder = driver;
		_model = m;
		JPanel _mainPanel = mp;
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
		
		words= new JPanel();
		words.setLayout(new GridLayout(5,5,0,0));
		_mainPanel.add(words);
		
		message=new JPanel();
		message.setLayout(new BoxLayout(message, BoxLayout.X_AXIS));
		_mainPanel.add(message);

		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
		_mainPanel.add(middlePanel);
		
		control=new JPanel();
		control.setLayout(new BoxLayout(control, BoxLayout.X_AXIS));
		_mainPanel.add(control);
		
		score= new JPanel();
		score.setLayout(new BoxLayout(score, BoxLayout.X_AXIS));
		_mainPanel.add(score);
		
		text=new JTextField("");
		_mainPanel.add(text);
		
		_inventoryPanel = new JPanel();
		_inventoryPanel.setLayout(new BoxLayout(_inventoryPanel, BoxLayout.X_AXIS));

		_guessPanel = new JPanel();
		_guessPanel.setLayout(new BoxLayout(_guessPanel, BoxLayout.X_AXIS));
	
		middlePanel.add(new JLabel("INVENTORY: "));
		middlePanel.add(_inventoryPanel);
		middlePanel.add(new JLabel("GUESS: "));
		middlePanel.add(_guessPanel);
		
		
		_model.startNewGame();
		_model.addObserver(this);
		if (_model.words().size()<_model.MinWordPerTurn) {
			_model.startNewGame();
			_model.addObserver(this);
		}
		
	}
	
	@Override
	public void update() {
		if(_model.name.length()==0) {
			_inventoryPanel.removeAll();
			_guessPanel.removeAll();
			words.removeAll();
			control.removeAll();
			score.removeAll();
			message.removeAll();
			level=1;
			words.add(text);
			JButton n = new JButton("Start");
			setButtonProperties(n);
			n.addActionListener(new textHandler(_model, text.getText()));
			words.add(n);
			updateJFrameIfNotHeadless();
		}else {
		_inventoryPanel.removeAll();
		ArrayList<Character> inventoryLetters = _model.getInventoryLetters();
		for (int i=0; i<inventoryLetters.size(); i=i+1) {
			JButton b = new JButton(""+inventoryLetters.get(i));
			setButtonProperties(b);
			_inventoryPanel.add(b);
			b.addActionListener(new InventoryButtonHandler(_model, i));
		}
		_guessPanel.removeAll();
		ArrayList<Character> guessLetters = _model.getGuessLetters();
		for (int i=0; i<guessLetters.size(); i=i+1) {
			JButton b = new JButton(""+guessLetters.get(i));
			setButtonProperties(b);
			_guessPanel.add(b);
			b.addActionListener(new GuessButtonHandler(_model, i));
		}
		words.removeAll();
		ArrayList<String> word= _model.words();
			for(String x: word) {
			JLabel b = new JLabel(x);
			setLabelProperties(b);
			words.add(b);
		}
		control.removeAll();
		JButton b = new JButton("shuffle");
		JButton bu = new JButton("clear");
		JButton but = new JButton("submit");
		JButton butt = new JButton("resign");
		setButtonProperties(b);
		setButtonProperties(bu);
		setButtonProperties(but);
		setButtonProperties(butt);
		b.addActionListener(new ShuffleButtonHandler(_model, inventoryLetters.size()));
		bu.addActionListener(new ClearButtonHandler(_model, guessLetters.size()));
		but.addActionListener(new SubmitButtonHandler(_model, guessLetters.size()));
		butt.addActionListener(new ResignButtonHandler(_model));
		control.add(b);
		control.add(bu);
		control.add(but);
		control.add(butt);
		
		score.removeAll();
		JLabel sco = new JLabel(text.getText()+" Score: "+ _model.scor);
		JLabel lv = new JLabel("Level: "+level);
		setLabelProperties(sco);
		setLabelProperties(lv);
		score.add(sco);
		score.add(lv);		

		if(_model.lastSubmittedWordWasValid()==0) {
			message.removeAll();
			JLabel button = new JLabel("Make a guess!");
			setLabelProperties(button);
			message.add(button);
			updateJFrameIfNotHeadless();
		}
		
		
		if(_model.lastSubmittedWordWasValid()==-1) {
			
			message.removeAll();
			JLabel JLabel = new JLabel("Sorry, try again!");
			JLabel.setFont(new Font("Courier", Font.BOLD, _model.font));
			JLabel.setBackground(Color.BLACK);
			JLabel.setForeground(Color.RED);
			JLabel.setOpaque(true);
			JLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
			message.add(JLabel);
			updateJFrameIfNotHeadless();
			
		}
		if(_model.lastSubmittedWordWasValid()==1) {
			level=1;
			for(int i = 0; i<word.size();i++) {
				if(word.get(i).length()==word.get((word.size()-1)).length()&&_model._wordsToFind.get(_model.wordss().get(i))) {
					
					level=level+1;
					score.removeAll();
					JLabel scor = new JLabel(text.getText()+" Score: "+ _model.scor);
					JButton lvl = new JButton("Enter Level: "+level);
					setLabelProperties(scor);
					setButtonProperties(lvl);
					lvl.addActionListener(new LevelButtonHandler(_model));
					score.add(scor);
					score.add(lvl);
				}
			}
			message.removeAll();
			JLabel JLabel = new JLabel("Congratulations, you found a word!");
			JLabel.setFont(new Font("Courier", Font.BOLD, _model.font));
			JLabel.setBackground(Color.BLACK);
			JLabel.setForeground(Color.GREEN);
			JLabel.setOpaque(true);
			JLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
			message.add(JLabel);
			updateJFrameIfNotHeadless();
		}}
		// This should be last statement of this method:
	}

	public void updateJFrameIfNotHeadless() {
		if (_windowHolder != null) {
			_windowHolder.updateJFrame();
		}
	}

	public void setButtonProperties(JButton button) {
		button.setFont(new Font("Courier", Font.BOLD, _model.font));
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}

	public void setLabelProperties(JLabel label) {
		label.setFont(new Font("Courier", Font.BOLD, _model.font));
		label.setBackground(Color.WHITE);
		label.setForeground(Color.BLACK);
		label.setOpaque(true);
		label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}
	

}
