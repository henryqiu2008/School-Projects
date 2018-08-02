
class FieldVarList {

	String fieldVar;
	FieldVarList fieldVarList;
	
	FieldVarList(String s, FieldVarList fvl) {
		// TODO Auto-generated constructor stub
		
		fieldVar = s;
		fieldVarList = fvl;
		
	}

	void printParseTree(String indent) {
		// TODO Auto-generated method stub
		
		String indent1 = indent + " ";
		
		LexAnalyzer.displayln(indent + indent.length() + " <field var list>");
		LexAnalyzer.displayln(indent1 + indent1.length() + " <field var> " + fieldVar);
		fieldVarList.printParseTree(indent1);
		
	}

}
