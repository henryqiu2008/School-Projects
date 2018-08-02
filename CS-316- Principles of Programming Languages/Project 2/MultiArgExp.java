
class MultiArgExp extends FunExp{
	
	void printParseTree(String indent){
		
		super.printParseTree(indent);
		String indent2 = indent + "  ";
		LexAnalyzer.displayln(indent2 + indent2.length() + " <multi argument exp>");
		
	}
}
