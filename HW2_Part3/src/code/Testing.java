package code;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.eclipsesource.json.*;


public class Testing {

	public static void main(String[] args) {
		
        // Instantiate the Models class to access its methods
        Model m = new Model("dictionaries/small.txt", "dictionaries/HW2.config");
        m.startNewGame();
        for(String x:m._wordsToFind.keySet()) {
        	System.out.println(x);
        }
        // Add calls to the other methods similar to the above method call to test your code
		
	}
	
}
