
class ArithExp extends MultiArgExp{

	void printParseTree(String indent) {
		// TODO Auto-generated method stub
		
		super.printParseTree(indent);
		String indent2 = indent + "  ";
		LexAnalyzer.displayln(indent2 + indent2.length() + " <arith exp>");
		
	}

}
