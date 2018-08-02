import java.util.*;

public abstract class Interpreter extends Parser
{
	public static HashMap<String, Val> varState = new HashMap<String, Val>(); 
	              // program state, i.e., virtual memory for variables
	public static HashMap<String, Val> expression = new HashMap<String, Val>();
				// one containing function body expressions indexed by function names, used to extract Exp to compute Eval(Exp, É¿)
	
	public static void main(String argv[])
	{
		// argv[0]: input file containing the function formulas
		// argv[1]: output file displaying the errors in definitions
		// argv[2]: input file containing expressions to be evaluated
		// argv[3]: output file for second input file, should display errors
		// result of second input file should be printed on terminal
		setIO( argv[0], argv[1] );
		setLex();

		getToken();
		Program program = program();
		if ( ! t.isEmpty() )
			displayln(t + " : Syntax Error, unexpected symbol where id expected");
		else if ( ! errorFound )
		{
			System.out.println("got past here");
			program.printParseTree("");       // print the parse tree in linearly indented form in argv[1] file
			program.M(varState);              // interpret the assignment list
			System.out.println(varState.toString()); // print the program state on the terminal
			
			closeIO();

			setIO( argv[2], argv[3]);
			setLex();
			
			getToken();
			
			Exp exp = exp(); // calls Exp class since we only did the Eval functions for the expressions and not the classes
			if( ! t.isEmpty())
				displayln(t + " : Syntax Error, unexpected symbol where id expected");
			else if ( ! errorFound )
			{
			
				System.out.println("Reached part 2.");
				
				
				exp.Eval(expression); //evalutes expressions inside hashmap
			
				//displayln("Expression: " + expression + "result = " + value.toString());
				System.out.println("Expression: " + expression + " result = " + exp.toString());
				
			}
		}

		closeIO();
	}
}