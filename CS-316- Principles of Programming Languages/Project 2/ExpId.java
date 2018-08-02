
class ExpId extends Exp{
	
	String id;
	
	ExpId(String s){
		id = s;
	}
	
	void printParseTree(String indent){
		super.printParseTree(indent);
		String indent1 = indent + " ";
		LexAnalyzer.displayln(indent1 + indent1.length() + " " + id);
	}
}
