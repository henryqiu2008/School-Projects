/**
 * 
 * This program was run using JDK 1.7.0
 * 
 * This class is a lexical analyzer for tokens defined by the grammar :
 * 	<letter> --> a|b|...|z|A|B...|Z
 * 	<digit> --> 0|1|...|9
 * 	<id> --> <letter> {<letter>|<digit>}
 * 	<int> --> [+/-] {<digit>}+
 * 	<float> --> [+/-] ( {<digit>}+ "." {<digit>} | "." {<digit>}+ )
 * 	<floatE> --> <float> (e|E) [+/-] {<digit>}+
 * 	<add> --> +
 * 	<sub> --> -
 * 	<mul> --> *
 * 	<div> --> /
 * 	<or> --> "|"
 * 	<and> --> &
 * 	<not> --> !
 * 	<lt> --> "<"
 * 	<le> --> "<="
 * 	<gt> --> ">"
 * 	<ge> --> ">="
 * 	<eq> --> "="
 * 	<LParen> --> "("
 * 	<RParen> --> ")"
 * 	<LBrace> --> "{"
 * 	<RBrace> --> "}"
 *  
 *	three new special/final states were added to this project
 *	Keywords : class, if, and null
 *	
 *	
 */


public abstract class LexicalAnalyzer extends IO
{
	public enum State 
       	{ 
	  // non-final states     ordinal number

		Start,             // 0
		Period,            // 1
		E,                 // 2
		EPlusMinus,        // 3

	  // final states

		Id,                // 4
		Int,               // 5
		Float,             // 6
		FloatE,            // 7
		Add,               // 8  +
		Sub,               // 9  -
		Mul,               // 10 *
		Div,               // 11 /
		LParen,            // 12 (
		RParen,            // 13 )
		LBrace,            // 14 {
		RBrace, 		   // 15 }
		Or,				   // 16 |
		Not,			   // 17 !
		And,			   // 18 &
		Le,			 	   // 19 <=
		Lt,				   // 20 <
		Ge,				   // 21 >=
		Gt, 			   // 22 >
		Eq,				   // 23 =
		Semicolon,		   // 24 ;

		//three extra keywords (final states) assigned in this project
		Keyword_class,
		Keyword_if,
		Keyword_null,
		
		UNDEF;

		private boolean isFinal()
		{
			return ( this.compareTo(State.Id) >= 0 );  
		}	
	}

	// By enumerating the non-final states first and then the final states,
	// test for a final state can be done by testing if the state's ordinal number
	// is greater than or equal to that of Id.

	// The following variables of "IO" class are used:

	//   static int a; the current input character
	//   static char c; used to convert the variable "a" to the char type whenever necessary

	public static String t; // holds an extracted token
	public static State state; // the current state of the FA
	 
    // This array implements the state transition function State x (ASCII char set) --> State.
    // The state argument is converted to its ordinal number used as
    // the first array index from 0 through 13. 
	
	private static String class_ = "class"; //assigns string class_ to represent "class" token
	private static String if_ = "if";		//assigns string if_ to represent "if" token
	private static String null_ = "null";	//assigns string null_ to represent "null" token

	private static int driver()

	// This is the driver of the FA. 
	// If a valid token is found, assigns it to "t" and returns 1.
	// If an invalid token is found, assigns it to "t" and returns 0.
	// If end-of-stream is reached without finding any non-whitespace character, returns -1.

	{
		State nextSt; // the next state of the FA

		t = "";
		state = State.Start;

		if ( Character.isWhitespace((char) a) )
			a = getChar(); // get the next non-whitespace character
		if ( a == -1 ) // end-of-stream is reached
			return -1;

		while ( a != -1 ) // do the body if "a" is not end-of-stream
		{
			c = (char) a;
			nextSt = nextState( state, c );
			if ( nextSt == State.UNDEF ) // The FA will halt.
			{
				if ( state.isFinal() )
					return 1; // valid token extracted
				else // "c" is an unexpected character
				{
					t = t+c;
					a = getNextChar();
					return 0; // invalid token found
				}
			}
			else // The FA will go on.
			{
				state = nextSt;
				t = t+c;
				a = getNextChar();
			}
		}

		// end-of-stream is reached while a token is being extracted

		if ( state.isFinal() )
			return 1; // valid token extracted
		else
			return 0; // invalid token found
	} // end driver

	public static void getToken()

	// Extract the next token using the driver of the FA.
	// If an invalid token is found, issue an error message.

	{
		int i = driver();
		if ( i == 0 )
			displayln(t + " : Lexical Error, invalid token");
	}

	private static State nextState(State s, char c)

	// Returns the next state of the FA given the current state and input char;
	// if the next state is undefined, UNDEF is returned.

	{
		switch( state )
		{
		case Start:
			if ( Character.isLetter(c) )
				return State.Id;
			else if ( Character.isDigit(c) )
				return State.Int;
			else if ( c == '+' )
				return State.Add;
			else if ( c == '-' )
				return State.Sub;
			else if ( c == '*' )
				return State.Mul;
			else if ( c == '/' )
				return State.Div;
			else if ( c == '(' )
				return State.LParen;
			else if ( c == ')' )
				return State.RParen;
			else if ( c == '{' )		// new conditionals to LBrace state
				return State.LBrace;
			else if ( c == '}' )		// new conditionals to RBrace state
				return State.RBrace;
			else if ( c == '|' )		// new conditionals to Or state
				return State.Or;
			else if ( c == '!' )		// new conditionals to Not state
				return State.Not;
			else if ( c == '&' )		// new conditionals to And state
				return State.And;
			else if ( c == '>' )		// new conditionals to GT state
				return State.Gt;		
			else if ( c == '<' )		// new conditionals to LT state
				return State.Lt;		
			else if ( c == '=' )		// new conditionals to EQ state
				return State.Eq;		
			else if ( c == '.' )		// new conditionals to Period state
				return State.Period;	
			else if ( c == ';' )
				return State.Semicolon; // new conditional for Semicolon State
			else
				return State.UNDEF;
		case Lt: // new LT case for <= (less than or equal to)
		    if ( c == '=' )
		    	return State.Le;
		    else
		    	return State.UNDEF;
		case Gt: // new GT case for >= (greater than or equal to)
			if ( c == '=' )
				return State.Ge;
			else
				return State.UNDEF;	
		case Id:
			if ( Character.isLetterOrDigit(c) )
				return State.Id;
			else
				return State.UNDEF;
		case Int:
			if ( Character.isDigit(c) )
				return State.Int;
			else if ( c == '.' )
				return State.Period;
			else if ( c == 'e' || c == 'E' ) //added this to the Int case to pick up any Floats
				return State.E;
			else
				return State.UNDEF;
		case Sub: // new Sub case for Floats
			if( c == '.' )
				return State.Period;
			else if( Character.isDigit(c) )
				return State.Int;
			else 
				return State.UNDEF;
		case Add: // new Add case for Floats
			if( c == '.')
				return State.Period;
			else if( Character.isDigit(c) )
				return State.Int;
			else 
				return State.UNDEF;
		case Period:
			if ( Character.isDigit(c) || c == ' ')
				return State.Float;
			else if ( c == 'e' || c == 'E' )
				return State.E;
			else
				return State.UNDEF;
		case Float:
			if ( Character.isDigit(c) )
				return State.Float;
			else if ( c == 'e' || c == 'E' )
				return State.E;
			else
				return State.UNDEF;
		case E:
			if ( Character.isDigit(c) )
				return State.FloatE;
			else if ( c == '+' || c == '-' )
				return State.EPlusMinus;
			else
				return State.UNDEF;
		case EPlusMinus:
			if ( Character.isDigit(c) )
				return State.FloatE;
			else
				return State.UNDEF;
		case FloatE:
			if ( Character.isDigit(c) )
				return State.FloatE;
			else
				return State.UNDEF;
		default:
			return State.UNDEF;

		}
	} // end nextState
	
	public static void main(String argv[])

	{		
		// argv[0]: input file containing source code using tokens defined above
		// argv[1]: output file displaying a list of the tokens 

		setIO( argv[0], argv[1] );
		
		int i;

		while ( a != -1 ) // while "a" is not end-of-stream
		{
			i = driver(); // extract the next token
			if ( i == 1 ){
				if(t.equals(class_)) //if token t is equal to class_ , initiate state as State.Keyword_class
					state = State.Keyword_class; 
				else if (t.equals(if_)) //if token t is equal to if_ , initiate state as State.Keyword_if
					state = State.Keyword_if;
				else if (t.equals(null_)) //if token t is equal to null_ , initiate state as State.Keyword_null
					state = State.Keyword_null;
				displayln( t + "   : "+state.toString());
			}
			else if ( i == 0 )
				displayln( t + " : Lexical Error, invalid token");
		} 

		closeIO();
	}
}
