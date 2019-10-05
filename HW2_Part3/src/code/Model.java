package code;

import java.util.Random;


import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;


public class Model {
	
	// Determines the maximum length of a word
	private int MAXIMUM_WORD_LENGTH =7;

	// Determines the maximum length of a word
	private int MINIMUM_WORD_LENGTH =3;

	// Holds all words from the dictionary file that have lengths between the max and min, inclusive
	private ArrayList<String> _words;
	
	// Holds all words from the dictionary file that have the max length
	private ArrayList<String> _seedWords;
	
	// Holds all words from _words that must be found by the player
	public HashMap<String,Boolean> _wordsToFind;

	// The letters in the inventory of this game
	private ArrayList<Character> _inventoryLetters;

	// The letters in the current guess
	private ArrayList<Character> _guessLetters;

	// The list of Observers for this Model
	private ArrayList<Observer> _observers;	

	/** PART 3 **/
	/** DECLARE ANY ADDITIONAL INSTANCE VARIABLE(S) HERE **/
	private boolean submitted;
	public int MaxWordPerTurn=16;
	public int MinWordPerTurn=5;
	private String word ;
	public int font=44;
	public String name="";
	public int scor=0;

	/** PART 3 **/
	/** METHODS YOU MUST DEFINE **/

	
	/*
	 * Define this method so that it does the initialization that needed at the start of 
	 * each new game:
	 *   - pick a seed word at random from amongst all the seed words
	 *   - generate the list of words that the player must find
	 *   - initialize the inventory letters (and shuffle them)
	 *   - initialize the guess letters to an empty ArrayList
	 *   - initialize any other instance variables you may declare appropriately
	 *   
	 * HINT: this method must be called when the program is started, once the GUI is set up.
	 */
	public void startNewGame() {
		Random ran = new Random();
		int n = ran.nextInt(_seedWords.size());
		String x = _seedWords.get(n);
		generateWordsToFind(x);
		_inventoryLetters = string2charList(x);
		shuffle();
		_guessLetters=new ArrayList<>();
	}
	public HashMap<String, Boolean> getWordsToFind() {
		return _wordsToFind;
	}
	
	public void getscore() {
		int score=0;
		for(String x:_wordsToFind.keySet()) {
			if(_wordsToFind.get(x)) {
				score = score+ x.length();
				scor=score;
			}
		}
	}
	
	/*
	 * Define this method so that it clears the guess by moving all the letters from the guess
	 * back to the inventory, and then notifies all Observers.
	 * 
	 * HINT: this method must be called when the 'clear' button is clicked
	 */
	public void clear() {
		for(int i=0; _guessLetters.size()>0; i++) {
			_inventoryLetters.add(_guessLetters.remove(0));
		}
		submitted=false;
		notifyObservers();
	}
	
	/*
	 * Define this method so that it shuffles all the letters from the inventory, and then
	 * notifies all Observers.
	 * 
	 * HINT: this method must be called when the 'shuffle' button is clicked
	 */
	public void shuffle() {
		Collections.shuffle(_inventoryLetters);
		submitted = false;
		notifyObservers();
		}
			
	
	/*
	 * Define this method so it checks to see whether the current guess is one of the words
	 * to be found.  If so, the word that was found must be paired with the Boolean value 
	 * true in the _wordsToFind HashMap.
	 * Must also clear the guess and notify Observers.  (Hint: see clear method above.)
	 * 
	 * HINT: this method must be called when the 'submit' button is clicked
	 */
	public void submit() {
		submitted = true;
		word = charList2string(_guessLetters);
		if(checkGuess(word)) {
			for(int i=0; 0 <_guessLetters.size(); i++) {
				_inventoryLetters.add(_guessLetters.remove(0));
			}
			notifyObservers();
		}
		for(int i=0; 0 <_guessLetters.size(); i++) {
			_inventoryLetters.add(_guessLetters.remove(0));
		}
		getscore();
		notifyObservers();
	}
	
	/*
	 * Define this method so that it returns a value indicating the status of the most recent guess.
	 * A return value of 1 indicates that the last action was to submit a guess, and that guess was
	 * a valid word.
	 * A return value of -1 indicates that the last action was to submit a guess, and that guess was
	 * NOT a valid word.
	 * A return value of 0 indicates that the last action was something other than to submit a guess
	 * (e.g. move a letter from the inventory to the guess or vice versa, clear, or shuffle).
	 */
	public int lastSubmittedWordWasValid() {
		if (submitted&&checkGuess(word)) {
			return 1;
		}else if(!submitted) {
			return 0;
		}else {
		return -1;
	}}
	
	/*
	 * Define this method so that it returns an ArrayList<String> suitable for use by the GUI to display 
	 * the current state of the game.  For example, if words to be found are:
	 * 		saw
	 * 		saws
	 * 		was
	 * and if none of the words have been guessed yet, this method must return an Array list consisting of
	 * Strings made up of dashes corresponding to the number of letters each word, as in ["---","---","----"]
	 * 
	 * All 3-letter words must become before all 4-letter words, which come before 5 letter words, and so on.
	 * Words with same length must be arranged alphabetically.
	 * 
	 * For example, if the first word found is "was" the ArrayList returned must be ["---","was","----"] whereas
	 * if the first word found is "saw" the the ArrayList returned must be ["saw","---","----"]. 
	 * 
	 * Hint: make use of filterWordsForLength
	 * Hint: you can use the substring method of the String class to create strings of dashes of the right length
	 */
	public ArrayList<String> words() {
		ArrayList<String> words = new ArrayList<>();
		String y="-------";
		for(String x: _wordsToFind.keySet()) {
			words.add(x);
		}
		ArrayList<String> wor = new ArrayList<>();
		for(int i=3; i<=7;i++) {
			ArrayList<String>word=filterWordsForLength(words,i);
			for(String x:word) {
				if (_wordsToFind.get(x)) {
					wor.add(x);
				}else {
					wor.add(y.substring(0, i));
				}
			}}
		ArrayList<String> w = new ArrayList<>();
		if(wor.size()>=MaxWordPerTurn) {
		for(int i=0; i<MaxWordPerTurn; i++) {
			w.add(wor.get(i));
		}}else {
			for(String x: wor) {
				w.add(x);
			}
		}

		return w;
	}
	
	public ArrayList<String> wordss(){
		ArrayList<String> words = new ArrayList<>();
		for(String x:_wordsToFind.keySet()) {
			words.add(x);
		}	
		ArrayList<String> wor = new ArrayList<>();
		for(int i=3; i<=7;i++) {
			ArrayList<String>word=filterWordsForLength(words,i);
			for(String x:word) {
				wor.add(x);
			}
		}
		return wor;
	}
	
	/*
	 * Define this method so that it constructs a String consisting of the Characters in the given ArrayList.
	 * 
	 * For example, given the ArrayList<Character> that prints as [W, i, l, m, a] this method must return the
	 * String "Wilma". 
	 * 
	 * This is a companion method to string2charList, defined below for part 1.
	 */
	public String charList2string(ArrayList<Character> list) {
		String y="";
		for(Character x: list) {
			y=y+x;
		}
		return y;
	}

	/** PART 3 **/
	/** METHODS YOU ARE GIVEN **/
	
	public void moveLetterFromInventoryToGuess(int index) {
		_guessLetters.add(_inventoryLetters.remove(index));
		submitted=false;
		notifyObservers();		
	}
	
	public void moveLetterFromGuessToInventory(int index) {
		_inventoryLetters.add(_guessLetters.remove(index));
		submitted=false;
		notifyObservers();		
	}
	
	public ArrayList<Character> getInventoryLetters() { return _inventoryLetters; }
	public ArrayList<Character> getGuessLetters() { return _guessLetters; }
	
	public void addObserver(Observer obs) {
		_observers.add(obs);
		notifyObservers();
	}

	public void notifyObservers() {
		for (Observer obs : _observers) {
			obs.update();
		}
	}

	/** PART 2 **/
	
	/* QUESTION 1
	 * 
	 * The constructor
	 * 
	 * The job of the constructor is to assign sensible initial values to each instance variable.
	 * To _words it should assign the value returned by readDictionaryFromFile, with the filename passed in as argument
	 * To _seedWords it should assign the value returned by filterWordsForLength,  with _words and the maximum word length passed in as arguments
	 * To _wordsToFind it should assign the value null.
	 * 
	 * @param filename - the name of a file of words (a "dictionary file")
	 */
	public Model(String filename, String Jsonfile) {
		_words = readDictionaryFromFile(filename, Jsonfile);
		_seedWords = filterWordsForLength(_words, MAXIMUM_WORD_LENGTH);
		_observers = new ArrayList<Observer>();
		_wordsToFind = new HashMap<>();
		word = "";
		submitted=false;
	}

	/* QUESTION 2
	 * 
	 * This method reads the words from the file specified by filename and returns an ArrayList<String> containing all the words from that file.
	 * 
	 * @param filename - the name of a file of words (a "dictionary file")
	 * @return an ArrayList<String> containing words
	 */
	public ArrayList<String> readDictionaryFromFile(String filename, String Jsonfile) {
		ArrayList<String> answer = new ArrayList<String>();
		String obj = "";
        try {
        		for (String x:Files.readAllLines(Paths.get(Jsonfile))) {
        			obj += x;
        		}
        }catch(IOException e) {
        		e.printStackTrace();
        }
        JsonObject json = Json.parse(obj).asObject();
        MINIMUM_WORD_LENGTH=json.get("minWordLength").asInt();
        MAXIMUM_WORD_LENGTH=json.get("mxnWordLength").asInt();
        font=json.get("fontSize").asInt();
        MaxWordPerTurn=json.get("maxWordPerTurn").asInt();
        
        
		try {
			for (String word : Files.readAllLines(Paths.get(filename))) {
				if (word.length() >= MINIMUM_WORD_LENGTH && word.length() <= MAXIMUM_WORD_LENGTH) {
					answer.add(word);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}
	
	/* QUESTION 3
	 * 
	 * Generates the set of words that can the player needs to find, based on the given seed.
	 * Creates a new HashMap<String,Boolean>, assigns it to _wordsToFine, and enters each such
	 * word into the map, paired with the boolean value false (since none of these words have
	 * yet been found by the player).
	 * 
	 * @param seed - the word whose letters make up the inventory of available letters in the game
	 */
	public void generateWordsToFind(String seed) {
		_wordsToFind = new HashMap<String,Boolean>();
		for (String word : possibleWords(seed, _words)) {
			_wordsToFind.put(word, false);
		}
	}
		
	/* QUESTION 4
	 * 
	 * Checks whether the guess is a one of the words to be found.  If so, updates that word's entry
	 * in _wordsToFind so it is paired with true rather than false; the method also returns true in
	 * this case.  If not _wordsToFind is not updated and the method returns false.
	 * 
	 * @param guess - the String being checked to see whether it is one of the words to be found
	 * @return true if guess is a word to be found, false otherwise
	 */
	public boolean checkGuess(String guess) {
		for (String s : _wordsToFind.keySet()) {
			if (s.equals(guess)) {
				_wordsToFind.put(guess, true);
				return true;
			}
		}
		return false;
	}

	/** PART 1 **/

	/*                                                                                                                                                                                                                                      
	 * The game will use a dictionary of words.                                                                                                                                                                                             
	 *                                                                                                                                                                                                                                      
	 * The 'starter words' are all supposed to be of a certain length.                                                                                                                                                                      
	 * In the TextTwist2 game the 'starter words' are all of length 7.                                                                                                                                                                      
	 *                                                                                                                                                                                                                                      
	 * Write a method that takes in an ArrayList<String> called list and                                                                                                                                                                    
	 * an int named length and returns a new ArrayList<String> containing                                                                                                                                                                   
	 * the members of list that contain exactly length characters.                                                                                                                                                                          
	 *                                                                                                                                                                                                                                      
	 * For example, is dictionary is an ArrayList<String>, then calling                                                                                                                                                                     
	 *                                                                                                                                                                                                                                      
	 *     filterWordsForLength(dictionary, 7)                                                                                                                                                                                              
	 *                                                                                                                                                                                                                                      
	 * will produce an ArrayList<String> of seven-letter words.                                                                                                                                                                             
	 *                                                                                                                                                                                                                                      
	 */
	public ArrayList<String> filterWordsForLength(ArrayList<String> words, int length) {
		ArrayList<String> answer = new ArrayList<String>();
		for(int i = 0; i < words.size(); i++) {
			if(words.get(i).length()==length) {
				answer.add(words.get(i));
			}
		}
		return answer;
	}

	/*                                                                                                                                                                                                                                      
	 * This method accepts a String as input and returns an ArrayList<Character> consisting of                                                                                                                                              
	 * the characters from the String word.                                                                                                                                                                                                 
	 *                                                                                                                                                                                                                                      
	 * N.B. Character is a new type for us.  A Character represents a single character from a String.                                                                                                                                       
	 *                                                                                                                                                                                                                                      
	 * Note that word.charAt(i) returns a value that can be directly added to an ArrayList<Character>                                                                                                                                       
	 * using the add method of the ArrayList<Character>.                                                                                                                                                                                    
	 *                                                                                                                                                                                                                                      
	 * For example, string2charList("Wilma") must yield the ArrayList<Character> that prints as [W, i, l, m, a]                                                                                                                             
	 *                                                                                                                                                                                                                                      
	 */
	public ArrayList<Character> string2charList(String word) {
		ArrayList<Character> list = new ArrayList<Character>();
		for(int i = 0; i < word.length(); i++) {
			list.add(word.charAt(i));
		}
		return list;
	}

	/*                                                                                                                                                                                                                                      
	 * This method determines whether or not a given String is an anagram of some subset of the                                                                                                                                             
	 * letters in the ArrayList<Character>.                                                                                                                                                                                                 
	 *                                                                                                                                                                                                                                      
	 * See:                                                                                                                                                                                                                                 
	 *              http://www.dictionary.com/browse/anagram                                                                                                                                                                                
	 *                                                                                                                                                                                                                                      
	 * The basic idea here is that we'll loop through each character in word, and remove each word from                                                                                                                                     
	 * the ArrayList<Character>.  The remove method of the ArrayList removes ONE occurrence from the                                                                                                                                        
	 * list.                                                                                                                                                                                                                                
	 *                                                                                                                                                                                                                                      
	 * Example:  Suppose list is the ArrayList<String> that prints as [b, o, o, k, k, e, e, p, e, r]                                                                                                                                        
	 * then list.remove('e') changes list to [b, o, o, k, k, e, p, e, r].                                                                                                                                                                   
	 * Calling list.remove('e') again changes list to [b, o, o, k, k, p, e, r].                                                                                                                                                             
	 *                                                                                                                                                                                                                                      
	 * The remove method returns a boolean value.  If the call changes the contents of the ArrayList the                                                                                                                                    
	 * method returns true.  If calling the method does not change the ArrayList then the method                                                                                                                                            
	 * returns false.                                                                                                                                                                                                                       
	 *                                                                                                                                                                                                                                      
	 * HINT: because this method will remove characters from ArrayList<Character> it is working with,                                                                                                                                       
	 * it is important to make a copy of what's in reference before using it.  Write a loop that copies                                                                                                                                     
	 * the contents of reference to a new ArrayList<Character>.                                                                                                                                                                             
	 *                                                                                                                                                                                                                                      
	 */
	public boolean anagramOfLetterSubset(String word, ArrayList<Character> reference) {
		ArrayList<Character> initial = string2charList(word);
		ArrayList<Character>compare = new ArrayList<Character>();
		for (int i=0; i<reference.size(); i=i+1) {
			compare.add(reference.get(i));
		}
		for(int i = 0; i < initial.size(); i++) {
			if(!compare.remove(initial.get(i))) {
				return false;
			}
		}
		return true;  // change the value returned                                                                                                                                                                                      
	}

	/*                                                                                                                                                                                                                                      
	 * This method takes a word (a String) and a dictionary of words (an ArrayList<String>) and returns                                                                                                                                     
	 * a collection of words (a HashSet<String>) that are anagrams of some subset of the letters in word.                                                                                                                                   
	 *                                                                                                                                                                                                                                      
	 * Put another way, this method finds all the words or length at least 2 that can be played from the                                                                                                                                    
	 * letters in word.                                                                                                                                                                                                                     
	 *                                                                                                                                                                                                                                      
	 * HashSet is a collection that, for our purposes in this homework, behaves like an ArrayList with                                                                                                                                      
	 * the following exception:                                                                                                                                                                                                             
	 *              calling add(X) on a HashSet adds X only if X is not already in the collection                                                                                                                                           
	 * In other words, HashSet does not allow duplicate entries.  Because HashSet does not allow duplicates                                                                                                                                 
	 * we get unique words in the result.                                                                                                                                                                                                   
	 *                                                                                                                                                                                                                                      
	 * HINT: in defining this method you should find a natural use for both string2charList and also                                                                                                                                        
	 * anagramOfLetterSubset.                                                                                                                                                                                                               
	 */
	public HashSet<String> possibleWords(String word, ArrayList<String> dictionary) {
		HashSet<String> words = new HashSet<String>();
		for(int i = 0; i < dictionary.size();i++) {
			if(anagramOfLetterSubset(dictionary.get(i),string2charList(word))){
				words.add(dictionary.get(i));
			}
		}
		return words;
	}

}
