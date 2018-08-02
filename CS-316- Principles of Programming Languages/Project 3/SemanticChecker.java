import java.util.*;

public abstract class SemanticChecker extends Parser
{
	static HashMap<String, LinkedList<String>> classSymbolTable = new HashMap<String, LinkedList<String>>();
		// class name mapped to the list of its field variables

	static HashMap<String, LinkedList<String>> functionSymbolTable = new HashMap<String, LinkedList<String>>();
		// function name mapped to the list of its parameter variables

	static String currentClassName;
	static HashMap<String, Integer> duplicateClassNumbers = new HashMap<String, Integer>();
		// records the current duplicate number of a class name
	static LinkedList<String> currentFieldVarList;

	static String currentFunName;
	static HashMap<String, Integer> duplicateFunNumbers = new HashMap<String, Integer>();
		// records the current duplicate number of a function name
	static LinkedList<String> currentParameterList;


	public static void main(String argv[])
	{
		// argv[0]: input file containing a program
		// argv[1]: output file displaying class names and their fields, function names and their parameters, error messages 

		setIO( argv[0], argv[1] );
		setLex();

		getToken();
		Program program = program(); // build a parse tree
		if ( ! t.isEmpty() )
			displayln(t + " : Syntax Error, unexpected symbol");
		else if ( ! errorFound )
		{
			program.semanticCheck();

			displayln("\nClass names and their field variables are displayed below.\n"+
			          "Duplicate class names are suffixed by duplicate numbers.\n");

			Set<Map.Entry<String, LinkedList<String>>> classSymbolSet = classSymbolTable.entrySet();
			for ( Map.Entry<String, LinkedList<String>> entry: classSymbolSet )
 				displayln(entry.toString());

			displayln("\nFunction names and their parameters are displayed below.\n"+
			          "Duplicate function names are suffixed by duplicate numbers.\n");

			Set<Map.Entry<String, LinkedList<String>>> functionSymbolSet = functionSymbolTable.entrySet();
			for ( Map.Entry<String, LinkedList<String>> entry: functionSymbolSet )
 				displayln(entry.toString());
		}
		
		closeIO();
	}
}
