class ClassDef
{
	ClassName className;
	ClassBody classBody;

	ClassDef(ClassName cn, ClassBody cb)
	{
		className = cn;
		classBody = cb;
	}

	void semanticCheck()
	{
		className.semanticCheck();
		classBody.semanticCheck();
	}
}