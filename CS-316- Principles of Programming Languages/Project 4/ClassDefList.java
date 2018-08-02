class ClassDefList
{
	ClassDef classDef;
	ClassDefList classDefList; // value can be null
	
	ClassDefList(ClassDef c, ClassDefList cl)
	{
		classDef = c;
		classDefList = cl;
	}

	void semanticCheck()
	{
		classDef.semanticCheck();
		if ( classDefList != null )
			classDefList.semanticCheck();
	}
}