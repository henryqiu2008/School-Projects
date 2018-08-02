import java.util.*;

class ClassName
{
	Id id;

	ClassName(Id i)
	{
		id = i;
	}

	void semanticCheck()
	{
		String className = id.id;
		if ( SemanticChecker.classSymbolTable.containsKey(className) )
		{
			IO.displayln("Error: class name "+className+" already declared");
			int duplicateClassNumber = SemanticChecker.duplicateClassNumbers.get(className);
			duplicateClassNumber++;
			SemanticChecker.duplicateClassNumbers.put(className, duplicateClassNumber);
			className = className+"("+duplicateClassNumber+")";
		}
		else
			SemanticChecker.duplicateClassNumbers.put(className, 1);
		SemanticChecker.currentClassName = className;
		SemanticChecker.currentFieldVarList = new LinkedList<String>();
		SemanticChecker.classSymbolTable.put(className, SemanticChecker.currentFieldVarList);
	}
}