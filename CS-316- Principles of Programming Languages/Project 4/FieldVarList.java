class FieldVarList
{
	FieldVar fieldVar;
	FieldVarList fieldVarList; // value can be null
	
	FieldVarList(FieldVar f, FieldVarList fl)
	{
		fieldVar = f;
		fieldVarList = fl;
	}

	void semanticCheck()
	{
		fieldVar.semanticCheck();
		if ( fieldVarList != null )
			fieldVarList.semanticCheck();
	}
}