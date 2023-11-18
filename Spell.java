/* Class Spell.  A spelling checker based on some ideas from
     Automatic Spelling Correction in Scientific and Scholarly Text
		 by Joseph J. Polluck and Antonio Zamora
		 Communications of the ACM, April 1984, Volume 27, Number 4
		 Pages 358-368.
*/

import java.io.*;
import java.util.Vector;
import util.TernaryTree;

//import TernaryTreeExtended;

final class Spell {
	// Do this the hard, brute-force way first.
	// Later reimplent functionality as a function of the double trie.

	// Note '&' & ' ' in the following decl.
	private final String ALPHABET="abcdefghijklmnopqrstuvwxyz& ";
	private static Vector corrections = new Vector();  // List of potential corrections
	private TernaryTree Dictionary = new TernaryTree();
	private TernaryTree Sounds = new TernaryTree();
	private TernaryTree Spellings = new TernaryTree();
	private TernaryTreeExtended Soundex = new TernaryTreeExtended();
	
	public Spell() {  // Constructor
		// Load the tree with the dictionary
		Dictionary.loadWords("/usr/share/dict/words");  // Include this file in dist?
		//Dictionary.loadWords("words");  // testing only
		// Probably using a double trie?
		// creating either a skeleton key offset, omission key, or soundex key
	}

	public void phonetic_check() {
	  // Load Sound -> Spellings
     FileReader r;
     BufferedReader br;
     StreamTokenizer in;
     String token;
     TernaryLeaf temp;
		 String sound;
     try {
       r = new FileReader(file);
       br = new BufferedReader(r);  // For efficiency!

/*     // This isn't any faster, just an alternative

       // read tokens by readline
       while ((token = br.readLine() ) != null) {
	 insertstr = token;
         root = insert(token);
       }   
*/

     // read tokens by StreamTokenizer class
       r = new FileReader(file);
       br = new BufferedReader(r);  // For efficiency!
       in = new StreamTokenizer(br);
       in.wordChars('&', '&'); // Add & for things like AT&T
			 in.whitespaceChars(' ', ' '); // Set to space
       while (in.nextToken() != StreamTokenizer.TT_EOF) {
			   sound=in.sval; // Sound to be stored
				 in.nextToken(); // Get second part (spellings of sound)
         insertstr = in.sval;	// Insert spellings as node of sound 
				 // actually do insert, in.sval will change
	 			 // thats why we need to store insertstr.
	       root = Sounds.insert(in.sval); 
       }
     }
     catch (FileNotFoundException e) {
       System.err.println("Didn't find dictionary: "+file+"!");
       System.err.println(e.getMessage() + "\nThe stack trace is:");
       e.printStackTrace();
     }
     catch (IOException e) {
       System.err.println(e.getMessage() + "\nThe stack trace is:");
       e.printStackTrace();
     }

		// Load Spellings -> Sound
		// Read in first token, Store in ternary trie.  Store all possible
		//   combinations in a single string at the end node. (see above)
		// Go through all of word and identify all phonemes.
		//   If we cannot read / identify all phonemes (because it does not
		//   exist) output an error.
	  // Go through all of word and _greedily_ identify all sub-phonemes
		//   and store in word_phonemes list.  Greedy means if there is for
		//   example an e and er, it will find er first (I think this is the best
		//   way for this to work)

		//step through entire trie of spellings to sound.  Add all possibilites
		//of matches into phoneme trie (vector?)
		
		//Identify all sub pieces of word (phonetic pieces?)
		
		//For each piece, substitute a match from possible_spellings
		
		//Do an exhaustive search of possible combinations (more than 1 phoneme)

		// Search in dictionary for word

		// If word is found, add it to list to show user as a possible
		// replacement.


		// For each sub-phoneme, replace it with each one of the possible
		//   replacements, and check in the dictionary to see if it's there
		// If the word is there, add it to possible corrections and return to
		//   next word / phoneme set.
		// Do we place higher value on consonants?  And if so, how do we do
		// weighting ?
		// What kind of stopping mechanism / limits do we have?  How to handle
		// more than one
	}

  StringBuffer delchar(int index, StringBuffer o) {
		int l = o.length();
		if (index > l) {
			System.err.println("Index out of range");
			System.exit(-1); // Don't continue
		}
		if (index == 0) {
			return new StringBuffer(o.toString().substring(1));
		}
		else if (index == l) {
			return new StringBuffer(o.toString().substring(0,l-1)); 
		}
		else {
			System.out.println("first sub:" + o.toString().substring(0,index));
			System.out.println("second sub:" + o.toString().substring(index+1,l));
			return new StringBuffer(o.toString().substring(0,index) + o.toString().substring(index+1,l));
		}
	}

  public boolean check(StringBuffer o) {
	  return check(o.toString());
  }

  public boolean check(String o) {  // word
    int P;
		int l;
		int m;
    
		StringBuffer w = new StringBuffer(o);  // Create StringBuffer for checks
    if (Dictionary.contains(w)) {
      return true;
		}
    else {
      // Get list of words within 50 of collating distance.
			// Handles omission case
      l = w.length();  // Save recalculation
			m = ALPHABET.length();  // Save recalculation
			// Handle omissions before last character
      for (int i = 0; i < l-1; i++) {
        for (int j = 0; j < m; j++) {
				  // FIXME look at, is it cheaper to undo insert or start over?
			    w = new StringBuffer(o);  // Reset to original word
				  w.insert(i,ALPHABET.charAt(j));	
					if (Dictionary.contains(w))
						corrections.addElement(w);
				}
			}

			// Handle omissions at last character
			for (int j = 0; j < m; j++) {
			  w = new StringBuffer(o);
				w.append(ALPHABET.charAt(j));
				if (Dictionary.contains(w))
					corrections.addElement(w);
			}

			// Handles transposition case
			char tc; // temp char
			for (int i = 0; i < l-2; i++) {  // Step through entire word
			  w = new StringBuffer(o);  // Start from known value  FIXME?(use prev.work)
			  // Swap two adjacent chars
			  tc = w.charAt(i+1);
				w.setCharAt(i+1,w.charAt(i));
				w.setCharAt(i,tc);
				if (Dictionary.contains(w))
					corrections.addElement(w);
				else { // Reverse the transpose and continue
				  // or is it faster to set w to o?
				  tc = w.charAt(i+1);
					w.setCharAt(i+1, w.charAt(i));
					w.setCharAt(i, tc);
				  continue;
				}
			}
			// Handles insertion
			w = new StringBuffer(o); // Set word to original 
			StringBuffer temp;
			for (int i = 0; i < l-1; i++) {
			  // FIXME, is it cheaper to undo or start over?
			  temp = delchar(i, w);
				if (Dictionary.contains(temp))
					corrections.addElement(temp);
			}
			// Handles substitution
			for (int i = 0; i < l-1; i++) {
				for (int j = 0; j < m; j++)  {
				  // Note, if you ever change the _new_ below, need to new the add
					// to the correction table.
			    w = new StringBuffer(o);  // Set word to original
				  w.setCharAt(i, ALPHABET.charAt(j));
					if (Dictionary.contains(w)) {
						corrections.addElement(w);
					}
					else
						continue;
				}
			}
			return false;
		}
	}

  public static void main (String argv[]) {
	  Spell s = new Spell();
		//StringBuffer w = new StringBuffer("thourough");
                StringBuffer w = new StringBuffer("laff");
                //StringBuffer w = new StringBuffer("kumputer");
		if (s.check(w)) {
		  System.out.println("The word : \""+w+"\" is spelled correctly");
		}
		else {
		  System.out.println("The word : \""+w+"\" is spelled incorrectly");
			System.out.println("Possible corrections are:");
			System.out.println(corrections);
		}
  }
}
