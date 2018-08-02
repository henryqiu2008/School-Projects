class Parameter
{
	Id id;
	
	Parameter(Id i)
	{
		id = i;
	}

	void semanticCheck()
	{
		String parameter = id.id;
		if ( SemanticChecker.currentParameterList.contains(parameter) )
		{
			IO.displayln("Error: parameter "+parameter+" already declared in function "+SemanticChecker.currentFunName);
			SemanticChecker.semanticErrorFound = true;
		}
		SemanticChecker.currentParameterList.add(parameter);
	}
}