
class ParameterList {
	
	String parameter;
	ParameterList parameterList;

	ParameterList(String p, ParameterList pl) {
		// TODO Auto-generated constructor stub
		
		parameter = p;
		parameterList = pl;
		
	}
	
	void printParseTree(String indent) {
		// TODO Auto-generated method stub
		
		String indent1 = indent + " ";
		
		LexAnalyzer.displayln(indent + indent.length() + " <parameter list>");
		//LexAnalyzer.displayln(indent1 + indent1.length() + " <parameter> " + parameter);
		//parameterList.printParseTree(indent1);
		
		ParameterList p = this;

		do
		{
			LexAnalyzer.displayln(indent1 + indent1.length() + " " + p.parameter);
			p = p.parameterList;
		} while ( p != null );
		
	}

}
