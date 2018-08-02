class FieldVar
{
	Id id;

	FieldVar(Id i)
	{
		id = i;
	}

	void semanticCheck()
	{
		String fieldVar = id.id;
		if ( SemanticChecker.currentFieldVarList.contains(fieldVar) )
		{
			IO.displayln("Error: field variable "+fieldVar+" already declared in class "+SemanticChecker.currentClassName);
			SemanticChecker.semanticErrorFound = true;
		}
		SemanticChecker.currentFieldVarList.add(fieldVar);
	}
}