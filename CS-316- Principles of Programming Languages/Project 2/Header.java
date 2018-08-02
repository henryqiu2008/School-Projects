
class Header {

	String funName;
	ParameterList parameterList;
	
	Header(String s, ParameterList pl){
		
		funName = s;
		parameterList = pl;
	}
	
	void printParseTree(String indent) {
		// TODO Auto-generated method stub
		
		String indent1 = indent + " ";
		String indent2 = indent1 + " ";
		LexAnalyzer.displayln(indent + indent.length() + " <header>");
		LexAnalyzer.displayln(indent1 + indent1.length() + " <fun name>");
		if ( parameterList != null )	
			parameterList.printParseTree(indent2);
	}

}
