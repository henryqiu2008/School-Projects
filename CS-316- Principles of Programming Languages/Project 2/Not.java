
class Not extends FunExp{

	Exp exp;
	
	Not(Exp e){
		
		exp = e;
	}
	
	void printParseTree(String indent){
		
		super.printParseTree(indent);
		String indent3 = indent + "   ";
		LexAnalyzer.displayln(indent3 + indent3.length() + " not");
		exp.printParseTree(indent3);
		
	}
	
}
