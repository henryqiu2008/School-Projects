import java.util.*;

public abstract class Interpreter extends SemanticChecker
{
	public static void main(String argv[])

	/* 
	   argv[0]: source program file containing class/function definitions
	   argv[1]: lexical/syntactical error messages for the source program in argv[0]
	   argv[2]: single expression to be evaluated
	   argv[3]: lexical/syntactical error messages for the expression in argv[2]
	 
	   The evaluation result and runtime errors will be displayed on the terminal.
	*/

	{
		setIO( argv[0], argv[1] );
		setLex();

		getToken();
		Program program = program(); // build a parse tree
		if ( ! t.isEmpty() )
			displayln(t + " : Syntax Error, unexpected symbol");
		else if ( ! syntaxErrorFound )
		{
			program.semanticCheck();

			displayln("\nClass names and their field variables are displayed below.\n"+
			          "Duplicate class names are suffixed by duplicate numbers.\n");

			Set<Map.Entry<String, LinkedList<String>>> classSymbolSet = classSymbolTable.entrySet();
			for ( Map.Entry<String, LinkedList<String>> entry: classSymbolSet )
 				displayln(entry.toString());

			displayln("\nFunction names and their parameters are displayed below.\n"+
			          "Duplicate function names are suffixed by duplicate numbers.\n");

			Set<Map.Entry<String, FunData>> functionSymbolSet = functionSymbolTable.entrySet();
			for ( Map.Entry<String, FunData> entry: functionSymbolSet )
 				displayln(entry.toString());

			if ( ! semanticErrorFound )
			{
				closeIO();
				setIO( argv[2], argv[3] );
				getToken();
				Exp exp = exp(); // build a parse tree of the expression to be evaluated
				if ( ! t.isEmpty() )
					displayln(t + " : Syntax Error, unexpected symbol");
				else if ( ! syntaxErrorFound )
				{
					Val v = exp.Eval(new HashMap<String,Val>());  // evaluate the given expression
					if ( v != null )
						System.out.println( v.toString() );   // print the value on the terminal
				}				
			}
		}
		
		closeIO();
	}
}