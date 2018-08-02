import java.util.*;

class FunName
{
	Id id;

	FunName(Id i)
	{
		id = i;
	}

	void semanticCheck()
	{
		String funName = id.id;
		if ( SemanticChecker.functionSymbolTable.containsKey(funName) )
		{
			IO.displayln("Error: function name "+funName+" already declared");
			int duplicateFunNumber = SemanticChecker.duplicateFunNumbers.get(funName);
			duplicateFunNumber++;
			SemanticChecker.duplicateFunNumbers.put(funName, duplicateFunNumber);
			funName = funName+"("+duplicateFunNumber+")";
			SemanticChecker.semanticErrorFound = true;
		}
		else
			SemanticChecker.duplicateFunNumbers.put(funName, 1);
		SemanticChecker.currentFunName = funName;
		SemanticChecker.currentParameterList = new LinkedList<String>();
		FunData funData = new FunData();
		funData.parameterList = SemanticChecker.currentParameterList;
		SemanticChecker.functionSymbolTable.put(funName, funData);
	}
}