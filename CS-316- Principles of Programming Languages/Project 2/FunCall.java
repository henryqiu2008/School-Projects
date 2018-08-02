
class FunCall extends MultiArgExp{

	String funName;
	ExpList expList;
	
	FunCall(String s, ExpList el){
		
		funName = s;
		expList = el;
		
	}
	
	void printParseTree(String indent){
		
		super.printParseTree(indent);
		String indent2 = indent + "  ";
		String indent3 = indent + "   ";
		LexAnalyzer.displayln(indent2 + indent2.length() + " <fun call>");
		LexAnalyzer.displayln(indent3 + indent3.length() + " " + funName);
		expList.printParseTree(indent3);
		
	}
	
}
