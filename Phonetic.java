public class Phonetic {
	public void Phonetic() {
	}

	public boolean check (StringBuffer s) {
	  // Load Sound -> Spellings
		// Read in first token.  Store in ternary trie.  Store all possible
		//   combinations in a single string at the end node (no change in
		//   Spell.java code)
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

	public static void main (String[] argv) {
	  Phonetic p = new Phonetic();
	}
}
