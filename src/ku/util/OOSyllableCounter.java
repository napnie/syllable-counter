package ku.util;

public class OOSyllableCounter {
	private State state;
	private final State START = new StartState();
	private final State CONSONANT = new ConsonantState();
	private final State VOWEL =  new VowelState();
	private final State MULTIVOWEL = new MultivowelState();
	private final State HYPHEN = new HyphenState();
	private final State NONWORD = new NonwordState();
	private final State FINAL = new FinalState();
	private final State END = new EndState();
	
	private int syllables;
	private int index;
	private int length;
	
	/**
	 * Count syllables in word
	 * @param word - String of word that want to count syllables
	 * @return syllables of word
	 */
	public int countSyllables(String word) {
		syllables = 0;
		setState(START);
		length = word.length();
		for (index = 0 ; index < length ; index++) {
			state.handleChar( word.charAt(index) );
		}
		return syllables;
	}
	
	/**
	 * Check if char is ignore
	 * @param c - char that want to check
	 * @return true if c is ignore
	 */
	private boolean isIgnore(char c) {
		if (c == '\'' || c == ' ') return true;
		return false;
	}
	
	/**
	 * Check if char is garbage.
	 * @param c - char that want to check
	 * @return true if c is garbage
	 */
	private boolean isGarbage(char c) {
		if ( !Character.isLetter(c) && !isVowel(c) && !isHyphen(c) && !isIgnore(c) ) return true;
		return false;
	}

	/**
	 * check if char is hyphen (-)
	 * @param c - char that want to check
	 * @return true if c is hyphen
	 */
	private boolean isHyphen(char c) {
		if (c == '-') return true;
		return false;
	}

	/**
	 * Check if char is vowel.
	 * @param c - char that want to check
	 * @return true if c is vowel
	 */
	private boolean isVowel(char c) {
		char[] vowel = {'a', 'A', 'e', 'E', 'i', 'I', 'o', 'O', 'u', 'U'};
		for (char check : vowel) {
			if (c == check) return true;
		}
		return false;
	}
	
	private void setState(State state) {
		this.state = state;
	}
	
	interface CountSyllablesState {
		public void count();
	}
	
	abstract class State {
		public abstract void handleChar(char c);
		public void enterState() { /* default is to do nothing */ };
	}
	
	class StartState extends State {

		@Override
		public void handleChar(char c) {
			if ( isGarbage(c) || isHyphen(c) ) setState(NONWORD);
			else if ( isVowel(c) || c == 'y' ) {
				setState(VOWEL);
			}
			else if ( Character.isLetter(c) || isIgnore(c) ) setState(CONSONANT);
		}
		
	}
	
	class ConsonantState extends State {

		@Override
		public void handleChar(char c) {
			if (index == length-2 ) {
				if ( isVowel(c) ) {
					syllables++;
					setState(END);
				}
				else setState(FINAL);
			}
			else if ( isVowel(c) || c == 'y' ) {
				setState(VOWEL);
			}
			else if ( Character.isLetter(c) ) ;// stay consonant
			else if ( isHyphen(c) ) setState(HYPHEN);
			else if ( isGarbage(c) ) setState(NONWORD);
		}
		
	}
	
	class VowelState extends State {

		@Override
		public void handleChar(char c) {
			enterState();
			if (index == length-1 ) setState(END);
			else if ( isVowel(c) ) setState(MULTIVOWEL);
			else if ( Character.isLetter(c) || isIgnore(c) ) setState(CONSONANT);
			else if ( isHyphen(c) ) setState(HYPHEN);
			else if ( isGarbage(c) ) setState(NONWORD);
		}
		
		public void enterState() {
			syllables++;
		}
	}
	
	class MultivowelState extends State {

		@Override
		public void handleChar(char c) {
			if (index == length-1 ) setState(END);
			else if ( isVowel(c) ) ;// stay multivowel
			else if ( Character.isLetter(c) || isIgnore(c) ) setState(CONSONANT);
			else if ( isHyphen(c) ) setState(HYPHEN);
			else if ( isGarbage(c) ) setState(NONWORD);
//			setState(CONSONANT);
		}
		
	}
	
	class HyphenState extends State {

		@Override
		public void handleChar(char c) {
			if ( isVowel(c) || c == 'y' ) {
				setState(VOWEL);
			}
			else if ( Character.isLetter(c) || isIgnore(c) ) setState(CONSONANT);
			else if ( isHyphen(c) ) setState(NONWORD);
			else if ( isGarbage(c) ) setState(NONWORD);
		}
		
	}
	
	class NonwordState extends State {

		@Override
		public void handleChar(char c) {
			enterState();
		}
		
		public void enterState() {
			syllables = 0;
		}
		
	}
	
	class FinalState extends State {

		@Override
		public void handleChar(char c) {
			if ( isGarbage(c) ) setState(NONWORD);
			else if ( isVowel(c) && c != 'e' ) syllables++;
			else if ( syllables == 0 && c == 'e' ) syllables++;
			else if ( c == 'y' ) syllables++;
			else if ( Character.isLetter(c) || isIgnore(c) ) setState(END);
		}
		
	}
	
	class EndState extends State {

		@Override
		public void handleChar(char c) {
			// do nothing.
		}
		
	}
}
