class Program
{
	ClassDefList classDefList; // value is null if <class def list> is empty.
	FunDefList funDefList;     // value is null if <fun def list> is empty.

	Program(ClassDefList c, FunDefList f)
	{
		classDefList = c;
		funDefList = f;
	}

	void semanticCheck()
	{
		if ( classDefList != null )
			classDefList.semanticCheck();
		if ( funDefList != null )
			funDefList.semanticCheck();
	}
}