class ClassBody
{
	FieldVarList fieldVarList; // value is null if <filed var list> is empty.

	ClassBody(FieldVarList f)
	{
		fieldVarList = f;
	}

	void semanticCheck()
	{
		if ( fieldVarList != null )
			fieldVarList.semanticCheck();
	}
}