
class Add extends ArithExp {
	
	ExpList expList;
	
	Add(ExpList el){
		
		expList = el;
		
	}
	
	void printParseTree(String indent){
		
		super.printParseTree(indent);
		String indent3 = indent + "   ";
		LexAnalyzer.displayln(indent3 + indent3.length() + " +");
		expList.printParseTree(indent3);
		
	}
	
}
