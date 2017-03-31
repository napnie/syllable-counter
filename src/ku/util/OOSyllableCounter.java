package ku.util;

/**
 * Count syllables of word with O-O approach to state machine.
 * @author Nitith Chayakul
 * @version 1.0
 *
 */
public class OOSyllableCounter extends SyllableCounter {
	/** state of this object */
	private State state;
	/** Start state : current char is at the start of word */
	private final State START = new StartState();
	/** Consonant state : current char is consonant */
	private final State CONSONANT = new ConsonantState();
	/** Single Vowel state : current char is single vowel */
	private final State SINGLE_VOWEL = new SingleVowelState();
	/** Multi Vowel state : current char is multi vowel */
	private final State MULTI_VOWEL = new MultiVowelState();
	/** Hyphen state : current char is hyphen */
	private final State HYPHEN = new HyphenState();
	/** Nonword state : current char is nonword */
	private final State NONWORD = new NonwordState();
	
	/** count of syllables in word */
	private int syllables;
	/** index of char in word */
	private int index;
	/** length of word */
	private int length;
	
	/** @see SyllableCounter#countSyllables(String) */
	public int countSyllables(String word) {
		syllables = 0;
		setState(START);
		length = word.length();
		for (index = 0 ; index < length ; index++) {
			state.handleChar( Character.toLowerCase( word.charAt(index) ) );
		}
		return syllables;
	}
	
	/**
	 * Check if char is ignore
	 * @param c - char that want to check
	 * @return true if c is ignore
	 */
	private boolean isIgnore(char c) {
		if (c == '\'') return true;
		return false;
	}
	
	/**
	 * Check if char is letter
	 * @param c - char that want to check
	 * @return true if c is letter
	 */
	private boolean isLetter(char c) {
		return Character.isLetter(c) ;
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
	
	/**
	 * Set state of OOSyllableCounter.
	 * @param state - state that want to set
	 */
	private void setState(State state) {
		this.state = state;
	}
	
	/** State for OOSyllableCounter. */
	abstract class State {
		/**
		 * See if this char is syllable.
		 * @param c - char that want to check
		 */
		public abstract void handleChar(char c);
		
		/** Event when entered this state. */
		public void enterState() { /* default is to do nothing */ };
	}
	
	/** Behavior for count syllable in Start state. */
	class StartState extends State {

		/** @see State#handleChar(char) */
		@Override
		public void handleChar(char c) {
			if ( isLetter(c) && !isVowel(c) ) setState(CONSONANT);
			else if ( isVowel(c) || c == 'y' ) {
				syllables++;
				setState(SINGLE_VOWEL);
			}
			else if ( !isLetter(c) ) setState(NONWORD);
			else if ( c == ' ' ) ; // remain start
		}
		
	}
	
	/** Behavior for count syllable in Consonant state. */
	class ConsonantState extends State {

		/** @see State#handleChar(char) */
		@Override
		public void handleChar(char c) {
			if ( c == 'e' && index == length-1 && syllables != 0 ) ;
			else if ( isVowel(c) || c == 'y' ) {
				syllables++;
				setState(SINGLE_VOWEL);
			}
			else if ( isLetter(c) && !isVowel(c) || isIgnore(c) ) ; //remain consonant
			else if ( isHyphen(c) ) setState(HYPHEN);
			else if ( !isLetter(c) ) setState(NONWORD);
		}
		
	}
	
	/** Behavior for count syllable in Single Vowel state. */
	class SingleVowelState extends State {

		/** @see State#handleChar(char) */
		@Override
		public void handleChar(char c) {
			if ( isLetter(c) && !isVowel(c) || isIgnore(c) ) setState(CONSONANT);
			else if ( isVowel(c) ) setState(MULTI_VOWEL);
			else if ( isHyphen(c) ) setState(HYPHEN);
			else if ( !isLetter(c) ) setState(NONWORD);
		}
	}
	
	/** Behavior for count syllable in Multi Vowel state. */
	class MultiVowelState extends State {

		/** @see State#handleChar(char) */
		@Override
		public void handleChar(char c) {
			if ( isLetter(c) && !isVowel(c) || isIgnore(c) ) setState(CONSONANT);
			else if ( isVowel(c) ) ; //remain multivowel
			else if ( isHyphen(c) ) setState(HYPHEN);
			else if ( !isLetter(c) ) setState(NONWORD);
		}
		
	}
	
	/** Behavior for count syllable in Hyphen state. */
	class HyphenState extends State {

		/** @see State#handleChar(char) */
		@Override
		public void handleChar(char c) {
			if ( c == 'y' && index == length-1 ) syllables++;
			else if ( isVowel(c) || c == 'y' ) {
				syllables++;
				setState(SINGLE_VOWEL);
			}
			else if ( isLetter(c) && !isVowel(c) || isIgnore(c) ) setState(CONSONANT);
			else if ( c == 'e' && index == length-1 && syllables != 0) ;
			else if ( isHyphen(c) ) setState(NONWORD);
			else if ( !isLetter(c) ) setState(NONWORD);
		}
		
	}
	
	/** Behavior for count syllable in Nonword state. */
	class NonwordState extends State {

		/** @see State#handleChar(char) */
		@Override
		public void handleChar(char c) {
			enterState();
		}
		
		/** @see State#enterState() */
		@Override
		public void enterState() {
			syllables = 0;
		}
		
	}
}
