class FunDefList
{
	FunDef funDef;
	FunDefList funDefList; // value can be null
	
	FunDefList(FunDef f, FunDefList fl)
	{
		funDef = f;
		funDefList = fl;
	}

	void semanticCheck()
	{
		funDef.semanticCheck();
		if ( funDefList != null )
			funDefList.semanticCheck();
	}
}