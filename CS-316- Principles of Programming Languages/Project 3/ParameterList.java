class ParameterList
{
	Parameter parameter;
	ParameterList parameterList; // value can be null
	
	ParameterList(Parameter p, ParameterList pl)
	{
		parameter = p;
		parameterList = pl;
	}

	void semanticCheck()
	{
		parameter.semanticCheck();
		if ( parameterList != null )
			parameterList.semanticCheck();
	}
}