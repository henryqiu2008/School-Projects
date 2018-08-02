
class Lt extends CompExp {

	ExpList expList;
	
	Lt(ExpList el) {
		// TODO Auto-generated constructor stub
		
		expList = el;
		
	}
	
	void printParseTree(String indent){
		
		super.printParseTree(indent);
		String indent3 = indent + "   ";
		LexAnalyzer.displayln(indent3 + indent3.length() + " <");
		expList.printParseTree(indent3);
		
	}
	
}
