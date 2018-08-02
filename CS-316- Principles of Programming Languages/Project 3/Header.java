class Header
{
	FunName funName;
	ParameterList parameterList; // value is null if <parameter list> is empty.

	Header(FunName f, ParameterList p)
	{
		funName = f;
		parameterList = p;
	}

	void semanticCheck()
	{
		funName.semanticCheck();
		if ( parameterList != null )
			parameterList.semanticCheck();
	}
}